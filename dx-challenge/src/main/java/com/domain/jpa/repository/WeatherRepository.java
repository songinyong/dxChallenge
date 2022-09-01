package com.domain.jpa.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.domain.jpa.Weather;

public interface WeatherRepository extends JpaRepository<Weather, Long>{

	  @Query("SELECT w FROM Weather w WHERE w.x =?1 AND w.y = ?2 AND w.time =?3 AND w.date = ?4")
	  Optional<Weather> findWeatherInfo(String x, String y, String baseTime, LocalDate date);
	  
	  //@Query("SELECT w FROM Weather w WHERE w.x =?1 AND w.y = ?2 Order by w.date DESC, w.time DESC")
	  Optional<Weather> findTopByXAndYOrderByDateDescTimeDesc(String X, String Y);
	  
	  
}

