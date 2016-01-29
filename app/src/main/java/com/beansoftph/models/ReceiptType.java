package com.beansoftph.models;

/**
 * Created by Shanyl Jimenez on 1/29/2016.
 */
public class ReceiptType {

    private String TypeOfReceipt;
    private String RObjectID;

    public ReceiptType(String typeOfReceipt, String RObjectID) {
        TypeOfReceipt = typeOfReceipt;
        this.RObjectID = RObjectID;
    }

    public String getTypeOfReceipt() {
        return TypeOfReceipt;
    }

    public void setTypeOfReceipt(String typeOfReceipt) {
        TypeOfReceipt = typeOfReceipt;
    }

    public String getRObjectID() {
        return RObjectID;
    }

    public void setRObjectID(String RObjectID) {
        this.RObjectID = RObjectID;
    }
}
