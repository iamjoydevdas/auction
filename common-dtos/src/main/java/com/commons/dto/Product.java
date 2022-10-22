package com.commons.dto;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Product {
    private String productId;
    private Integer startPrice;
    private String productName;
    private String shortDesc;
    private String detailDesc;
    private String category;
    private Date endDate;

    private List<BidProduct> bids = new ArrayList<>();
    public List<BidProduct> getBids() {
        return bids;
    }

    public void setBids(List<BidProduct> bids) {
        this.bids = bids;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Integer startPrice) {
        this.startPrice = startPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
