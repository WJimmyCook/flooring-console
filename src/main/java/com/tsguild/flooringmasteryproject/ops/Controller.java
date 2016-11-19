/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.flooringmasteryproject.ops;

import com.tsguild.flooringmasteryproject.dao.OrderDaoImpl;
import com.tsguild.flooringmasteryproject.dao.ProductDaoImpl;
import com.tsguild.flooringmasteryproject.dao.TaxDaoImpl;
import com.tsguild.flooringmasteryproject.dto.Order;
import com.tsguild.flooringmasteryproject.ui.ConsoleIO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jimmy Cook
 */
public class Controller {

    ConsoleIO io;
    OrderDaoImpl orders;
    TaxDaoImpl tax;
    ProductDaoImpl product;
    ProductionMode mode;
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String BG_CYAN = "\u001B[46m\u001B[30m";
    private static final String BG_BLUE = "\u001B[44m\u001B[37m";

    public Controller(ConsoleIO io, OrderDaoImpl orders, TaxDaoImpl tax, ProductDaoImpl product, ProductionMode mode) {
        this.io = io;
        this.orders = orders;
        this.tax = tax;
        this.product = product;
        this.mode = mode;
    }

    public void run(){

        orders.loadFromFile();
        try { // tries to load taxes
            tax.loadFromFile();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            io.print("Tax file cannot be loaded. Contact your system administrator");
            return;
        }
        try { // tries to lead product file
            product.loadFromFile();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            io.print("Product file cannot be loaded. Contact your system administrator");
            return;
        }

        boolean keepRunning = true;

        while (keepRunning) {
            menu();
            int choice = io.readInt("Enter menu number: ", 1, 6);

            switch (choice) {
                case 1:
                    displayOrder();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5: {
                    try {
                        // checks config.properties file to see if prod or test mode
                        if (mode.getEnvironment().equalsIgnoreCase("prod")) {
                            orders.saveToFile();
                            io.print(ANSI_GREEN + "SAVE SUCCESSFUL" + ANSI_RESET);
                        } else {
                            io.print(ANSI_RED + "You are in test mode. You can't save your work." + ANSI_RESET);
                        }
                    } catch (IOException ex) {
                        io.print("No Production config file found");
                    }
                }
                break;
                default: // int 6 is only other allowed choice
                    keepRunning = false;
                    break;
            }

        }

    }

