package com.psc.sample.oauthlogin.scontroller;

import com.psc.sample.oauthlogin.document.OPSearch;
import com.psc.sample.oauthlogin.search.OPSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderProduct")
@RequiredArgsConstructor
public class OPSearchController {

    private final OPSearchService opSearchService;

    @PostMapping
    public void save(@RequestBody OPSearch opSearch){
        opSearch.setId(0L);
        opSearchService.save(opSearch);
    }

    @GetMapping("/{id}")
    public OPSearch findById(@PathVariable Long id){
        return opSearchService.findById(id);
    }

    @GetMapping("/searchForTest")
    public List<OPSearch> findByGoodName(@RequestParam(value="goodName" , required = false) String goodName){
        return opSearchService.findByGoodName(goodName);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        opSearchService.deleteById(id);
    }

    @PostMapping("/deleteAll")
    public void deleteAll(){
        opSearchService.deleteAll();
    }

    @PostMapping("/findAll")
    public List<OPSearch> findAll(){
        return opSearchService.findAll();
    }
}
