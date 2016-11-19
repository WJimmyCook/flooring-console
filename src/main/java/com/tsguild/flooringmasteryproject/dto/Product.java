/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.flooringmasteryproject.dto;

/**
 *
 * @author Jimmy Cook
 */
public class Product {
    
    String ProductType;
    double materialCost;
    double laborCost;

    public Product(String ProductType, double materialCost, double laborCost) {
        this.ProductType = ProductType;
        this.materialCost = materialCost;
        this.laborCost = laborCost;
    }
    
    public Product(){}

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String ProductType) {
        this.ProductType = ProductType;
    }

    public double getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(double materialCost) {
        this.materialCost = materialCost;
    }

    public double getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(double laborCost) {
        this.laborCost = laborCost;
    }
    
    
}
