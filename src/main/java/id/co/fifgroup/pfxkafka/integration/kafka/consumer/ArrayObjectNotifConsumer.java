package id.co.fifgroup.pfxkafka.integration.kafka.consumer;

import id.co.fifgroup.pfxkafka.common.consts.KafkaTopicConstants;
import id.co.fifgroup.pfxkafka.common.enums.DataFieldTypeEnum;
import id.co.fifgroup.pfxkafka.common.model.dto.DataProcessNotifDTO;
import id.co.fifgroup.pfxkafka.service.ArrayObjectProcessorService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ArrayObjectNotifConsumer {
    public static final Logger LOGGER = LoggerFactory.getLogger(DataStreamConsumer.class);

    private ArrayObjectProcessorService arrayObjectProcessorService;


    @KafkaListener(topics = KafkaTopicConstants.KAFKA_TOPIC_PROCESS_NOTIF, groupId = "array-object-processor")
    public void process(DataProcessNotifDTO message) {
        DataFieldTypeEnum messageDataType = DataFieldTypeEnum.findByType(message.getDataType());
        if (!DataFieldTypeEnum.ARRAY.equals(messageDataType)) {
            return;
        }
        arrayObjectProcessorService.process(message);
    }

    @Autowired
    public void setArrayObjectProcessorService(ArrayObjectProcessorService arrayObjectProcessorService) {
        this.arrayObjectProcessorService = arrayObjectProcessorService;
    }
}
