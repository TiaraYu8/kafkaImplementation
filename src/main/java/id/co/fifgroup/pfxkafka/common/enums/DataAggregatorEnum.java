package id.co.fifgroup.pfxkafka.common.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum DataAggregatorEnum {
    INDIVIDUAL("INDIVIDUAL"),
    COMPANY("COMPANY"),
    ;

    DataAggregatorEnum(String type) {
        this.type = type;
    }

    private final String type;

    public static DataAggregatorEnum findByType(String type) {
        for(DataAggregatorEnum e: values()) {
            if (StringUtils.equalsIgnoreCase(type, e.type)) {
                return e;
            }
        }
        return null;
    }
}
