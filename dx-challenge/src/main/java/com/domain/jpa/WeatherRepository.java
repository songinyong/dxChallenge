package com.domain.jpa;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WeatherRepository extends JpaRepository<Weather, Long>{

	  @Query("SELECT w FROM Weather w WHERE w.x =?1 AND w.y = ?2 AND w.time =?3 AND w.date = ?4")
	  Optional<Weather> findWeatherInfo(String x, String y, String baseTime, LocalDate date);
	  
	  @Query("SELECT w FROM Weather w WHERE w.x =?1 AND w.y = ?2 AND w.time =?3 AND w.date = ?4")
	  Optional<Weather> findLastWeatherInfo(String x, String y, String baseTime, LocalDate date);
}
