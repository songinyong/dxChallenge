package com.api.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.domain.jpa.Weather;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class WeatherDto {

	//기온
	private float th1;
	//1시간 강수량
	private float rn1;
	//동서바람성분
	private float uuu;
	//남북바람성분
	private float vvv;
	//습도
	private float reh;
	//강수형태
	private float pty;
	//풍향
	private float vec;
	//풍속
	private float wsd;
	
	private String x;
	private String y;
	private String time;
	private LocalDate date;
	
	
	@Builder
	public Weather toEntity() {
		return Weather.builder()
				.pty(pty)
				.reh(reh)
				.rn1(rn1)
				.t1h(th1)
				.uuu(uuu)
				.vvv(vvv)
				.vec(vec)
				.wsd(wsd)
				.x(x)
				.y(y)
				.time(time)
				.date(date)
				.build();
    }
}
