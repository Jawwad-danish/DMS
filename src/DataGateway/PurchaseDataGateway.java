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
import javax.swing.JOptionPane;

/**
 *
 * @author kna
 */
public class PurchaseDataGateway {
    // Constructor

    public PurchaseDataGateway() {

        this.connection_ = DbConnMgr.getConnection();

    }

    // Destructor
    protected void finalize() throws Throwable {

        super.finalize();
        this.connection_.close();

    }

    public static PurchaseDataGateway getInstance() {
        if (purchaseDataGateway_ == null) {
            purchaseDataGateway_ = new PurchaseDataGateway();
        }
        return purchaseDataGateway_;
    }

    public ResultSet getLastPurchase(){
        query_ = "SELECT * FROM PURCHASE WHERE PURCHASE_ID = (SELECT MAX(PURCHASE_ID) FROM PURCHASE)";

        try {

            preparedStatement_ = connection_.prepareStatement(query_);
            result_ = preparedStatement_.executeQuery();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return result_;
    }
    
    public void insertPurchase(Date purchaseDate,
            int vendorID,
            Double Amount) throws Exception{
        query_ = "INSERT INTO PURCHASE (PURCHASE_DATE , VENDOR_ID , AMOUNT) "
                + "VALUES (?,?,?)";
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setDate(1, Conversion.convertToSqlDate(purchaseDate));
            preparedStatement_.setInt(2, vendorID);
            preparedStatement_.setDouble(3, Amount);

            preparedStatement_.executeQuery();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void insertPurchaseDetail(int purchaseID,
            int productID,
            int quantity,
            int replace,
            double amount) throws Exception {

        query_ = "INSERT INTO PURCHASE_DETAIL (PURCHASE_ID , PRODUCT_ID , QUANTITY , REPLACE, AMOUNT) "
                + "VALUES (?,?,?,?,?)";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setInt(1, purchaseID);
            preparedStatement_.setInt(2, productID);
            preparedStatement_.setInt(3, quantity);
            preparedStatement_.setInt(4, replace);
            preparedStatement_.setDouble(5, amount);

            preparedStatement_.executeQuery();
        } catch (Exception ex) {
            throw ex;
        }

    }

    public void update() {

    }

    public void delete(int ID) {

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

    private String query_;
    private ResultSet result_;
    private Connection connection_;
    private Statement statement_;
    private PreparedStatement preparedStatement_;
    private static PurchaseDataGateway purchaseDataGateway_ = null;

}
