/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salesInvoice.control;

import com.salesInvoice.model.Invoice;
import com.salesInvoice.model.InvoiceTableModel;
import com.salesInvoice.model.Item;
import com.salesInvoice.model.ItemsTableModel;
import com.salesInvoice.view.InvoiceDialog;
import com.salesInvoice.view.InvoiceForm;
import com.salesInvoice.view.ItemDialog;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DateFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author moham
 */
public class Controller implements ActionListener {

    private InvoiceForm form;
    private InvoiceDialog invoiceDialog;
    private ItemDialog itemDialog;


    public Controller(InvoiceForm form){
        this.form = form;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println("Action Command is: " + actionCommand);
        switch (actionCommand){
            case"Load File" ->loadFile();
            case"Save File"-> saveFile();
            case "Add New Invoice"->createNewInvoice();
            case "Delete Invoice"->deleteInvoice();
            case"Add New Item"-> addNewItem();
            case "Delete Item"-> deleteItem();
            case  "Add New Invoice Ok" -> newInvoiceOk();
            case "Add New Invoice Cancel", "Add Item Cancel" -> addNewCancel();
            case "Add Item Ok" -> newItemOk();
        }
    }

//Add CSV Files
    private void loadFile(){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV",  "csv");
        fileChooser.setFileFilter(filter);

        try {
            int result =fileChooser.showOpenDialog(form);
           String[] loadedFileExt = filter.getExtensions();
                System.out.println(Arrays.toString(loadedFileExt));
            if(loadedFileExt[0] == "csv" &&result == JFileChooser.APPROVE_OPTION){
                File fileHeader = fileChooser.getSelectedFile();
                Path headerPath =  Paths.get(fileHeader.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headerPath);
                System.out.println("Invoices have been read");

                ArrayList<Invoice> invoicesArray = new ArrayList<>();
                ArrayList<Item> itemsArray = new ArrayList<>();
                for (String headerLine :headerLines){
                    String[] headerParts =  headerLine.split(",");
                    int id = Integer.parseInt(headerParts[0]);
                    String invoiceDate  = headerParts[1];
                    String customerName  = headerParts[2];
                    Invoice invoice = new Invoice(id, invoiceDate, customerName);

                    invoicesArray.add(invoice);
                }
                System.out.println(invoicesArray);
                System.out.println("After Adding Invoices");

                result = fileChooser.showOpenDialog(form);
                if(loadedFileExt[0] == "csv"&& result == JFileChooser.APPROVE_OPTION){
                    File itemFile = fileChooser.getSelectedFile();
                    Path itemPath = Paths.get(itemFile.getAbsolutePath());
                    List<String> itemItems = Files.readAllLines(itemPath);
                    System.out.println("Items have been read");

                    for (String itemItem :itemItems){
                        String[] itemParts =  itemItem.split(",");
                        int id = Integer.parseInt(itemParts[0]);
                        String itemName  = itemParts[1];
                        double itemPrice  = Double.parseDouble(itemParts[2]);
                        int count = Integer.parseInt(itemParts[3]);
                        Invoice inv = null;
                        for (Invoice invoice:invoicesArray){
                            if(invoice.getId()==id){
                                inv = invoice;
                                break;
                            }
                        }

                        Item item  = new Item(itemName,itemPrice, count,inv);
                        inv.getItems().add(item);
                        System.out.println(item);
                    }
                }
                form.setInvoices(invoicesArray);
                InvoiceTableModel invoiceTableModel = new InvoiceTableModel(invoicesArray);

                form.setInvoiceTableModel(invoiceTableModel);
                form.getInvoiceTable().setModel(invoiceTableModel);
                form.getInvoiceTableModel().fireTableDataChanged();
}

        } catch (IOException fileException){
            System.out.println("Invalid File Format");

        } catch (Exception e){
            e.printStackTrace();
        }
    }

//Save CSV Files
    private void saveFile(){
     ArrayList<Invoice> invoices = form.getInvoices();
     String headerLines ="";
     String itemLines ="";

     for (Invoice  invoice:invoices ){

         headerLines += invoice.getHeaderCSVFormat();
         headerLines +="\n";

        for(Item item:invoice.getItems()){
             itemLines += item.getItemsCSVFormat();
            itemLines += "\n";
        }
     }

     JFileChooser chooser = new JFileChooser();
     int result = chooser.showSaveDialog(form);
     try{
     if (result == JFileChooser.APPROVE_OPTION){
         File headerFile = chooser.getSelectedFile();
         FileWriter headerWriter = new FileWriter(headerFile);
         headerWriter.write(headerLines);
         headerWriter.flush();
         headerWriter.close();

         result = chooser.showSaveDialog(form);

         if (result == JFileChooser.APPROVE_OPTION){
             File itemsFile = chooser.getSelectedFile();
             FileWriter itemsWriter = new FileWriter(itemsFile);
             itemsWriter.write(itemLines);
             itemsWriter.flush();
             itemsWriter.close();

         }
     }
     } catch(IOException ioException){
         ioException.printStackTrace();
     }
    }

//Create New Invoice Method
    private void createNewInvoice(){
        invoiceDialog = new InvoiceDialog(form);
        invoiceDialog.setTitle("Add New Invoice");
        invoiceDialog.setVisible(true);
    }

//    Delete Invoice Method
    private void deleteInvoice(){
        int activeInvoice = form.getInvoiceTable().getSelectedRow();
        if(activeInvoice == -1){
            System.out.println("Please Select Invoice to Delete");
        }else{

            Invoice invoice = form.getInvoices().get(activeInvoice);
            form.getInvoices().remove(activeInvoice);
            form.getInvoiceTableModel().fireTableDataChanged();
        }
    }

//    Add New Item Method
    private void addNewItem(){
        itemDialog = new ItemDialog(form);
        itemDialog.setTitle("Add New Item");
        itemDialog.setVisible(true);

    }

//    Delete Item Method
    private void deleteItem(){
        int activeItem = form.getItemsTable().getSelectedRow();
        int activeInvoice = form.getInvoiceTable().getSelectedRow();
        if(activeInvoice!=-1 && activeItem != -1){
            Invoice invoice = form.getInvoices().get(activeInvoice);
            invoice.getItems().remove(activeItem);
            updateItemsTable(invoice);

        }
    }

