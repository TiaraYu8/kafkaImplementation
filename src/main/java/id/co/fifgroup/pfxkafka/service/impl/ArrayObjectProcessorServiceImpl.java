package id.co.fifgroup.pfxkafka.service.impl;

import id.co.fifgroup.pfxkafka.common.consts.KTableLabelConstants;
import id.co.fifgroup.pfxkafka.common.enums.DataAggregatorEnum;
import id.co.fifgroup.pfxkafka.common.enums.PfxProcessEnum;
import id.co.fifgroup.pfxkafka.common.model.dto.KafkaDataStreamDTO;
import id.co.fifgroup.pfxkafka.common.model.dto.DataProcessNotifDTO;
import id.co.fifgroup.pfxkafka.service.ArrayObjectProcessorService;
import id.co.fifgroup.pfxkafka.service.PfxResultProcessorServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class ArrayObjectProcessorServiceImpl implements ArrayObjectProcessorService {
    private StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    private Map<PfxProcessEnum, PfxResultProcessorServiceInterface> pfxServiceMap;

    @Override
    public void process(DataProcessNotifDTO processNotifDTO) {
        ReadOnlyKeyValueStore<String, List<KafkaDataStreamDTO>> arrayObjectDataStream =
                Objects.requireNonNull(streamsBuilderFactoryBean.getKafkaStreams()).store(
                        StoreQueryParameters.fromNameAndType(KTableLabelConstants.KTABLE_LABEL_ARRAY_OBJECT_DATA,
                                QueryableStoreTypes.keyValueStore()));

        List<KafkaDataStreamDTO> dataStreamDTOS = arrayObjectDataStream.get(processNotifDTO.getDataUUID());
        dataStreamDTOS.parallelStream().forEach(dataStreamDTO -> {
            try {
                DataAggregatorEnum aggregator = DataAggregatorEnum.findByType(dataStreamDTO.getAggregatorType());
                PfxProcessEnum processorEnum = PfxProcessEnum.findByName(dataStreamDTO.getFieldName());
                if (processorEnum == null) {
                    return;
                }

                pfxServiceMap.get(processorEnum).process(processNotifDTO.getDataStream(), dataStreamDTO.getDataId(),
                        dataStreamDTO.getDataUUID());
            } catch (Exception e) {
                log.error(e.getLocalizedMessage(), e);
            }
        });
    }

    @Autowired
    public void setStreamsBuilderFactoryBean(StreamsBuilderFactoryBean streamsBuilderFactoryBean) {
        this.streamsBuilderFactoryBean = streamsBuilderFactoryBean;
    }


    @Autowired
    @Qualifier("pfxServiceMap")
    public void setPfxServiceMap(Map<PfxProcessEnum, PfxResultProcessorServiceInterface> pfxServiceMap) {
        this.pfxServiceMap = pfxServiceMap;
    }
}
