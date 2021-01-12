/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataGateway;

import DbConnPkg.DbConnMgr;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author jawdan
 */
public class ItemDiscountDataGateway {
    
    public ItemDiscountDataGateway() {
        connection_ = DbConnMgr.getConnection();
    }
     // Destructor
    
    protected void finalize() throws Throwable {
  
        super.finalize();
        this.connection_.close();
        
    }
    public static ItemDiscountDataGateway getInstance(){
        
        if(itemDiscountDataGateway_ == null)
        {
            itemDiscountDataGateway_ = new ItemDiscountDataGateway();
        }
        return itemDiscountDataGateway_;
    }
    
        public ResultSet find(int itemID , int customerID){
        
        query_ = "SELECT * FROM ITEMDISCOUNT "
                + "WHERE ITEM_ID = ? AND CUSTOMER_ID  = ?";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
           preparedStatement_.setInt(1, itemID);
           preparedStatement_.setInt(2, customerID);
            result_ = preparedStatement_.executeQuery();
            
        } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
            
        }
        return result_;
    }
    
    public ResultSet findAll(){
        
        query_ = "SELECT * FROM ITEM_DISCOUNT";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            result_ = preparedStatement_.executeQuery(query_);
            
        } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
            
        }
        return result_;
    }
    
    public void insert( int itemID , int customerID , double amount){
        
        query_ = "INSERT INTO ITEM_DISCOUNT (ITEM_ID , CUSTOMER_ID , AMOUNT ) "
                + "VALUES( ? , ? , ? )";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            
            preparedStatement_.setInt(1, itemID);
            preparedStatement_.setInt(2, customerID);
            preparedStatement_.setDouble(3, amount);
                       
            preparedStatement_.executeUpdate();
            
        } catch (Exception ex) {
            
            JOptionPane.showMessageDialog(null, ex);
            
        }
    }
    
    public void updateByCustomer(int customerID , int itemID , double amount){
        
        query_ = "UPDATE ITEM_DISCOUNT SET ITEM_ID = ? , AMOUNT = ? "
                + "WHERE CUSTOMER_ID = ?";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            
            preparedStatement_.setInt(1, itemID);
            preparedStatement_.setDouble(2, amount);
            preparedStatement_.setInt(2, customerID);
            
            preparedStatement_.executeUpdate();
        } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);

        }
        
    }
    
    public void update(int itemID , int customerID , double amount){
        
        query_ = "UPDATE ITEM_DISCOUNT SET AMOUNT = ? "
                + "WHERE CUSTOMER_ID = ? "
                + "AND ITEM_ID = ?";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            
            preparedStatement_.setDouble(1, amount);
            preparedStatement_.setInt(2, customerID);
            preparedStatement_.setInt(3, itemID);
            
            preparedStatement_.executeUpdate();
        } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);

        }
        
    }
    
    public void delete(int itemID , int customerID){
        
        query_ = "DELETE FROM ITEM_DISCOUNT WHERE ITEM_ID = ? AND CUSTOMER_ID = ?";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            
            preparedStatement_.setInt(1, itemID);
            preparedStatement_.setInt(2, customerID);
            
            preparedStatement_.executeUpdate();
        } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);

        }
        
    }
    
    
    
    private String query_;
    private ResultSet result_;
    private Connection connection_;
    private Statement statement_;
    private PreparedStatement preparedStatement_;
    private static ItemDiscountDataGateway itemDiscountDataGateway_ = null;
}
