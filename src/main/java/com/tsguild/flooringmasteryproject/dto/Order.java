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
public class Order {
    
    int orderNum;
    String customerName;
    String state;
    String date;
    double taxRate;
    String productType;
    double area;
    double costSqFt;
    double laborSqFt;
    double materialCost;
    double laborCost;
    double tax;
    double total;

    public Order(int orderNum, String customerName, String state, String date, 
            double taxRate, String productType, double area, double costSqFt, double laborSqFt) {
        this.orderNum = orderNum;
        this.customerName = customerName;
        this.state = state;
        this.date = date;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.costSqFt = costSqFt;
        this.laborSqFt = laborSqFt;
        this.materialCost = area * costSqFt;
        this.laborCost = area * laborSqFt;
        this.tax = taxRate/100 * (this.materialCost + this.laborCost);
        this.total = this.tax + this.materialCost + this.laborCost;
    }
    
    public Order(){
        
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    public String getDate(){
        return date;
    }
    
    public void setDate(String date){
        this.date = date;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getCostSqFt() {
        return costSqFt;
    }

    public void setCostSqFt(double costSqFt) {
        this.costSqFt = costSqFt;
    }

    public double getLaborSqFt() {
        return laborSqFt;
    }

    public void setLaborSqFt(double laborSqFt) {
        this.laborSqFt = laborSqFt;
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

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
    
    
}
