package com.psc.sample.oauthlogin.service;

import com.psc.sample.oauthlogin.domain.CancelProduct;
import com.psc.sample.oauthlogin.repository.CancelProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CancelProductService {

    private final CancelProductRepository cancelProductRepository;


    public List<CancelProduct> findAllByUserId(Long userId) {


        return cancelProductRepository.findAllByUserId(userId);

    }

    public CancelProduct findById(Long cancelProductId) {

        return cancelProductRepository.findById(cancelProductId).get();

    }
}
