package com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.dto.PurchaseDto;
import com.api.dto.StockDto;
import com.api.dto.StockInStoreDto;
import com.api.dto.StoreAndStockDto;
import com.api.dto.StoreDto;
import com.domain.jpa.Stock;
import com.domain.jpa.Store;

public interface PostService {

	public Page<StoreDto> findAllStore(Pageable pageRequest);
	
	public List<Store> test();
	
	public List<Store> findByCategory(String category);
	
	public Optional<Store> findByRoomId(Long id);
	
	public List<StockDto> findStockByRoomId(Long StoreId);
	
	public List<Store> findStoreByKeyword(String keyword);
	
	public HashMap purchaseItem(PurchaseDto dto);
	
	public List<StockDto> findAllStock();
	
	public List<StoreAndStockDto> findStoreAndStock();
	
	public List<StoreAndStockDto> findStoreAndStockByCategory(String store_code);
	
	//제품 정보 안에 가제 정보 포함해서 제공 220811 요청으로 추가
	public List<StockInStoreDto> findStockInStore();
	
	public List<StockInStoreDto> findSeaonAndWeather();
}
