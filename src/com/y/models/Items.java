package com.y.models;


public class Items {
    private All_InvoicesList header;
    private String itemName;
    private double itemPrice;
    private int itemCount;

    public Items() {
    }

    public Items(All_InvoicesList header, String itemName, double itemPrice, int itemCount) {
        this.header = header;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
    }

    public All_InvoicesList getHeader() {
        return header;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setHeader(All_InvoicesList header) {
        this.header = header;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
    
    public double getItemTotal(){
        return itemPrice*itemCount;
    }

    @Override
    public String toString() {
        return header.getInvoiceNumber() + "," + itemName + "," + itemPrice + "," + itemCount;
    }

    
    
    
    
    
}