package id.co.fifgroup.pfxkafka.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface DataStreamProcessorService {
    void processDataStream(JsonNode pfxDataStream, String aggregatorType);
}
