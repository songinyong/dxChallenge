package com.domain.jpa;

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
@Table(name="weather")
public class Weather {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	//기온
	@Column()
	private float t1h;
	
	//1시간 강수량
	@Column()
	private float rn1;
	//동서바람성분
	@Column()
	private float uuu;
	//남북바람성분
	@Column()
	private float vvv;
	//습도
	@Column()
	private float reh;
	//강수형태
	@Column()
	private float pty;
	//풍향
	@Column()
	private float vec;
	//풍속
	@Column()
	private float wsd;
}
