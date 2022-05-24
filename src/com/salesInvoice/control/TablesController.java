package com.salesInvoice.control;

import com.salesInvoice.model.Invoice;
import com.salesInvoice.model.ItemsTableModel;
import com.salesInvoice.view.InvoiceForm;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class TablesController implements ListSelectionListener {
    private InvoiceForm form;

    public TablesController(InvoiceForm form){
        this.form = form;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex =  form.getInvoiceTable().getSelectedRow();

        if(selectedIndex !=-1){

            System.out.println("You Have Selected: "+selectedIndex);
            Invoice currentInvoice = form.getInvoices().get(selectedIndex);
//        Get Labels Data

            form.getInvNumLabel().setText(""+currentInvoice.getId());
            form.getInvDateLabel().setText(""+currentInvoice.getInvoiceDate());
            form.getInvTotalLabel().setText(""+ currentInvoice.getTotal());
            form.getCustomerNameLabel().setText(currentInvoice.getCustomerName());

            ItemsTableModel itemsTableModel = new ItemsTableModel(currentInvoice.getItems());

            form.getItemsTable().setModel(itemsTableModel);
            itemsTableModel.fireTableDataChanged();
        }

    }
}
