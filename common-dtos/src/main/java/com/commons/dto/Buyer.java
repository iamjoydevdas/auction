package com.commons.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


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

   /* public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(Integer bidAmount) {
        this.bidAmount = bidAmount;
    }*/

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }

//    private Integer productId;
//    private Integer bidAmount;
    private UserInfo info;
}
