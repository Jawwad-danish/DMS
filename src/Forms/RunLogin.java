/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import Forms.Form_Login;
import Forms.Form_Home;
import DbConnPkg.DbConnMgr;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.Connection;
import javax.swing.JPasswordField;

/**
 *
 * @author DanBroz
 */
public class RunLogin {
    Form_Login  login;

 
    RunLogin(Form_Login L) 
    {
               this.login = L;
           
    }
 
   
    
    public void Run(JTextField Uname, JPasswordField Pass,JLabel w , Form_Home h )
    {
            Connection conn = DbConnMgr.getConnection();
            Statement stmt ;
            ResultSet rs ;
            boolean valid = false;
            if(!(Uname.getText().equals("")) && !(Pass.getText().equals(""))){
            try{
                 String sql = "select * from account where username = '"+Uname.getText()+"' and password = '"+Pass.getText()+"'";
                 stmt = conn.createStatement();
                 rs = stmt.executeQuery(sql);

            
                    while(rs.next()){
                       
                        if(Uname.getText().equals(rs.getString(1)) && Pass.getText().equals(rs.getString(2)))
                        {
                            valid = true;
                            break;
                        }
                    }
                    if(valid)
                    {

                       h.setVisible(true);
                     //   h.setEnabled(true);
                        h.toFront();
                        login.setVisible(false);

                    }
                    else{
                     //   JOptionPane.showMessageDialog(null, "Invalid username or password");
                        Uname.setText("");
                        Pass.setText("");
                        w.setText("Invalid Username or Password!");
                        
                        Uname.requestFocus();
                        
                    }

                }catch(Exception e){
                   JOptionPane.showMessageDialog(null, e);
           }
            }
       }
    }    

