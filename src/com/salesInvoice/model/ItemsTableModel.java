package com.salesInvoice.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ItemsTableModel extends AbstractTableModel {

    private ArrayList<Item> Items;
    private  String [] itemsTableColumns= {"Number", "Item","Price",  "Count", "Total"};

    public ItemsTableModel(ArrayList<Item> items) {
        this.Items = items;
    }

    @Override
    public int getRowCount() {
        return Items.size();
    }

    @Override
    public int getColumnCount() {
        return itemsTableColumns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Item item = Items.get(rowIndex);

        switch (columnIndex){
            case 0 :return item.getInvoice().getId();
            case 1 :return item.getItemName();
            case 2 :return item.getPrice();
            case 3 :return item.getCount();
            case 4 :return item.getItemTotal();
            }

    return "Row & Columns";}
}
