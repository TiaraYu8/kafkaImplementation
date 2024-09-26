package id.co.fifgroup.pfxkafka.service.impl.pfx;

import com.fasterxml.jackson.databind.JsonNode;
import id.co.fifgroup.pfxkafka.service.pfxInterface.PfxContractOverviewService;
import org.springframework.stereotype.Service;

@Service
public class PfxContractOverviewServiceImpl implements PfxContractOverviewService {
    @Override
    public void process(JsonNode jsonNode, int pfxId, String pfxUUID) {

    }
}
