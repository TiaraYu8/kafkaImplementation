package id.co.fifgroup.pfxkafka.integration.kafka.consumer;

import id.co.fifgroup.pfxkafka.common.consts.KafkaTopicConstants;
import id.co.fifgroup.pfxkafka.common.enums.DataFieldTypeEnum;
import id.co.fifgroup.pfxkafka.common.model.dto.DataProcessNotifDTO;
import id.co.fifgroup.pfxkafka.service.SingleObjectProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SingleObjectNotifConsumer {
    public static final Logger LOGGER = LoggerFactory.getLogger(DataStreamConsumer.class);

    private SingleObjectProcessorService singleObjectProcessorService;

    @KafkaListener(topics = KafkaTopicConstants.KAFKA_TOPIC_PROCESS_NOTIF, groupId = "single-object-processor")
    public void process(DataProcessNotifDTO message) {
        DataFieldTypeEnum messageDataType = DataFieldTypeEnum.findByType(message.getDataType());
        if (!DataFieldTypeEnum.SINGLE.equals(messageDataType)) {
            return;
        }
        singleObjectProcessorService.process(message);
    }

    @Autowired
    public void setSingleObjectProcessorService(SingleObjectProcessorService singleObjectProcessorService) {
        this.singleObjectProcessorService = singleObjectProcessorService;
    }
}
