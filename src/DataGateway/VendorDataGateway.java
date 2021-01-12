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
public class VendorDataGateway {
    
    // Constructor
    public VendorDataGateway() {
        connection_ = DbConnMgr.getConnection();
    }
    
    // Destructor
    protected void finalize() throws Throwable {
  
        super.finalize();
        this.connection_.close();
        
    }
    
    public static VendorDataGateway getInstance(){
        
        if(vendorDataGateway_ == null)
        {
            vendorDataGateway_ = new VendorDataGateway();
        }
        return vendorDataGateway_;
    }
    
    public ResultSet getVendor(int ID){
        
        query_ = "SELECT * FROM VENDOR "
                + "WHERE VENDOR_ID = ?";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setInt(1, ID);
            result_ = preparedStatement_.executeQuery();
            
        } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
            
        }
        return result_;
    }
   
    public ResultSet getVendor(String vendorName){
        
        query_ = "SELECT * FROM VENDOR "
                + "WHERE VENDOR_NAME = ?";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setString(1, vendorName);
            result_ = preparedStatement_.executeQuery();
            
        } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
            
        }
        return result_;
    }
    
    public ResultSet getAll(){
        
        query_ = "SELECT * FROM VENDOR";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            result_ = preparedStatement_.executeQuery();
            
        } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
            
        }
        return result_;
    }
    
    public void insert( String name ){
        
        query_ = "INSERT INTO VENDOR (VENDOR_NAME ) "
                + "VALUES( ?  )";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            
            preparedStatement_.setString(1, name);
                       
            preparedStatement_.executeUpdate();
            
        } catch (Exception ex) {
            
            JOptionPane.showMessageDialog(null, ex);
            
        }
    }
    
    public void update(int ID , String name ){
        
        query_ = "UPDATE VENDOR SET VENDOR_NAME = ? "
                + "WHERE VENDOR_ID = ?";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            
            preparedStatement_.setString(1, name);
            preparedStatement_.setInt(2, ID);
            
            preparedStatement_.executeUpdate();
        } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);

        }
        
    }
    
    public void delete(int ID){
        
        query_ = "DELETE FROM VENDOR WHERE VENDOR_ID = ?";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            
            preparedStatement_.setInt(1, ID);
            
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
    private static VendorDataGateway vendorDataGateway_ = null;
}
