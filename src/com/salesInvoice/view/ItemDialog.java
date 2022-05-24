package com.salesInvoice.view;

import javax.swing.*;
import java.awt.*;

public class ItemDialog extends JDialog {
    private JTextField itemName;
    private JTextField itemCount;
    private JTextField itemPrice;
    private JLabel itemNameLabel;
    private JLabel itemCountLabel;
    private JLabel itemPriceLabel;
    private JButton okButton;
    private JButton cancelButton;

    public ItemDialog (InvoiceForm form){
        itemNameLabel = new JLabel("Item Name: ");
        itemName = new JTextField(20);
        itemCountLabel = new JLabel("Count: ");
        itemCount = new JTextField(20);
        itemPriceLabel = new JLabel("Price: ");
        itemPrice = new JTextField(10);

        okButton = new JButton("Ok");
        okButton.setActionCommand("Add Item Ok");
        okButton.addActionListener(form.getController());

        cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Add Item Cancel");
        cancelButton.addActionListener(form.getController());


        setLayout( new GridLayout(4,2));

        add(itemNameLabel);
        add(itemName);
        add(itemPriceLabel);
        add(itemPrice);
        add(itemCountLabel);
        add(itemCount);

        add(okButton);
        add(cancelButton);

        pack();

    }

    public JTextField getItemName() {
        return itemName;
    }

    public JTextField getItemCount() {
        return itemCount;
    }

    public JTextField getItemPrice() {
        return itemPrice;
    }
}
