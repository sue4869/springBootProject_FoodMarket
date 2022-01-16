package com.psc.sample.oauthlogin.batch.jobs;

import com.psc.sample.oauthlogin.Erepository.OPSearchRepository;
import com.psc.sample.oauthlogin.document.OPSearch;
import com.psc.sample.oauthlogin.domain.CancelProduct;
import com.psc.sample.oauthlogin.domain.OrderedProduct;
import com.psc.sample.oauthlogin.repository.CancelProductRepository;
import com.psc.sample.oauthlogin.repository.OrderedProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    private OrderedProductRepository orderedProductRepository;
    private final CancelProductRepository cancelProductRepository;
    private final OPSearchRepository opSearchRepository;

    @Value("${spring.application.name}")
    private String instanceId;

    @Bean
    public Job job(){
        return jobBuilderFactory.get("job")
                .start(step1()) //STEP 1 실행
                .next(step2())
                .build();
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1")
                .tasklet((stepContribution, chunkContext) -> {

                    log.debug("======= 전송 배치 시작 =======");
//                    List<OrderedProduct> orderedProducts1 = orderedProductRepository.findAllByDeliveryStatus("배송완료");
//                    if(orderedProducts1.size() > 0){
//                        for(OrderedProduct op : orderedProducts1){
//
////                            op.setDeliveryStatus("배송준비");
//
////                            orderedProductRepository.delete(op);
//                        }
//                    }
//
//                    List<OPSearch> opSearchList1 = opSearchRepository.findAllByDeliveryStatus("배송완료");
//                    if(opSearchList1.size() > 0){
//                        for(OPSearch s : opSearchList1){
////                            opSearchRepository.delete(s);
//                        }
//                    }

                    List<OrderedProduct> orderedProducts2 = orderedProductRepository.findAllByDeliveryStatus("배송중");
                    if(orderedProducts2.size() > 0){
                        for(OrderedProduct op : orderedProducts2){

                            op.setDeliveryStatus("배송완료");

                            orderedProductRepository.save(op);
                        }
                    }
                    List<OPSearch> opSearchList2 = opSearchRepository.findAllByDeliveryStatus("배송중");
                    if(opSearchList2.size() > 0){
                        for(OPSearch s : opSearchList2){

                            s.setDeliveryStatus("배송완료");

                            opSearchRepository.save(s);
                        }
                    }

                    List<OrderedProduct> orderedProducts3 = orderedProductRepository.findAllByDeliveryStatus("배송준비");
                    if(orderedProducts3.size() > 0){
                        for(OrderedProduct op : orderedProducts3){

                            op.setDeliveryStatus("배송중");

                            orderedProductRepository.save(op);
                        }
                    }

                    List<OPSearch> opSearchList3 = opSearchRepository.findAllByDeliveryStatus("배송준비");
                    if(opSearchList3.size() > 0){
                        for(OPSearch s : opSearchList3){

                            s.setDeliveryStatus("배송중");

                            opSearchRepository.save(s);
                        }
                    }

                    List<OrderedProduct> orderedProducts4 = orderedProductRepository.findAllByDeliveryStatus("주문접수");
                    if(orderedProducts4.size() > 0){
                        for(OrderedProduct op : orderedProducts4){

                            op.setDeliveryStatus("배송준비");

                           orderedProductRepository.save(op);
                        }
                    }

                    List<OPSearch> opSearchList4 = opSearchRepository.findAllByDeliveryStatus("주문접수");
                    if(opSearchList4.size() > 0){
                        for(OPSearch s : opSearchList4){

                            s.setDeliveryStatus("배송준비");

                            opSearchRepository.save(s);
                        }
                    }

                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step2(){
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    List<CancelProduct> cancelProductList = cancelProductRepository.findAll();

                    for(CancelProduct CP : cancelProductList){

                        int month = CP.getRegDate().getMonthValue();
                        int nowMonth = LocalDateTime.now().getMonthValue();

                        int day = CP.getRegDate().getDayOfMonth();
                        int nowDay = CP.getRegDate().getDayOfMonth();

                        int hour = CP.getRegDate().getHour();
                        int nowHour = CP.getRegDate().getHour();

                        int minute = CP.getRegDate().getMinute();
                        int nowMinute = LocalDateTime.now().getMinute();

                        int monthDegree = month-nowMonth;
                        int dayDegree = day-nowDay;
                        int hourDegree = hour-nowHour;
                        int minuteDegree = minute-nowMinute;

                        if(monthDegree < 0 || dayDegree < 0 || hourDegree < 0 || minuteDegree <= -10){
                            cancelProductRepository.delete(CP);
                        }

                    }

                    return RepeatStatus.FINISHED;
                }).build();
    }

}
