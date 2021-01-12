/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModules;

import DataGateway.AreaDataGateway;
import DataGateway.CustomerDataGateway;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jawdan
 */
public class CustomerModule {

    public CustomerModule() {

        customerDataGateway_ = CustomerDataGateway.getInstance();
        areaDataGateway_ = AreaDataGateway.getInstance();

    }

    public static CustomerModule getInstance() {

        if (customerModule_ == null) {
            customerModule_ = new CustomerModule();
        }
        return customerModule_;
    }

    public void fillCustomerViewTable(JTable customerTable) {

        DefaultTableModel tableModel = (DefaultTableModel) customerTable.getModel();
        result_ = customerDataGateway_.getView();
        try{
            while(result_.next()){
            tableModel.addRow(new Object[] {result_.getInt(1) , 
                                                result_.getString(2) , result_.getString(5) , result_.getString(6)});
        }
        customerDataGateway_.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }

    }
    public void fillCustomerTable(JTable customerTable) {

        DefaultTableModel tableModel = (DefaultTableModel) customerTable.getModel();
        result_ = customerDataGateway_.getView();
        try{
            while(result_.next()){
            tableModel.addRow(new Object[] {result_.getInt(1) , result_.getString(2) ,
                                            result_.getString(3),result_.getString(4), 
                                            result_.getString(5) , result_.getString(6)});
        }
           customerDataGateway_.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }

    }
    public int getLastCustomerID(){
        result_ = customerDataGateway_.getLastCustomer();
        try{
            if(result_.next()){
                return result_.getInt(1);
            }
            
        customerDataGateway_.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        return -1;
    } 

    public void submit(String name,
            String cont_1,
            String cont_2,
            String areaID,
            String subAreaID) {
        try {
            customerDataGateway_.insert(name, cont_1, cont_2, Integer.valueOf(areaID),Integer.valueOf(subAreaID));
            customerDataGateway_.commit();
            JOptionPane.showMessageDialog(null, "Customer submitted SUCCESSFULLY !");
            customerDataGateway_.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            JOptionPane.showMessageDialog(null, "Customer submission FAILED !");
        }

    }
    public void submitCustomerProductDiscount(int customerID,
            int productID,
            double discount){
        try{
            customerDataGateway_.insertCustomerDiscount(customerID, productID, discount);
            customerDataGateway_.commit();
            JOptionPane.showMessageDialog(null, "discount for productID = "+productID+" submitted SUCCESSFULLY !");
            customerDataGateway_.close();
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            JOptionPane.showMessageDialog(null, "discount for productID = "+productID+" submission FAILED !");
        }
        
    }

    public void fillAreaComboBox(JComboBox<String> cboxArea) {
        result_ = areaDataGateway_.getAllAreas();
        try {
            while (result_.next()) {
                cboxArea.addItem(result_.getString("AREA_NAME"));
            }
            customerDataGateway_.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void fillSubAreaComboBox(JComboBox<String> cboxSubArea, String areaName) {
        int areaID = -1;
        result_ = areaDataGateway_.getAreaID(areaName);
        try {
            if (result_.next());
            areaID = result_.getInt("AREA_ID");
            
            customerDataGateway_.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        result_ = areaDataGateway_.getSubAreas(areaID);
        try {
            while (result_.next()) {
                cboxSubArea.addItem(result_.getString("SUB_AREA_NAME"));
            }
            customerDataGateway_.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void fillCustomerNameCbox(JComboBox<String> cboxCustomerName) {

        result_ = customerDataGateway_.getAll();
        try {
            while (result_.next()) {
                cboxCustomerName.addItem(result_.getString("CUSTOMER_NAME"));
            }
            customerDataGateway_.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public int getAreaId(String name){
        
       result_ = areaDataGateway_.getAreaID(name);
       try{
           if(result_.next()){
               customerDataGateway_.close();
            return result_.getInt("AREA_ID");
           }
           else{
               customerDataGateway_.close();
               return -1;
           }
       }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);
           return -1;
       }
    }
    public int getSubAreaId(String name){
        
       result_ = areaDataGateway_.getSubAreaID(name);
       try{
           if(result_.next()){
               customerDataGateway_.close();
            return result_.getInt("SUB_AREA_ID");
           }
           else{
               customerDataGateway_.close();
               return -1;
           }
       }
       catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);
           return -1;
       }
    }
    
    public void deleteCustomer(JTable customerTable) {
        int[] index = customerTable.getSelectedRows();
        int customerID;

        for (int i = 0; i < index.length; i++) {
            customerID = Integer.valueOf(customerTable.getModel().getValueAt(index[i], 0).toString());
            customerDataGateway_.delete(customerID);
        }
    }

    private ResultSet result_;
    private CustomerDataGateway customerDataGateway_;
    private AreaDataGateway areaDataGateway_;
    private static CustomerModule customerModule_ = null;

}
