package com.y.models;

import com.y.controller.ActionsListener;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class All_InvoicesListTable extends AbstractTableModel{
    private ArrayList<All_InvoicesList> headerArrayList;

    public All_InvoicesListTable(ArrayList<All_InvoicesList> headerArrayList) {
        this.headerArrayList = headerArrayList;
    }

    public All_InvoicesListTable() {
       
    }
    
            
    @Override
    public int getRowCount() {
        return headerArrayList.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int i, int j) {
        All_InvoicesList header = headerArrayList.get(i);
        switch (j) {
            case 0:
                return header.getInvoiceNumber();
            case 1:
                return ActionsListener.simpleDateFormat.format(header.getInvoiceDate());
            case 2:
                return header.getCutomer();
            case 3:
                return header.getTotal();

        }
        return "";
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "No.";
            case 1:
                return "Date";
            case 2:
                return "Customer";
            case 3:
                return "Total";
        }
        return "";
    }
  
}
