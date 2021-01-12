/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModules;

import DataGateway.AreaDataGateway;
import DataGateway.CustomerDataGateway;
import DataGateway.EmployeeDataGateway;
import DataGateway.ProductDataGateway;
import DataGateway.SaleDataGateway;
import Utilities.TableService;
import com.toedter.calendar.JDateChooser;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kna
 */
public class SaleModule {

    public SaleModule() {
        saleDataGateway_ = SaleDataGateway.getInstance();
        productDataGateway_ = ProductDataGateway.getInstance();
        employeeDataGateway_ = EmployeeDataGateway.getInstance();
        customerDataGateway_ = CustomerDataGateway.getInstance();
        areaDataGateway_ = AreaDataGateway.getInstance();
    }

    public SaleModule(JTable tableSaleItem,
            JTable tableEmployeeName,
            JFormattedTextField fieldCustomerID,
            JTextField fieldCutomerName,
            JTextField fieldAreaName,
            JTextField fieldSubAreaName,
            JComboBox<String> cboxEmployeeName,
            JDateChooser dateChooser,
            JTextField fieldDiscount,
            JFormattedTextField fieldReceived,
            JTextField fieldTotal,
            JTextField fieldBalance) {

        saleDataGateway_ = SaleDataGateway.getInstance();
        productDataGateway_ = ProductDataGateway.getInstance();
        employeeDataGateway_ = EmployeeDataGateway.getInstance();
        customerDataGateway_ = CustomerDataGateway.getInstance();
        areaDataGateway_ = AreaDataGateway.getInstance();
        this.tableSaleItem = tableSaleItem;
        this.tableEmployeeName = tableEmployeeName;
        this.fieldCustomerID = fieldCustomerID;
        this.fieldCustomerName = fieldCutomerName;
        this.fieldAreaName = fieldAreaName;
        this.fieldSubAreaName = fieldSubAreaName;
        this.cboxEmployeeName = cboxEmployeeName;
        this.dateChooser = dateChooser;
        this.fieldDiscount = fieldDiscount;
        this.fieldReceived = fieldReceived;
        this.fieldTotal = fieldTotal;
        this.fieldNetTotal = fieldBalance;

    }

