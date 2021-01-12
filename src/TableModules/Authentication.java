/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModules;

import DataGateway.AccountDataGateway;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author jawdan
 */
public class Authentication {
    
    public Authentication(){
        
        accountDataGateway_ = AccountDataGateway.getInstance();
    
    }
    public static Authentication getInstance(){
        
        if(authentication_ == null)
        {
            authentication_ = new Authentication();
        }
        return authentication_;
    }
    
    public boolean authenticate(String userName , String password){
        
        boolean isUserValid = false;
        result_ = accountDataGateway_.find(userName, password);
        try {
            isUserValid = result_.next();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        return  isUserValid;
    }
    
    private ResultSet result_;
    private AccountDataGateway accountDataGateway_;
    private static Authentication authentication_ = null;
    
}
