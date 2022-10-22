package com.commons.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "buyer")
public class Buyer {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }

    private UserInfo info;

    public List<BidProduct> getBids() {
        return bids;
    }

    public void setBids(List<BidProduct> bids) {
        this.bids = bids;
    }

    private List<BidProduct> bids = new ArrayList<>();


}
