package com.beansoftph.models;

/**
 * Created by Shanyl Jimenez on 1/29/2016.
 */
public class ReceiptType {

    private String TypeOfReceipt;

    public ReceiptType(String typeOfReceipt) {
        TypeOfReceipt = typeOfReceipt;
    }

    public String getTypeOfReceipt() {
        return TypeOfReceipt;
    }

    public void setTypeOfReceipt(String typeOfReceipt) {
        TypeOfReceipt = typeOfReceipt;
    }
}
