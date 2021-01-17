/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbConnPkg;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.io.FileReader;
import java.util.Properties;

/**
 *
 * @author DanBroz
 */

public class DbConnMgr {   
    
    public static Connection getConnection()
    {
        String host ;
        String port;        
        String sid;
        String user;
        String password;
        
        try{
            FileReader reader = new FileReader("config.txt");
            Properties prop = new Properties();
            
            prop.load(reader);
            
            host = prop.getProperty("host");
            port = prop.getProperty("port");
            sid = prop.getProperty("sid");
            user = prop.getProperty("user");
            password = prop.getProperty("password");
            
            Class.forName("oracle.jdbc.OracleDriver");
            
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@"+
                    host+":"+port+":"+sid,user,password);
            conn.setAutoCommit(false);
            
          return conn;
          
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null , e);
            
        }
        return null;
    }
            
}
