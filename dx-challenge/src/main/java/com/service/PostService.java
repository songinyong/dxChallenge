package com.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.dto.StoreDto;

public interface PostService {

	public Page<StoreDto> findAllStore(Pageable pageRequest);
}
