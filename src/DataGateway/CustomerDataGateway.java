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
public class CustomerDataGateway {

    // Constructor
    public CustomerDataGateway() {

        this.connection_ = DbConnMgr.getConnection();

    }

    // Destructor
    protected void finalize() throws Throwable {

        super.finalize();
        this.connection_.close();

    }

    public static CustomerDataGateway getInstance() {
        if (customerDataGateway_ == null) {
            customerDataGateway_ = new CustomerDataGateway();
        }
        return customerDataGateway_;
    }
    // Method to Find Record by ID

    public ResultSet find(int ID) {

        query_ = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = ?";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setInt(1, ID);
            result_ = preparedStatement_.executeQuery();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        return result_;
    }

    // Method to Find all customer names
    public ResultSet getCustomerName() {

        query_ = "SELECT CUSTOMER_NAME FROM CUSTOMER";
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            result_ = preparedStatement_.executeQuery();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return result_;
    }

    public ResultSet getProductDiscount(int customerID, int productID) {
        query_ = "SELECT DISCOUNT_AMOUNT FROM CUSTOMER_PRODUCT_DISCOUNT "
                + "WHERE PRODUCT_ID = ? AND CUSTOMER_ID = ?";
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setInt(1, productID);
            preparedStatement_.setInt(2, customerID);
            result_ = preparedStatement_.executeQuery();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return result_;
    }

    public ResultSet getCustomer(String customerName) {
        query_ = "SELECT * FROM CUSTOMER WHERE CUSTOMER_NAME = ?";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setString(1, customerName);
            result_ = preparedStatement_.executeQuery();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return result_;
    }

    public ResultSet getCustomer(int customerID) {
        query_ = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = ?";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setInt(1, customerID);
            result_ = preparedStatement_.executeQuery();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return result_;
    }
    

    // Method to Find all records
    public ResultSet getAll() {

        query_ = "SELECT * FROM CUSTOMER";
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            result_ = preparedStatement_.executeQuery();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
      
        return result_;

    }

    public ResultSet getView() {

        query_ = "SELECT DISTINCT C.CUSTOMER_ID AS ID , C.CUSTOMER_NAME AS NAME , C.CONTACT_NO_1 AS CONTACT#1 , "
                + "C.CONTACT_NO_2 AS CONTACT#2 ,A.AREA_NAME AS AREA , S.SUB_AREA_NAME AS SUB_AREA "
                + "FROM CUSTOMER C ,AREA A , SUB_AREA S WHERE A.AREA_ID = C.AREA_ID AND C.SUB_AREA_ID = S.SUB_AREA_ID "
                + "ORDER BY C.CUSTOMER_NAME ASC";
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            result_ = preparedStatement_.executeQuery();
            //preparedStatement_.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return result_;

    }

    public ResultSet getLastCustomer(){
        query_ = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = (SELECT MAX(CUSTOMER_ID) FROM CUSTOMER)";
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            result_ = preparedStatement_.executeQuery();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return result_;
    }
    public void insertCustomerDiscount(int customerID,
            int productID,
            double discount)throws Exception {

        query_ = "INSERT INTO CUSTOMER_PRODUCT_DISCOUNT ( CUSTOMER_ID , PRODUCT_ID , DISCOUNT_AMOUNT )"
                + "VALUES ( ? , ? , ? )";
        try {
            preparedStatement_ = connection_.prepareStatement(query_);

            preparedStatement_.setInt(1, customerID);
            preparedStatement_.setInt(2, productID);
            preparedStatement_.setDouble(3, discount);

            preparedStatement_.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void insert(String name,
            String cont_1,
            String cont_2,
            int areaID,
            int subAreaID) throws Exception {

        query_ = "INSERT INTO CUSTOMER ( CUSTOMER_NAME , CONTACT_NO_1 , CONTACT_NO_2 , AREA_ID , SUB_AREA_ID)"
                + "VALUES ( ? , ? , ? , ? , ? )";
        try {
            preparedStatement_ = connection_.prepareStatement(query_);

            preparedStatement_.setString(1, name);
            preparedStatement_.setString(2, cont_1);
            preparedStatement_.setString(3, cont_2);
            preparedStatement_.setInt(4, areaID);
            preparedStatement_.setInt(5, subAreaID);

            preparedStatement_.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void update(int ID,
            String name,
            String cont_1,
            String cont_2,
            int areaID) {

        query_ = "UPDATE CUSTOMER SET CUSTOMER_NAME = ? , CONTACT_NO_1 = ? , CONTACT_NO_2  = ? , AREA_ID = ? "
                + "WHERE CUSTOMER_ID = ?";
        try {
            preparedStatement_ = connection_.prepareStatement(query_);

            preparedStatement_.setString(1, name);
            preparedStatement_.setString(2, cont_1);
            preparedStatement_.setString(3, cont_2);
            preparedStatement_.setInt(4, areaID);
            preparedStatement_.setInt(5, ID);

            preparedStatement_.executeUpdate();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void delete(int ID) {
        query_ = "DELETE FROM CUSTOMER WHERE CUSTOMER_ID = ?";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);

            preparedStatement_.setInt(1, ID);

            preparedStatement_.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void commit() {
        try {
            connection_.commit();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void rollBack() {
        try {
            connection_.rollback();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }
    
    public void close(){
        try{
            preparedStatement_.close();
            result_.close();
            connection_.close();
            connection_ = DbConnMgr.getConnection();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private String query_;
    private ResultSet result_;
    private Connection connection_;
    private Statement statement_;
    private PreparedStatement preparedStatement_;
    private static CustomerDataGateway customerDataGateway_ = null;

}
