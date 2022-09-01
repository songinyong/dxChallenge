package com.domain.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.domain.jpa.Food;

public interface FoodRepository extends JpaRepository<Food, Long>{

	   @Query("SELECT f FROM Food f WHERE f.store_id =?1")
	   List<Food> findFoodByStoreId(long storeId);
}
