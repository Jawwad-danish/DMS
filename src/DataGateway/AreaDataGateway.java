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
public class AreaDataGateway {

    public AreaDataGateway() {
        connection_ = DbConnMgr.getConnection();
    }
    // Destructor

    protected void finalize() throws Throwable {

        super.finalize();
        this.connection_.close();

    }

    public static AreaDataGateway getInstance() {

        if (areaDataGateway_ == null) {
            areaDataGateway_ = new AreaDataGateway();
        }
        return areaDataGateway_;
    }

    public boolean findByID(int ID) {

        query_ = "SELECT * FROM AREA "
                + "WHERE AREA_ID = ?";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setInt(1, ID);
            result_ = preparedStatement_.executeQuery();

            if (result_.next()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, ex);
            return false;

        }
    }

    public boolean findByName(String name) {

        query_ = "SELECT * FROM AREA "
                + "WHERE AREA_NAME = ?";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setString(1, name);
            result_ = preparedStatement_.executeQuery();

            if (result_.next()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, ex);
            return false;
        }
    }

    public ResultSet getAreaID(String name) {

        query_ = "SELECT AREA_ID FROM AREA WHERE AREA_NAME = ?";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setString(1, name);
            result_ = preparedStatement_.executeQuery();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);

        }
        return result_;
    }
    
    public ResultSet getSubAreaID(String name) {

        query_ = "SELECT SUB_AREA_ID FROM SUB_AREA WHERE SUB_AREA_NAME = ?";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setString(1, name);
            result_ = preparedStatement_.executeQuery();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);

        }
        return result_;
    }
    
    public ResultSet getSubAreas(int areaID) {

        query_ = "SELECT * FROM SUB_AREA WHERE AREA_ID = ?";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setInt(1, areaID);
            result_ = preparedStatement_.executeQuery();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);

        }
        return result_;
    }
     public ResultSet getSubArea(int subAreaID) {

        query_ = "SELECT * FROM SUB_AREA WHERE SUB_AREA_ID = ?";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setInt(1, subAreaID);
            result_ = preparedStatement_.executeQuery();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);

        }
        return result_;
    }
    public ResultSet getArea(int areaID) {

        query_ = "SELECT * FROM AREA WHERE AREA_ID = ?";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setInt(1, areaID);
            result_ = preparedStatement_.executeQuery();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);

        }
        return result_;
    }
    
    public ResultSet getAllSubAreas() {

        query_ = "SELECT * FROM SUB_AREA";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            result_ = preparedStatement_.executeQuery();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);

        }
        return result_;
    }
    public ResultSet getAllAreas() {

        query_ = "SELECT * FROM AREA";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            result_ = preparedStatement_.executeQuery();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);

        }
        return result_;
    }

    public void insert(String name) {

        query_ = "INSERT INTO AREA (AREA_NAME ) "
                + "VALUES( ?  )";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);

            preparedStatement_.setString(1, name);

            preparedStatement_.executeUpdate();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, ex);

        }
    }

    public void update(int ID, String name) {

        query_ = "UPDATE AREA SET AREA_NAME = ? "
                + "WHERE AREA_ID = ?";

        try {
            preparedStatement_ = connection_.prepareStatement(query_);

            preparedStatement_.setString(1, name);
            preparedStatement_.setInt(2, ID);

            preparedStatement_.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);

        }

    }

    public void delete(int ID) {

        query_ = "DELETE FROM AREA WHERE AREA_ID = ?";

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
    private static AreaDataGateway areaDataGateway_ = null;
}
