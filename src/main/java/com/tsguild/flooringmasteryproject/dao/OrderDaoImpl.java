/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.flooringmasteryproject.dao;

import com.tsguild.flooringmasteryproject.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author Jimmy Cook
 */
public class OrderDaoImpl implements OrderDao {

    private final String DELIMITER;
    private final String DIRECTORY;
    HashMap<String, HashMap<Integer, Order>> orders;

    public OrderDaoImpl() {
        DELIMITER = ",";
        DIRECTORY = "orders/";
        orders = new HashMap<>();
    }

    public Set<String> getAllDates() {
        return orders.keySet();
    }

    @Override
    public Collection<Order> getAllOrders(String date) {
        return orders.get(date).values();
    }

    @Override
    public void addOrder(Order o) {
        if (orders.containsKey(o.getDate())) {
            orders.get(o.getDate()).put(o.getOrderNum(), o);
        } else {
            orders.put(o.getDate(), new HashMap<>());
            orders.get(o.getDate()).put(o.getOrderNum(), o);

        }
    }

    @Override
    public Order getOrder(String date, int orderNum) {
            return orders.get(date).get(orderNum);
    }

    @Override
    public Order removeOrder(String date, int orderNum) {
        return orders.get(date).remove(orderNum);
    }

    @Override
    public void saveToFile() {

        for (String s : orders.keySet()) {
            try {
                PrintWriter write = new PrintWriter(new FileWriter(DIRECTORY
                        + "Order_" + s + ".txt"));
                for (Order o : orders.get(s).values()) {
                    write.print(o.getOrderNum() + DELIMITER
                            + o.getCustomerName().replace(",", "::") + DELIMITER
                            + o.getState() + DELIMITER
                            + o.getDate() + DELIMITER
                            + o.getTaxRate() + DELIMITER
                            + o.getProductType() + DELIMITER
                            + o.getArea() + DELIMITER
                            + o.getCostSqFt() + DELIMITER
                            + o.getLaborSqFt() + DELIMITER
                            + o.getMaterialCost() + DELIMITER
                            + o.getLaborCost() + DELIMITER
                            + o.getTax() + DELIMITER
                            + o.getTotal()
                    );
                    write.println();
                }
                write.flush();
                write.close();
            } catch (IOException ex) {
                Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        try {
            // after saving all the files, delete any empty file
            this.deleteEmptyFile();
        } catch (IOException ex) {
            Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void loadFromFile() {
        
        try (Stream<Path> paths = Files.walk(Paths.
                get("orders"))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        //System.out.println(filePath.getFileName());
                        Scanner reader = new Scanner(new BufferedReader(new FileReader(DIRECTORY + filePath.getFileName())));

                        while (reader.hasNextLine()) {
                            String line = reader.nextLine();
                            String[] lineProperties = line.split(DELIMITER);

                            if (lineProperties.length != 13) {
                                continue;
                            }

                            String customerName = lineProperties[1].replace("::", ",");
                            String state = lineProperties[2];
                            String date = lineProperties[3];
                            String type = lineProperties[5];

                            try {
                                int orderNum = Integer.parseInt(lineProperties[0]);
                                double taxRate = Double.parseDouble(lineProperties[4]);
                                double area = Double.parseDouble(lineProperties[6]);
                                double costSqFt = Double.parseDouble(lineProperties[7]);
                                double laborSqFt = Double.parseDouble(lineProperties[8]);
                                double materialCost = Double.parseDouble(lineProperties[9]);
                                double laborCost = Double.parseDouble(lineProperties[10]);
                                double taxTotal = Double.parseDouble(lineProperties[11]);
                                double total = Double.parseDouble(lineProperties[12]);

                                Order anOrder = new Order();

                                anOrder.setOrderNum(orderNum);
                                anOrder.setCustomerName(customerName);
                                anOrder.setState(state);
                                anOrder.setDate(date);
                                anOrder.setTaxRate(taxRate);
                                anOrder.setProductType(type);
                                anOrder.setArea(area);
                                anOrder.setCostSqFt(costSqFt);
                                anOrder.setLaborSqFt(laborSqFt);
                                anOrder.setMaterialCost(materialCost);
                                anOrder.setLaborCost(laborCost);
                                anOrder.setTax(taxTotal);
                                anOrder.setTotal(total);

                                this.addOrder(anOrder);

                            } catch (NumberFormatException e) {
                                Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, e);
                            }

                        }

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
        } catch (IOException ex) {
            Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    try (Stream<Path> paths = Files.walk(Paths.
//                get("orders"))) {
//            paths.forEach(filePath -> {
//                if (Files.isRegularFile(filePath)) {

    @Override
    public void deleteEmptyFile() throws IOException {

        try (Stream<Path> paths = Files.walk(Paths.get("orders"))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    File file = new File(filePath.toString());
                    if (file.length() == 0) {
                        file.delete();
                    }
                }
            });
        }
    }

    @Override
    public List<Integer> getAllOrderNumbers() {
        List<Integer> orderNums = new ArrayList();
        orders.keySet().stream().forEach((date) -> {
            orderNums.addAll(orders.get(date).keySet());
        });
        
        return orderNums;
    }

    @Override
    public int getNewOrderNumber() {
        Random r = new Random();
        int rand;
        boolean keepLooping = true;
        
        do{
            rand = r.nextInt(999999);
            if(!this.getAllOrderNumbers().contains(rand)){
                keepLooping = false;
            }
        }while(keepLooping);
        return rand;
    }
}
