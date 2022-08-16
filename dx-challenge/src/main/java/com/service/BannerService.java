package com.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;

import com.api.dto.BannerDto;

public interface BannerService {

	public void bringWeatherInfo() throws  IOException;
	
	public List<BannerDto> findSeaonAndWeather();
}
