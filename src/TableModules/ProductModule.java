/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModules;

import DataGateway.ProductDataGateway;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kna
 */
public class ProductModule {
     public ProductModule(){
        
        productDataGateway_ = ProductDataGateway.getInstance();
        
    }
    
    public static ProductModule getInstance(){
        
        if(productModule_ == null)
        {
            productModule_ = new ProductModule();
        }
        return productModule_;
    }
    
    
    
    public void fillProductViewTable(DefaultTableModel tableModelCustomerListTable){
        
        result_ = productDataGateway_.getView();
        try{
            while(result_.next()){
            tableModelCustomerListTable.addRow(new Object[] {result_.getInt(1) , 
                                                result_.getString(2) , result_.getDouble(3) , result_.getInt(4)});
        }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    
    
    
    private ResultSet result_;
    private ProductDataGateway productDataGateway_;
    private static ProductModule productModule_ = null;
    
    
    
}



