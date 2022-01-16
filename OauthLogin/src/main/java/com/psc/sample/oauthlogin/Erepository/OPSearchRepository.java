package com.psc.sample.oauthlogin.Erepository;

import com.psc.sample.oauthlogin.document.OPSearch;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface OPSearchRepository extends ElasticsearchRepository<OPSearch, Long> {
    List<OPSearch> findByGoodNameContains(String goodName , Sort sort);

    List<OPSearch> findAllByDeliveryStatus(String deliveryStatus);

    List<OPSearch> findAll();
}
