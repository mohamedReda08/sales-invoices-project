/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salesInvoice.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 *
 * @author moham
 */
public class InvoiceTableModel extends AbstractTableModel {

    private ArrayList<Invoice> invoices;
    private String[] columns = {"Number", "Date", "Customer", "Total"};

    public InvoiceTableModel(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public int getRowCount() {
        return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    public String getColumName(int columnCount){
        return columns[columnCount];
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Invoice invoice = invoices.get(rowIndex);
        return
                switch (columnIndex) {
                    case (0) -> invoice.getId();
                    case (1) -> invoice.getInvoiceDate();
                    case (2) -> invoice.getCustomerName();
                    case (3) -> invoice.getTotal();
                    default -> "";
                };

    }
}
