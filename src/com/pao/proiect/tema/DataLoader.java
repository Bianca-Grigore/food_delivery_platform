package com.pao.proiect.tema;
import com.pao.proiect.tema.model.*;
import com.pao.proiect.tema.service.RestaurantService;
import com.pao.proiect.tema.service.OrderService;
import com.pao.proiect.tema.service.UserService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataLoader {
    public static void loadData(UserService userService, RestaurantService restaurantService) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader("src/com/pao/proiect/tema/date_intrare"))){
            String line;
            String category = "";

            while((line = br.readLine()) != null){
                line = line.trim();
                if(line.isEmpty() || line.startsWith("#")) continue;

                if(line.startsWith("[")){
                    category = line;
                    continue;
                }
                try {
                    String[] data = line.split(";");
                    switch (category) {
                        case "[CUSTOMER]":
                            String nume = data[0].trim();
                            String email = data[1].trim();
                            String telefon = data[2].trim();
                            String parola = data[3].trim();
                            String city = data[4].trim();
                            String street = data[5].trim();
                            String building = data[6].trim();
                            String postalCode = data[7].trim();
                            String details = data[8].trim();

                            Address adr = new Address(city, street, building, postalCode, details);
                            Customer customer = new Customer(nume, email, telefon, parola, adr);
                            userService.registerUser(customer);
                            break;

                        case "[RESTAURANT]":
                            String name = data[0].trim();
                            String address = data[1].trim();
                            String adminName = data[2].trim();
                            String emailAdmin = data[3].trim();
                            String adminPhone = data[4].trim();
                            String adminPassword = data[5].trim();
                            String accesLevel = data[6].trim();
                            String isOpen = data[7].trim();
                            RestaurantAdmin admin = new RestaurantAdmin(adminName, emailAdmin, adminPhone, adminPassword, name, AccessLevel.valueOf(accesLevel));
                            Restaurant restaurant = new Restaurant(name, address, admin);
                            userService.registerUser(admin);
                            restaurantService.addRestaurant(restaurant);
                            restaurant.setOpen(isOpen.equalsIgnoreCase("true"));
                            break;

                        case "[FOOD_ITEM]":
                            restaurantService.findByName(data[0].trim()).ifPresent(r -> {
                                FoodItem foodItem = new FoodItem(
                                        data[1].trim(),
                                        Double.parseDouble(data[2].trim()),
                                        Double.parseDouble(data[3].trim()),
                                        data[4].trim(),
                                        Integer.parseInt(data[5].trim()),
                                        Boolean.parseBoolean(data[6].trim()),
                                        Boolean.parseBoolean(data[7].trim()),
                                        Double.parseDouble(data[8].trim()),
                                        Spiciness.valueOf(data[9].trim().toUpperCase()),
                                        CourseType.valueOf(data[10].trim().toUpperCase())
                                );
                                r.getMenu().addProduct(foodItem);
                            });
                            break;

                        case "[DRINK_ITEM]":
                            restaurantService.findByName(data[0].trim()).ifPresent(r -> {
                                DrinkItem drinkItem = new DrinkItem(
                                        data[1].trim(),
                                        Double.parseDouble(data[2].trim()),
                                        Double.parseDouble(data[3].trim()),
                                        data[4].trim(),
                                        Integer.parseInt(data[5].trim()),
                                        Boolean.parseBoolean(data[6].trim()),
                                        Boolean.parseBoolean(data[7].trim()),
                                        Boolean.parseBoolean(data[8].trim()),
                                        Integer.parseInt(data[9].trim()),
                                        Boolean.parseBoolean(data[10].trim()),
                                        Double.parseDouble(data[11].trim())
                                );
                                r.getMenu().addProduct(drinkItem);
                            });
                            break;

                        case "[DRIVER]":
                            VehicleType vehicleType = VehicleType.valueOf(data[4].trim().toUpperCase());
                            boolean isAvailable = Boolean.parseBoolean(data[5].trim());
                            DeliveryPerson driver = new DeliveryPerson(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), vehicleType, isAvailable);
                            userService.registerUser(driver);
                            break;
                    }
                }catch(ArrayIndexOutOfBoundsException e){
                    System.err.println("Incomplet line at category: " + category + " - " + line);
                }catch (NumberFormatException e){
                    System.err.println("Invalid number format at category: " + category + " - " + line);
                }catch(IllegalArgumentException e){
                    System.err.println("Invalid enum value at category: " + category + " - " + line);
                }catch(Exception e){
                    System.err.println("Error processing line at category: " + category + " - " + line);
                }
            }
        }
    }
}