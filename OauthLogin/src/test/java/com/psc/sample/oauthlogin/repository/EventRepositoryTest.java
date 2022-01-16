package com.psc.sample.oauthlogin.repository;

import com.psc.sample.oauthlogin.domain.Event;
import com.psc.sample.oauthlogin.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

//    @Test
//    void test_1(){
//
////        event et = event.builder()
////                .id(0L)
////                .eventName("행사1")
////                .image("/images/eventList1.jpg")
////                .build();
//
//
//        // db 에 데이터 값 넣기
//        Event et1 = Event.builder()
//                .id(1L)
//                .eventName("행사1")
//                .image("/images/eventList1.jpg")
//                .build();
//
//        Event et2 = Event.builder()
//                .id(2L)
//                .eventName("행사2")
//                .image("/images/eventList2.jpg")
//                .build();
//
//        Event et3 = Event.builder()
//                .id(3L)
//                .eventName("행사3")
//                .image("/images/eventList3.jpg")
//                .build();
//
//        Event et4 = Event.builder()
//                .id(4L)
//                .eventName("행사4")
//                .image("/images/eventList3.jpg")
//                .build();
////        eventRepository.save(et1);
//        eventRepository.save(et4);
//        eventRepository.save(et2);
//        eventRepository.save(et3);
//    }
}