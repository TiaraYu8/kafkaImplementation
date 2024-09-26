package id.co.fifgroup.pfxkafka.integration.kafka.topology;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import id.co.fifgroup.pfxkafka.common.consts.KTableLabelConstants;
import id.co.fifgroup.pfxkafka.common.consts.KafkaTopicConstants;
import id.co.fifgroup.pfxkafka.common.enums.DataFieldTypeEnum;
import id.co.fifgroup.pfxkafka.common.holder.DataPayloadContextHolder;
import id.co.fifgroup.pfxkafka.common.model.dto.DataProcessNotifDTO;
import id.co.fifgroup.pfxkafka.common.model.dto.KafkaDataStreamDTO;
import id.co.fifgroup.pfxkafka.integration.kafka.publisher.DataProcessNotifProducer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataArrayObjectTopology {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSingleObjectTopology.class);

    private DataProcessNotifProducer dataProcessNotifProducer;

    @Autowired
    public void topologyProcess(StreamsBuilder streamsBuilder) {
        KStream<String, KafkaDataStreamDTO> dataStream = streamsBuilder
                .stream(KafkaTopicConstants.KAFKA_TOPIC_PROCESSED_DATA, Consumed.with(Serdes.String(), new JsonSerde<>(KafkaDataStreamDTO.class)))
                .selectKey((key, value) -> value.getDataUUID())
                .filter((s, kafkaDataStreamDTO) -> DataFieldTypeEnum.ARRAY.getType().equals(kafkaDataStreamDTO.getFieldType()));

        KTable<String, List<KafkaDataStreamDTO>> dataTable = dataStream.map((key, data) ->
                        KeyValue.pair(data.getDataUUID(), data))
                .groupByKey(Grouped.with(Serdes.String(), new JsonSerde<>(KafkaDataStreamDTO.class)))
                .aggregate(ArrayList::new,
                        (key, value, aggregate) -> {
                            aggregate.add(value);
                            return aggregate;
                        }, Materialized.<String, List<KafkaDataStreamDTO>, KeyValueStore<Bytes, byte[]>>as(KTableLabelConstants.KTABLE_LABEL_ARRAY_OBJECT_DATA)
                                .withKeySerde(Serdes.String())
                                .withValueSerde(new JsonSerde<>(new TypeReference<List<KafkaDataStreamDTO>>() {
                                })));

        dataTable.toStream().foreach((dataUUID, kafkaDataStreamDTOS) -> {
            DataProcessNotifDTO notifDTO = new DataProcessNotifDTO();
            KafkaDataStreamDTO dataStreamDTO = kafkaDataStreamDTOS.get(0);
            JsonNode dataPayloadFromContext = DataPayloadContextHolder.getInstance().getDataPayload(dataUUID, DataFieldTypeEnum.ARRAY.getType());

            notifDTO.setDataId(dataStreamDTO.getDataId());
            notifDTO.setDataUUID(dataUUID);
            notifDTO.setAggregatorType(dataStreamDTO.getAggregatorType());
            notifDTO.setDataType(DataFieldTypeEnum.ARRAY.getType());
            notifDTO.setDataStream(dataPayloadFromContext);

            dataProcessNotifProducer.publishMessage(notifDTO);

            DataPayloadContextHolder.getInstance().removeDataPayload(dataUUID, DataFieldTypeEnum.ARRAY.getType());
        });
    }

    @Autowired
    public void setDataProcessNotifProducer(DataProcessNotifProducer dataProcessNotifProducer) {
        this.dataProcessNotifProducer = dataProcessNotifProducer;
    }
}
