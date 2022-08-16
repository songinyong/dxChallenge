package com.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.dto.BannerDto;
import com.api.dto.KeyWordDto;
import com.api.dto.PurchaseDto;
import com.api.dto.StockDto;
import com.api.dto.StockInStoreDto;
import com.api.dto.StoreAndStockDto;
import com.api.dto.StoreDto;
import com.domain.jpa.Store;
import com.service.BannerService;
import com.service.PostService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@CrossOrigin(origins="*")
public class PostsApiController {

	private final PostService postService;
	
	private final BannerService bannerService;



    //특정 지갑 주소를 제외한 전체 AND sell_state 기준 페이지로 출력
    @GetMapping("/store2")
    public Page<StoreDto> pageStore(@PageableDefault(size=10) Pageable pageRequest) {
    	return postService.findAllStore(pageRequest);
    }
 
    @GetMapping("/store")
    public List<Store> test() {
    	return postService.test();
    }
    
    @GetMapping("/store/{store_code}")
    public List<Store> storebycategory(@PathVariable String store_code) {
    	return postService.findByCategory(store_code);
    }
    
    @GetMapping("/storeid/{id}")
    public Optional<Store> storebyId(@PathVariable Long id) {
    	return postService.findByRoomId(id);
    } 
 
    @GetMapping("/stock")
    public List<StockDto> allStock() {
    	return postService.findAllStock();
    }  
    
    @GetMapping("/stock/{id}")
    public List<StockDto> stockbyStoreId(@PathVariable Long id) {
    	return postService.findStockByRoomId(id);
    } 
    
    @GetMapping("/search/store/{keyword}")
    public List<Store> findStoreByKeyword(@PathVariable String keyword) {
    	return postService.findStoreByKeyword(keyword);
    }
    
    @PostMapping("/search/store")
    public List<Store> findStoreByKeywordBody(@RequestBody KeyWordDto keyword) {

    	return postService.findStoreByKeyword(keyword.getKeyword());
    }
    
    @PutMapping("/purchase")
    public HashMap purchaseStock(@RequestBody PurchaseDto purchaseDto) {
    	return postService.purchaseItem(purchaseDto);
    }    
    
    @GetMapping("/store/stocks")
    public List<StoreAndStockDto> findStoreAndStock() {
    	return postService.findStoreAndStock();
    }
    
    @GetMapping("/store/stocks/{store_code}")
    public List<StoreAndStockDto> findStoreAndStockByCategory(@PathVariable String store_code) {
    	return postService.findStoreAndStockByCategory(store_code);
    }    
    
    @GetMapping("/stockin")
    public List<StockInStoreDto> findStockInStore() {
    	return postService.findStockInStore();
    }
    
    @GetMapping("/bannertest")
    public List<StockInStoreDto> findSeaonAndWeather() {
    	return postService.findSeaonAndWeather();
    }
    
    @GetMapping("/test")
    public void bringWeatherInfo() throws IOException {
    	bannerService.bringWeatherInfo();
    }
    
    @GetMapping("/banner")
    public List<BannerDto> bringBannerInfo()  {
    	return bannerService.findSeaonAndWeather();
    }

}
