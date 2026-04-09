package com.pao.proiect.tema.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Menu {
    private List<MenuItem> items;

    public Menu(){
        this.items = new ArrayList<>();
    }

    public void addItem(MenuItem item){
        if(item != null && !items.contains(item)){
            items.add(item);
        }
    }

    public boolean removeItem(int id){
        return items.removeIf(item -> item.getId() == id);
    }

    public void sortMenu(){
        Collections.sort(items);
    }

    public List<MenuItem> getVeganMenu(){
        return items.stream().filter(MenuItem::isVegan).collect(Collectors.toList());
    }

    public List<MenuItem> getVegetarianMenu(){
        return items.stream().filter(MenuItem::isVegetarian).collect(Collectors.toList());
    }

    public List<MenuItem> getSafeMenu(Allergen allergen){
        return items.stream().filter(item -> !item.getAllergens().contains(allergen)).collect(Collectors.toList());
    }

    public List<DrinkItem> getDrinks(){
        return items.stream().filter(item -> item instanceof DrinkItem).map(item -> (DrinkItem) item).collect(Collectors.toList());
    }

    public List<MenuItem> getItems(){
        return Collections.unmodifiableList(items);
    }
}