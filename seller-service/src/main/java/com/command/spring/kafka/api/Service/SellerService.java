package com.command.spring.kafka.api.Service;

import com.command.spring.kafka.api.config.SequenceGeneratorService;
import com.command.spring.kafka.api.repository.AvailiableProductsRepo;
import com.command.spring.kafka.api.repository.BuyerRepository;
import com.command.spring.kafka.api.repository.SellerRepository;
import com.commons.Excption.AuctionException;
import com.commons.dto.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Autowired
    private AvailiableProductsRepo availiableProductsRepo;

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
      //  buyer.setId((int) sequenceGeneratorService.generateSequence(Buyer.SEQUENCE_NAME));
      //  buyer.getInfo().setId((int) sequenceGeneratorService.generateSequence(Buyer.SEQUENCE_NAME));
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

    @Transactional
    public Seller addProduct(String sellerId, Product product) throws Exception {
        Seller seller = sellerRepository.findById(sellerId).filter(Objects::nonNull).orElseThrow(() -> new Exception("No Seller Found"));
        product.setProductId(RandomStringUtils.randomAlphanumeric(5));
        seller.getProducts().add(product);
        Seller save = sellerRepository.save(seller);
        AvailibleProduct availibleProduct = new AvailibleProduct();
        availibleProduct.setName(product.getProductName());
        availibleProduct.setDesc(product.getShortDesc());
        availibleProduct.setEndDate(product.getEndDate());
        availibleProduct.setSellerId(seller.getSellerId());
        availibleProduct.setPrice(product.getStartPrice());
        availibleProduct.setCategory(product.getCategory());
        availibleProduct.setProductId(product.getProductId());
        availiableProductsRepo.save(availibleProduct);
        return save;
    }

    public Seller deleteProduct(String sellerId, String productId) throws Exception {
        Seller seller = sellerRepository.findById(sellerId).filter(Objects::nonNull).orElseThrow(() -> new Exception("No Seller Found"));
        List<Product> products = seller.getProducts();
        products.removeIf(product -> product.getProductId().equals(productId));
        return sellerRepository.save(seller);
    }

    public void maintainBid(BidProduct bid) throws Exception {

        Seller seller = sellerRepository.findById(bid.getSellerId())
                .orElseThrow(() -> new Exception("No Seller Found"));

        Product prd = seller
                .getProducts()
                .stream().filter(product -> product.getProductId().equals(bid.getProductId()))
                .findFirst().orElseThrow(() -> new Exception("No product Found"));

        List<BidProduct> bids = prd
                .getBids();
        List<Product> existingPrd = seller
                .getProducts();
        existingPrd.remove(prd);
        BidProduct existingBid = bids.stream().filter(bidProduct -> bidProduct.getBuyerId().equals(bid.getBuyerId()))
                .findFirst().orElse(null);
        if(Objects.isNull(existingBid)){
            BidProduct newBid = new BidProduct();
            newBid.setBidAmount(bid.getBidAmount());
            newBid.setBuyerId(bid.getBuyerId());
            Buyer buyer = buyerRepository.findById(bid.getBuyerId()).get();
            newBid.setEmail(buyer.getInfo().getEmail());
            newBid.setPhone(buyer.getInfo().getPhone());
            bids.add(newBid);
        } else {
            bids.remove(existingBid);
            existingBid.setBidAmount(bid.getBidAmount());
            bids.add(existingBid);
        }
        prd.setBids(bids);

        existingPrd.add(prd);
        seller.setProducts(existingPrd);
        sellerRepository.save(seller);
    }
}
