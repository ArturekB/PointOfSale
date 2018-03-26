package com.pl.impaq.pointOfSale.application;

import com.pl.impaq.pointOfSale.domain.*;
import java.util.LinkedList;
import java.util.List;

public class PointOfSale {
    private Display display;
    private ProductRepository repository;
    private CodeScanner codeScanner;
    private Printer printer;
    private List<ReceiptLine> boughtProducts = new LinkedList<>();
    private final String PRODUCT_NOT_FOUND = "Product not found";
    private final String INVALID_BAR_CODE = "Invalid bar code";

    PointOfSale(Display display, ProductRepository repository, CodeScanner codeScanner, Printer printer) {
        this.display = display;
        this.repository = repository;
        this.codeScanner = codeScanner;
        this.printer = printer;
    }

    public void handleTransaction() {
        BarCode code;
        do {
            code = codeScanner.getNextCode();
            if (code.isInvalid()) {
                display.showMessage(INVALID_BAR_CODE);
                continue;
            }
            if (code.endsTransaction()) {
                finalizeTransaction();
            } else {
                Product product = repository.getOptional(code).orElse(Product.empty());
                performPurchase(product);
            }
        } while (!code.endsTransaction());
    }

    private void finalizeTransaction() {
        Receipt receipt = createReceipt();
        display.showMessage(receipt.getDisplayMessage());
        printer.print(receipt);
    }

    private Receipt createReceipt() {
        return new Receipt(boughtProducts);
    }

    private void performPurchase(Product product) {
        if (product == Product.empty())
            display.showMessage(PRODUCT_NOT_FOUND);
        else {
            boughtProducts.add(product.createReceiptLine());
            display.showMessage(product.getDisplayMessage());
        }
    }
}
