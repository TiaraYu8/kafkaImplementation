package id.co.fifgroup.pfxkafka.integration.kafka.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import id.co.fifgroup.pfxkafka.common.consts.KafkaTopicConstants;
import id.co.fifgroup.pfxkafka.common.enums.DataAggregatorEnum;
import id.co.fifgroup.pfxkafka.service.DataStreamProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DataStreamConsumer {

    public static final Logger LOGGER = LoggerFactory.getLogger(DataStreamConsumer.class);

    private DataStreamProcessorService dataStreamProcessorService;

    @KafkaListener(topics = KafkaTopicConstants.KAFKA_TOPIC_PFX, groupId = "pfx-consumer")
    public void process(JsonNode jsonNode) {
        DataAggregatorEnum aggregatorType = DataAggregatorEnum.findByType(jsonNode.asText("prodSource"));
        if (aggregatorType == null) {
            LOGGER.info("{} message is filtered", KafkaTopicConstants.KAFKA_TOPIC_PFX);
            return;
        }

        dataStreamProcessorService.processDataStream(jsonNode, aggregatorType.getType());
    }

    @Autowired
    public void setDataStreamProcessorService(DataStreamProcessorService dataStreamProcessorService) {
        this.dataStreamProcessorService = dataStreamProcessorService;
    }
}
