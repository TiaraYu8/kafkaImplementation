package id.co.fifgroup.pfxkafka.integration.kafka.publisher;

import id.co.fifgroup.pfxkafka.common.consts.KafkaTopicConstants;
import id.co.fifgroup.pfxkafka.common.model.dto.KafkaDataStreamDTO;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataStreamPublisher {
    private KafkaTemplate<String, KafkaDataStreamDTO> kafkaTemplate;

    public void publishMessage(KafkaDataStreamDTO message) {
        ProducerRecord<String, KafkaDataStreamDTO> producerRecord = new ProducerRecord<>(
                KafkaTopicConstants.KAFKA_TOPIC_PROCESSED_DATA,
                message);

        kafkaTemplate.send(producerRecord);
    }

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, KafkaDataStreamDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
