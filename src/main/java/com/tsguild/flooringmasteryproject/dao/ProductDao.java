/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.flooringmasteryproject.dao;

import com.tsguild.flooringmasteryproject.dto.Product;
import java.io.FileNotFoundException;

/**
 *
 * @author Jimmy Cook
 */
public interface ProductDao {
    
    public Product getProductInfo(String productType);
    
    public void loadFromFile() throws FileNotFoundException;
    
    public boolean containsType(String type);
}
