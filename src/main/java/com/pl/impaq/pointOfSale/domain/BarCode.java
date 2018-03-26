package com.pl.impaq.pointOfSale.domain;

public class BarCode {

    private String code;

    public BarCode(String code) {
        this.code = code;
    }

    public boolean isInvalid() {
        return code.equals("");
    }

    public boolean endsTransaction() {
        return code.equals("exit");
    }

    @Override
    public String toString() {
        return code;
    }
}
