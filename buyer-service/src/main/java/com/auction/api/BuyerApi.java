package com.auction.api;

import com.commons.dto.BidProduct;
import com.auction.repo.BuyerRepository;
import com.commons.Excption.AuctionException;
import com.commons.dto.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.commons.dto.Constants.BID_T;

@RestController
@RequestMapping("/api/v1/buyer")
public class BuyerApi {
    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private KafkaTemplate<String, Object> template;


    @PostMapping("")
    public ResponseEntity<Buyer> createBuyer(@RequestBody Buyer buyer) {
        return new ResponseEntity<>(buyerRepository.save(buyer), HttpStatus.CREATED);
    }

    @GetMapping("/{buyerId}")
    public ResponseEntity<Buyer> getBuyer(@PathVariable("buyerId") String buyerId) throws AuctionException {
        return new ResponseEntity<>(buyerRepository.findById(buyerId).orElseThrow(()-> new AuctionException("no buyer found")), HttpStatus.OK);
    }

    @PutMapping("/{buyerId}/{sellerId}")
    public ResponseEntity<Buyer> bid(@PathVariable("buyerId") String buyerId,
                                     @PathVariable("sellerId") String sellerId,
                                     @RequestBody BidProduct product) throws AuctionException {

        Buyer buyer = buyerRepository.findById(buyerId).orElseThrow(() -> new AuctionException("no buyer found"));
        List<BidProduct> bids = buyer.getBids();
        bids.add(product);
        buyer.setBids(bids);
        buyerRepository.save(buyer);
        product.setBuyerId(buyerId);
        product.setSellerId(sellerId);
        template.send(BID_T, product);
        return new ResponseEntity<>(buyer, HttpStatus.NO_CONTENT);
    }
}
