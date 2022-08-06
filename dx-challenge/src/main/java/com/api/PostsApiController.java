package com.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.api.dto.StoreDto;
import com.domain.jpa.Store;
import com.service.PostService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@CrossOrigin(origins="*")
public class PostsApiController {

	private final PostService postService;



    //특정 지갑 주소를 제외한 전체 AND sell_state 기준 페이지로 출력
    @GetMapping("/store")
    public Page<StoreDto> pageNotwalletandsellState(@PageableDefault(size=10) Pageable pageRequest) {
    	return postService.findAllStore(pageRequest);
    }
 
    @GetMapping("/store2")
    public List<Store> test() {
    	return postService.test();
    }

}
