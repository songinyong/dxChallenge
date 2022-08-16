package com.domain.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WeatherFoodRepository extends JpaRepository<WeatherFood, Long>{

	
	   @Query(nativeQuery = true, value="SELECT * FROM Weather_food f WHERE f.weather_code =?1")
	   List<WeatherFood> findByWeatherCode(String code);
}
