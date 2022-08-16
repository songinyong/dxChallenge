package com.domain.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockRepository extends JpaRepository<Stock, Long> {
	
	@Query("SELECT s FROM Stock s WHERE s.store_id =?1")
    List<Stock> findStockByStoreId(long storeId);
	
	@Query("SELECT s FROM Stock s WHERE s.food_id =?1")
	List<Stock> findStockByFoodId(long foodId);
	


	
}
