package com.atguigu.spring.beans.scope;

import com.atguigu.spring.beans.autowire.Car;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-scope.xml");

        Car car = (Car) ctx.getBean("car");
        Car car1 = (Car) ctx.getBean("car");
        System.out.println(car == car1);

    }
}
