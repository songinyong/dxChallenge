package com.domain.jpa;

import java.time.LocalDateTime;

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

public class Posts  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable =false)
    private String title;

    @Column(nullable = false)
    private int sell_state;
    
    @Column(nullable = false)
    private float price;
    
	@Column()
	private String nft_description;
	@Column()
	private String nft_hash;
	
	//아이템 아이디를 16진수롤 변환해서 저장
	@Column()
	private String token_id ;
	@Column()
	private String token_name ;
	@Column()
	private String creator;
	@Column()
	private String image_path;
	@Column()
	private String owner;
	@Column()
	private LocalDateTime createdDate ;
	
	@Column()
	private LocalDateTime modifiedDate ;
    

    @Builder
    public Posts(String title, float price, String nft_description, String nft_hash, String token_id, String token_name, String creator, String image_path, String owner, LocalDateTime createdDate, LocalDateTime modifiedDate ) {
        this.title = title;    
        this.sell_state = 0 ;
        this.price = price ;
        this.nft_description = nft_description;
        this.nft_hash = nft_hash;
        this.token_id = token_id;
        this.token_name = token_name;
        this.creator = creator;
        this.image_path = image_path;
        this.owner = owner;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        
    }
    // price 업데이트
    public void update(float price) {
        this.price = price;
    }
    //sell_state 업데이트
    public void stateUpdate(int sell_state) {
    	this.sell_state = sell_state;
    }
    
    public void synUpdate(String owner, LocalDateTime modifiedDate ) {
    	this.owner = owner ;
    	this.modifiedDate = modifiedDate ;
    }
}