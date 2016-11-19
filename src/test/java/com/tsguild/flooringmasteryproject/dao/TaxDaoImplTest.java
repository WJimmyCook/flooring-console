/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.flooringmasteryproject.dao;

import java.io.FileNotFoundException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jimmy Cook
 */
public class TaxDaoImplTest {

    TaxDaoImpl testObj;

    public TaxDaoImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws FileNotFoundException {

        testObj = new TaxDaoImpl("taxesTESTDATA.txt");
        testObj.loadFromFile();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getTax method, of class TaxDaoImpl.
     */
    @Test
    public void testGetTaxAL() {

        double expResult = 4;
        double result = testObj.getTax("AL");
        assertEquals(expResult, result, 0.01);
    }

    @Test
    public void testGetTaxCA() {

        double expResult = 7.5;
        double result = testObj.getTax("CA");
        assertEquals(expResult, result, 0.01);
    }

    @Test
    public void testGetTaxWrong() {

        double expResult = 9;
        double result = testObj.getTax("OH");
        Assert.assertNotEquals(expResult, result, 0.01);
    }

   

    /**
     * Test of containsState method, of class TaxDaoImpl.
     */
    @Test
    public void testContainsState() {
        boolean expResult = true;
        boolean result = testObj.containsState("AL");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testContainsStateB() {
        boolean expResult = true;
        boolean result = testObj.containsState("OH");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testContainsStateEmpty() {
        boolean expResult = false;
        boolean result = testObj.containsState("");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testContainsStateLetters() {
        boolean expResult = false;
        boolean result = testObj.containsState("AJKDL");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testContainsStateSpecialChars() {
        boolean expResult = false;
        boolean result = testObj.containsState("@$");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testContainsStateSpaces() {
        boolean expResult = false;
        boolean result = testObj.containsState("  AL  ");
        assertEquals(expResult, result);
    }
    
}
