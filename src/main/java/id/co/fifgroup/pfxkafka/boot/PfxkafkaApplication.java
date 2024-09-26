package id.co.fifgroup.pfxkafka.boot;

import id.co.fifgroup.pfxkafka.core.configuration.PfxKafkaConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class PfxkafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PfxKafkaConfiguration.class, args);
	}

}
