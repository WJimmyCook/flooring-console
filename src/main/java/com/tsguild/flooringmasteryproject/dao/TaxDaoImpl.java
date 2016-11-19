/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.flooringmasteryproject.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Jimmy Cook
 */
public class TaxDaoImpl implements TaxDao {

    final private String FILE_NAME;
    final private String DELIMITER;
    HashMap<String, Double> taxes;
    
    public TaxDaoImpl(){
        FILE_NAME = "taxes.txt";
        DELIMITER = ",";
        taxes = new HashMap<>();
    }
    
    public TaxDaoImpl(String fileName){
        FILE_NAME = fileName;
        DELIMITER = ",";
        taxes = new HashMap<>();
    }

    @Override
    public double getTax(String state) {
        return taxes.get(state);
    }

    @Override
    public void loadFromFile() throws FileNotFoundException {

        Scanner reader = new Scanner(new BufferedReader(new FileReader(FILE_NAME)));

        while (reader.hasNextLine()) {
            String line = reader.nextLine();

            String[] lineProps = line.split(DELIMITER);

            if (lineProps.length != 2) {

            } else {

                try {
                    String state = lineProps[0];
                    String taxString = lineProps[1];
                    double tax = Double.parseDouble(taxString);

                    taxes.put(state, tax);
                } catch (NumberFormatException e) {
                    System.out.println("Numbers are wacky");
                }

            }

        }
        
        reader.close();
    }

    @Override
    public boolean containsState(String state) {
        return taxes.containsKey(state);
    }

}
