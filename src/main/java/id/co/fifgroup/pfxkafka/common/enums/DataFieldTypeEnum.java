package id.co.fifgroup.pfxkafka.common.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum DataFieldTypeEnum {
    SINGLE("SINGLE_OBJECT"),
    ARRAY("ARRAY_OBJECT")
    ;

    DataFieldTypeEnum(String type) {
        this.type = type;
    }

    private final String type;

    public static DataFieldTypeEnum findByType(String type) {
        for(DataFieldTypeEnum e : values()) {
            if (StringUtils.equalsIgnoreCase(e.getType(), type)) {
                return e;
            }
        }

        return null;
    }
}
