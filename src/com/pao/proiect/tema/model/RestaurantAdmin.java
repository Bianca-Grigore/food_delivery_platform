package com.pao.proiect.tema.model;


public final class RestaurantAdmin extends User {
    private String restaurantName;
    private boolean isActive;
    private AccessLevel accessLevel;

    public RestaurantAdmin(String name, String email, String phoneNum, String password, String restaurantName, AccessLevel accessLevel) {
        super(name, email, phoneNum, password);
        this.restaurantName = restaurantName;
        this.isActive = true;
        this.accessLevel = accessLevel;
    }

    @Override
    public String getRole() {
        return "Restaurant Admin";
    }

    @Override
    public String toString() {
        return super.toString() + ", restaurant name: " + restaurantName + ", access level: " + accessLevel + ", active: " + isActive;
    }

    public void deactivateAccount() {
        this.isActive = false;
    }


    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName( String name){
        this.restaurantName = name;
    }

    public boolean isActive(){
        return isActive;
    }

    public AccessLevel getAccessLevel(){
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel){
        this.accessLevel = accessLevel;
    }
}