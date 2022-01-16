package com.psc.sample.oauthlogin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

    private int page;
    private int size;

    public PageRequestDTO(){
        this.page = 1;
        this.size = 3; // 한페이지에 몇개의 장바구니를 보여줄지
    }

    public Pageable getPageable(Sort sort){

        return PageRequest.of(page -1 , size, sort);
    }

}
