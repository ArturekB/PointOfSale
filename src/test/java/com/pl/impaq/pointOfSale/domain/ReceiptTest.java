package com.pl.impaq.pointOfSale.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class ReceiptTest {

    @Test
    public void shouldCalculateTotal(){
        Receipt sut = createTestReceipt();

        String total = sut.getDisplayMessage();

        assertThat(total.equalsIgnoreCase("Total: 34,96"));
    }

    private Receipt createTestReceipt() {
        List<ReceiptLine> lines = new LinkedList<>();
        lines.add(new ReceiptLine("Product1", BigDecimal.valueOf(12.99)));
        lines.add(new ReceiptLine("Product2", BigDecimal.valueOf(15.99)));
        lines.add(new ReceiptLine("Product1",BigDecimal.valueOf(5.98)));
        return new Receipt(lines);
    }
}
