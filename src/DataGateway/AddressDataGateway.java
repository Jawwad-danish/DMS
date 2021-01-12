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
public class AddressDataGateway {

    public AddressDataGateway() {
        connection_ = DbConnMgr.getConnection();
    }
    // Destructor

    protected void finalize() throws Throwable {

        super.finalize();
        this.connection_.close();

    }

    public static AddressDataGateway getInstance() {

        if (addressDataGateway_ == null) {
            addressDataGateway_ = new AddressDataGateway();
        }
        return addressDataGateway_;
    }

    public ResultSet find(int ID) {

        query_ = "SELECT * FROM ADDRESS "
                + "WHERE ADDRESS_ID = ?";

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

        query_ = "SELECT * FROM ADDRESS";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            result_ = preparedStatement_.executeQuery(query_);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);

        }
        return result_;
    }

    public void insert(String name, String addressLine, String city, int zipCode) {

        query_ = "INSERT INTO ADDRESS (ADDRESS_LINE , ADDRESS_LINE , CITY , ZIPCODE ) "
                + "VALUES( ? , ? , ? , ? )";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);

            preparedStatement_.setString(1, name);
            preparedStatement_.setString(2, addressLine);
            preparedStatement_.setString(3, city);
            preparedStatement_.setInt(4, zipCode);

            preparedStatement_.executeUpdate();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, ex);

        }
    }

    public void update(int ID, String name, String addressLine, String city, int zipCode) {

        query_ = "UPDATE ADDRESS SET ADDRESS_NAME = ?  , ADDRESS_LINE = ? , CITY = ? , ZIPCODE = ? "
                + "WHERE AREA_ID = ?";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);

            preparedStatement_.setString(1, name);
            preparedStatement_.setString(2, addressLine);
            preparedStatement_.setString(3, city);
            preparedStatement_.setInt(4, zipCode);
            preparedStatement_.setInt(5, ID);

            preparedStatement_.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);

        }

    }

    public void delete(int ID) {

        query_ = "DELETE FROM ADDRESS WHERE ADDRESS_ID = ?";

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
    private static AddressDataGateway addressDataGateway_ = null;
}