    private void menu(){
        
        // fancy menu display
        io.print("");
        io.print(BG_BLUE + "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        io.print(BG_BLUE + "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        io.print(BG_BLUE + "::" + BG_CYAN + "                                                                  " + BG_BLUE + "::");
        io.print(BG_BLUE + "::" + ANSI_RESET + BG_CYAN + "          _____ _                  __  __            _            " + BG_BLUE + "::");
        io.print(BG_BLUE + "::" + ANSI_RESET + BG_CYAN + "         |  ___| | ___   ___  _ __|  \\/  | __ _ _ __| |_          " + BG_BLUE + "::");
        io.print(BG_BLUE + "::" + ANSI_RESET + BG_CYAN + "         | |_  | |/ _ \\ / _ \\| '__| |\\/| |/ _` | '__| __|         " + BG_BLUE + "::");
        io.print(BG_BLUE + "::" + ANSI_RESET + BG_CYAN + "         |  _| | | (_) | (_) | |  | |  | | (_| | |  | |_          " + BG_BLUE + "::");
        io.print(BG_BLUE + "::" + ANSI_RESET + BG_CYAN + "         |_|   |_|\\___/ \\___/|_|  |_|  |_|\\__,_|_|   \\__|         " + BG_BLUE + "::");
        io.print(BG_BLUE + "::" + ANSI_RESET + BG_CYAN + "                                                EST. 1901         " + BG_BLUE + "::");
        io.print(BG_BLUE + "::" + ANSI_RESET + BG_CYAN + "                                                                  " + BG_BLUE + "::");
        io.print(BG_BLUE + "::" + ANSI_RESET + BG_CYAN + "  1) Display Orders                                               " + BG_BLUE + "::");
        io.print(BG_BLUE + "::" + ANSI_RESET + BG_CYAN + "  2) Add an Order                                                 " + BG_BLUE + "::");
        io.print(BG_BLUE + "::" + ANSI_RESET + BG_CYAN + "  3) Edit an Order                                                " + BG_BLUE + "::");
        io.print(BG_BLUE + "::" + ANSI_RESET + BG_CYAN + "  4) Remove an Order                                              " + BG_BLUE + "::");
        try {
            if (mode.getEnvironment().equalsIgnoreCase("TEST")) {
                io.print(BG_BLUE + "::" + ANSI_RESET + BG_CYAN + "  5) Save Current Work " + ANSI_RED + "    ***TEST MODE CANNOT SAVE WORK***       " + BG_BLUE + "::");
            } else {
                io.print(BG_BLUE + "::" + ANSI_RESET + BG_CYAN + "  5) Save Current Work                                            " + BG_BLUE + "::");
            }
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            io.print(BG_BLUE + "::" + ANSI_RESET + BG_CYAN + "  5) Save Current Work                                            " + BG_BLUE + "::");
        } 
        io.print(BG_BLUE + "::" + ANSI_RESET + BG_CYAN + "  6) Quit                                                         " + BG_BLUE + "::");
        io.print(BG_BLUE + "::" + ANSI_RESET + BG_CYAN + "                                                                  " + BG_BLUE + "::");
        io.print(BG_BLUE + "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        io.print(BG_BLUE + "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + ANSI_RESET);
        io.print("");

    }

    private void displayOrder() {
        String date = getValidDate();

        if (orders.getAllDates().contains(date)) {
            io.print("");
            io.print(BG_BLUE + "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + ANSI_RESET);
            io.print(BG_BLUE + "::" + ANSI_RESET + BG_CYAN + "                   ALL ORDERS FOR " + myDateFormat(date) + "                      " + BG_BLUE + "::");
            io.print(BG_BLUE + "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + ANSI_RESET);
            io.print("");
            for (Order o : orders.getAllOrders(date)) {
                io.print(BG_BLUE + "::" + ANSI_RESET + " Order #: " + o.getOrderNum() + ", Customer name: " + o.getCustomerName());
                io.print(BG_BLUE + "::" + ANSI_RESET + " State: " + o.getState() + ", Tax rate: " + String.format("%.2f", o.getTaxRate()) + "%");
                io.print(BG_BLUE + "::" + ANSI_RESET + " Material cost SqFt: " + String.format("$%.2f", o.getCostSqFt()) + ", Labor cost SqFt: " + String.format("$%.2f", o.getLaborSqFt()));
                io.print(BG_BLUE + "::" + ANSI_RESET + " Material cost: " + String.format("$%.2f", o.getMaterialCost()) + ", Labor cost: " + String.format("$%.2f", o.getLaborCost()) + ", Tax: " + String.format("$%.2f", o.getTax()));
                io.print(BG_BLUE + "::" + ANSI_RESET + " Total: " + String.format("$%.2f", o.getTotal()));
                io.print("");
            }
        } else {
            io.print("There are no orders for " + date);
        }

    }

    private void addOrder() {
        io.print("");
        io.print(BG_BLUE + "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + ANSI_RESET);
        io.print(BG_BLUE + "::" + ANSI_RESET + BG_CYAN + "                         ADD A NEW ORDER                          " + BG_BLUE + "::");
        io.print(BG_BLUE + "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + ANSI_RESET);
        io.print("");
        String date = getValidDate();

        String name = io.readString("Customer name: ");
        String state = io.readString("State abbreviation: ").toUpperCase();
        // loops until a valid state abbreviation is entered
        while (!tax.containsState(state)) {
            io.print(String.format(ANSI_RED + "NOT A VALID STATE!" + ANSI_RESET));
            state = io.readString("Please enter the 2-letter state abbreviation: ").toUpperCase();
        }
        double taxRate = tax.getTax(state);
        String type = io.readString("Product type: ").toUpperCase();
        // loops until a product type that we carry is entered
        while (!product.containsType(type)) {
            io.print(String.format(ANSI_RED + "NOT A VALID PRODUCT!" + ANSI_RESET));
            type = io.readString("Enter product type: ").toUpperCase();
        }
        double area = io.readDouble("Area: ", 0.0, 200000.0);
        double costSqFt = product.getProductInfo(type).getMaterialCost();
        double laborSqFt = product.getProductInfo(type).getLaborCost();
        double materialCost = area * costSqFt;
        double laborCost = area * laborSqFt;
        double taxTotal = taxRate / 100 * (materialCost + laborCost);
        double total = materialCost + laborCost + taxTotal;
        int orderNum = orders.getNewOrderNumber();
        
        // print output for customer to verify
        io.print("");
        io.print(String.format(BG_BLUE + "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + ANSI_RESET));
        io.print("Order #: " + orderNum);
        io.print("Date: " + myDateFormat(date));
        io.print("Customer name: " + name);
        io.print("State: " + state);
        io.print("Tax rate: " + String.format("%.2f", taxRate) + "%");
        io.print("Product type: " + type);
        io.print("Area: " + area);
        io.print("Cost per square foot: " + String.format("$%.2f", costSqFt));
        io.print("Labor cost per square foot: " + String.format("$%.2f", laborSqFt));
        io.print("Material cost: " + String.format("$%.2f", materialCost));
        io.print("Labor cost: " + String.format("$%.2f", laborCost));
        io.print("Tax: " + String.format("$%.2f", taxTotal));
        io.print("Total: " + String.format("$%.2f", total));
        io.print(String.format(BG_BLUE + "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + ANSI_RESET));

        String commit = io.readString("Commit order? (y/n)");
        if (commit.equalsIgnoreCase("y")) {

            Order anOrder = new Order(orderNum, name, state, date, taxRate,
                    type, area, costSqFt, laborSqFt);

            orders.addOrder(anOrder);
            io.print(String.format(ANSI_GREEN + "Order #" + orderNum
                    + " for " + name + " successfully added to records." + ANSI_RESET));

        } else {
            io.print(String.format(ANSI_RED + "Order NOT saved." + ANSI_RESET));
        }

    }

    private void editOrder() {
        String date = getValidDate();
        if (orders.getAllDates().contains(date)) {
            io.print("");
            io.print(BG_BLUE + "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + ANSI_RESET);
            io.print(BG_BLUE + "::" + ANSI_RESET + BG_CYAN + "                   EDIT ORDERS FOR " + myDateFormat(date) + "                     " + BG_BLUE + "::");
            io.print(BG_BLUE + "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + ANSI_RESET);
            io.print("");
            for (Order o : orders.getAllOrders(date)) {
                io.print(BG_BLUE + "::" + ANSI_RESET + " Order #: " + o.getOrderNum());
                io.print(BG_BLUE + "::" + ANSI_RESET + " Customer name: " + o.getCustomerName());
                io.print("");
            }
        } else {
            io.print(String.format(ANSI_RED + "There are no orders for " + date + ANSI_RESET));
            return;
        }
        
        int orderNum;
        boolean notOrderNumber = true;
        do{
            orderNum = io.readInt("Enter order number: ");
            for(Order o : orders.getAllOrders(date)){
                if(o.getOrderNum() == orderNum){
                    notOrderNumber = false;
                }
            }
        }while(notOrderNumber);

        Order orderEdit = orders.removeOrder(date, orderNum);

        String newName = io.readString("Enter customer name (" + orderEdit.getCustomerName() + "):");
        if (newName.length() > 0) {
            orderEdit.setCustomerName(newName);
        }

        String newState = io.readString("Enter state (" + orderEdit.getState() + "):");
        if (newState.length() > 0) {
            while (!tax.containsState(newState)) {
                io.print(String.format(ANSI_RED + "NOT A VALID STATE!" + ANSI_RESET));
                newState = io.readString("Please enter the 2-letter state abbreviation: ").toUpperCase();
            }
            orderEdit.setState(newState.toUpperCase());
        }

        boolean dateInvalid = true;
        while (dateInvalid) {
            String newDate = io.readString("Order date (" + orderEdit.getDate() + ") : ");
            if (newDate.length() == 0) {
                dateInvalid = false;
                continue;
            }
            if (newDate.length() != 8) { 
                io.print(String.format(ANSI_RED + "NOT A VALID DATE!" + ANSI_RESET));
                continue;
            }

            try { // convert string 
                int month = Integer.parseInt(newDate.substring(0, 2));
                int days = Integer.parseInt(newDate.substring(2, 4));
                int year = Integer.parseInt(newDate.substring(4, 8));

                GregorianCalendar cal = new GregorianCalendar();
                if (month < 13 && days < 32 && year > 1900) {
                    if (month == 2 && days == 29) {
                        if (cal.isLeapYear(year)) {
                            dateInvalid = false;
                            orderEdit.setDate(newDate);
                            io.print(ANSI_GREEN + "HORRAY FOR LEAP DAY!!" + ANSI_RESET);
                        } else {
                            io.print(ANSI_RED + year + " IS NOT A LEAP YEAR!" + ANSI_RESET);
                        }
                    } else if (month == 2 && days < 29) {
                        dateInvalid = false;
                        orderEdit.setDate(newDate);
                    } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                        if (days == 31) {
                            io.print(ANSI_RED + "NOT A VALID DATE!" + ANSI_RESET);
                        } else {
                            dateInvalid = false;
                            orderEdit.setDate(newDate);
                        }
                    } else {
                        dateInvalid = false;
                        orderEdit.setDate(newDate);
                    }

                } else {
                    io.print(ANSI_RED + "NOT A VALID DATE!" + ANSI_RESET);
                }

            } catch (NumberFormatException e) {
                io.print(String.format(ANSI_RED + "NOT A VALID DATE!" + ANSI_RESET));
            }

        }

//    double taxRate;
        String newTax = io.readString("Enter tax rate (" + orderEdit.getTaxRate() + "):");

        if (newTax.length()
                > 0) {
            try {
                double newTaxDouble = Double.parseDouble(newTax);
                orderEdit.setTaxRate(newTaxDouble);
            } catch (NumberFormatException e) {
                io.print(String.format(ANSI_RED + "NOT A NUMBER!" + ANSI_RESET));
            }

        }
//    String productType;
        String type = io.readString("Enter product type (" + orderEdit.getProductType() + "):");

        if (type.length() > 0) {
            while (!product.containsType(type.toUpperCase())) {
                io.print(String.format(ANSI_RED + "NOT A VALID PRODUCT!" + ANSI_RESET));
                type = io.readString("Enter product type: ").toUpperCase();
            }
            orderEdit.setProductType(type.toUpperCase());
        }
//    double area;
        String newArea = io.readString("Enter area ("
                + String.format("%.2f", orderEdit.getArea()) + "):");

        if (newArea.length()
                > 0) {
            try {
                double newAreaDouble = Double.parseDouble(newArea);
                orderEdit.setTaxRate(newAreaDouble);
            } catch (NumberFormatException e) {
                io.print(String.format(ANSI_RED + "NOT A NUMBER!" + ANSI_RESET));
            }

        }
//    double costSqFt;
        String newCostSqFt = io.readString("Enter cost per sqft ("
                + String.format("%.2f", orderEdit.getCostSqFt()) + "):");

        if (newCostSqFt.length()
                > 0) {
            try {
                double newCostSqFtDouble = Double.parseDouble(newCostSqFt);
                orderEdit.setTaxRate(newCostSqFtDouble);
            } catch (NumberFormatException e) {
                io.print(String.format(ANSI_RED + "NOT A NUMBER!" + ANSI_RESET));
            }

        }
//    double laborSqFt;
        String newLaborSqFt = io.readString("Enter labor cost per sqft ("
                + String.format("%.2f", orderEdit.getLaborSqFt()) + "):");

        if (newLaborSqFt.length()
                > 0) {
            try {
                double newLaborSqFtDouble = Double.parseDouble(newLaborSqFt);
                orderEdit.setTaxRate(newLaborSqFtDouble);
            } catch (NumberFormatException e) {
                io.print(String.format(ANSI_RED + "NOT A NUMBER!" + ANSI_RESET));
            }

        }

        String newMaterialCost = io.readString("Enter material cost ("
                + String.format("%.2f", orderEdit.getMaterialCost()) + "):");

        if (newMaterialCost.length()
                > 0) {
            try {
                double newMaterialCostDouble = Double.parseDouble(newMaterialCost);
                orderEdit.setTaxRate(newMaterialCostDouble);
            } catch (NumberFormatException e) {
                io.print(String.format(ANSI_RED + "NOT A NUMBER!" + ANSI_RESET));
            }

        }

        String newLaborCost = io.readString("Enter labor cost ("
                + String.format("%.2f", orderEdit.getLaborCost()) + "):");

        if (newLaborCost.length()
                > 0) {
            try {
                double newLaborCostDouble = Double.parseDouble(newLaborCost);
                orderEdit.setTaxRate(newLaborCostDouble);
            } catch (NumberFormatException e) {
                io.print(String.format(ANSI_RED + "NOT A NUMBER!" + ANSI_RESET));
            }

        }

        String newTaxTotal = io.readString("Enter tax ("
                + String.format("%.2f", orderEdit.getTax()) + "):");

        if (newTaxTotal.length()
                > 0) {
            try {
                double newTaxTotalDouble = Double.parseDouble(newTaxTotal);
                orderEdit.setTaxRate(newTaxTotalDouble);
            } catch (NumberFormatException e) {
                io.print(String.format(ANSI_RED + "NOT A NUMBER!" + ANSI_RESET));
            }

        }

        String newTotal = io.readString("Enter total ("
                + String.format("%.2f", orderEdit.getTotal()) + "):");

        if (newTotal.length()
                > 0) {
            try {
                double newTotalDouble = Double.parseDouble(newTotal);
                orderEdit.setTaxRate(newTotalDouble);
            } catch (NumberFormatException e) {
                io.print(String.format(ANSI_RED + "NOT A NUMBER!" + ANSI_RESET));
            }

        }

        orders.addOrder(orderEdit);

        io.print(String.format(ANSI_GREEN + "Order #" + orderEdit.getOrderNum() + 
                " for " + orderEdit.getCustomerName() + " successfully edited!" + ANSI_RESET));

    }

    private void removeOrder() {
        String date = getValidDate();

        if (orders.getAllDates().contains(date)) {
            io.print("");
            io.print(BG_BLUE + "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + ANSI_RESET);
            io.print(BG_BLUE + "::" + ANSI_RESET + BG_CYAN + "                  REMOVE ORDERS FOR " + myDateFormat(date) + "                    " + BG_BLUE + "::");
            io.print(BG_BLUE + "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + ANSI_RESET);
            io.print("");
            for (Order o : orders.getAllOrders(date)) {
                io.print("Order #: " + o.getOrderNum() + ", Customer name: " + o.getCustomerName());
            }
        } else {
            io.print(String.format(ANSI_RED + "There are no orders for " + date + ANSI_RESET));
            return;
        }
        
        int orderNum;
        boolean notOrderNumber = true;
        do{
            orderNum = io.readInt("Enter order number: ");
            for(Order o : orders.getAllOrders(date)){
                if(o.getOrderNum() == orderNum){
                    notOrderNumber = false;
                }
            }
        }while(notOrderNumber);

        Order removed = orders.removeOrder(date, orderNum);

        String answer = io.readString("Are you sure you want to remove order #"
                + removed.getOrderNum() + " for " + removed.getCustomerName() + "? (y/n)");

        if (answer.equalsIgnoreCase("y")) {
            io.print(ANSI_GREEN + "RECORD DELETED!" + ANSI_RESET);
        } else {
            orders.addOrder(removed);
            io.print(ANSI_GREEN + "RECORD NOT DELETED" + ANSI_RESET);
        }
    }

    private String getValidDate() {
        String date = "";
        // keeps looping until a date is given in a valid format
        boolean dateInvalid = true;
        while (dateInvalid) {
            date = io.readString("Order date (MMDDYYYY): ");
            if (date.length() != 8) {
                io.print(ANSI_RED + "NOT A VALID DATE!" + ANSI_RESET);
                continue;
            }

            try {
                int month = Integer.parseInt(date.substring(0, 2));
                int days = Integer.parseInt(date.substring(2, 4));
                int year = Integer.parseInt(date.substring(4, 8));

                GregorianCalendar cal = new GregorianCalendar();
                if (month < 13 && days < 32 && year > 1900) {
                    if (month == 2 && days == 29) {
                        if (cal.isLeapYear(year)) {
                            dateInvalid = false;
                            io.print(ANSI_GREEN + "HORRAY FOR LEAP DAY!!" + ANSI_RESET);
                        } else {
                            io.print(ANSI_RED + year + " IS NOT A LEAP YEAR!" + ANSI_RESET);
                        }
                    } else if (month == 2 && days < 29) {
                        dateInvalid = false;
                    } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                        if (days == 31) {
                            io.print(ANSI_RED + "NOT A VALID DATE!" + ANSI_RESET);
                        } else {
                            dateInvalid = false;
                        }
                    } else {
                        dateInvalid = false;
                    }

                } else {
                    io.print(ANSI_RED + "NOT A VALID DATE!" + ANSI_RESET);
                }

            } catch (NumberFormatException e) {
                io.print(ANSI_RED + "NOT A VALID DATE!" + ANSI_RESET);
            }
        }
        return date;
    }
    
    private String myDateFormat(String date){
        return date.substring(0,2) + "/" + date.substring(2,4) + "/" + date.substring(4,8);
    }

}
