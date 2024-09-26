package id.co.fifgroup.pfxkafka.common.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class KafkaDataStreamDTO implements Serializable {
    private static final long serialVersionUID = 2157770537103855227L;

    private int dataId;
    private String fieldName;
    private String fieldType;
    private String aggregatorType;
    private String dataUUID;
}