    public void fetchProductDetailsToSaleItemTable() throws Exception {

        int productID;
        int customerID = 0;
        Exception default_ex = new Exception();

        productID = Integer.valueOf(tableSaleItem.getModel().getValueAt(tableSaleItem.getSelectedRow(), COLUMN_ITEM_ID).toString());

        if (!TableService.isValuePresentInUnselectedRowsAtColumn(tableSaleItem, COLUMN_ITEM_ID, String.valueOf(productID))) {
            if (!fieldCustomerID.getText().equals("")) {
                customerID = Integer.valueOf(fieldCustomerID.getText());

                TableService.setSelectedRowEmpty(tableSaleItem);

                if (productDataGateway_.findByID(productID)) {

                    result_ = productDataGateway_.getProductDetail(productID);
                    try {
                        if (result_.next()) {
                            if (result_.getInt(4) > 0) {
                                tableSaleItem.getModel().setValueAt(result_.getInt(1), tableSaleItem.getSelectedRow(), COLUMN_ITEM_ID);
                                tableSaleItem.getModel().setValueAt(result_.getString(2), tableSaleItem.getSelectedRow(), COLUMN_ITEM_NAME);
                                tableSaleItem.getModel().setValueAt(result_.getDouble(3), tableSaleItem.getSelectedRow(), COLUMN_RATE);
                                tableSaleItem.getModel().setValueAt(result_.getInt(4), tableSaleItem.getSelectedRow(), COLUMN_AVAILABLE);
                            } else {
                                JOptionPane.showMessageDialog(null, result_.getString(2) + " is out of Stock");
                                throw default_ex;
                            }
                        }
                    } catch (Exception ex) {
                        if (!(ex == default_ex)) {
                            JOptionPane.showMessageDialog(null, ex);
                        }
                        throw ex;
                    }
                    result_ = customerDataGateway_.getProductDiscount(customerID, productID);
                    try {
                        if (result_.next()) {
                            tableSaleItem.getModel().setValueAt(result_.getDouble(1), tableSaleItem.getSelectedRow(), COLUMN_FIXED_DISC);
                        }
                    } catch (Exception ex) {
                        if (!(ex == default_ex)) {
                            JOptionPane.showMessageDialog(null, ex);
                        }
                        throw ex;
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "InValid productId = " + productID + " !");
                    TableService.setSelectedRowEmpty(tableSaleItem);
                    throw default_ex;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Customer Not Selected");
                TableService.setSelectedRowEmpty(tableSaleItem);
                throw default_ex;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Product already inserted !");
            TableService.setSelectedRowEmpty(tableSaleItem);
            throw default_ex;
        }
    }

    public void fillEmployeeNameComboBox() {
        result_ = employeeDataGateway_.getAll();
        try {
            while (result_.next()) {
                cboxEmployeeName.addItem(result_.getString("EMPLOYEE_NAME"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void fetchCustomerDetailsToFields() {
        int areaID = -1;
        int subAreaID = -1;

        try {
            result_ = customerDataGateway_.getCustomer(Integer.valueOf(fieldCustomerID.getText()));
            if (result_.next()) {
                fieldCustomerName.setText(result_.getString("CUSTOMER_NAME"));
                areaID = result_.getInt("AREA_ID");
                subAreaID = result_.getInt("SUB_AREA_ID");

                result_ = areaDataGateway_.getArea(areaID);
                if (result_.next()) {
                    fieldAreaName.setText(result_.getString("AREA_NAME"));
                }
                result_ = areaDataGateway_.getSubArea(subAreaID);
                if (result_.next()) {
                    fieldSubAreaName.setText(result_.getString("SUB_AREA_NAME"));
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Customer ID !");
                clearCustomerFields();
            }
        } catch (Exception ex) {
            if (ex.getMessage().contains("input string")) {
            } else {
                JOptionPane.showMessageDialog(null, ex);
            }
            clearCustomerFields();
        }

    }

    public void fetchCreditInvoicesToCreditInvoiceTable(JTable tableCreditInvoice) {
        ResultSet resultCreditSale, resultRecovery;
        Vector<Integer> saleIDs = new Vector<Integer>();

        DefaultTableModel tableModel = (DefaultTableModel) tableCreditInvoice.getModel();
        resultCreditSale = saleDataGateway_.getCreditSalesWithCustomerAndArea();

        try {
            boolean isResultRecoveryNotEmpty;
            while (resultCreditSale.next()) {
                saleIDs.add(resultCreditSale.getInt(1));
            }
            resultCreditSale.beforeFirst();
            resultRecovery = saleDataGateway_.getTotalRecovery(saleIDs);
            isResultRecoveryNotEmpty = resultRecovery.next(); 
            
            while (resultCreditSale.next()) {
                double recoveryAmount;
               
                if(isResultRecoveryNotEmpty){
                    if(resultRecovery.getInt(1) == resultCreditSale.getInt(1)) {
                        recoveryAmount = resultRecovery.getDouble(2);
                        isResultRecoveryNotEmpty = resultRecovery.next();                    
                    }
                    else {
                         recoveryAmount = 0;
                    }
                }
                else {
                     recoveryAmount = 0;
                }
                
                tableModel.addRow(new Object[]{resultCreditSale.getInt(1),
                                    new SimpleDateFormat("dd/MM/yy").format(resultCreditSale.getDate(2)), 
                                    resultCreditSale.getString(3), resultCreditSale.getString(4),
                                    resultCreditSale.getString(5),
                                    resultCreditSale.getDouble(6), recoveryAmount,
                                    resultCreditSale.getDouble(7)});
                    
            }
            saleDataGateway_.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        
    }

    public void submitRecoveyAmount(int saleID,
            Date recoveryDate,
            Double amount) {
        try {
            saleDataGateway_.insertSaleRecovery(saleID, recoveryDate, amount);
            saleDataGateway_.commit();
            JOptionPane.showMessageDialog(null, "Amount of  " + amount + "  submitted for sale id: " + saleID);
            saleDataGateway_.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void fillInvoiceRecoveyTable(JTable tableInvoiceRecovery, int saleID) {

        DefaultTableModel tableModel = (DefaultTableModel) tableInvoiceRecovery.getModel();
        result_ = saleDataGateway_.getRecoveries(saleID);
        try {
            while (result_.next()) {
                tableModel.addRow(new Object[]{result_.getDate(2), result_.getDouble(3)});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void submitSale() {

        int customerID;
        Vector<Integer> employeeIDs;
        int productID;
        Double netDiscount;
        Double total;
        Double balance;
        Double received;
        Date saleDate;
        int saleID;
        Double discount;
        Double amount;
        int quantity;
        int replace;
        int RowWithproductOutOfStock;

        try {
            RowWithproductOutOfStock = checkProductAvailabilities();
            if (RowWithproductOutOfStock == -1) {
                // Inserting Sale
                customerID = Integer.valueOf(fieldCustomerID.getText());
                netDiscount = Double.valueOf(fieldDiscount.getText());
                total = Double.valueOf(fieldTotal.getText()) - netDiscount;
                balance = Double.valueOf(fieldTotal.getText()) - netDiscount;
                received = Double.valueOf(fieldReceived.getText());
                saleDate = dateChooser.getDate();

                saleDataGateway_.insertSale(customerID, saleDate, netDiscount, total, balance);

                saleID = getLastSaleID();

                // Inserting SaleDetail
                for (int i = 0; i < tableSaleItem.getRowCount(); i++) {
                    productID = Integer.valueOf(tableSaleItem.getValueAt(i, COLUMN_ITEM_ID).toString());
                    quantity = Integer.valueOf(tableSaleItem.getValueAt(i, COLUMN_SOLD_QTY).toString());
                    replace = Integer.valueOf(tableSaleItem.getValueAt(i, COLUMN_REPLACE).toString());
                    discount = quantity * (Double.valueOf(tableSaleItem.getValueAt(i, COLUMN_FIXED_DISC).toString())
                            + Double.valueOf(tableSaleItem.getValueAt(i, COLUMN_EXTRA_DISC).toString()));
                    amount = quantity * Double.valueOf(tableSaleItem.getValueAt(i, COLUMN_RATE).toString());
                    saleDataGateway_.insertSaleDetail(saleID, productID, quantity, replace, discount, amount);
                }

                // Inserting SaleEmployee
                employeeIDs = getSelectedEmployeeIDs();
                for (int i = 0; i < employeeIDs.size(); i++) {
                    saleDataGateway_.insertSaleEmployee(saleID, employeeIDs.elementAt(i));
                }

                JOptionPane.showMessageDialog(null, "Sale Submission SUCCESSFULL !\n\nInvoice ID : " + saleID);

                // Inserting Recovery
                if (received > 0) {
                    submitRecoveyAmount(saleID, saleDate, received);
                }

                // committing
                saleDataGateway_.commit();
                saleDataGateway_.close();

                resetComponents();

            } else {
                JOptionPane.showMessageDialog(null, tableSaleItem.getValueAt(RowWithproductOutOfStock, COLUMN_ITEM_NAME).toString()
                        + " is out of stock");
                tableSaleItem.changeSelection(RowWithproductOutOfStock, COLUMN_SOLD_QTY, false, false);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            JOptionPane.showMessageDialog(null, "Sale Submission FAILED !");
            saleDataGateway_.rollBack();
        }

    }

    private void resetComponents() {

        DefaultTableModel tableModel = (DefaultTableModel) tableSaleItem.getModel();

        TableService.deleteAllRows((DefaultTableModel) tableSaleItem.getModel());
        tableModel.addRow(new Object[]{null, null, null, null, null, null, null, null});

        fieldCustomerID.setText("");
        fieldCustomerName.setText("");
        fieldAreaName.setText("");
        fieldSubAreaName.setText("");
        fieldReceived.setText("");

        fieldCustomerID.requestFocus();
    }

    private void clearCustomerFields() {
        fieldCustomerName.setText("");
        fieldCustomerName.setText("");
        fieldSubAreaName.setText("");
        fieldAreaName.setText("");
    }

    private int getLastSaleID() {
        try {
            result_ = saleDataGateway_.getLastSale();
            if (result_.next()) {
                return result_.getInt(1);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getStackTrace()[0].getLineNumber() + "\n" + ex);
        }
        return -1;
    }

    private Vector<Integer> getSelectedEmployeeIDs() {
        Vector<Integer> employeeIDs = new Vector<Integer>();

        for (int i = 0; i < tableEmployeeName.getRowCount(); i++) {
            try {
                result_ = employeeDataGateway_.getEmployee(tableEmployeeName.getModel().getValueAt(i, COLUMN_EMPLOYEE_NAME).toString());
                if (result_.next()) {
                    employeeIDs.add(result_.getInt(1));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getStackTrace()[0].getLineNumber() + "\n" + ex);
            }
        }
        return employeeIDs;
    }

    private int checkProductAvailabilities() {
        int soldQuantity;
        int replace;
        int available;
        for (int i = 0; i < tableSaleItem.getRowCount(); i++) {
            soldQuantity = Integer.valueOf(tableSaleItem.getModel().getValueAt(i, COLUMN_SOLD_QTY).toString());
            replace = Integer.valueOf(tableSaleItem.getModel().getValueAt(i, COLUMN_REPLACE).toString());
            available = Integer.valueOf(tableSaleItem.getModel().getValueAt(i, COLUMN_AVAILABLE).toString());
            if ((soldQuantity + replace) > available) {
                return i;
            }
        }
        return -1;
    }

    //View Data Controllers
    private JTable tableSaleItem;
    private JTable tableEmployeeName;
    JFormattedTextField fieldCustomerID;
    JTextField fieldCustomerName;
    JTextField fieldAreaName;
    JTextField fieldSubAreaName;
    private JComboBox<String> cboxEmployeeName;
    private JDateChooser dateChooser;
    private JTextField fieldDiscount;
    private JFormattedTextField fieldReceived;
    private JTextField fieldTotal;
    private JTextField fieldNetTotal;

    private ResultSet result_;
    private SaleDataGateway saleDataGateway_;
    private ProductDataGateway productDataGateway_;
    private EmployeeDataGateway employeeDataGateway_;
    private CustomerDataGateway customerDataGateway_;
    private AreaDataGateway areaDataGateway_;

    public static final int COLUMN_ITEM_ID = 0;
    public static final int COLUMN_ITEM_NAME = 1;
    public static final int COLUMN_SOLD_QTY = 2;
    public static final int COLUMN_REPLACE = 3;
    public static final int COLUMN_RATE = 4;
    public static final int COLUMN_FIXED_DISC = 5;
    public static final int COLUMN_EXTRA_DISC = 6;
    public static final int COLUMN_AVAILABLE = 7;
    public static final int COLUMN_EMPLOYEE_NAME = 0;
}
