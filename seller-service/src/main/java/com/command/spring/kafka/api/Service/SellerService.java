package com.command.spring.kafka.api.Service;

import com.command.spring.kafka.api.config.SequenceGeneratorService;
import com.command.spring.kafka.api.repository.BuyerRepository;
import com.command.spring.kafka.api.repository.SellerRepository;
import com.commons.Excption.AuctionException;
import com.commons.dto.Buyer;
import com.commons.dto.Constants;
import com.commons.dto.Product;
import com.commons.dto.Seller;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service
public class SellerService {
    @Autowired
    private KafkaTemplate<String, Object> template;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Transactional
    public void saveSeller(Seller seller) throws AuctionException {
     //   seller.setId((int) sequenceGeneratorService.generateSequence(Seller.SEQUENCE_NAME));
       // seller.getProduct().setProductId(seller.getId());
       // seller.getInfo().setId(seller.getId());
        sellerRepository.save(seller);
        template.send(Constants.SELL_T, seller);
    }

    @Transactional
    public void saveBuyer(Buyer buyer) throws AuctionException {
        buyer.setId((int) sequenceGeneratorService.generateSequence(Buyer.SEQUENCE_NAME));
        buyer.getInfo().setId((int) sequenceGeneratorService.generateSequence(Buyer.SEQUENCE_NAME));
        buyerRepository.save(buyer);
        template.send(Constants.BID_T, buyer);
    }

    @Transactional
    public Seller addSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    public Seller getSeller(String sellerId) throws Exception {
        return sellerRepository.findById(sellerId).filter(Objects::nonNull).orElseThrow(()-> new Exception("No Seller Found"));
    }

    public Seller addProduct(String sellerId, Product product) throws Exception {
        Seller seller = sellerRepository.findById(sellerId).filter(Objects::nonNull).orElseThrow(() -> new Exception("No Seller Found"));
        product.setProductId(RandomStringUtils.randomAlphanumeric(5));
        seller.getProducts().add(product);
        return sellerRepository.save(seller);
    }

    public Seller deleteProduct(String sellerId, String productId) throws Exception {
        Seller seller = sellerRepository.findById(sellerId).filter(Objects::nonNull).orElseThrow(() -> new Exception("No Seller Found"));
        List<Product> products = seller.getProducts();
        products.removeIf(product -> product.getProductId().equals(productId));
        return sellerRepository.save(seller);
    }
}
