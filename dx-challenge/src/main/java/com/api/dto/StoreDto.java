package com.api.dto;

import com.domain.jpa.Store;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreDto {

	private Long id ;
	private String store_nm ;
	private String x ;
	private String y ;
	private String rating;
	private String address;
	
	public StoreDto(Store entity) {
		this.id = entity.getId();
		this.store_nm = entity.getStore_nm();
		this.x = entity.getX();
		this.y = entity.getY();
		this.rating = entity.getRating();
		this.address = entity.getAddress();
	}
}
