package com.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.dto.StoreDto;
import com.domain.jpa.Posts;
import com.domain.jpa.Store;
import com.domain.jpa.StoreRepository;

import lombok.RequiredArgsConstructor;


@Service
public class PostServiceImpl implements PostService {
	
	
	
	
	private StoreRepository storeRepository;
	
	@Autowired
	public void setUserRepository(StoreRepository userRepository) {
	    this.storeRepository = userRepository;
	 }

	
	@Transactional
    //모든 상점 기본 정보 출략
    public Page<StoreDto> findAllStore(Pageable pageRequest) {
		
		 Page<Store> storeList = storeRepository.findAll(pageRequest);
		
		 Page<StoreDto> dtoList = storeList.map(StoreDto::new);

    	
    	return dtoList;
    }

}
