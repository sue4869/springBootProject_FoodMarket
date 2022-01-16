package com.psc.sample.oauthlogin.search;

import com.psc.sample.oauthlogin.Erepository.OPSearchRepository;
import com.psc.sample.oauthlogin.document.OPSearch;
import com.psc.sample.oauthlogin.scontroller.OPSearchController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OPSearchService {

    private final OPSearchRepository opSearchRepository;


    public void save(OPSearch opSearch) {
        opSearchRepository.save(opSearch);
    }

    public OPSearch findById(Long id) {
        return opSearchRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        opSearchRepository.deleteById(id);
    }

    public List<OPSearch> findByGoodName(String goodName) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return opSearchRepository.findByGoodNameContains(goodName, sort);
    }

    public void deleteAll() {
        opSearchRepository.deleteAll();
    }

    public List<OPSearch> findAll() {
        return opSearchRepository.findAll();
    }
}
