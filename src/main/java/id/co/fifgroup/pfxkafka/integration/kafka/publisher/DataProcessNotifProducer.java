package id.co.fifgroup.pfxkafka.integration.kafka.publisher;

import id.co.fifgroup.pfxkafka.common.consts.KafkaTopicConstants;
import id.co.fifgroup.pfxkafka.common.model.dto.DataProcessNotifDTO;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataProcessNotifProducer {
    private KafkaTemplate<String, DataProcessNotifDTO> kafkaTemplate;

    public void publishMessage(DataProcessNotifDTO message) {
        ProducerRecord<String, DataProcessNotifDTO> producerRecord = new ProducerRecord<>(
                KafkaTopicConstants.KAFKA_TOPIC_PROCESS_NOTIF, message);
        kafkaTemplate.send(producerRecord);
    }

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, DataProcessNotifDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
