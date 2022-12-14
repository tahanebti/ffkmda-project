package com.tahanebti.ffkmda.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
//@ConditionalOnBean(CacheManager.class)
@Component
public class StartupListener implements CommandLineRunner {
	
	  @Autowired
	  private JobLauncher jobLauncher;

	  @Autowired   
	  private Job job;
	
	@Override
	public void run(String... args) throws Exception {
		 log.info("Extract Batch job was started");

	        jobLauncher.run(job, newExecution());

	     log.info("Extract Batch job was stopped");
	 
	}

    private JobParameters newExecution() {
        Map<String, JobParameter> parameters = new HashMap<>();

        JobParameter parameter = new JobParameter(new Date());
        parameters.put("currentTime", parameter);

        return new JobParameters(parameters);
    }
}
