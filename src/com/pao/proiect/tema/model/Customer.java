package com.pao.proiect.tema.model;

import java.util.ArrayList;
import java.util.List;

public final class Customer extends User {
    private Address address;
    private int points;
    private List<Order> orderHistory;

    public Customer(String name, String email, String phoneNum, String password, Address address) {
        super(name, email, phoneNum, password);
        this.address = address;
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

    public void addOrder(Order order){
        if(order != null){
            this.orderHistory.add(order);
        }
    }

    @Override
    public String toString(){
        return super.toString() + " address: " + address + ", loyalty points: " + points;
    }

    public Address getAddress() {
        return address;
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

}