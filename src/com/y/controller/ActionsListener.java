package com.y.controller;

import com.y.models.All_InvoicesList;
import com.y.models.All_InvoicesListTable;
import com.y.models.Items;
import com.y.models.ItemsTable;
import com.y.view.SIGA_GUI;
import com.y.view.CreateNewInvoice;
import com.y.view.CreateNewLine;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class ActionsListener implements ActionListener {

    private SIGA_GUI appFrame;
    private CreateNewInvoice createNewInvoice;
    private CreateNewLine createNewLine;
   
    public ActionsListener(SIGA_GUI appFrame){
        this.appFrame = appFrame;
    }
   
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Load File":
                loadFile();
                break;
            case "Save File":
                saveFile();
                break;
            case "Create New Invoice":
                createNewInvoice();
                break;
            case "Create Invoice":
                createInvoice();
                break;
            case "Cancel Invoice":
                cancelInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "Create New Line":
                createNewLine();
                break;
            case "Create Line":
                createLine();
                break;
            case "Cancel Line":
                cancelLine();
            case "Delete Line":
                deleteLine();
                break;
        }
    }
    private void loadFile() {
        JOptionPane.showMessageDialog(appFrame, "Select Location for Headers file Loading.","Load Headers", JOptionPane.INFORMATION_MESSAGE);
        JFileChooser fileChooser = new JFileChooser();
        try {
            int response = fileChooser.showOpenDialog(appFrame);
            if (response == JFileChooser.APPROVE_OPTION){
                File headerFile = fileChooser.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());

                ArrayList<All_InvoicesList> headers = new ArrayList<>();
                List<String> allHeaders = Files.readAllLines(headerPath);
                
                for (String oneHeader : allHeaders){
                   String[] array = oneHeader.split(",");
                   String col1 = array[0];
                   String col2 = array[1];
                   String col3 = array[2];
                   
                   int no = Integer.parseInt(col1);
                   Date date = simpleDateFormat.parse(col2);
                   String cutomer = col3;
                   All_InvoicesList header = new All_InvoicesList(no, date, cutomer);
                   headers.add(header);
                }   
            appFrame.setHeaderArrayList(headers);
            
            JOptionPane.showMessageDialog(appFrame, "Select Location for Lines file Loading.","Load Lines", JOptionPane.INFORMATION_MESSAGE);
            response = fileChooser.showOpenDialog(appFrame);
            if (response == JFileChooser.CANCEL_OPTION){
                JOptionPane.showMessageDialog(appFrame, "CSV File for Line not Selected!", "No Lines File", JOptionPane.ERROR_MESSAGE);
            }
            else if (response == JFileChooser.APPROVE_OPTION){
                File lineFile = fileChooser.getSelectedFile();
                Path linePath = Paths.get(lineFile.getAbsolutePath());
 
                ArrayList<Items> items = new ArrayList<>();
                List<String> allLines = Files.readAllLines(linePath);

                for (String oneLine : allLines){
                    String[] array = oneLine.split(",");
                    String col1 = array[0];
                    String col2 = array[1];
                    String col3 = array[2];
                    String col4 = array[3];
                    
                    int no = Integer.parseInt(col1);
                    String itemName = col2;
                    double itemPrice = Double.parseDouble(col3);
                    int count = Integer.parseInt(col4);
                    All_InvoicesList header = appFrame.getNo(no);
                    
                    Items line = new Items(header, itemName, itemPrice,count);
                    header.getLines().add(line); 
                }            
                appFrame.setItemsArrayList(items);

            }
                All_InvoicesListTable headerTable = new All_InvoicesListTable(headers);
                appFrame.setHeaderTable(headerTable);
                appFrame.getInvoicesTable().setModel(headerTable);                   
        }
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(appFrame, "Could not open this file! \n Not a CSV file.", "Invalid File", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException exception) {
                JOptionPane.showMessageDialog(appFrame, "Could not open this file \n Not correctly formatted.", "Invalid File", JOptionPane.ERROR_MESSAGE);
            } catch (ArrayIndexOutOfBoundsException exception){
                JOptionPane.showMessageDialog(appFrame, "Could not open this file \n Not correctly formatted.", "Invalid File", JOptionPane.ERROR_MESSAGE);
                appFrame.getInvoiceItemsTable().removeAll();
            }
        }
    private void saveFile() {
        JOptionPane.showMessageDialog(appFrame, "Select location for Headers file saving.","Save Headers", JOptionPane.INFORMATION_MESSAGE);
        JFileChooser fileChooser = new JFileChooser();
        try {
        int response = fileChooser.showSaveDialog(appFrame);
        if (response == JFileChooser.APPROVE_OPTION){
            File headerFile = fileChooser.getSelectedFile();
            FileWriter fileHeaderWriter = new FileWriter(headerFile);
            ArrayList<All_InvoicesList> headersArrayList = appFrame.getHeaderArrayList();
            String headers = "";
            String lines = "";
            
            for (All_InvoicesList header: headersArrayList){
                headers += header.toString();
                headers += "\n";
                for (Items item: header.getLines()){
                lines += item.toString();
                lines += "\n";
                }
            }
            JOptionPane.showMessageDialog(appFrame, "Select location for Lines file saving.","Save Lines", JOptionPane.INFORMATION_MESSAGE);
            response = fileChooser.showSaveDialog(appFrame);
            File lineFile = fileChooser.getSelectedFile();
            FileWriter fileLineWriter = new FileWriter(lineFile);

            headers = headers.substring(0, headers.length()-1);
            fileHeaderWriter.write(headers);
            fileHeaderWriter.close();
            
            lines = lines.substring(0, lines.length()-1);
            fileLineWriter.write(lines);
            fileLineWriter.close();
            
            
            JOptionPane.showMessageDialog(appFrame, "Files Were Saved Successfully","Files Saved", JOptionPane.INFORMATION_MESSAGE);
            if (headersArrayList == null) {
                throw new Exception();
            }
         }      
        }catch (IOException exception) {
            JOptionPane.showMessageDialog(appFrame, "Could not save this File!", "Invalid File", JOptionPane.ERROR_MESSAGE);
        } catch (Exception exception){
            JOptionPane.showMessageDialog(appFrame, "Nothing to save! \n New files were added. \n Please load it to continue", "No Data", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createNewInvoice() {
        createNewInvoice = new CreateNewInvoice(appFrame);
        createNewInvoice.setVisible(true);
       try { 
        int invoiceNo = 0;
        for (All_InvoicesList header : appFrame.getHeaderArrayList()){
        if (header.getInvoiceNumber()> invoiceNo)
            invoiceNo = header.getInvoiceNumber();
            }
        invoiceNo++;
        createNewInvoice.getInvoiceNoValueLabel().setText("" + invoiceNo);
       } catch (Exception exception){
           JOptionPane.showMessageDialog(appFrame,"Please select Headers & Lines files!", "Files Were Not Selected", JOptionPane.ERROR_MESSAGE);
           createNewInvoice.setVisible(false);
     }
    }  
    
    private void createInvoice() {
       int invoiceNo = 0;
       for (All_InvoicesList header : appFrame.getHeaderArrayList()){
        if (header.getInvoiceNumber()> invoiceNo)
            invoiceNo = header.getInvoiceNumber();
            }
       invoiceNo++;
       String cutstomerName = createNewInvoice.getCustomerNameTextField().getText();
       String date = createNewInvoice.getInvoiceDateTextField().getText();
       Date invoiceDate = new Date();
       try {
           invoiceDate = simpleDateFormat.parse(date);
         } catch (ParseException exception) {
           JOptionPane.showMessageDialog(appFrame, "Please use dd-MM-yyyy format! \n Using today!", "Invalid Date Format", JOptionPane.ERROR_MESSAGE);
        } 
       if ("".equals(cutstomerName)){
       JOptionPane.showMessageDialog(appFrame, "Please enter customer name!", "Invalid Customer Name", JOptionPane.ERROR_MESSAGE);
       }else{
       All_InvoicesList header = new All_InvoicesList(invoiceNo, invoiceDate, cutstomerName);
       appFrame.getHeaderArrayList().add(header);
       appFrame.getHeaderTable().fireTableDataChanged();
       createNewInvoice.dispose();
       createNewInvoice = null;
       }   
    }

    private void cancelInvoice() {
        createNewInvoice.dispose();
        createNewInvoice = null;
    }
    
        
    private void deleteInvoice() {
        int selectedInvoiceIndex = appFrame.getInvoicesTable().getSelectedRow();
           if (selectedInvoiceIndex != -1){ 
            appFrame.getHeaderArrayList().remove(selectedInvoiceIndex);
            appFrame.getHeaderTable().fireTableDataChanged();
            appFrame.getInvoiceItemsTable().setModel(new ItemsTable(new ArrayList<Items>()));
            appFrame.getInvoiceNumerValueLabel().setText("");
            appFrame.getCustomerNameValueLabel().setText("");
            appFrame.getInvoiceDateValueLabel().setText("");
            appFrame.getInvoiceTotalValueLabel().setText("");
        }
    }
    private void createNewLine() {
        createNewLine = new CreateNewLine(appFrame);
        createNewLine.setVisible(true);    
    }
    
    private void createLine() {
        createNewLine.setVisible(false);
        int selectedInvoice = appFrame.getInvoicesTable().getSelectedRow();
        String itemName = createNewLine.getItemNameTextField().getText();
        String itemPriceString = createNewLine.getItemPriceTextField().getText();
        String countString = createNewLine.getCountTextField().getText();
        double itemPrice = 0;
        int count = 0;
        try {
            itemPrice = Double.parseDouble(itemPriceString); 
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(appFrame, "Please enter a Number for Price!", "Invalid Price", JOptionPane.ERROR_MESSAGE);
       } try {
             count = Integer.parseInt(countString);
       } catch (NumberFormatException exception){
           JOptionPane.showMessageDialog(appFrame, "Please enter an Integer for Count!", "Invalid Count", JOptionPane.ERROR_MESSAGE);
       } 

       if (selectedInvoice != -1 && createNewLine != null){
            All_InvoicesList invoiceNo = appFrame.getHeaderArrayList().get(selectedInvoice);
            Items item = new Items(invoiceNo, itemName, itemPrice, count);
            appFrame.getItemsArrayList().add(item);
            appFrame.getHeaderTable().fireTableDataChanged();
            appFrame.getInvoicesTable().setRowSelectionInterval(selectedInvoice, selectedInvoice);
            appFrame.getItemsTable().fireTableDataChanged();
            createNewLine.dispose();
            createNewLine = null;
        }
    }

    private void cancelLine() {
        createNewLine.dispose();
        createNewLine = null;
    }
    
    private void deleteLine() {
        int selectedInvoice = appFrame.getInvoicesTable().getSelectedRow(); 
        int selectedItemIndex = appFrame.getInvoiceItemsTable().getSelectedRow();
           if (selectedItemIndex != -1){ 
            appFrame.getItemsArrayList().remove(selectedItemIndex);
            appFrame.getHeaderTable().fireTableDataChanged();
            appFrame.getInvoicesTable().setRowSelectionInterval(selectedInvoice, selectedInvoice);
            appFrame.getItemsTable().fireTableDataChanged();
        }
    }
}
