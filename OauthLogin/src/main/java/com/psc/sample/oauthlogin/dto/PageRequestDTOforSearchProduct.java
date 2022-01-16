package com.psc.sample.oauthlogin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTOforSearchProduct {

    private int page;
    private int size;

    public PageRequestDTOforSearchProduct(){
        this.page = 1;
        this.size = 9; // 한페이지에 몇개의 장바구니를 보여줄지
    }

    public Pageable getPageable(Sort sort){

        return PageRequest.of(page -1 , size, sort);
    }

}
