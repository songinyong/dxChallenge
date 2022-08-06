package com.domain.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostsRepository {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
    
    //sell_state 기준으로 모든 게시물 조회
   @Query("SELECT p FROM Posts p WHERE p.sell_state =?1 ORDER BY p.id DESC ")
   List<Posts> findUsersPosts(int sell_state);
   
   @Query("SELECT p FROM Posts p WHERE p.sell_state =?1 AND p.token_id=?2")
   Posts CheckSellState(int sell_state, String token_id);
   
   @Query("SELECT p FROM Posts p WHERE p.token_id=?1")
   Optional<Posts> findBytokenID(String token_id);
   
   @Query(value="SELECT p.token_id FROM Posts p WHERE p.token_id =?1 ", nativeQuery = true)
	List<String> checkID(String token_id);
    
    Page<Posts> findAll(Pageable pageable);
    
    
    @Query("SELECT p FROM Posts p WHERE p.owner =:wallet")
    Page<Posts> findWallet(Pageable pageable, String wallet);
    
    @Query("SELECT p FROM Posts p WHERE p.owner =:wallet AND p.sell_state = :sell_state")
    Page<Posts> findWalletAndSellState(Pageable pageable, String wallet, int sell_state);
    
    @Query("SELECT p FROM Posts p WHERE p.owner NOT IN :wallet AND p.sell_state = :sell_state")
    Page<Posts> findNotWalletAndSellState(Pageable pageable, String wallet, int sell_state);
    

}