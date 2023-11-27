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
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

/**
 *
 * @author DanBroz
 */

public class DbConnMgr {   
    
    public static Connection getConnection()
    {
        try {
            FileReader reader = new FileReader("config.txt");
            Properties prop = new Properties();
            prop.load(reader);

            String host = prop.getProperty("host");
            String port = prop.getProperty("port");
            String sid = prop.getProperty("sid");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");
            String db_type = prop.getProperty("db_type");
            
            switch (db_type){
                case "mtls":
                    return getConnectionMTLS();
                case "basic":
                    return getConnectionBasic(host, port, sid, user, password);
                default:
                    return getConnectionBasic(host, port, sid, user, password);
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null , e);   
        }        
        return null;
    }
    
    private static Connection getConnectionMTLS(){
        
        try{
            final String DB_URL="jdbc:oracle:thin:@dbname_medium?TNS_ADMIN=C:\\Users\\Jawad Danish\\Documents\\jawwad";
            final String DB_USER = "ADMIN";
            String DB_PASSWORD = "Shareefnsons" ;
            final String CONN_FACTORY_CLASS_NAME="oracle.jdbc.pool.OracleDataSource";

            // Get the PoolDataSource for UCP
            PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();

            pds.setConnectionFactoryClassName(CONN_FACTORY_CLASS_NAME);
            pds.setURL(DB_URL);
            pds.setUser(DB_USER);
            pds.setPassword(DB_PASSWORD);
            pds.setConnectionPoolName("JDBC_UCP_POOL");
            pds.setInitialPoolSize(5);
            pds.setMinPoolSize(5);
            pds.setMaxPoolSize(20);

            Connection conn = pds.getConnection();
            
          return conn;
          
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null , e);
            
        }
        return null;
    }
    
    
    private static Connection getConnectionBasic(final String host, 
                                                final String port,
                                                final String sid,
                                                final String user,
                                                final String password){
        
        
        try{
                        
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
