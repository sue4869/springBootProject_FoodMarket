package com.psc.sample.oauthlogin.Erepository;

import com.psc.sample.oauthlogin.document.ProductSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductSearchRepository extends ElasticsearchRepository<ProductSearch , Long> {

//    List<ProductSearch> findByGoodNameContains(String goodName, Sort sort);

    Page<ProductSearch> findByGoodNameContains(String goodName, Pageable pageable);

    List<ProductSearch> findAll();

}
