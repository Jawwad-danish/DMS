/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModules;

import DataGateway.EmployeeDataGateway;
import DataGateway.SaleDataGateway;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author kna
 */
public class EmployeeModule {
    public EmployeeModule(){
        
        employeeDataGateway_ = EmployeeDataGateway.getInstance();
    }
    
    public static EmployeeModule getInstance(){
        
        if(employeeModule_ == null)
        {
            employeeModule_ = new EmployeeModule();
        }
        return employeeModule_;
    }
    
    public void fillAreaComboBox(JComboBox<String> cboxEmployeeName){
        
        result_ = employeeDataGateway_.getAll();
        try{
            while(result_.next()){
                      cboxEmployeeName.addItem(result_.getString("EMPLOYEE_NAME"));
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        
    }
    
    
    
    
    private ResultSet result_;
    private EmployeeDataGateway employeeDataGateway_;
    private static EmployeeModule employeeModule_ = null;
}
