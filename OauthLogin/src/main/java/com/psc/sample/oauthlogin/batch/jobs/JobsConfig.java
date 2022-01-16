//package com.psc.sample.oauthlogin.batch.jobs;
//
//// 배치 쿼리 과정을 하나의 단위로 만들어 표현한 객체이고 여러 Step 인스턴스를 포함하는 컨테이너
//
//import com.psc.sample.oauthlogin.batch.tasklets.FoodTasklet;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@RequiredArgsConstructor
//public class JobsConfig {
//
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//
//    // JobBuilderFactory 를 통해서 jobs 생성
//    @Bean
//    public Job jobs(){
//        return jobBuilderFactory.get("tutorialJob")
//            .start(tutorialStep()) // Step 설정
//            .build();
//    }
//
//    // StepBuilderFactory 를 통해서 tutorialsStep 생성
//    @Bean
//    public Step tutorialStep() {
//        return stepBuilderFactory.get("tutorialStep")
//            .tasklet(new FoodTasklet()) // Tasklet 설정
//            .build();
//    }
//
//}
