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
public class PageRequestDTOforGoodDetailReview {

    private int page;
    private int size;

    public PageRequestDTOforGoodDetailReview(){
        this.page = 1;
        this.size = 5; // 한페이지에 몇개의 후기를 보여줄지
    }

    public Pageable getPageableforGoodDetailReview(Sort sort){

        return PageRequest.of(page -1 , size, sort);
    }

}
