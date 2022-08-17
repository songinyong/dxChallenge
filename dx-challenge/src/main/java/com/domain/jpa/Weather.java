package com.domain.jpa;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
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
	
	@Column()
	private String x;
	
	@Column()
	private String y;	
	
	@Column()
	private String time;
	
	@Column()
	private LocalDate date;
	
    @Builder
    public Weather(float t1h, float rn1, float uuu, float vvv , float reh, float pty, float vec, float wsd, String x, String y, String time, LocalDate date ) {
    	this.t1h = t1h ;
    	this.rn1 = rn1 ;
    	this.uuu = uuu ;
		this.vvv = vvv;
		this.reh = reh;
		this.pty = pty;
		this.vec = vec;
		this.wsd = wsd;
		this.x = x;
		this.y = y;
		this.time = time;
		this.date = date;
    }
    
	
}
