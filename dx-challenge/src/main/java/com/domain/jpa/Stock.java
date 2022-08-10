package com.domain.jpa;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name="stock")

public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@Column()
	private Long store_id ;
	@Column()
	private Long food_id ;
	@Column()
	private int quanity;
	@Column()
	private int discount_rate;
	@Column()
	private LocalDateTime expiration_date;
	
	public void setQuanity(int quanity) {
		this.quanity = quanity ;
	}

}
