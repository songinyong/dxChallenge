package com.api.dto;

import java.util.List;

import com.domain.jpa.Store;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreAndStockDto {

	
	private Long id ;
	private String store_nm ;
	private String x ;
	private String y ;
	private String rating;
	private String address;
	private String code_nm;
	private List<StockDto> stockList;
	
	public StoreAndStockDto(Store entity, List<StockDto> stock) {
		this.id = entity.getId();
		this.store_nm = entity.getStore_nm();
		this.x = entity.getX();
		this.y = entity.getY();
		this.rating = entity.getRating();
		this.address = entity.getAddress();
		this.code_nm = entity.getCode_nm();
		this.stockList = stock;
	}
}
