package com.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.dto.StoreDto;
import com.domain.jpa.Store;

public interface PostService {

	public Page<StoreDto> findAllStore(Pageable pageRequest);
	
	public List<Store> test();
}
