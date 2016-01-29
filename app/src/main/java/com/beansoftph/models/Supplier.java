package com.beansoftph.models;

/**
 * Created by Shanyl Jimenez on 1/29/2016.
 */
public class Supplier {

    private String SupplierName;
    private String TIN;

    public Supplier(String supplierName, String TIN) {
        SupplierName = supplierName;
        this.TIN = TIN;
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
}
