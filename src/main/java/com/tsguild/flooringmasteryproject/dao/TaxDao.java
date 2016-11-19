/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.flooringmasteryproject.dao;

import java.io.FileNotFoundException;
import java.util.Set;

/**
 *
 * @author Jimmy Cook
 */
public interface TaxDao {
    
    public double getTax(String state);
    
    public void loadFromFile() throws FileNotFoundException;
    
    public boolean containsState(String state);
}
