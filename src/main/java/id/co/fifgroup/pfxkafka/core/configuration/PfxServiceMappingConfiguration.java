package id.co.fifgroup.pfxkafka.core.configuration;

import id.co.fifgroup.pfxkafka.common.enums.PfxProcessEnum;
import id.co.fifgroup.pfxkafka.service.PfxResultProcessorServiceInterface;
import id.co.fifgroup.pfxkafka.service.pfxInterface.PfxContractOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PfxServiceMappingConfiguration {

    private PfxContractOverviewService pfxContractOverviewService;


    @Autowired
    public PfxServiceMappingConfiguration(PfxContractOverviewService pfxContractOverviewService) {
        this.pfxContractOverviewService = pfxContractOverviewService;
    }

    @Bean
    @Qualifier("pfxServiceMap")
    public Map<PfxProcessEnum, PfxResultProcessorServiceInterface> pfxServiceMap() {
        Map<PfxProcessEnum, PfxResultProcessorServiceInterface> serviceMap = new HashMap<>();

        return serviceMap;
    }

}
