package com.auction.api;

import com.auction.repo.BuyerRepository;
import com.commons.dto.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/buyer")
public class BuyerApi {
    @Autowired
    private BuyerRepository buyerRepository;

    @PostMapping("")
    public ResponseEntity<Buyer> createBuyer(@RequestBody Buyer buyer) {
        return new ResponseEntity<>(buyerRepository.save(buyer), HttpStatus.CREATED);
    }
}
