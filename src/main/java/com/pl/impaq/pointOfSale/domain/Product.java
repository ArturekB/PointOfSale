package com.pl.impaq.pointOfSale.domain;

import java.math.BigDecimal;

public class Product {
    private BarCode code;
    private String name;
    private BigDecimal price;

    private static final Product empty = new Product(new BarCode(""), "", BigDecimal.ZERO);

    public Product(BarCode code, String name, BigDecimal price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public BarCode getBarCode() {
        return code;
    }

    public String getDisplayMessage() {
        return name + ": " + price;
    }

    public ReceiptLine createReceiptLine() {
        return new ReceiptLine(name, price);
    }

    public static Product empty() {
        return empty;
    }
}
