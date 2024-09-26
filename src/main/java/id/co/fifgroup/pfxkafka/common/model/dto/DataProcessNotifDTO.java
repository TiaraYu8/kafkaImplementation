package id.co.fifgroup.pfxkafka.common.model.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DataProcessNotifDTO implements Serializable {
    private static final long serialVersionUID = -2452540545116811713L;

    private int dataId;
    private String dataUUID;
    private String dataType;
    private String aggregatorType;
    private JsonNode dataStream;
}
