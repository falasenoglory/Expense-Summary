package com.beansoftph.models;


/**
 * Created by Shanyl Jimenez on 1/29/2016.
 */
public class ChartOfAccounts {

    private String AccountName;


    public ChartOfAccounts(String accountName, String CObjectID) {
        AccountName = accountName;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

}
