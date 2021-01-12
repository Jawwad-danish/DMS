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
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author kna
 */
public class SaleDataGateway {

    // Constructor
    public SaleDataGateway() {

        this.connection_ = DbConnMgr.getConnection();

    }

    // Destructor
    protected void finalize() throws Throwable {

        super.finalize();
        this.connection_.close();

    }

    public static SaleDataGateway getInstance() {
        if (saleDataGateway_ == null) {
            saleDataGateway_ = new SaleDataGateway();
        }
        return saleDataGateway_;
    }

    public ResultSet getLastSale() {

        query_ = "SELECT * FROM SALE WHERE SALE_ID = (SELECT MAX(SALE_ID) FROM SALE)";

        try {

            preparedStatement_ = connection_.prepareStatement(query_);
            result_ = preparedStatement_.executeQuery();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return result_;
    }

    public ResultSet getCreditSales() {
        query_ = "SELECT * FROM SALE WHERE BALANCE > 0";

        try {

            preparedStatement_ = connection_.prepareStatement(query_);
            result_ = preparedStatement_.executeQuery();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return result_;
    }

    public ResultSet getCreditSalesWithCustomerAndArea() {
        query_ = "SELECT S.SALE_ID , S.SALE_DATE , C.CUSTOMER_NAME , A.AREA_NAME , SA.SUB_AREA_NAME , S.NET_AMOUNT , S.BALANCE "
                + "FROM  SALE S , CUSTOMER C , AREA A, SUB_AREA SA "
                + "WHERE S.CUSTOMER_ID = C.CUSTOMER_ID "
                + "AND C.AREA_ID = A.AREA_ID "
                + "AND C.SUB_AREA_ID = SA.SUB_AREA_ID "
                + "AND S.BALANCE > 0 ORDER BY SALE_ID ASC";

        try {

            preparedStatement_ = connection_.prepareStatement(query_,ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                                        ResultSet.CONCUR_READ_ONLY);
            result_ = preparedStatement_.executeQuery();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return result_;
    }

    public ResultSet getTotalRecovery(Vector<Integer> saleIDs) {
        
        String queryPart = "";
        for (int i = 0; i < saleIDs.size(); i++) {
            if(i == saleIDs.size()-1)
            {
                queryPart += saleIDs.elementAt(i);
                break;
            }
            queryPart += saleIDs.elementAt(i)+",";           
        }
        
        query_ = "SELECT SALE_ID , SUM(AMOUNT) FROM RECOVERY_RECORD WHERE SALE_ID IN ("+queryPart+") GROUP BY SALE_ID"
                +" ORDER BY SALE_ID ASC";
        
        try {

            preparedStatement_ = connection_.prepareStatement(query_);

            result_ = preparedStatement_.executeQuery();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return result_;
        
    }

    public ResultSet getRecoveries(int saleID) {
        query_ = "SELECT * FROM RECOVERY_RECORD WHERE SALE_ID = ?";

        try {

            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setInt(1, saleID);

            result_ = preparedStatement_.executeQuery();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return result_;
    }

    public void insertSaleDetail(int saleID,
            int productID,
            int quantity,
            int replace,
            Double discount,
            Double amount) throws Exception {

        query_ = "INSERT INTO SALE_DETAIL ( SALE_ID , PRODUCT_ID , QUANTITY , REPLACE , DISCOUNT , AMOUNT )"
                + "VALUES ( ? , ? , ? , ? , ? , ? )";
        try {
            preparedStatement_ = connection_.prepareStatement(query_);

            preparedStatement_.setInt(1, saleID);
            preparedStatement_.setInt(2, productID);
            preparedStatement_.setInt(3, quantity);
            preparedStatement_.setDouble(4, replace);
            preparedStatement_.setDouble(5, discount);
            preparedStatement_.setDouble(6, amount);

            preparedStatement_.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        }

    }

    public void insertSaleEmployee(int saleID,
            int employeeID) {
        query_ = "INSERT INTO SALE_EMPLOYEE ( SALE_ID , EMPLOYEE_ID )"
                + "VALUES ( ? , ? )";
        try {
            preparedStatement_ = connection_.prepareStatement(query_);

            preparedStatement_.setInt(1, saleID);
            preparedStatement_.setInt(2, employeeID);

            preparedStatement_.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void insertSaleRecovery(int saleID,
            Date recoveryDate,
            Double amount) throws Exception {
        query_ = "INSERT INTO RECOVERY_RECORD VALUES (? , ? , ?)";
        try {
            preparedStatement_ = connection_.prepareStatement(query_);

            preparedStatement_.setInt(1, saleID);
            preparedStatement_.setDate(2, Conversion.convertToSqlDate(recoveryDate));
            preparedStatement_.setDouble(3, amount);

            preparedStatement_.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        }

    }

    public void insertSale(int customerID,
            Date saleDate,
            Double netDiscount,
            Double netAmount,
            Double balance) throws Exception {

        query_ = "INSERT INTO SALE ( CUSTOMER_ID , SALE_DATE , NET_DISCOUNT , NET_AMOUNT , BALANCE )"
                + "VALUES ( ? , ? , ? , ? , ? )";
        try {
            preparedStatement_ = connection_.prepareStatement(query_);

            preparedStatement_.setInt(1, customerID);
            preparedStatement_.setDate(2, Conversion.convertToSqlDate(saleDate));
            preparedStatement_.setDouble(3, netDiscount);
            preparedStatement_.setDouble(4, netAmount);
            preparedStatement_.setDouble(5, balance);

            preparedStatement_.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void update(int ID,
            int customerID,
            Date saleDate,
            Double netDiscount,
            Double netAmount,
            String paymentStatus) {

        query_ = "UPDATE SALE SET SALE_ID = ? , CUSTOMER_ID = ? , SALE_DATE = ? , NET_DISCOUNT = ? , NET_AMOUNT = ? , PAYMENT_STATUS = ? "
                + "WHERE SALE_ID = ?";
        try {
            preparedStatement_ = connection_.prepareStatement(query_);

            preparedStatement_.setInt(1, ID);
            preparedStatement_.setInt(2, customerID);
            preparedStatement_.setDate(3, Conversion.convertToSqlDate(saleDate));
            preparedStatement_.setDouble(4, netDiscount);
            preparedStatement_.setDouble(5, netAmount);
            preparedStatement_.setString(6, paymentStatus);
            preparedStatement_.setInt(7, ID);

            preparedStatement_.executeUpdate();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void delete(int ID) {
        query_ = "DELETE FROM SALE WHERE SALE_ID = ?";

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

    public void close() {
        try {
            preparedStatement_.close();
            result_.close();
            connection_.close();
            connection_ = DbConnMgr.getConnection();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private String query_;
    private ResultSet result_;
    private Connection connection_;
    private Statement statement_;
    private PreparedStatement preparedStatement_;
    private static SaleDataGateway saleDataGateway_ = null;

}
