package com.psc.sample.oauthlogin.service;

import com.psc.sample.oauthlogin.Erepository.OPSearchRepository;
import com.psc.sample.oauthlogin.repository.OrderedProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefundService {

    private final OrderedProductRepository orderedProductRepository;
    private final OPSearchRepository opSearchRepository;

    public void deleteById(Long id) {
        orderedProductRepository.deleteById(id);
        opSearchRepository.deleteById(id);
    }
}
