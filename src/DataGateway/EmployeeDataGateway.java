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
public class EmployeeDataGateway {

    public EmployeeDataGateway() {
        connection_ = DbConnMgr.getConnection();
    }
     // Destructor
    
    protected void finalize() throws Throwable {
  
        super.finalize();
        this.connection_.close();
        
    }
    public static EmployeeDataGateway getInstance(){
        
        if(employeeDataGateway_ == null)
        {
            employeeDataGateway_ = new EmployeeDataGateway();
        }
        return employeeDataGateway_;
    }
    
    public ResultSet find(int ID){
        
        query_ = "SELECT * FROM EMPLOYEE "
                + "WHERE EMPLOYEE_ID = ?";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setInt(1, ID);
            result_ = preparedStatement_.executeQuery(query_);
            
        } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
            
        }
        return result_;
    }
    
    public ResultSet getEmployeeName(){
        
        query_ = "SELECT EMPLOYEE_NAME FROM CUSTOMER";
        try
        {
            preparedStatement_ = connection_.prepareStatement(query_);
            result_ = preparedStatement_.executeQuery();
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return result_;
    }
    
    public ResultSet getEmployee(String employeeName){
        query_ = "SELECT * FROM EMPLOYEE WHERE EMPLOYEE_NAME = ?";
        
        try
        {
            preparedStatement_ = connection_.prepareStatement(query_);
            preparedStatement_.setString(1, employeeName);
            result_ = preparedStatement_.executeQuery();
        }
        catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);
        }
        return result_;
        
    }
    
    public ResultSet getAll(){
        
            query_ = "SELECT * FROM EMPLOYEE";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            result_ = preparedStatement_.executeQuery(query_);
            
        } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
            
        }
            
        return result_;
    }
    
    public void insert( String name , String CNIC , String  contact_1 ,  String contact_2 , String designation , int addressID , Date hireDate , double basicSalary){
        
        query_ = "INSERT INTO EMPLOYEE (EMPLOYEE_NAME , CNIC_NUMBER , CONTACT_NO_1 , "
                + " CONTACT_NO_2 , DESIGNATION , ADDRESS_ID , HIRE_DATE , BASIC_SALARY) "
                + "VALUES(?  , ? , ? , ? , ? , ? , ? , ?)";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            
            preparedStatement_.setString(1, name);
            preparedStatement_.setString(2, CNIC);
            preparedStatement_.setString(3, contact_1);
            preparedStatement_.setString(4,  contact_2);
            preparedStatement_.setString(5, designation);
            preparedStatement_.setInt(6, addressID);
            preparedStatement_.setDate(7, Conversion.convertToSqlDate(hireDate));
            preparedStatement_.setDouble(8, basicSalary);
            
            preparedStatement_.executeUpdate();
            
        } catch (Exception ex) {
            
            JOptionPane.showMessageDialog(null, ex);
            
        }
    }
    
    public void update(int ID , String name , String CNIC , String  contact_1 ,  String contact_2 , String designation , int addressID , Date hireDate , double basicSalary){
        
        query_ = "UPDATE EMPLOYEE SET EMPLOYEE_NAME = ? , CNIC_NUMBER = ? , CONTACT_NO_1 = ? , "
                + "CONTACT_NO_2 = ? , DESIGNATION = ?, ADDRESS_ID = ? , HIRE_DATE = ? , BASIC_SALARY = ? "
                + "WHERE EMPLOYEE_ID = ?";
        
        try {
            preparedStatement_ = connection_.prepareStatement(query_);
            
            preparedStatement_.setString(1, name);
            preparedStatement_.setString(2, CNIC);
            preparedStatement_.setString(3, contact_1);
            preparedStatement_.setString(4,  contact_2);
            preparedStatement_.setString(5, designation);
            preparedStatement_.setInt(6, addressID);
            preparedStatement_.setDate(7, Conversion.convertToSqlDate(hireDate));
            preparedStatement_.setDouble(8, basicSalary);
            preparedStatement_.setInt(9, ID);
            
            preparedStatement_.executeUpdate();
        } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);

        }
        
    }
    
    public void delete(int ID){
        
        query_ = "DELETE FROM EMPLOYEE WHERE EMPLOYEE_ID = ?";
        
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
    private static EmployeeDataGateway employeeDataGateway_ = null;
}
