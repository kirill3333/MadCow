package ru.scrumdev.sample.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.math.BigDecimal;

public class Bid implements IsSerializable {
    private Integer type;
    private Integer qty;
    private BigDecimal price;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
