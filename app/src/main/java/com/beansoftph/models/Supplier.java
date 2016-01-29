package com.beansoftph.models;

/**
 * Created by Shanyl Jimenez on 1/29/2016.
 */
public class Supplier {

    private String SupplierName;
    private String TIN;
    private String SObjectID;

    public Supplier(String supplierName, String TIN, String SObjectID) {
        SupplierName = supplierName;
        this.TIN = TIN;
        this.SObjectID = SObjectID;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public String getSObjectID() {
        return SObjectID;
    }

    public void setSObjectID(String SObjectID) {
        this.SObjectID = SObjectID;
    }
}
