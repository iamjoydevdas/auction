package com.commons.dto;


import java.util.List;

public class MappedProductModel {
    protected Seller seller;

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<Buyer> getBuyer() {
        return buyer;
    }

    public void setBuyer(List<Buyer> buyer) {
        this.buyer = buyer;
    }

    protected List<Buyer> buyer;
}
