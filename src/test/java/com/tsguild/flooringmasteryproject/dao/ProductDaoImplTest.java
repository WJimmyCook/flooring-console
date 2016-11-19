/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.flooringmasteryproject.dao;

import com.tsguild.flooringmasteryproject.dto.Product;
import java.io.FileNotFoundException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.unitils.reflectionassert.ReflectionAssert;

/**
 *
 * @author Jimmy Cook
 */
public class ProductDaoImplTest {
    
        ProductDaoImpl testObj;
        
    public ProductDaoImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws FileNotFoundException {
        testObj = new ProductDaoImpl("productsTESTDATA.txt");
        testObj.loadFromFile();
       
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getProductInfo method, of class ProductDaoImpl.
     */
    @Test
    public void testGetProductInfoWood() {
//        public Product(String ProductType, double materialCost, double laborCost)
        Product expResult = new Product("WOOD", 5.15, 4.75);
        Product result = testObj.getProductInfo("WOOD");
        ReflectionAssert.assertReflectionEquals(expResult, result);
    }
    
    @Test
    public void testGetProductInfoCarpet() {
//        public Product(String ProductType, double materialCost, double laborCost)
        Product expResult = new Product("CARPET", 2.25, 2.10);
        Product result = testObj.getProductInfo("CARPET");
        ReflectionAssert.assertReflectionEquals(expResult, result);
    }
    
    @Test
    public void testGetProductInfoLaminate() {
//        public Product(String ProductType, double materialCost, double laborCost)
        Product expResult = new Product("LAMINATE", 1.75, 2.10);
        Product result = testObj.getProductInfo("LAMINATE");
        ReflectionAssert.assertReflectionEquals(expResult, result);
    }
    
    @Test
    public void testGetProductInfoTile() {
//        public Product(String ProductType, double materialCost, double laborCost)
        Product expResult = new Product("TILE", 3.50, 4.15);
        Product result = testObj.getProductInfo("TILE");
        ReflectionAssert.assertReflectionEquals(expResult, result);
    }

    
    /**
     * Test of containsType method, of class ProductDaoImpl.
     * Will not get lowercase input
     */
    @Test
    public void testContainsTypeWood() {
        boolean expResult = true;
        boolean result = testObj.containsType("WOOD");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testContainsTypeCarpet() {
        boolean expResult = true;
        boolean result = testObj.containsType("CARPET");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testContainsTypeEmpty() {
        boolean expResult = false;
        boolean result = testObj.containsType("");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testContainsTypeSpaces() {
        boolean expResult = false;
        boolean result = testObj.containsType("  WOOD  ");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testContainsTypeNumbers() {
        boolean expResult = false;
        boolean result = testObj.containsType("234");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testContainsType() {
        boolean expResult = false;
        boolean result = testObj.containsType("ARDVARKBONES");
        assertEquals(expResult, result);
    }
    
    
    
}
