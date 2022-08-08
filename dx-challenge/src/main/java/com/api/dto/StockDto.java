package com.api.dto;

import java.time.LocalDateTime;

import com.domain.jpa.Food;
import com.domain.jpa.Stock;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StockDto {
	
	private Long store_id ;
	private String food_nm ;
	private String photo_link ;
	private int original_price ;
	private int  quanity;
	private int saled_price;
	private LocalDateTime expiration_date;
	private Long stock_id;
	
	public StockDto(Food food, Stock stock) {
		this.store_id = stock.getStore_id();
		this.food_nm = food.getFood_nm();
		this.photo_link = food.getPhoto_link();
		this.original_price = food.getPrice();
		this.quanity = stock.getQuanity();
		this.saled_price =  (food.getPrice() * (100-stock.getDiscount_rate())/100 );
		this.expiration_date = stock.getExpiration_date();
		this.stock_id = stock.getId();
	}

}
