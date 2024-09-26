package id.co.fifgroup.pfxkafka.service;

import id.co.fifgroup.pfxkafka.common.model.dto.DataProcessNotifDTO;

public interface ArrayObjectProcessorService {
    void process(DataProcessNotifDTO processNotifDTO);
}
