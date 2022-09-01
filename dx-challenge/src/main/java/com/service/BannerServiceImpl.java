package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.dto.BannerDto;
import com.api.dto.WeatherDto;
import com.domain.jpa.Calorie;
import com.domain.jpa.Food;
import com.domain.jpa.MarketPrice;
import com.domain.jpa.SeasonFood;
import com.domain.jpa.Stock;
import com.domain.jpa.Store;
import com.domain.jpa.Weather;
import com.domain.jpa.WeatherFood;
import com.domain.jpa.repository.CalorieRepository;
import com.domain.jpa.repository.FoodRepository;
import com.domain.jpa.repository.MarketPriceRepository;
import com.domain.jpa.repository.SeasonFoodRepository;
import com.domain.jpa.repository.StockRepository;
import com.domain.jpa.repository.StoreRepository;
import com.domain.jpa.repository.WeatherFoodRepository;
import com.domain.jpa.repository.WeatherRepository;


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
	private MarketPriceRepository marketRepository;
	

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
	@Autowired
	public void setMarketPriceRepository(MarketPriceRepository marketRepository) {
		this.marketRepository = marketRepository ;
	}	
	
	// 날씨 정보 기상청에서 받아와 DB에 저장
	@Transactional
	public void bringWeatherInfo() throws IOException, ParseException{

			
		LocalTime currentTime = LocalTime.now();
		LocalDate now = LocalDate.now();
		
		String paramX = "99";
		String paramY = "75";
		String baseTime = currentTime.format(DateTimeFormatter.ofPattern("HH"))+"00";

		if(weatherRepository.findWeatherInfo(paramX, paramY, baseTime, now).isEmpty()) {
			
			/*
			String uri = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
			uri = uri + "?" + this.mapToUrlParam(params);*/
			
			
			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=6Tlk%2BUjvrCguBXcirdYV5U2D%2BrQVbjuXCFkOYIwSrxh%2FP78httfck7bK4%2B2wJdJnCMWtjjLqcog%2BLJXB%2Fvuoyg%3D%3D"); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
	        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
	        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(now.format(DateTimeFormatter.ofPattern("yyyyMMdd")), "UTF-8")); /*‘21년 6월 28일 발표*/
	        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /*06시 발표(정시단위) */
	        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(paramX, "UTF-8")); /*예보지점의 X 좌표값*/
	        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(paramY, "UTF-8")); /*예보지점의 Y 좌표값*/
	       
			URL url;
			url = new URL(urlBuilder.toString());
	
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	
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
	        String result = sb.toString();
	        
	        
	        JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParser.parse(result);
			JSONObject parse_response = (JSONObject) jsonObj.get("response");
			JSONObject parse_body = (JSONObject) parse_response.get("body");// response 로 부터 body 찾아오기
			JSONObject parse_items = (JSONObject) parse_body.get("items");// body 로 부터 items 받아오기
			// items로 부터 itemlist 를 받아오기 itemlist : 뒤에 [ 로 시작하므로 jsonarray이다.
			JSONArray parse_item = (JSONArray) parse_items.get("item");
			
			//System.out.println(parse_item);

			JSONObject obj; // object에 담을 객체
			String category; //category 
			/*
			 * 
			 * */
			WeatherDto weatherDto = new WeatherDto();
			
			obj = (JSONObject) parse_item.get(0);
			
			weatherDto.setX(String.valueOf(obj.get("nx")));
			weatherDto.setY(String.valueOf(obj.get("ny")));
			weatherDto.setTime(String.valueOf(obj.get("baseTime")));
			weatherDto.setDate(now);
			
			
			
			for(int i=0; i< parse_item.size(); i++) {
				
				obj = (JSONObject) parse_item.get(i);
				if(obj.get("category").equals("T1H"))
					weatherDto.setTh1(Float.parseFloat((String) obj.get("obsrValue")));
				else if(obj.get("category").equals("RN1"))
					weatherDto.setRn1(Float.parseFloat((String)obj.get("obsrValue")));
				else if(obj.get("category").equals("UUU"))
					weatherDto.setUuu(Float.parseFloat((String)obj.get("obsrValue")));
				else if(obj.get("category").equals("VEC"))
					weatherDto.setVec(Float.parseFloat((String)obj.get("obsrValue")));
				else if(obj.get("category").equals("VVV"))
					weatherDto.setVec(Float.parseFloat((String)obj.get("obsrValue")));			
				else if(obj.get("category").equals("WSD"))
					weatherDto.setWsd(Float.parseFloat((String)obj.get("obsrValue")));	
				else if(obj.get("category").equals("REH"))
					weatherDto.setReh(Float.parseFloat((String)obj.get("obsrValue")));		
				else if(obj.get("category").equals("PTY"))
					weatherDto.setPty(Float.parseFloat((String)obj.get("obsrValue")));	
			}
			
			weatherRepository.save(weatherDto.toEntity());

		}


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
		List<MarketPrice> marketPriceList = marketRepository.findAll();
		
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
		HashMap<Long, MarketPrice> marketPrice = new HashMap<Long, MarketPrice >();
		
		for(Food f : foodLlist) 
			food.put(f.getId(), f);

		for(Store s : storeList) 
			store.put(s.getId(), s);
		
		for(Calorie c : calorieList )
			calorie.put(c.getId(), c);

		for(MarketPrice m : marketPriceList)
			marketPrice.put(m.getFood_id(), m);
		
		for(Stock s : stockList) {

			int price = 0;
			Food temp = food.get(s.getFood_id());
			if(marketPrice.containsKey(temp.getId()))
				price = marketPrice.get(temp.getId()).getMarket_price();
			
			returnList.add(new BannerDto(temp ,s , store.get(s.getStore_id()), calorie.get(temp.getCalorie_id()), "season", price));
			
		}
		
		//날씨 부분
		//Optional<Weather> weather = weatherRepository.findById((long) 0);
		String paramX = "99";
		String paramY = "75";
		
		Optional<Weather> weather =weatherRepository.findTopByXAndYOrderByDateDescTimeDesc(paramX, paramY);

		String bannerWeather = "";
		
		if(weather.isPresent()) {
			
			if(weather.get().getRn1() > 5) 
				bannerWeather = "rain";

			else if(weather.get().getT1h() > 26) 
				bannerWeather = "hot";
		
			if(!bannerWeather.equals("")) {
				
				for(Store s : storeList) 
					store.put(s.getId(), s);
				
				for(Calorie c : calorieList )
					calorie.put(c.getId(), c);
				
				for(MarketPrice m : marketPriceList)
					marketPrice.put(m.getFood_id(), m);
				
				List<WeatherFood> wFoodList =  weatherFoodRepository.findByWeatherCode(bannerWeather);
				
				for(WeatherFood w : wFoodList ) {
					
					Food targetFood = foodRepository.findById(w.getFood_id()).get();
					Stock stock = stockRepository.findStockByFoodId(targetFood.getId()).get(0);
					Optional<Store> stores = storeRepository.findById(targetFood.getStore_id());
					
					int price = 0;
		
					if(marketPrice.containsKey(targetFood.getId()))
						price = marketPrice.get(targetFood.getId()).getMarket_price();
					
					returnList.add(new BannerDto(targetFood, stock, stores.get(), calorie.get(targetFood.getCalorie_id()), bannerWeather, price));
					
				}
			}
			
		}
		
		/*
		List<BannerDto>  temp = Weather();

		if(temp.size()>0)
			returnList.addAll(temp);
		*/
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
				List<MarketPrice> marketPriceList = marketRepository.findAll();
				
				HashMap<Long, Store> store = new HashMap<Long, Store>();
				HashMap<Long, Calorie> calorie = new HashMap<Long, Calorie>();
				HashMap<Long, MarketPrice> marketPrice = new HashMap<Long, MarketPrice >();
				
				

				for(Store s : storeList) 
					store.put(s.getId(), s);
				
				for(Calorie c : calorieList )
					calorie.put(c.getId(), c);
				
				for(MarketPrice m : marketPriceList)
					marketPrice.put(m.getFood_id(), m);
				
				List<WeatherFood> wFoodList =  weatherFoodRepository.findByWeatherCode(bannerWeather);
				
				for(WeatherFood w : wFoodList ) {
					
					Food food = foodRepository.findById(w.getFood_id()).get();
					Stock stock = stockRepository.findStockByFoodId(food.getId()).get(0);
					Optional<Store> stores = storeRepository.findById(food.getStore_id());
					
					
					int price = 0;
		
					if(marketPrice.containsKey(food.getId()))
						price = marketPrice.get(food.getId()).getMarket_price();
					
					
					return_list.add(new BannerDto(food, stock, stores.get(), calorie.get(food.getCalorie_id()), bannerWeather, price));
					
				}
				
				return return_list;
				
			}

						
			
		}
		
		    return return_list;
		
	}
	
	
}
