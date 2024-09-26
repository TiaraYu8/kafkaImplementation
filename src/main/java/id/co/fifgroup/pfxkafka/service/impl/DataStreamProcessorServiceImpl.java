package id.co.fifgroup.pfxkafka.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import id.co.fifgroup.pfxkafka.common.enums.DataFieldTypeEnum;
import id.co.fifgroup.pfxkafka.common.enums.DataAggregatorEnum;
import id.co.fifgroup.pfxkafka.common.enums.PfxProcessEnum;
import id.co.fifgroup.pfxkafka.common.holder.DataPayloadContextHolder;
import id.co.fifgroup.pfxkafka.common.model.dto.KafkaDataStreamDTO;
import id.co.fifgroup.pfxkafka.integration.kafka.publisher.DataStreamPublisher;
import id.co.fifgroup.pfxkafka.service.DataStreamProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataStreamProcessorServiceImpl implements DataStreamProcessorService {

    private DataStreamPublisher dataStreamPublisher;

    private Map<DataAggregatorEnum, List<PfxProcessEnum>> aggregatorProcessorMap;

    @Override
    public void processDataStream(JsonNode dataStream, String aggregatorType) {
        int Id = getDataIdByAggregator(dataStream, aggregatorType);
        String dataUUID = dataStream.asText("randomUUID");

        DataPayloadContextHolder.getInstance().putDataPayload(dataUUID, DataFieldTypeEnum.ARRAY.getType(), dataStream);
        DataPayloadContextHolder.getInstance().putDataPayload(dataUUID, DataFieldTypeEnum.SINGLE.getType(), dataStream);

        DataAggregatorEnum aggregatorEnum = DataAggregatorEnum.findByType(aggregatorType);
        aggregatorProcessorMap.get(aggregatorEnum).parallelStream().forEach(processor -> {
            KafkaDataStreamDTO dataStreamDTO = new KafkaDataStreamDTO();
            dataStreamDTO.setDataId(Id);
            dataStreamDTO.setDataUUID(dataUUID);
            dataStreamDTO.setFieldType(processor.getFieldType().getType());
            dataStreamDTO.setFieldName(processor.getName());
            dataStreamDTO.setAggregatorType(aggregatorType);

            dataStreamPublisher.publishMessage(dataStreamDTO);
        });
    }

    private int getDataIdByAggregator(JsonNode dataStream, String aggregatorType) {
        if (DataAggregatorEnum.INDIVIDUAL.getType().equals(aggregatorType)) {
            return dataStream.get("Body").
                    get("GetCustomReportResponse").
                    get("GetCustomReportResult").
                    get("Individual").
                    get("Identifications").
                    get("PfxId").asInt();
        }

        return dataStream.get("Body").
                get("GetCustomReportResponse").
                get("GetCustomReportResult").
                get("Company").
                get("Identifications").
                get("PfxId").asInt();
    }

    @Autowired
    public void setDataStreamPublisher(DataStreamPublisher dataStreamPublisher) {
        this.dataStreamPublisher = dataStreamPublisher;
    }

    @Autowired
    @Qualifier("aggregatorProcessorMap")
    public void setAggregatorProcessorMap(Map<DataAggregatorEnum, List<PfxProcessEnum>> aggregatorProcessorMap) {
        this.aggregatorProcessorMap = aggregatorProcessorMap;
    }
}
