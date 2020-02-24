package com.atguigu.spring.beans.cycle;

public class Car {

    public Car() {
        System.out.println("car's constructor........");
    }

    private String brand;

    public void setBrand(String brand) {
        System.out.println("set brand........");
        this.brand = brand;
    }

    public void init(){
        System.out.println("init..........");
    }

    public void destroy(){
        System.out.println("destroy.......");
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                '}';
    }
}
