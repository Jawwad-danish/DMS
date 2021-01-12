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
 * @author jawdan
 */
public class AccountDataGateway {

    public AccountDataGateway() {
        connection_ = DbConnMgr.getConnection();
    }
    // Destructor

    protected void finalize() throws Throwable {

        super.finalize();
        this.connection_.close();

    }

    public static AccountDataGateway getInstance() {

        if (accountDataGateway_ == null) {
            accountDataGateway_ = new AccountDataGateway();
        }
        return accountDataGateway_;
    }

    public ResultSet find(String userName, String password) {

        query_ = "SELECT * FROM ACCOUNT "
                + "WHERE USERNAME = ? AND PASSWORD = ?";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setString(1, userName);
            preparedStatement_.setString(2, password);
            result_ = preparedStatement_.executeQuery();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);

        }
        return result_;
    }

    public ResultSet find(int ID) {

        query_ = "SELECT * FROM ACCOUNT "
                + "WHERE ACCOUNT_ID = ?";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setInt(1, ID);
            result_ = preparedStatement_.executeQuery(query_);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);

        }
        return result_;
    }

    public ResultSet findAll() {

        query_ = "SELECT * FROM ACCOUNT";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            result_ = preparedStatement_.executeQuery(query_);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);

        }
        return result_;
    }

    public void insert(String userName, String password, String type) {

        query_ = "INSERT INTO ACCOUNT (USERNAME , PASSWORD , TYPE  , "
                + "VALUES( ? , ? , ? )";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);

            preparedStatement_.setString(1, userName);
            preparedStatement_.setString(2, password);
            preparedStatement_.setString(3, type);

            preparedStatement_.executeUpdate();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, ex);

        }
    }

    public void update(int ID, String userName, String password, String type) {

        query_ = "UPDATE ACCOUNT SET USERNAME = ? , PASSWORD = ? , TYPE = ? , "
                + "WHERE ACCOUNT_ID = ?";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);

            preparedStatement_.setString(1, userName);
            preparedStatement_.setString(2, password);
            preparedStatement_.setString(3, type);
            preparedStatement_.setInt(4, ID);

            preparedStatement_.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);

        }

    }

    public void delete(int ID) {

        query_ = "DELETE FROM ACCOUNT WHERE EMPLOYEE_ID = ?";

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

    private String query_;
    private ResultSet result_;
    private Connection connection_;
    private Statement statement_;
    private PreparedStatement preparedStatement_;
    private static AccountDataGateway accountDataGateway_ = null;

}
