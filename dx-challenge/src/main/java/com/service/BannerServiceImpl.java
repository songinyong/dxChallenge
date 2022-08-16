package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.api.dto.BannerDto;
import com.api.dto.StockInStoreDto;
import com.domain.jpa.Calorie;
import com.domain.jpa.CalorieRepository;
import com.domain.jpa.Food;
import com.domain.jpa.FoodRepository;
import com.domain.jpa.SeasonFood;
import com.domain.jpa.SeasonFoodRepository;
import com.domain.jpa.Stock;
import com.domain.jpa.StockRepository;
import com.domain.jpa.Store;
import com.domain.jpa.StoreRepository;
import com.domain.jpa.Weather;
import com.domain.jpa.WeatherFood;
import com.domain.jpa.WeatherFoodRepository;
import com.domain.jpa.WeatherRepository;


@Service
public class BannerServiceImpl implements BannerService {

	
	//보안 토큰 주소
	@Value("${key.weather}")
	String weather_key ;
	
	
	private WeatherRepository weatherRepository;
	
	private WeatherFoodRepository weatherFoodRepository;
	private SeasonFoodRepository seasonRepository;
	
	private StoreRepository storeRepository;
	private FoodRepository foodRepository;
	private CalorieRepository calorieRepository;
	private StockRepository stockRepository;
	
	@Autowired
	public void setStoreRepository(StoreRepository storeRepository) {
	    this.storeRepository = storeRepository;
	 }
	
	@Autowired
	public void setWeatherRepository(WeatherRepository weatherRepository) {
	    this.weatherRepository = weatherRepository;
	 }
	

	
	@Autowired
	public void setWeatherFoodRepository(WeatherFoodRepository weatherFoodRepository) {
	    this.weatherFoodRepository = weatherFoodRepository;
	 }
	
	
	@Autowired
	public void setFoodRepository(FoodRepository foodRepository) {
	    this.foodRepository = foodRepository;
	 }
	
	@Autowired
	public void setCalorieRepository(CalorieRepository calorieRepository) {
	    this.calorieRepository = calorieRepository;
	 }
	
	@Autowired
	public void setStockRepository(StockRepository stockRepository) {
	    this.stockRepository = stockRepository;
	 }
	@Autowired
	public void setSeasonFoodRepository(SeasonFoodRepository seasonRepository) {
		this.seasonRepository = seasonRepository ;
	}
	
	
	
