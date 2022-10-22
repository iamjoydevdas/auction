package com.auction.repo;

import com.commons.dto.Buyer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BuyerRepository extends MongoRepository<Buyer, String>{
    //List<Buyer> findByProductId(Integer productId);
}

