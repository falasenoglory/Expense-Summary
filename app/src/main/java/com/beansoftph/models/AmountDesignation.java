package com.beansoftph.models;

/**
 * Created by Shanyl Jimenez on 1/29/2016.
 */
public class AmountDesignation {

    private String  AmountDesig;
    private String AObjectID;

    public AmountDesignation(String amountDesig, String AObjectID) {
        AmountDesig = amountDesig;
        this.AObjectID = AObjectID;
    }

    public String getAmountDesig() {
        return AmountDesig;
    }

    public void setAmountDesig(String amountDesig) {
        AmountDesig = amountDesig;
    }

    public String getAObjectID() {
        return AObjectID;
    }

    public void setAObjectID(String AObjectID) {
        this.AObjectID = AObjectID;
    }
}
