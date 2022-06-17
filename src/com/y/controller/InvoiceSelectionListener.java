package com.y.controller;

import static com.y.controller.ActionsListener.simpleDateFormat;
import com.y.models.All_InvoicesList;
import com.y.models.Items;
import com.y.models.ItemsTable;
import com.y.view.SIGA_GUI;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class InvoiceSelectionListener implements ListSelectionListener{

    private SIGA_GUI appFrame;

    public InvoiceSelectionListener(SIGA_GUI appFrame) {
        this.appFrame = appFrame;
    }

    public InvoiceSelectionListener() {
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedInoviceIndex = appFrame.getInvoicesTable().getSelectedRow();
        if (selectedInoviceIndex != -1){
            All_InvoicesList selectedInvoice = appFrame.getHeaderArrayList().get(selectedInoviceIndex);
            ArrayList<Items> itemArrayList = selectedInvoice.getLines();
            ItemsTable itemsTable = new ItemsTable(itemArrayList);
            appFrame.setItemsArrayList(itemArrayList);
            appFrame.getInvoiceItemsTable().setModel(itemsTable);
            appFrame.getInvoiceNumerValueLabel().setText("" + selectedInvoice.getInvoiceNumber());
            appFrame.getCustomerNameValueLabel().setText(selectedInvoice.getCutomer());
            appFrame.getInvoiceDateValueLabel().setText(simpleDateFormat.format(selectedInvoice.getInvoiceDate()));
            appFrame.getInvoiceTotalValueLabel().setText("" + selectedInvoice.getTotal());
        }      
    }
    
}
