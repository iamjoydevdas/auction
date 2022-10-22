package com.command.spring.kafka.api.controller;

import com.command.spring.kafka.api.Service.SellerService;
import com.commons.Excption.AuctionException;
import com.commons.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/seller")
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @KafkaListener(groupId = Constants.GRP_ID_BUY, topics = Constants.BID_T, containerFactory = "kafkaListenerContainerFactoryBuyer")
    public void consumeBidder(BidProduct bid) throws Exception {
       // serviceImpl.consumeBidder(buyer);
      //  return buyer;
        sellerService.maintainBid(bid);
        //BidProduct product = new BidProduct();
    }

    @PostMapping("")
    public ResponseEntity<Seller> addSeller(@RequestBody Seller seller) {
        return new ResponseEntity<Seller>(sellerService.addSeller(seller), HttpStatus.CREATED);
    }


    @GetMapping("/{sellerId}")
    public ResponseEntity<Seller> getSeller(@PathVariable("sellerId") String sellerId) throws Exception {
        return new ResponseEntity<Seller>(sellerService.getSeller(sellerId), HttpStatus.CREATED);
    }

    @PostMapping("/{sellerId}/product")
    public ResponseEntity<Seller> addProduct(@PathVariable("sellerId") String sellerId, @RequestBody Product product) throws Exception {
        return new ResponseEntity<Seller>(sellerService.addProduct(sellerId, product), HttpStatus.CREATED);
    }

    @DeleteMapping("/{sellerId}/{productId}")
    public ResponseEntity<Seller> deleteProduct(@PathVariable("sellerId") String sellerId, @PathVariable("productId") String productId) throws Exception {
        return new ResponseEntity<Seller>(sellerService.deleteProduct(sellerId, productId), HttpStatus.CREATED);
    }
}
