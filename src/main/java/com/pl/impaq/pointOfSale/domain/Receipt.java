package com.pl.impaq.pointOfSale.domain;

import java.math.BigDecimal;
import java.util.List;

public class Receipt {
    private List<ReceiptLine> lines;
    private BigDecimal total;

    public Receipt(List<ReceiptLine> boughtProducts) {
        lines = boughtProducts;
        total = (boughtProducts.isEmpty()) ? BigDecimal.ZERO : CalculateTotalPrice(boughtProducts);
    }

    private BigDecimal CalculateTotalPrice(List<ReceiptLine> boughtProducts) {
        return boughtProducts.stream().map(ReceiptLine::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getDisplayMessage() {
        return "Total: "+total;
    }

}
