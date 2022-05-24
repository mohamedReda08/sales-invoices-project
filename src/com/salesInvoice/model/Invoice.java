/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salesInvoice.model;

import java.util.ArrayList;

/**
 *
 * @author moham
 */
public class Invoice {
    private int id;
    private String invoiceDate;
    private String customerName;
    private double total;
    private ArrayList<Item> items;


    public Invoice() {
    }

    public double getTotal(){
        total = 0;
        for (Item item:getItems()){
            total+=item.getItemTotal();
        }
        return total;
    }
    public Invoice(int id, String invoiceDate, String customerName) {
        this.id = id;
        this.invoiceDate = invoiceDate;
        this.customerName = customerName;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public ArrayList<Item> getItems() {
        if (items == null){
            items = new ArrayList<>();
        }
        return items;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", invoiceDate='" + invoiceDate + '\'' +
                ", customerName='" + customerName + '\'' +
                '}';
    }

    public String getHeaderCSVFormat(){
        return id +","+ invoiceDate + ","+ customerName;
    }
}
