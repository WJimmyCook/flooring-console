/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.flooringmasteryproject.dao;

import com.tsguild.flooringmasteryproject.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Jimmy Cook
 */
public class ProductDaoImpl implements ProductDao{
    
    final private String FILE_NAME;
    final private String DELIMITER;
    HashMap<String, Product> products;
    
    public ProductDaoImpl(){
        FILE_NAME = "products.txt";
        DELIMITER = ",";
        products = new HashMap<>();
    }
    
    public ProductDaoImpl(String fileName){
        FILE_NAME = fileName;
        DELIMITER = ",";
        products = new HashMap<>();
    }

    @Override
    public Product getProductInfo(String productType) {
        return products.get(productType);
    }

    @Override
    public void loadFromFile() throws FileNotFoundException {
        
        Scanner reader = new Scanner(new BufferedReader(new FileReader(FILE_NAME)));

        while (reader.hasNextLine()) {
            String line = reader.nextLine();

            String[] lineProps = line.split(DELIMITER);
            
            // There should only be 3 items in the array: type, cost, and labor
            if (lineProps.length != 3) {

            } else {

                try {
                    String type = lineProps[0];
                    String costString = lineProps[1];
                    String laborString = lineProps[2];
                    double cost = Double.parseDouble(costString);
                    double labor = Double.parseDouble(laborString);
                    
//                    public Product(String ProductType, double materialCost, double laborCost)
                    Product temp = new Product(type, cost, labor);
                    products.put(type, temp);
                    
                } catch (NumberFormatException e) {
                    System.out.println("Numbers are wacky");
                }

            }

        }
        
        reader.close();
    }

    @Override
    public boolean containsType(String type) {
        return products.containsKey(type);
    }
    
    
}
