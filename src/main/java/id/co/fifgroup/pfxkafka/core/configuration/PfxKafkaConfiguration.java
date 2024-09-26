package id.co.fifgroup.pfxkafka.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.fifgroup.pfxkafka.common.enums.DataAggregatorEnum;
import id.co.fifgroup.pfxkafka.common.enums.PfxProcessEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableKafka
@Configuration
@EnableKafkaStreams
@EntityScan(basePackages = "id.co.fifgroup")
@ComponentScan(basePackages = "id.co.fifgroup")
@EnableJpaRepositories(basePackages = "id.co.fifgroup")
public class PfxKafkaConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @Qualifier("aggregatorProcessorMap")
    public Map<DataAggregatorEnum, List<PfxProcessEnum>> aggregatorProcessorMap() {
        Map<DataAggregatorEnum, List<PfxProcessEnum>> map = new HashMap<>();
        map.put(DataAggregatorEnum.INDIVIDUAL, composeIndividualProcessorList());
        map.put(DataAggregatorEnum.COMPANY, composeCompanyProcessorList());

        return map;
    }

    private List<PfxProcessEnum> composeIndividualProcessorList() {
        List<PfxProcessEnum> processorList = new ArrayList<>();

        return processorList;
    }

    private List<PfxProcessEnum> composeCompanyProcessorList() {
        List<PfxProcessEnum> processorList = new ArrayList<>();

        return processorList;
    }
}
