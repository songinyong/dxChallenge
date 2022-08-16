package com.api.dto;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import com.domain.jpa.Calorie;
import com.domain.jpa.Food;
import com.domain.jpa.Stock;
import com.domain.jpa.Store;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BannerDto {

	
	private Long stock_id;
	private String food_nm ;
	private String photo_link ;
	private int original_price ;
	private int quanity;
	private int saled_price;
	private String expiration_date;
	
	private Long store_id ;
	private String store_nm ;
	private String x ;
	private String y ;
	private String rating;
	private String address;
	private String code_nm;
	private String opening_hour;
	
    private float calorie ;
    private float pro ;
    private float car ;
    private float fat ;
    private float nat ;
    
    private int market_price;
    
    
    //무슨 배너인지 구분
    private String banner_code;
	
	public BannerDto(Food food, Stock stock, Store store, Calorie calorie, String banner_code, int market_price) {
		
		this.food_nm = food.getFood_nm();
		this.photo_link = food.getPhoto_link();
		this.original_price = food.getPrice();
		this.quanity = stock.getQuanity();
		this.saled_price =  (food.getPrice() * (100-stock.getDiscount_rate())/100 );
		this.expiration_date = timeSet(stock.getExpiration_date());
		this.stock_id = stock.getId();
		this.store_id = stock.getStore_id();
		
		this.store_nm = store.getStore_nm();
		this.x = store.getX();
		this.y = store.getY();
		this.rating = store.getRating();
		this.address = store.getAddress();
		this.code_nm = store.getCode_nm();
		this.opening_hour = store.getOpening_hour();
		
		this.calorie = calorie.getCalorie();
		this.pro = calorie.getPro();
		this.car = calorie.getCar();
		this.fat = calorie.getFat();
		this.nat = calorie.getNat();
		
		this.banner_code = banner_code;
		
		this.market_price = market_price;
	}
	
	private String timeSet(LocalDateTime expiration_date) {
        // 2. DayOfWeek 객체 구하기        
		DayOfWeek dayOfWeek = expiration_date.getDayOfWeek();         
		// 3. 숫자 요일 구하기        
		int dayOfWeekNumber = dayOfWeek.getValue();

		StringBuilder builder = new StringBuilder();
		//
		builder.append(expiration_date.getDayOfMonth()+"일");
		if(dayOfWeekNumber == 1)
			builder.append("(월요일) ");
		else if(dayOfWeekNumber == 2)
			builder.append("(화요일) ");
		else if(dayOfWeekNumber == 3)
			builder.append("(수요일) ");
		else if(dayOfWeekNumber == 4)
			builder.append("(목요일) ");	
		else if(dayOfWeekNumber == 5)
			builder.append("(금요일) ");	
		else if(dayOfWeekNumber == 6)
			builder.append("(토요일) ");
		else if(dayOfWeekNumber == 7)
			builder.append("(일요일) ");		
		
		if(expiration_date.getHour() < 10)
		    builder.append("0"+expiration_date.getHour()+":");
		else
			builder.append(expiration_date.getHour()+":");

		if(expiration_date.getMinute() < 10)
		    builder.append("0"+expiration_date.getMinute());
		else
			builder.append(expiration_date.getMinute());
		
		builder.append("시 까지");
		return builder.toString();
		
		
	}
	
	
}
