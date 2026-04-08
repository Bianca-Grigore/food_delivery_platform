package com.pao.proiect.tema.model;

import java.util.HashSet;
import java.util.Set;

public abstract sealed class MenuItem implements Comparable<MenuItem> permits FoodItem, DrinkItem{
    protected String name;
    protected double calories;
    protected double price;
    protected int id;
    private static int id_num=0;
    protected String description;
    protected boolean isAvailable;
    protected int estimatedTime;
    private boolean isVegan;
    private boolean isVegeraian;
    private Set<Allergen> allergens;

    public MenuItem(String name, double calories, double price, String description, int estimatedTime, boolean isVegan, boolean isVegetarian) {
        this.name = name;
        this.calories = calories;
        this.price = price;
        this.id = id_num;
        id_num+=1;
        this.description = description;
        this.estimatedTime = estimatedTime;
        this.isAvailable = true;
        this.isVegan = isVegan;
        this.isVegeraian = isVegan || isVegetarian;
        this.allergens = new HashSet<>();
    }

    public void addAlergen(Allergen allergen){
        if(allergen == null) return;

        if(this.isVegan && (allergen == Allergen.Lactose || allergen == Allergen.Eggs || allergen == Allergen.Fish || allergen == Allergen.Crustaceans || allergen == Allergen.Molluscs)){
            System.out.println("Warning: Adding a non-vegan allergen to a vegan menu item.");
            return;
        }

        if(this.isVegeraian && (allergen == Allergen.Fish || allergen == Allergen.Crustaceans || allergen == Allergen.Molluscs)){
            System.out.println("Warning: Adding a non-vegetarian allergen to a vegetarian menu item.");
            return;
        }
        this.allergens.add(allergen);
    }

    public boolean applyDiscount(double p){
        if (p > 0 && p < 100){
            double discountAmount = price * (p / 100);
            price -= discountAmount;
            return true;
        }
           return false;
        }

    @Override
    public String toString(){
        return "MenuItem " + id + ", name: " + name +
                ", calories: " + calories + ", price: " + price +
                ", description: " + description + ", estimated time: " +
                estimatedTime + " minutes, vegan: " + isVegan + ", vegetarian: " + isVegeraian+
                ", allergens: " + allergens;
    }

    @Override
    public int compareTo(MenuItem other){
        int pc = Double.compare(this.price, other.price);
        if(pc == 0){
            return this.name.compareTo(other.name);
        }
        return pc;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        MenuItem menuItem = (MenuItem) o;
        return id == menuItem.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public int getId() {
        return id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isVegan(){
        return isVegan;
    }

    public boolean isVegetarian(){
        return isVegeraian;
    }

    public Set<Allergen> getAllergens(){
        return allergens;
    }
}
