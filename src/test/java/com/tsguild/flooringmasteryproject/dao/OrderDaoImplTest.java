/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.flooringmasteryproject.dao;

import com.tsguild.flooringmasteryproject.dto.Order;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.unitils.reflectionassert.ReflectionAssert;

/**
 *
 * @author Jimmy Cook
 */
public class OrderDaoImplTest {

    OrderDaoImpl testObj;

    Order[] ordersForTesting = {
        new Order(1, "Chip", "KY", "12232016", 3.75, "CARPET", 230.0, 13.95, 12.0),
        new Order(2, "Pumba", "KY", "12252016", 4.75, "TILE", 212.0, 11.95, 10.0),
        new Order(5, "Jeff Bob", "OH", "12252016", 3.75, "TILE", 21.0, 1.95, 1.0),};
    
    Order[] ordersForTestingDuplicate = {
        new Order(1, "Chip", "KY", "12232016", 3.75, "CARPET", 230.0, 13.95, 12.0),
        new Order(2, "Pumba", "KY", "12252016", 4.75, "TILE", 212.0, 11.95, 10.0),
        new Order(5, "Jeff Bob", "OH", "12252016", 3.75, "TILE", 21.0, 1.95, 1.0),};

    public OrderDaoImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        testObj = new OrderDaoImpl();

//        (int orderNum, String customerName, String state, String date, 
//            double taxRate, String productType, double area, double costSqFt, double laborSqFt)
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addOneToEmptyDaoTest() {
        Order carpet = new Order(2, "Some Guy", "KY", "12012016", 4.75, "CARPET", 212.0, 11.95, 10.0);

        testObj.addOrder(carpet);

        Order hope = testObj.getOrder(carpet.getDate(), carpet.getOrderNum());

        Assert.assertEquals(carpet, hope);
    }
    
    @Test
    public void testAgainstEmptyDao(){
        Assert.assertEquals(0, testObj.getAllDates().size());
        
    }
    
    @Test 
    public void testReplaceOneOrder(){
        testObj.addOrder(ordersForTesting[0]);
        testObj.addOrder(ordersForTestingDuplicate[0]);
        Assert.assertEquals("Expected order count doesn't match.", 1, testObj.getAllDates().size());
        Assert.assertEquals("Replaced order doesn't match.", ordersForTestingDuplicate[0], testObj.getOrder("12232016", 1));
        Assert.assertNotNull("List of all orders should not be null.", testObj.getAllOrders("12232016"));
        Assert.assertEquals("Expected order count doens't match", 1, testObj.getAllDates().size());
        Assert.assertTrue("Returned order doesn't match as expected.", testObj.getAllOrders("12232016").contains(ordersForTestingDuplicate[0]));
    }
    
    @Test
    public void testAddAndRemoveOneOrder() {
        testObj.addOrder(ordersForTesting[0]);
        Order removed = testObj.removeOrder("12232016", 1);

        Assert.assertNotNull("Order should be returned on removal.", removed);
        Assert.assertEquals("Order should be returned on removal.", ordersForTesting[0], removed);
        Assert.assertEquals("Expected 0 orders after adding/removing one ord.", 0, testObj.getAllOrders("12232016").size());
        Assert.assertNull("Order should return null after being removed.", testObj.getOrder("12232016", 1));
        Assert.assertNotNull("List of all orders should not be null.", testObj.getAllOrders("12232016"));
        Assert.assertEquals("Expected order count should be zero when adding/removing a single order.", 0, testObj.getAllOrders("12232016").size());
    }

    /**
     * Test of getKeySet method, of class OrderDaoImpl.
     */
    @Test
    public void testGetKeySet() {
        Set<String> expResult = new HashSet<>();
        expResult.add("12232016");
        expResult.add("12252016");

        Order o1 = ordersForTesting[0];
        Order o2 = ordersForTesting[1];
        testObj.addOrder(o1);
        testObj.addOrder(o2);
        Set<String> result = testObj.getAllDates();
        assertEquals(expResult, result);
        assertEquals(expResult.size(),result.size());
        assertNotNull(result);

    }

