package id.co.fifgroup.pfxkafka.common.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum PfxProcessEnum {
    DATA_INDIVIDU("DATA_INDIVIDU", "Individual", DataFieldTypeEnum.SINGLE),
    DATA_COMPANY("DATA_COMPANY", "Company", DataFieldTypeEnum.SINGLE),
    ;
    private final String name;
    private final String fieldName;
    private final DataFieldTypeEnum fieldType;

    PfxProcessEnum(String name, String fieldName, DataFieldTypeEnum fieldType) {
        this.name = name;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public static PfxProcessEnum findByName(String name) {
        for (PfxProcessEnum e : values()) {
            if (StringUtils.equalsIgnoreCase(e.getName(), name)) {
                return e;
            }
        }

        return null;
    }
}