    private void newInvoiceOk(){

        String customerName = invoiceDialog.getCustomerName().getText();
        LocalDate currentDate = LocalDate.now();
        System.out.println(currentDate);
        String invoiceDate = invoiceDialog.getInvoiceDate().getText();
        LocalDate invDate = LocalDate.parse(invoiceDate);
        System.out.println(invDate);
    try {
        if ((currentDate.isAfter(invDate))|| currentDate.equals(invDate) ){

            int invoiceID = form.getNextInvoiceID();

            Invoice invoice = new Invoice(invoiceID, invoiceDate, customerName);
            form.getInvoices().add(invoice);
            form.getInvoiceTableModel().fireTableDataChanged();
            invoiceDialog.setVisible(false);
            invoiceDialog.dispose();
            invoiceDialog = null;
        }
}
    catch (DateTimeParseException date){
        System.out.println("Invalid Date Format");
    }catch (DateTimeException futureDate){
        System.out.println("Invalid Date");
    }

    }

    private void newItemOk(){
        int activeInvoice = form.getInvoiceTable().getSelectedRow();
        String itemName = itemDialog.getItemName().getText();
        double itemPrice = Double.parseDouble(itemDialog.getItemPrice().getText());
        int itemCount = Integer.parseInt(itemDialog.getItemCount().getText());
        if(activeInvoice != -1){

            Invoice invoice = form.getInvoices().get(activeInvoice);
            Item item = new Item(itemName, itemPrice, itemCount, invoice);
            invoice.getItems().add(item);
            updateItemsTable(invoice);
        }
        addNewCancel();

    }

    private void addNewCancel(){
        itemDialog.setVisible(false);
        itemDialog.dispose();
        itemDialog = null;
    }

    private void updateItemsTable(Invoice invoice){
        ItemsTableModel itemsTableModel = new ItemsTableModel(invoice.getItems());
        form.getItemsTable().setModel(itemsTableModel);
        itemsTableModel.fireTableDataChanged();
        form.getInvoiceTableModel().fireTableDataChanged();

    }

}
