/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataGateway;

import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

/**
 *
 * @author Admin
 */
public class FirebaseDataGateway {
    
    public static FirebaseDataGateway getInstance(){
        if(firebaseDataGateway_ == null)
        {
            firebaseDataGateway_ = new FirebaseDataGateway();
        }
        return firebaseDataGateway_;
    }
    
    public Map<String , Object> getSales(){
        try{
            firebaseResponse_ = firebase_.get("sales");
        }
        catch(FirebaseException fex){
            JOptionPane.showMessageDialog(null , fex);  
        }
        catch(UnsupportedEncodingException ueex){
            JOptionPane.showMessageDialog(null , ueex);
        }
        return firebaseResponse_.getBody();
    }
    
    
    //----------private members-----------
    
    private FirebaseDataGateway(){
        try{
            firebase_ = new Firebase( getFirebaseURL() );
        }catch(FirebaseException ex){
             JOptionPane.showMessageDialog(null , ex);
        }
    }
    private String getFirebaseURL(){
        String baseURL = null;
        try{
            FileReader reader = new FileReader("config.txt");
            Properties prop = new Properties();
            
            prop.load(reader);
            
            baseURL = prop.getProperty("baseURL");      
          
        }catch(Exception ex){            
            JOptionPane.showMessageDialog(null , ex);            
        }
        return baseURL;
    }
    private Firebase firebase_ = null;
    private FirebaseResponse firebaseResponse_ = null;
    private static FirebaseDataGateway firebaseDataGateway_ = null;
    
}
