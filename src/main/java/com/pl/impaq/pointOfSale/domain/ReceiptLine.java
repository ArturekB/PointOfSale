package com.pl.impaq.pointOfSale.domain;

import java.math.BigDecimal;

public class ReceiptLine {
    private String name;
    private BigDecimal price;

    public ReceiptLine(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReceiptLine that = (ReceiptLine) o;

        return (price.equals(that.price) && name.equals(that.name));
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }
}
