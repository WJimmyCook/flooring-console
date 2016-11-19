/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.flooringmasteryproject.dao;

import com.tsguild.flooringmasteryproject.dto.Order;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Jimmy Cook
 */
public interface OrderDao {
    
    public Collection<Order> getAllOrders(String date);
    
    public void addOrder(Order o);
    
    public Order getOrder(String date, int orderNum);
    
    public Order removeOrder(String date, int orderNum);
    
    public void saveToFile();
    
    public void loadFromFile();
    
    public void deleteEmptyFile() throws IOException;
    
    public List<Integer> getAllOrderNumbers();
    
    public int getNewOrderNumber();
}
