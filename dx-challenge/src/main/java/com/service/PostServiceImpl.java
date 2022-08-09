package com.service;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.dto.PurchaseDto;
import com.api.dto.StockDto;
import com.api.dto.StoreAndStockDto;
import com.api.dto.StoreDto;
import com.domain.jpa.CodeNm;
import com.domain.jpa.Food;
import com.domain.jpa.FoodRepository;
import com.domain.jpa.Stock;
import com.domain.jpa.StockRepository;
import com.domain.jpa.Store;
import com.domain.jpa.StoreCodeRepository;
import com.domain.jpa.StoreRepository;


@Service
public class PostServiceImpl implements PostService {
	
	
	
	
	private StoreRepository storeRepository;
	private StoreCodeRepository codeRepository;
	private StockRepository stockRepository;
	private FoodRepository foodRepository;
	
	
	@Autowired
	public void setUserRepository(StoreRepository userRepository) {
	    this.storeRepository = userRepository;
	 }

	@Autowired
	public void setCodeRepository(StoreCodeRepository codeRepository) {
	    this.codeRepository = codeRepository;
	 }

	@Autowired
	public void setStockRepository(StockRepository stockRepository) {
	    this.stockRepository = stockRepository;
	 }

	@Autowired
	public void setFoodRepository(FoodRepository foodRepository) {
	    this.foodRepository = foodRepository;
	 }
	
	@Transactional
    //모든 상점 기본 정보 출략
    public Page<StoreDto> findAllStore(Pageable pageRequest) {
		
		 Page<Store> storeList = storeRepository.findAll(pageRequest);
		
		 Page<StoreDto> dtoList = storeList.map(StoreDto::new);

    	
    	return dtoList;
    }

	@Transactional
    //모든 상점 기본 정보 출략
    public List<Store> test() {
		
		 List<Store> storeList = storeRepository.findAll();
		
    	return storeList;
    }

	@Transactional
    //카테고리 코드를 받아서 출력
    public List<Store> findByCategory(String store_code) {
		
		
		Optional<CodeNm> code = codeRepository.findByCateory(store_code);
		
		if(code.isPresent()) {
			List<Store> store = storeRepository.findByCateory(code.get().getCODE_NM());
			return store;
		}
		else {
			return new ArrayList<Store>();
		}
		
		
    }
	
	@Transactional
    //특정 가게 아이디 기준 가게 정보 출력
    public Optional<Store> findByRoomId(Long id) {
		
		Optional<Store> store = storeRepository.findById(id);

    	return store;

    }
	
	
	//상점의 모든 상품 목록 출력	
	@Transactional
    public List<StockDto> findAllStock() {
		
		HashMap<Long, Food> food = new HashMap<Long, Food>();
		List<Food> food_list = foodRepository.findAll();
		

		for(Food f : food_list) 
			food.put(f.getStore_id(), f);
		
		List<Stock> stockList = stockRepository.findAll();
		List<StockDto> printStockList = new ArrayList<StockDto>();
		
		for(Stock s : stockList) 
			printStockList.add(new StockDto(food.get(s.getStore_id()), s));
		
        return printStockList;

    }

	
	
	//상점 아이디 기준 상점의 상품 목록 출력	
	@Transactional
    public List<StockDto> findStockByRoomId(Long StoreId) {
		
		
		List<Food> food_list = foodRepository.findFoodByStoreId(StoreId);
		
		HashMap<Long, Food> food = new HashMap<Long, Food>();
		for(Food f : food_list) 
			food.put(f.getId(), f);
		
		
		List<Stock> stockList = stockRepository.findStockByStoreId(StoreId);
		
		List<StockDto> printStockList = new ArrayList<StockDto>();
		
		for(Stock s : stockList) 
			printStockList.add(new StockDto(food.get(s.getFood_id()), s));
		
		
        return printStockList;

    }
	
	//모든 상점 및 판대 상품 목록 조회
	@Transactional
    public List<StoreAndStockDto> findStoreAndStock() {

		List<StoreAndStockDto> return_list = new ArrayList<StoreAndStockDto>();
		
		List<Store> storeList = storeRepository.findAll();
		
		List<Food> food_list = foodRepository.findAll();
		HashMap<Long, Food> food = new HashMap<Long, Food>();
		for(Food f : food_list) 
			food.put(f.getId(), f);
		
		for(Store s : storeList) {
			

					
			
			List<Stock> stockList = stockRepository.findStockByStoreId(s.getId());
			
			List<StockDto> printStockList = new ArrayList<StockDto>();
			
			
			for(Stock stock : stockList) {
				
				    printStockList.add(new StockDto(food.get(stock.getFood_id()), stock));	
			}
				
			
			
			return_list.add(new StoreAndStockDto(s, printStockList ) );
			
		}
		
		return return_list;

		
	}
	
	
	@Transactional
	// 키워드 기준 상점 목록 출력
	public List<Store> findStoreByKeyword(String keyword) {
		
		List<Store> storeList = storeRepository.findStoreByKeyword(keyword);
		
		return storeList ;
	}
	
	// 물건 구매
	@Transactional
	public HashMap purchaseItem(PurchaseDto dto) {
		
		HashMap map = new HashMap();
		
		Optional<Stock> stock = stockRepository.findById(dto.getStockId());
		
		if(stock.isPresent()) {
			int quantity = stock.get().getQuanity();
			
			//구매하려는 수량이 재고 수량보다 많을때
			if(dto.getAmount() > quantity) {
				map.put("result", false);
				map.put("errorCode", "P2");
				map.put("msg", "요청하신 구매 수량이 재고 수량보다 많습니다");
				return map ;
				
			}
			//구매하려는 수량이 잘못 요청되었을때
			else if(dto.getAmount() <= 0) {
				map.put("result", false);
				map.put("errorCode", "P3");
				map.put("msg", "잘못 요청된 주문입니다");
				return map ;
			}
			else {
				
				stock.get().setQuanity(quantity-dto.getAmount());
				stockRepository.save(stock.get());
				
				map.put("result", true);
				map.put("msg", "구매 완료했습니다");
				return map ;
			}
			
		}
		else {
			map.put("result", false);
			map.put("errorCode", "P1");
			map.put("msg", "존재하지 않는 상품입니다");
			return map ;
			
		}

	}
	
	
	
}