    @Test
    public void testGetKeySetFalse() {

        Set<String> expResult = new HashSet<>();
        expResult.add("12232016");
        expResult.add("12252088");

        Order o1 = ordersForTesting[0];
        Order o2 = ordersForTesting[1];
        testObj.addOrder(o1);
        testObj.addOrder(o2);
        Set<String> result = testObj.getAllDates();
        Assert.assertNotEquals(expResult, result);
        Assert.assertNotNull(result);
        Assert.assertEquals(expResult.size(), result.size());
       

    }

    @Test
    public void testGetKeySetMissingOne() {

        Set<String> expResult = new HashSet<>();
        expResult.add("12232016");

        Order o1 = ordersForTesting[0];
        Order o2 = ordersForTesting[1];
        testObj.addOrder(o1);
        testObj.addOrder(o2);
        Set<String> result = testObj.getAllDates();
        Assert.assertNotEquals(expResult, result);
        Assert.assertNotNull(result);
        Assert.assertNotEquals(expResult.size(), result.size());

    }

    /**
     * Test of getOrder method, of class OrderDaoImpl.
     */
    @Test
    public void testGetOrder() {
        Order expResult = new Order(1, "Chip", "KY", "12232016", 3.75, "CARPET", 230.0, 13.95, 12.0);

        Order o1 = ordersForTesting[0];
        testObj.addOrder(o1);
        Order result = testObj.getOrder("12232016", 1);
        Assert.assertNotNull(result);
        Assert.assertEquals(expResult.getDate(), result.getDate());
        Assert.assertEquals(expResult.getOrderNum(), result.getOrderNum());
        Assert.assertEquals(expResult.getCustomerName(), result.getCustomerName());
        Assert.assertEquals(expResult.getArea(), result.getArea(), 0.01);
        Assert.assertEquals(expResult.getCostSqFt(), result.getCostSqFt(), 0.01);
        Assert.assertEquals(expResult.getLaborSqFt(), result.getLaborSqFt(), 0.01);
        Assert.assertEquals(expResult.getProductType(), result.getProductType());
        Assert.assertEquals(expResult.getState(), result.getState());
        Assert.assertEquals(expResult.getMaterialCost(), result.getMaterialCost(), 0.01);
        Assert.assertEquals(expResult.getTax(), result.getTax(), 0.01);
        Assert.assertEquals(expResult.getTaxRate(), result.getTaxRate(), 0.01);
        Assert.assertEquals(expResult.getTotal(), result.getTotal(), 0.01);
        Assert.assertEquals(expResult.getLaborCost(), result.getLaborCost(), 0.01);
    }

    @Test
    public void testGetOrderB() {
        Order expResult = new Order(2, "Pumba", "KY", "12252016", 4.75, "TILE", 212.0, 11.95, 10.0);

        Order o1 = ordersForTesting[1];
        testObj.addOrder(o1);
        Order result = testObj.getOrder("12252016", 2);
        Assert.assertNotNull(result);
        Assert.assertEquals(expResult.getDate(), result.getDate());
        Assert.assertEquals(expResult.getOrderNum(), result.getOrderNum());
        Assert.assertEquals(expResult.getCustomerName(), result.getCustomerName());
        Assert.assertEquals(expResult.getArea(), result.getArea(), 0.01);
        Assert.assertEquals(expResult.getCostSqFt(), result.getCostSqFt(), 0.01);
        Assert.assertEquals(expResult.getLaborSqFt(), result.getLaborSqFt(), 0.01);
        Assert.assertEquals(expResult.getProductType(), result.getProductType());
        Assert.assertEquals(expResult.getState(), result.getState());
        Assert.assertEquals(expResult.getMaterialCost(), result.getMaterialCost(), 0.01);
        Assert.assertEquals(expResult.getTax(), result.getTax(), 0.01);
        Assert.assertEquals(expResult.getTaxRate(), result.getTaxRate(), 0.01);
        Assert.assertEquals(expResult.getTotal(), result.getTotal(), 0.01);
        Assert.assertEquals(expResult.getLaborCost(), result.getLaborCost(), 0.01);
    }