	// 계절정보 불러옴
	@Transactional
	public void bringWeatherInfo() throws IOException{

		RestTemplate rt = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", "application/json");
		headers.set("ServiceKey", "6Tlk%2BUjvrCguBXcirdYV5U2D%2BrQVbjuXCFkOYIwSrxh%2FP78httfck7bK4%2B2wJdJnCMWtjjLqcog%2BLJXB%2Fvuoyg%3D%3D");
		
		HttpEntity request = new HttpEntity(headers);
		
		Map<String, String> params = new HashMap<String, String>();

		params.put("pageNo", "1");
		params.put("numOfRows", "100");
		params.put("dataType", "JSON");
		params.put("base_date", "20220816");
		params.put("base_time", "0600");
		params.put("nx", "129");
		params.put("ny", "35");
		
		String uri = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
		
		uri = uri + "?" + this.mapToUrlParam(params);
		
		
		//ResponseEntity<String> response =rt.getForEntity(uri, String.class, params );
		
		
		/*
		ResponseEntity<String> response = rt.exchange(
				uri,
				  HttpMethod.GET,
				  request,
				  String.class
				);*/
		
		
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=6Tlk%2BUjvrCguBXcirdYV5U2D%2BrQVbjuXCFkOYIwSrxh%2FP78httfck7bK4%2B2wJdJnCMWtjjLqcog%2BLJXB%2Fvuoyg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20220816", "UTF-8")); /*‘21년 6월 28일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0600", "UTF-8")); /*06시 발표(정시단위) */
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("99", "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("75", "UTF-8")); /*예보지점의 Y 좌표값*/
       
	
			 URL url;
			url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());

	}
	
	private static String mapToUrlParam(Map<String, String> params) {
		  StringBuffer paramData = new StringBuffer();
		  for (Map.Entry<String, String> param : params.entrySet()) {
		  	if (paramData.length() != 0) {
		  		paramData.append('&');
		  	}
		    paramData.append(param.getKey());
		    paramData.append('=');
		    paramData.append(String.valueOf(param.getValue()));
		  }
		  return paramData.toString();
		}

	
	
	
	// 배너 광고 정보 출력
	@Transactional
	public List<BannerDto> findSeaonAndWeather() {
		
		List<BannerDto> returnList = new ArrayList<BannerDto>();
		
		List<Stock> stockList = new ArrayList<Stock>();
		
		List<SeasonFood> seasonList = seasonRepository.findAll();
		
		
		for(SeasonFood s : seasonList ) {
			
			List<Stock> temp = stockRepository.findStockByFoodId(s.getFood_id());
			
			if(!temp.isEmpty())
				stockList.addAll(temp);
			
		}
		
		List<Store> storeList = storeRepository.findAll();
		List<Food> foodLlist = foodRepository.findAll();
		List<Calorie> calorieList = calorieRepository.findAll();
		
		
		HashMap<Long, Food> food = new HashMap<Long, Food>();
		HashMap<Long, Store> store = new HashMap<Long, Store>();
		HashMap<Long, Calorie> calorie = new HashMap<Long, Calorie>();
		
		for(Food f : foodLlist) 
			food.put(f.getId(), f);

		for(Store s : storeList) 
			store.put(s.getId(), s);
		
		for(Calorie c : calorieList )
			calorie.put(c.getId(), c);
		
		for(Stock s : stockList) {

			Food temp = food.get(s.getFood_id());
			returnList.add(new BannerDto(temp ,s , store.get(s.getStore_id()), calorie.get(temp.getCalorie_id()), "season"));
			
		}
		
		
		List<BannerDto>  temp = Weather();

		if(temp.size()>0)
			returnList.addAll(temp);
		
		return returnList;
		
		
	}
	
	
	
	
	// 계절정보 불러옴
	@Transactional
	private List<BannerDto> Weather() {
		
		
		List<BannerDto> return_list = new ArrayList<BannerDto>();
		
		Optional<Weather> weather = weatherRepository.findById((long) 0);
		
		String bannerWeather = "";
		
		if(weather.isPresent()) {
			
			if(weather.get().getRn1() > 5) 
				bannerWeather = "rain";

			else if(weather.get().getT1h() > 26) 
				bannerWeather = "hot";
			
			if(!bannerWeather.equals("")) {
				
				List<Store> storeList = storeRepository.findAll();
				List<Calorie> calorieList = calorieRepository.findAll();
				
				HashMap<Long, Store> store = new HashMap<Long, Store>();
				HashMap<Long, Calorie> calorie = new HashMap<Long, Calorie>();
				

				for(Store s : storeList) 
					store.put(s.getId(), s);
				
				for(Calorie c : calorieList )
					calorie.put(c.getId(), c);
				
				List<WeatherFood> wFoodList =  weatherFoodRepository.findByWeatherCode(bannerWeather);
				
				for(WeatherFood w : wFoodList ) {
					
					Food food = foodRepository.findById(w.getFood_id()).get();
					Stock stock = stockRepository.findStockByFoodId(food.getId()).get(0);
					Optional<Store> stores = storeRepository.findById(food.getStore_id());
					
					return_list.add(new BannerDto(food, stock, stores.get(), calorie.get(food.getCalorie_id()), bannerWeather));
					

					System.out.println(return_list);
				}
				
				return return_list;
				
			}

						
			
		}
		
		    return return_list;
		
	}
	
	
}
