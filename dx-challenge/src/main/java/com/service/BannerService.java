package com.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;

import com.api.dto.BannerDto;

public interface BannerService {

	public void bringWeatherInfo() throws  IOException, ParseException;
	
	public List<BannerDto> findSeaonAndWeather();
}
