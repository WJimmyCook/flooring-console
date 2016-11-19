/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.flooringmasteryproject.ops;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Jimmy Cook
 */
public class ProductionMode {
    String result="";
    InputStream inputStream;

    public ProductionMode() {
    }
    
    public String getEnvironment() throws IOException{
        
        try{
            Properties prop = new Properties();
            String propFileName = "config.properties";
            
            // inputStream is similar to scanner. Takes in file as stream and reads from it
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            
            if(inputStream != null){
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            // assigns the value of the property status
            result = prop.getProperty("status");
            
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return result;
    }
}
