package com.beansoftph.models;


/**
 * Created by Shanyl Jimenez on 1/29/2016.
 */
public class ChartOfAccounts {

    private String AccountName;
    private String CObjectID;

    public ChartOfAccounts(String accountName, String CObjectID) {
        AccountName = accountName;
        this.CObjectID = CObjectID;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    public String getCObjectID() {
        return CObjectID;
    }

    public void setCObjectID(String CObjectID) {
        this.CObjectID = CObjectID;
    }
}
