package com.domain.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.domain.jpa.Store;



public interface StoreRepository extends JpaRepository<Store, Long>  {

	   @Query("SELECT s FROM Store s WHERE s.code_nm =?1")
	   List<Store> findByCateory(String code_nm);
	   
		@Query("SELECT s FROM Store s WHERE s.store_nm like %?1%")
	    List<Store> findStoreByKeyword(String keyword);

}
 