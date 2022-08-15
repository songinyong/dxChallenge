package com.domain.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StoreCodeRepository extends JpaRepository<StoreCode, Long>{

	   @Query(nativeQuery = true, value="SELECT c.code_nm FROM store_code c WHERE c.code =?1")
	   Optional<CodeNm> findByCateory(String code);
	   
	   
}
