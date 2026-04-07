package com.pao.proiect.tema.model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private String adress;
    private int points;
    List<Order> orderHistory;

    public Customer(String name, String email, String phoneNum, String password, String adress){
        super(name, email, phoneNum, password);
        this.adress = adress;
        this.points = 0;
        this.orderHistory = new ArrayList<>();
    }

    @Override
    public String getRole(){
        return "Customer";
    }

    public void addLoyaltyPoints(int p){
        if(p > 0){
            this.points += p;
        }
    }

    public String getAdress() {
        return adress;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
