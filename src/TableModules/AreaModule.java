/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModules;

import DataGateway.AreaDataGateway;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author jawdan
 */
public class AreaModule {
    
    public AreaModule(){
        
        areaDataGateway_ = AreaDataGateway.getInstance();
        
    }
    
    public static AreaModule getInstance(){
        
        if(areaModule_ == null)
        {
            areaModule_ = new AreaModule();
        }
        return areaModule_;
    }
    
    public void fillAreaComboBox(JComboBox<String> cboxAreaName){
        
        result_ = areaDataGateway_.getAllAreas();
        try{
            while(result_.next()){
                      cboxAreaName.addItem(result_.getString("AREA_NAME"));
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        
    }
    
    public int getAreaId(String name){
        
       result_ = areaDataGateway_.getAreaID(name);
       try{
           if(result_.next()){
            return result_.getInt("AREA_ID");
           }
           else{
               return -1;
           }
       }
       catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);
           return -1;
       }
    }
    
    public void submit(String AreaName){
        
        if(!areaDataGateway_.findByName(AreaName)){
            areaDataGateway_.insert(AreaName);
        }
        
    }
    
    
    private ResultSet result_;
    private AreaDataGateway areaDataGateway_;
    private static AreaModule areaModule_ = null;
    
}