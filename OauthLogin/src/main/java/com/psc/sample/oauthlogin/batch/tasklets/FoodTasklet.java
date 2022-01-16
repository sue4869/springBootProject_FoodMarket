//package com.psc.sample.oauthlogin.batch.tasklets;
//
//import lombok.extern.log4j.Log4j2;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//
//// Step 안에서 수행될 비즈니스 로직 전략의 인터페이스
//@Slf4j
//public class FoodTasklet implements Tasklet {
//
//    @Override
//    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//
//        log.debug("executed tasklet !!");
//
//        return RepeatStatus.FINISHED;
//    }
//}
