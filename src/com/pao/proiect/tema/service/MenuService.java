package com.pao.proiect.tema.service;

import com.pao.proiect.tema.model.MenuItem;
import java.util.*;

public class MenuService {
    private List<MenuItem> menu;

    private MenuService(){
        this.menu = new ArrayList<>();
    }

    private static class Holder{
        private static final MenuService INSTANCE = new MenuService();
    }

    public static MenuService getInstance(){
        return MenuService.Holder.INSTANCE;
    }

    public void addProduct(MenuItem item){
        if(item == null){
            throw new IllegalArgumentException("Menu item cannot be null");
        }

        if(!menu.contains(item)){
            menu.add(item);
            System.out.println("Product added to menu: " + item.getName());
        }
        else{
            System.out.println("Product already exists in menu: " + item.getName());
        }
    }

    public void deleteProduct(MenuItem item){
        if(item == null){
            throw new IllegalArgumentException("Menu item cannot be null");
        }
        if(menu.remove(item)){
            System.out.println("Product removed from menu: " + item.getName());
        }
        else{
            System.out.println("Product not found in menu: " + item.getName());
        }
    }

    public List<MenuItem> getMenu(){
        return Collections.unmodifiableList(menu);
    }

    public Optional<MenuItem> findById(int id){
        return menu.stream().filter(item -> item.getId() == id).findFirst();
    }

    public Optional<MenuItem> findByName(String name){
        if(name == null){
            return Optional.empty();
        }
        return menu.stream().filter(item -> item.getName() != null && item.getName().equalsIgnoreCase(name)).findFirst();
    }

    public List<MenuItem> getVeganMenu(){
        return menu.stream().filter(MenuItem::isVegan).toList();
    }

    public List<MenuItem> getVegetarianMenu(){
        return menu.stream().filter(MenuItem::isVegetarian).toList();
    }

    public List<MenuItem> getWithPrice(double price) {
        return menu.stream().filter(item -> item.getPrice() <= price).sorted().toList();
    }
}
