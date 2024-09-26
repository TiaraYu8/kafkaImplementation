package id.co.fifgroup.pfxkafka.common.holder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class DataPayloadContextHolder {
    public static DataPayloadContextHolder instance = null;

    private Map<String, JsonNode> dataPayloadMap;

    public void putDataPayload(String dataUUID, String dataType, JsonNode dataPayload) {
        dataPayloadMap.put(composeKey(dataUUID, dataType), dataPayload);
    }

    public JsonNode getDataPayload(String dataUUID, String dataType) {
        if (!dataPayloadMap.containsKey(composeKey(dataUUID, dataType))) {
            return new ObjectMapper().createObjectNode();
        }

        return dataPayloadMap.get(composeKey(dataUUID, dataType));
    }

    public void removeDataPayload(String dataUUID, String dataType) {
        dataPayloadMap.remove(composeKey(dataUUID, dataType));
    }

    private String composeKey(String dataUUID, String dataType) {
        return String.format("%s-%s", dataUUID, dataType);
    }

    public static DataPayloadContextHolder getInstance() {
        if (instance == null) {
            instance = new DataPayloadContextHolder();
        }
        return instance;
    }

    private DataPayloadContextHolder() {
        dataPayloadMap = new HashMap<>();
    }
}
