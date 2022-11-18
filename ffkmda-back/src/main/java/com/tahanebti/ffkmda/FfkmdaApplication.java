package com.tahanebti.ffkmda;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(
        exclude = {
                BatchAutoConfiguration.class,
                JmxAutoConfiguration.class
        },
        excludeName = {
                "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration",
        }
)

@EnableAutoConfiguration
@EnableBatchProcessing
@EnableScheduling
public class FfkmdaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FfkmdaApplication.class, args);
	}
	
}
