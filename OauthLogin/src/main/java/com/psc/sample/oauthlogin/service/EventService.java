package com.psc.sample.oauthlogin.service;

import com.psc.sample.oauthlogin.domain.Event;
import com.psc.sample.oauthlogin.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {


    private final EventRepository eventRepository;


    // HomeController 에 eventRepository 에 내장되있는 함수 findAll() 을 호출함으로써
    // events 에 값을 저장한다.
    // findImage() 를 반환값을 List<event> 로 해준다.

    public List<Event> findEvent(){


//        List<event> events = eventRepository.findAll();
//        return events;

        // 아래와 같이 바로 반환값에 리스트로 뿌려줘도되고
        // 위와같이 그냥 List<event> 로 값을 받아서 events 를 반환해줘도 된다.
        return eventRepository.findAll().stream().collect(Collectors.toList());
    }
}

