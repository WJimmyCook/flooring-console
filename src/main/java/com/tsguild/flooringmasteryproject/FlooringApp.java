/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsguild.flooringmasteryproject;

import com.tsguild.flooringmasteryproject.ops.Controller;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Jimmy Cook
 * 
 * Partnered with Shade and Sarah
 */
public class FlooringApp {
    public static void main(String[] args) {
        
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BeanFactory factory = context;
        Controller flooringFactory = (Controller) factory
                .getBean("Controller");
        flooringFactory.run();
        
        
        
    }
}
