package id.co.fifgroup.pfxkafka.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface PfxResultProcessorServiceInterface {
    void process(JsonNode jsonNode, int pfxId, String pfxUUID);
}
