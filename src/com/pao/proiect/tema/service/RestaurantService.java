package com.pao.proiect.tema.service;

import com.pao.proiect.tema.model.Restaurant;
import com.pao.proiect.tema.repository.RestaurantsRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RestaurantService{
    private final List<Restaurant> restaurants;
    AuditService audit = AuditService.getInstance();
    RestaurantsRepository restaurantsRepository = new RestaurantsRepository();
    public RestaurantService() {
        this.restaurants = new ArrayList<>();
    }

    private static class Holder{
        private static final RestaurantService INSTANCE = new RestaurantService();
    }

    public static RestaurantService getInstance(){
        return Holder.INSTANCE;
    }

    public void addRestaurant(Restaurant restaurant) throws SQLException {
        if(restaurant == null)
            throw new IllegalArgumentException("Restaurant cannot be null");

        boolean exists = restaurants.stream().anyMatch(r -> r.getName().equalsIgnoreCase(restaurant.getName()));
        if(!exists){
            restaurantsRepository.save(restaurant);
            audit.log("add_restaurant");
        }
    }

    public Optional<Restaurant> findByName(String name){
        audit.log("find_restaurant_by_name");
        return restaurants.stream().filter(r -> r.getName().equalsIgnoreCase(name)).findFirst();
    }

    public List<Restaurant> getAll(){
        audit.log("get_all_restaurants");
        return Collections.unmodifiableList(restaurants);
    }
}