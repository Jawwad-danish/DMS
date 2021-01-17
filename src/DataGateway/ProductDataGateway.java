/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataGateway;

import DbConnPkg.DbConnMgr;
import Utilities.Conversion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author jawdan
 */
class ProductRecord{
    
    int productID;
    String productName;
    int vendorID;
    String vendorName;
    double salePrice;
    double purchasePrice;
    double margin;

    public ProductRecord(int productID,
                        String productName,
                        int vendorID,
                        String vendorName,
                        double salePrice,
                        double purchasePrice,
                        double margin) {
        this.productID = productID;
        this.productName = productName;
        this.vendorID = vendorID;
        this.vendorName = vendorName;
        this.salePrice = salePrice;
        this.purchasePrice = purchasePrice;
        this.margin = margin;
    }
    
    
}

public class ProductDataGateway {
    
    public ProductDataGateway() {
        connection_ = DbConnMgr.getConnection();
        ProductRecordMap = new HashMap();
        initialize();
    }
     // Destructor
    
    protected void finalize() throws Throwable {
  
        super.finalize();
        this.connection_.close();
        
    }
    public void initialize(){
        
        int key;
        ProductRecord row;   
        
        ProductRecordMap.clear();        
        result_ = getProductRecord();
        try{
            while(result_.next()){
                row = new ProductRecord(result_.getInt(1), 
                                        result_.getString(2),
                                        result_.getInt(3),
                                        result_.getString(4), 
                                        result_.getDouble(5), 
                                        result_.getDouble(6),
                                        result_.getDouble(7));
                
                key = row.productID;
                
                ProductRecordMap.put(key, row);
        }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        
    }
    public static ProductDataGateway getInstance(){
        
        if(productDataGateway_ == null)
        {
            productDataGateway_ = new ProductDataGateway();
        }
        return productDataGateway_;
    }
  
    public boolean findByID(int ID){
        return ProductRecordMap.containsKey(ID);
    }
    
    private ResultSet getProductRecord(){
        
        query_ = "SELECT p.product_id , p.product_name , p.vendor_id , v.vendor_name , p.sale_price ,"
                 +"p.purchase_price , p.margin FROM product p ,vendor v "
                 + "WHERE p.vendor_id = v.vendor_id";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            result_ = preparedStatement_.executeQuery();
            
        } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
            
        }
            
        return result_;
    }
    public ResultSet getProduct(int vendorID){
            query_ = "SELECT * FROM PRODUCT WHERE VENDOR_ID = ?";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setInt(1, vendorID);
            result_ = preparedStatement_.executeQuery();
            
        } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
            
        }
            
        return result_;
    }
    public ResultSet getAll(){
        
            query_ = "SELECT * FROM PRODUCT";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            result_ = preparedStatement_.executeQuery();
            
        } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
            
        }
            
        return result_;
    }
    
    public ResultSet getProductDetail(int ID){
        query_ = "SELECT P.PRODUCT_ID , P.PRODUCT_NAME, P.SALE_PRICE, S.QUANTITY, P.PURCHASE_PRICE "
                + "FROM PRODUCT P, STORE S "
                + "WHERE P.PRODUCT_ID = S.PRODUCT_ID "
                + "AND S.CONDITION = 'FRESH' "
                + "AND P.PRODUCT_ID = ?";
        try{
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setInt(1, ID);
            result_ = preparedStatement_.executeQuery();
        }catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
            
        }
        return result_;
    }
    
    public ResultSet getView(){
        
        query_ = "SELECT P.PRODUCT_ID AS ID, P.PRODUCT_NAME AS DESCRIPTION, P.SALE_PRICE AS PRICE, S.QUANTITY AS AVAILABLE " +
                 "FROM PRODUCT P , STORE S " +
                 "WHERE P.PRODUCT_ID = S.PRODUCT_ID " +
                 "AND S.CONDITION = 'FRESH'";
        try{
            preparedStatement_ = connection_.prepareStatement(query_);
            result_ = preparedStatement_.executeQuery();
        }catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
            
        }
        return result_;
    }
    
    public void insert( String description ,
                        int brandID , 
                        double purchasePrice , 
                        double salePrice , 
                        double margin){
        
        query_ = "INSERT INTO PRODUCT ( DESCRIPTION , VENDOR_ID , PURCHASE_PRICE , SALE_PRICE , MARGIN ) "
                + "VALUES(? , ? , ? , ? , ?)";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            
            preparedStatement_.setString(1, description);
            preparedStatement_.setInt(2, brandID);
            preparedStatement_.setDouble(3, salePrice);
            preparedStatement_.setDouble(4,  purchasePrice);
            preparedStatement_.setDouble(5, margin);
            
            preparedStatement_.executeUpdate();
            
        } catch (Exception ex) {
            
            JOptionPane.showMessageDialog(null, ex);
            
        }
    }
    
    public void update(int ID ,
                       String description ,
                       int brandID , 
                       double purchasePrice , 
                       double salePrice ,
                       double margin){
        
        query_ = "UPDATE PRODUCT SET DESCRIPTION = ? , VENDOR_ID = ? , PURCHASE_PRICE = ? , SALE_PRICE = ? , MARGIN = ?"
                + "WHERE PRODUCT_ID = ?";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            
            preparedStatement_.setString(1, description);
            preparedStatement_.setInt(2, brandID);
            preparedStatement_.setDouble(3, purchasePrice);
            preparedStatement_.setDouble(4,  salePrice);
            preparedStatement_.setDouble(5, margin);
            preparedStatement_.setInt(6, ID);
            
            
            preparedStatement_.executeUpdate();
        } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);

        }
        
    }
    
    public void delete(int ID){
        
        query_ = "DELETE FROM PRODUCT WHERE PRODUCT_ID = ?";
        
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
    private static ProductDataGateway productDataGateway_ = null;
    private Map ProductRecordMap;
}
