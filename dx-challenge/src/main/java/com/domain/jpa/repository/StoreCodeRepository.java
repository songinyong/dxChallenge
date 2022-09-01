package com.domain.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.domain.jpa.StoreCode;
import com.domain.jpa.Interface.CodeNm;

public interface StoreCodeRepository extends JpaRepository<StoreCode, Long>{

	   @Query(nativeQuery = true, value="SELECT c.code_nm FROM store_code c WHERE c.code =?1")
	   Optional<CodeNm> findByCateory(String code);
	   
	   
}
