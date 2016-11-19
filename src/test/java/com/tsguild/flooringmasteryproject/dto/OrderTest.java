/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.flooringmasteryproject.dto;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jimmy Cook
 */
public class OrderTest {
    
        Order testObj;
    public OrderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        testObj = new Order(1, "Chip", "KY", "12232016", 3.75, "CARPET", 230.0, 13.95, 12.0);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Constructor, of class Order.
     */
//    public Order(int orderNum, String customerName, String state, String date, 
//            double taxRate, String productType, double area, double costSqFt, double laborSqFt) {
//        this.orderNum = orderNum;
//        this.customerName = customerName;
//        this.state = state;
//        this.date = date;
//        this.taxRate = taxRate;
//        this.productType = productType;
//        this.area = area;
//        this.costSqFt = costSqFt;
//        this.laborSqFt = laborSqFt;
//        this.materialCost = area * costSqFt;
//        this.laborCost = area * laborSqFt;
//        this.tax = taxRate/100 * (this.materialCost + this.laborCost);
//        this.total = this.tax + this.materialCost + this.laborCost;
//    }
    
    @Test
    public void testOrderMaterialCost(){
        assertEquals("Material cost doesn't equal what it should",3208.5, testObj.getMaterialCost(),0.01);
    }
    
    @Test
    public void testOrderLaborCost(){
        assertEquals("Labor cost doesn't equal what it should",2760, testObj.getLaborCost(),0.01);
    }
    
    @Test
    public void testOrderTax(){
        assertEquals("Tax doesn't equal what it should",223.81875, testObj.getTax(),0.01);
    }
    
    @Test 
    public void testOrderTotal(){
        assertEquals("Total doesn't add up",6192.31875, testObj.getTotal(),0.01);
    }
    
}
