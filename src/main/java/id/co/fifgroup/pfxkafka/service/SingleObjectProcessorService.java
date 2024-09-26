package id.co.fifgroup.pfxkafka.service;

import id.co.fifgroup.pfxkafka.common.model.dto.DataProcessNotifDTO;

public interface SingleObjectProcessorService {
    void process(DataProcessNotifDTO notifDTO);
}
