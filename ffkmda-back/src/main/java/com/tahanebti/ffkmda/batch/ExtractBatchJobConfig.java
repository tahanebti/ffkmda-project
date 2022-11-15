package com.tahanebti.ffkmda.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.tahanebti.ffkmda.club.Club;
import com.tahanebti.ffkmda.club.ClubResponse;


@Configuration
public class ExtractBatchJobConfig {

    private static final String PROPERTY_REST_API_URL = "rest.api.url";
    
    @Bean
    public ItemReader<Club> itemReader(Environment environment, RestTemplate restTemplate) {
        return new RESTClubReader(environment.getRequiredProperty(PROPERTY_REST_API_URL), restTemplate);
    }
    
    @Bean
    public ItemWriter<Club> itemWriter() {
        return new LoggingItemWriter();
    }
    
	
    @Bean
    public Step exampleJobStep(ItemReader<Club> reader,
                               ItemWriter<Club> writer,
                               StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("exampleJobStep")
                .<Club, Club>chunk(1)
                .reader(reader)
                .writer(writer)
                .build();
    }
    
    @Bean
    public Job exampleJob(Step exampleJobStep,
                          JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get("exampleJob")
                .incrementer(new RunIdIncrementer())
                .flow(exampleJobStep)
                .end()
                .build();
    }
    

    
}