    /**
     * Test of removeOrder method, of class OrderDaoImpl.
     */
    @Test
    public void testRemoveOrder() {
        Order expResult = new Order(2, "Pumba", "KY", "12252016", 4.75, "TILE", 212.0, 11.95, 10.0);

        Order o1 = ordersForTesting[1];
        testObj.addOrder(o1);
        Order result = testObj.removeOrder("12252016", 2);
        Assert.assertNotNull(result);
        Assert.assertEquals(expResult.getDate(), result.getDate());
        Assert.assertEquals(expResult.getOrderNum(), result.getOrderNum());
        Assert.assertEquals(expResult.getCustomerName(), result.getCustomerName());
        Assert.assertEquals(expResult.getArea(), result.getArea(), 0.01);
        Assert.assertEquals(expResult.getCostSqFt(), result.getCostSqFt(), 0.01);
        Assert.assertEquals(expResult.getLaborSqFt(), result.getLaborSqFt(), 0.01);
        Assert.assertEquals(expResult.getProductType(), result.getProductType());
        Assert.assertEquals(expResult.getState(), result.getState());
        Assert.assertEquals(expResult.getMaterialCost(), result.getMaterialCost(), 0.01);
        Assert.assertEquals(expResult.getTax(), result.getTax(), 0.01);
        Assert.assertEquals(expResult.getTaxRate(), result.getTaxRate(), 0.01);
        Assert.assertEquals(expResult.getTotal(), result.getTotal(), 0.01);
        Assert.assertEquals(expResult.getLaborCost(), result.getLaborCost(), 0.01);
    }

    @Test
    public void testRemoveOrderB() {
        Order expResult = new Order(1, "Chip", "KY", "12232016", 3.75, "CARPET", 230.0, 13.95, 12.0);

        Order o1 = ordersForTesting[0];
        testObj.addOrder(o1);
        Order result = testObj.removeOrder("12232016", 1);
        Assert.assertNotNull(result);
        Assert.assertEquals(expResult.getDate(), result.getDate());
        Assert.assertEquals(expResult.getOrderNum(), result.getOrderNum());
        Assert.assertEquals(expResult.getCustomerName(), result.getCustomerName());
        Assert.assertEquals(expResult.getArea(), result.getArea(), 0.01);
        Assert.assertEquals(expResult.getCostSqFt(), result.getCostSqFt(), 0.01);
        Assert.assertEquals(expResult.getLaborSqFt(), result.getLaborSqFt(), 0.01);
        Assert.assertEquals(expResult.getProductType(), result.getProductType());
        Assert.assertEquals(expResult.getState(), result.getState());
        Assert.assertEquals(expResult.getMaterialCost(), result.getMaterialCost(), 0.01);
        Assert.assertEquals(expResult.getTax(), result.getTax(), 0.01);
        Assert.assertEquals(expResult.getTaxRate(), result.getTaxRate(), 0.01);
        Assert.assertEquals(expResult.getTotal(), result.getTotal(), 0.01);
        Assert.assertEquals(expResult.getLaborCost(), result.getLaborCost(), 0.01);
    }

    @Test
    public void testGetAllOrders() {

        Collection<Order> expResult = new HashSet<>();
        Order o1 = new Order(2, "Pumba", "KY", "12252016", 4.75, "TILE", 212.0, 11.95, 10.0);
        Order o2 = new Order(5, "Jeff Bob", "OH", "12252016", 3.75, "TILE", 21.0, 1.95, 1.0);
        expResult.add(o1);
        expResult.add(o2);

        Order oo1 = ordersForTesting[0];
        Order oo2 = ordersForTesting[1];
        Order oo3 = ordersForTesting[2];
        testObj.addOrder(oo1);
        testObj.addOrder(oo2);
        testObj.addOrder(oo3);
        Collection<Order> result = testObj.getAllOrders("12252016");

        Assert.assertEquals(expResult.size(), result.size());
        Assert.assertNotNull(result);
        Assert.assertNotNull(expResult);
    }

    @Test
    public void testGetAllOrdersOnlyOne() {

        Collection<Order> expResult = new HashSet<>();
        Order o1 = new Order(1, "Chip", "KY", "12232016", 3.75, "CARPET", 230.0, 13.95, 12.0);
        expResult.add(o1);

        Order oo1 = ordersForTesting[0];
        Order oo2 = ordersForTesting[1];
        Order oo3 = ordersForTesting[2];
        testObj.addOrder(oo1);
        testObj.addOrder(oo2);
        testObj.addOrder(oo3);
        Collection<Order> result = testObj.getAllOrders("12232016");

        Assert.assertEquals(expResult.size(), result.size());
        Assert.assertNotNull(result);
        Assert.assertNotNull(expResult);
    }

}
