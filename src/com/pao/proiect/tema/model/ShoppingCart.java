package com.pao.proiect.tema.model;

import com.pao.proiect.tema.exception.EmptyCartException;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Customer customer;
    private Restaurant restaurant;
    private Map<MenuItem, Integer> items;

    public ShoppingCart(Customer customer, Restaurant restaurant){
        this.customer = customer;
        this.restaurant = restaurant;
        this.items = new HashMap<>();
    }

    public void addItem(MenuItem item, int quantity){
        if(quantity <= 0){
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    public void removeItem(MenuItem item, int quantity){
        if(!items.containsKey(item)){
            return;
        }
        int currentQuantity = items.get(item);
        if(quantity >= currentQuantity){
            items.remove(item);
        }
        else{
            items.put(item, currentQuantity - quantity);
        }
    }

    public double calculateSubtotal(){
        double total = 0;
        for(Map.Entry<MenuItem, Integer> entry : items.entrySet()){
            total += (entry.getKey().getPrice() * entry.getValue());
        }
        return total;
    }

    public void clearCart(){
        items.clear();
    }

    public Order checkout(double deliveryFee, Payment paymentMethod, String notes) throws EmptyCartException {
        if(items.isEmpty()){
            throw new EmptyCartException("Cannot checkout with an empty cart.");
        }

        try{
            Order newOrder = new Order(customer, restaurant, items, deliveryFee, paymentMethod, notes);
            clearCart();
            return newOrder;
        }catch (Exception e){
            throw new RuntimeException("Failed to create order: " + e.getMessage());
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public Map<MenuItem, Integer> getItems() {
        return items;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
}
