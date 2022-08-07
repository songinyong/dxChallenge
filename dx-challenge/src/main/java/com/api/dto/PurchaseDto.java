package com.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PurchaseDto {

	private Long stockId;
	private int  amount;
	
	@Builder
	public PurchaseDto(Long stockId, int amount) {
		this.stockId = stockId;
		this.amount = amount ;
	}
}
