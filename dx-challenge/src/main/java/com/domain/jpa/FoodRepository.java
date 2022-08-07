package com.domain.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FoodRepository extends JpaRepository<Food, Long>{

	   @Query("SELECT f FROM Food f WHERE f.store_id =?1")
	   List<Food> findFoodByStoreId(long storeId);
}
