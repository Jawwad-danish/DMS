/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.text.SimpleDateFormat;

/**
 *
 * @author jawdan
 */
public class Conversion {
    
    public static java.sql.Date convertToSqlDate(java.util.Date javaDate){
        
        return new java.sql.Date(javaDate.getTime());
    }
    
}
