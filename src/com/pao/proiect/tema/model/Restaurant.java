package com.pao.proiect.tema.model;

public class Restaurant {
    private RestaurantAdmin admin;
    private Menu menu;
    private String name;
    private String address;
    private boolean isOpen;

    public Restaurant(String name, String address, RestaurantAdmin admin){
        this.name = name;
        this.address = address;
        this.admin = admin;
        this.isOpen = false;
        this.menu = new Menu();
    }

    public void open(){
        this.isOpen = true;
        System.out.println("Restaurant " + name + " is now open.");
    }

    public void close(){
        this.isOpen = false;
        System.out.println("Restaurant " + name + " is now closed.");
    }

    public void addProduct(MenuItem item){
        this.menu.addItem(item);
    }
}