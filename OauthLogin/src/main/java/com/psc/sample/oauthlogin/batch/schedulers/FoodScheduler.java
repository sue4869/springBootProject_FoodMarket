package com.psc.sample.oauthlogin.batch.schedulers;

import com.psc.sample.oauthlogin.batch.jobs.JobConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FoodScheduler {

    private final JobConfiguration jobConfiguration;
    private final JobLauncher jobLauncher;

    // 5분마다 실행
    @Scheduled(initialDelay =180*1000L , fixedDelay = 180*1000L)
    public void executeJob(){

        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time" , new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(confMap);

        try {
            jobLauncher.run(
                    jobConfiguration.job() , jobParameters

            );
        } catch (JobExecutionException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
