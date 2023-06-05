package com.tahanebti.ffkmda;

import org.springframework.core.env.Environment;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
@EnableCaching
@Slf4j
public class FfkmdaApplication implements CommandLineRunner {

    @Autowired
    private Environment environment;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("\n----Begin logging FfkmdaApplication----");

        log.info("----System Properties from VM Arguments----");
        log.info("server.port: " + System.getProperty("server.port"));
        log.info("----Program Arguments----");
        for (String arg : args) {
            log.info(arg);
        }

        if (environment != null) {
            getActiveProfiles();
            log.info("----Environment Properties----");
            log.info("server.port: " + environment.getProperty("server.port"));
            log.info("com.tahanebti.ffkmda.environment: " + environment.getProperty("com.tahanebti.ffkmda.environment"));
            log.info("spring.datasource.url: " + environment.getProperty("spring.datasource.url"));
            log.info("spring.datasource.username: " + environment.getProperty("spring.datasource.username"));
            log.info("spring.datasource.password: " + environment.getProperty("spring.datasource.password"));
            log.info("spring.jpa.database-platform: " + environment.getProperty("spring.jpa.database-platform"));
            log.info("spring.jpa.hibernate.ddl-auto: " + environment.getProperty("spring.jpa.hibernate.ddl-auto"));
        }

        log.info("----End logging BooksServiceApplication----");
    }

    private void getActiveProfiles() {
        for (final String profileName : environment.getActiveProfiles()) {
            log.info("Currently active profile - " + profileName);
        }
    }
    
	public static void main(String[] args) {
		SpringApplication.run(FfkmdaApplication.class, args);
	}
	
}
