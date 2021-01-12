/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataGateway;

import DbConnPkg.DbConnMgr;
import Utilities.Conversion;
import static groovyjarjarantlr.actions.csharp.ActionLexerTokenTypes.ID;
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
public class AttendanceDataGateway {
    
    
     public AttendanceDataGateway() {
        connection_ = DbConnMgr.getConnection();
    }
     // Destructor
    
    protected void finalize() throws Throwable {
  
        super.finalize();
        this.connection_.close();
        
    }
    public static AttendanceDataGateway getInstance(){
        
        if(attendanceDataGateway_ == null)
        {
            attendanceDataGateway_ = new AttendanceDataGateway();
        }
        return attendanceDataGateway_;
    }
    
    public ResultSet find( Date attendanceDate , int employeeID ){
        
        query_ = "SELECT * FROM ATTENDANCE "
                + "WHERE ATTENDANCE_DATE = ? AND EMPLOYEE_ID = ?";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setDate(1, Conversion.convertToSqlDate(attendanceDate));
            preparedStatement_.setInt(2, employeeID);
            result_ = preparedStatement_.executeQuery(query_);
            
        } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
            
        }
        return result_;
    }
    
    public ResultSet findAll(){
        
        query_ = "SELECT * FROM ATTENDANCE";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            result_ = preparedStatement_.executeQuery(query_);
            
        } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
            
        }
        return result_;
    }
    
    public void insert( Date attendanceDate , int employeeID , String status ){
        
        query_ = "INSERT INTO ATTENDANCE (ATTENDANCE_DATE , EMPLOYEE_ID , STATUS ) "
                + "VALUES( ? , ? , ? )";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            
            preparedStatement_.setDate(1, Conversion.convertToSqlDate(attendanceDate));
            preparedStatement_.setInt(2, employeeID);
            preparedStatement_.setString(3, status);
            
            preparedStatement_.executeUpdate();
            
        } catch (Exception ex) {
            
            JOptionPane.showMessageDialog(null, ex);
            
        }
    }
    
    public void update( Date attendanceDate , int employeeID , String status ){
        
        query_ = "UPDATE ATTENDANCE SET STATUS = ? "
                + "WHERE ATTENDANCE_DATE = ? AND EMPLOYEE_ID = ?";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            
            preparedStatement_.setDate(2, Conversion.convertToSqlDate(attendanceDate));
            preparedStatement_.setInt(3, employeeID);
            preparedStatement_.setString(1, status);
            
            preparedStatement_.executeUpdate();
        } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);

        }
        
    }
    
    public void delete(Date attendanceDate , int employeeID){
        
        query_ = "DELETE FROM ATTENDANCE WHERE ATTENDANCE_DATE = ? AND EMPLOYEE_ID = ?";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            
            preparedStatement_.setDate(1, Conversion.convertToSqlDate(attendanceDate));
            preparedStatement_.setInt(2, employeeID);
            
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
    private static AttendanceDataGateway attendanceDataGateway_ = null;
}
