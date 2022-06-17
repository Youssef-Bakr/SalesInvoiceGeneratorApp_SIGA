package com.y.models;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ItemsTable extends AbstractTableModel {
    private ArrayList<Items> itemsArrayList;

    public ItemsTable(ArrayList<Items> lineArrayList) {
        this.itemsArrayList = lineArrayList;
    }
    
    
    public ItemsTable() {
    }
    
    

    @Override
    public int getRowCount() {
        return itemsArrayList.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int i, int j) {
      Items line = itemsArrayList.get(i);
        switch (j) {
            case 0:
                return i + 1 ;
            case 1:
                return line.getItemName();
            case 2:
                return line.getItemPrice();
            case 3:
                return line.getItemCount();
            case 4:
                return line.getItemTotal();
        }
        return "";
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "No.";
            case 1:
                return "Item Name";
            case 2:
                return "Item Price";
            case 3:
                return "Count";
            case 4:
                return "Item Total";
        }
        return "";
    }
    
    
    
    
    
    
}
