package com.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.api.PostsApiController;
import com.domain.jpa.Posts;
import com.domain.jpa.repository.PostsRepository;



@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest(PostsApiController.class)
public class PostsApiControllerTest {
	
	//@LocalServerPort
	private int port = 8081;
	
	@Autowired
	private TestRestTemplate restTemplate ;
	
	@Autowired
	private PostsRepository postsRepository;
	
    @Autowired
    private MockMvc mockMvc; // mockMvc 생성
	
	/*
	@After
	public void tearDown() throws Exception {
		postsRepository.deleteAll();
	}*/
    @Test
	public void Posts_store_test() throws Exception {
        //given
		
		/*
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .price(0)
                .build());

        Long updateId = savedPosts.getId();
        int price = 100 ;

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .price(price)
                .build();
        */

        String url = "http://3.34.139.36:" + port + "/store";


        mockMvc.perform(
                get("/store")) //해당 url로 요청을 한다.
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 응답 status를 ok로 테스트
                .andDo(print());// 응답값 print


    }
	
	
	/*
	@Test
	public void Posts_save_test() throws Exception {
		
	
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", 0);
		
		String url = "http://localhost:" + port + "posts";
		
		ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
		restTemplate.getForEntity(url, null, null)
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);
		List<Posts> all = postsRepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo(title);
		
	}*/

	/*
    @Test
    public void Posts_update_test() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
              
                .price(0)
                .build());

        Long updateId = savedPosts.getId();
        int price = 100 ;

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .price(price)
                .build();

        String url = "http://localhost:" + port + "/posts" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity , Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getPrice()).isEqualTo(price);
        
    }*/

}
