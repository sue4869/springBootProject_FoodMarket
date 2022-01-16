package com.psc.sample.oauthlogin.scontroller;

import com.psc.sample.oauthlogin.document.ProductSearch;
import com.psc.sample.oauthlogin.search.ProductSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ProductSearch")
@RequiredArgsConstructor
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    @PostMapping
    public void save(@RequestBody ProductSearch productSearch){
//        productSearch.setId(0L);
        productSearchService.save(productSearch);
    }

    @PostMapping("/list")
    public void saveAll(@RequestBody List<ProductSearch> productSearchList){
        productSearchService.saveAll(productSearchList);
    }

    @GetMapping("/{id}")
    public ProductSearch findById(@PathVariable Long id){
        return productSearchService.findById(id);
    }

//    @GetMapping("/searchForTest")
//    public List<ProductSearch> findByGoodName(@RequestParam(value = "goodName" , required = false) String goodName){
////        return productSearchService.findByGoodName(goodName);
//    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        productSearchService.deleteById(id);
    }

    @PostMapping("/deleteAll")
    public void deleteAll(){
        productSearchService.deleteAll();
    }

    @PostMapping("/findAll")
    public List<ProductSearch> findAll(){
        return productSearchService.findAll();
    }


}
