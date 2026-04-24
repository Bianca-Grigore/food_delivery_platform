package com.pao.proiect.tema;

import com.pao.proiect.tema.model.*;
import com.pao.proiect.tema.service.RestaurantService;
import com.pao.proiect.tema.service.OrderService;
import com.pao.proiect.tema.service.UserService;

import java.io.IOException;
import java.util.Scanner;

public class Main{
    private static final UserService user = UserService.getInstance();
    private static final OrderService order = OrderService.getInstance();
    private static final RestaurantService restaurant = RestaurantService.getInstance();
    private static User userCurrent = null;

    public static void main(String[] args) {
        try {
            DataLoader.loadData(user, restaurant);
        }catch(IOException e){
            System.err.println("Loading data error: " + e.getMessage());
            return;
        }
        System.out.println("Data loaded successfully!");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        Scanner scanner = new Scanner(System.in);
        boolean active = true;
        while (active){
            interactiveMenu();
            String command = scanner.nextLine().trim();
            switch(command) {
                case "1" -> {
                    registerUser(scanner);
                    break;
                }

                case "2"->{
                    login(scanner);
                    break;
                }

                case "3"->{
                    logout();
                    break;
                }

                case "4"->{
                    System.out.println("Restaurant display");
                    for(int i=0; i<restaurant.getAll().size(); i++){
                        System.out.println((i+1) + ". " + restaurant.getAll().get(i));
                    }
                    System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    break;
                }

                case "5"->{
                    System.out.println("Menu display for a restaurant");
                    System.out.println("Enter restaurant name:");
                    String name = scanner.nextLine().trim();
                    restaurant.findByName(name).ifPresentOrElse(r -> {
                        r.getMenu().getItems().forEach(item -> System.out.println(item));
                    }, () -> {
                        System.out.println("Restaurant not found: " + name);
                    });
                    break;
                }

                case "6"-> {
                    System.out.println("Search for a specific product");
                    System.out.println("Enter product name:");
                    String name = scanner.nextLine().trim().toLowerCase();
                    specificProduct(name);
                    break;
                }

                case "7"->{
                    System.out.println("Menu filtering");
                    filterMenu(scanner);
                    break;
                }

                case "8"->{

                }

                case "9"->{}
                case "10"->{}
                case "11"->{}
                case "12"->{}
                case "13"->{}
                case "14"->{}
                case "15"->{}
                case "16"->{}
                case "17"->{}
                case "18"->{}
                case "19"->{}

                case "0"-> {
                    active = false;
                    break;
                }
            }
        }
    }
    private static void interactiveMenu(){
        System.out.println("-----Authentication and account-----");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Logout");

        System.out.println("-----Menu and exploring-----");
        System.out.println("4. Restaurant display");
        System.out.println("5. Menu display for a restaurant");
        System.out.println("6. Search for a specific product");
        System.out.println("7. Menu filtering");

        System.out.println("-----Cart and checkout (customer)-----");
        System.out.println("8. Add product to shopping cart");
        System.out.println("9. View shopping cart");
        System.out.println("10. Add another delivery address");
        System.out.println("11. Add banck card");
        System.out.println("12. Place order");
        System.out.println("13. Card payment processing");

        System.out.println("-----History and rating-----");
        System.out.println("14. Order history display");
        System.out.println("15. Rate a delivery person");

        System.out.println("-----Restaurant management (restaurant admins only)-----");
        System.out.println("16. Add product to menu");
        System.out.println("17. Update product price");
        System.out.println("18. Remove product from menu");

        System.out.println("-----Deliveries (delivery person only)-----");
        System.out.println("19. Update order status");

        System.out.println("-----EXIT-----");
        System.out.println("0.Exit");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    private static void registerUser(Scanner scanner){
        System.out.println("Register");
        System.out.println("Choose one account:");
        System.out.println("1.Customer");
        System.out.println("2.Restaurant Admin");
        System.out.println("3.Delivery Person");
        String choice = scanner.nextLine().trim();
        System.out.println("Enter name:");
        String name = scanner.nextLine().trim();
        System.out.println("Enter email:");
        String email = scanner.nextLine().trim();
        System.out.println("Enter phone number:");
        String phoneNum = scanner.nextLine().trim();
        System.out.println("Enter password:");
        String password = scanner.nextLine().trim();
        switch (choice){
            case "1"->{
                System.out.println("Enter address: ");
                System.out.println("City:");
                String city = scanner.nextLine().trim();
                System.out.println("Street:");
                String street = scanner.nextLine().trim();
                System.out.println("Building number:");
                String building = scanner.nextLine().trim();
                System.out.println("Postal code:");
                String postal = scanner.nextLine().trim();
                System.out.println("Details:");
                String details = scanner.nextLine().trim();
                user.registerUser(new Customer(name, email, phoneNum, password, new Address(city, street, building, postal, details)));
                System.out.println("Customer registered successfully!");
            }
            case "2"->{
                System.out.println("Enter restaurant name:");
                String restaurant = scanner.nextLine().trim();
                System.out.println("Enter access level (OWNER, MANAGER)");
                String access = scanner.nextLine().trim();
                try {
                    AccessLevel accessLevel = AccessLevel.valueOf(access.toUpperCase());
                    user.registerUser(new RestaurantAdmin(name, email, phoneNum, password, restaurant, accessLevel));
                    System.out.println("Restaurant admin registered successfully!");
                }catch (IllegalArgumentException e){
                    System.out.println("Invalid access level. Registration failed.");
                }
            }

            case "3"->{
                System.out.println("Enter vehicle type (BICYCLE, SCOOTER, MOTORCYCLE, CAR): ");
                String vehicle = scanner.nextLine().trim();
                try {
                    VehicleType vehicleType = VehicleType.valueOf(vehicle.toUpperCase());
                    user.registerUser(new DeliveryPerson(name, email, phoneNum, password, vehicleType, true));
                    System.out.println("Delivery person registered successfully!");
                }catch (IllegalArgumentException e){
                    System.out.println("Invalid vehicle type. Registration failed.");
                }
            }
        }
    }

    private static void login(Scanner scanner){
        System.out.println("-----Login-----");
        if(userCurrent != null){
            System.out.println("Already logged in as " + userCurrent.getName() + ". Please logout first.");
            return;
        }
        System.out.println("Enter email:");
        String email = scanner.nextLine().trim();
        System.out.println("Enter password:");
        String password = scanner.nextLine().trim();
        User userFromEmail = user.getUserIdexByEmail().get(email);
        if(userFromEmail != null && userFromEmail.getPassword().equals(password)){
            userCurrent = userFromEmail;
            System.out.println("Login successful! Welcome, " + userCurrent.getName() + "!");
        }
        else{
            System.out.println("Invalid email or password. Login failed.");
        }
    }

    private static void logout(){
        System.out.println("-----Logout-----");
        if(userCurrent != null){
            userCurrent = null;
            System.out.println("Logout successful!");
        }
        else {
            System.out.println("No user is currently logged in.");
        }
    }

    private static void specificProduct(String product){
        boolean found = false;
        for(Restaurant r : restaurant.getAll()){
            for(MenuItem item : r.getMenu().getItems()){
                if(item.getName().toLowerCase().contains(product)){
                    System.out.println("Restaurant: " + r.getName() + " - " + item);
                    found = true;
                }
            }
        }
        if(!found){
            System.out.println("No product found with name containing: " + product);
        }
    }

    private static void filterMenu(Scanner scanner) {
        System.out.println("Choose a restaurant for menu filtering. Enter for all.");
        String res = scanner.nextLine().trim();
        System.out.println("Choose filter criteria:");
        System.out.println("1. Only vegan");
        System.out.println("2. Only vegetarian");
        System.out.println("3. Price less than or equal to");
        System.out.println("4. Safe menu items for allergies");
        String choice = scanner.nextLine().trim();
        boolean found = false;

        switch (choice) {
            case "1" -> {
                System.out.println("Vegan menu items:");
                for(Restaurant r : restaurant.getAll()){
                    if(!res.isEmpty() && !r.getName().equalsIgnoreCase(res)){
                        continue;
                    }
                    var vegan = r.getMenu().getVeganMenu();
                    if(!vegan.isEmpty()){
                        System.out.println("Restaurant: " + r.getName());
                        vegan.forEach(item -> System.out.println(" - " + item));
                        found = true;
                    }
                }
            }

            case "2" ->{
                System.out.println("Vegetarian menu items:");
                for(Restaurant r : restaurant.getAll()){
                    if(!res.isEmpty() && !r.getName().equalsIgnoreCase(res)){
                        continue;
                    }
                    var vegetarian = r.getMenu().getVegetarianMenu();
                    if(!vegetarian.isEmpty()){
                        System.out.println("Restaurant: " + r.getName());
                        vegetarian.forEach(item -> System.out.println(" - " + item));
                        found = true;
                    }
                }
            }

            case "3" ->{
                System.out.println("Enter maximum price:");
                String price = scanner.nextLine().trim();
                try{
                    double maxPrice = Double.parseDouble(price);
                    System.out.println("Menu items with price less than or equal to " + maxPrice + ":");
                    for(Restaurant r : restaurant.getAll()){
                        if(!res.isEmpty() && !r.getName().equalsIgnoreCase(res)){
                            continue;
                        }

                        for(MenuItem item : r.getMenu().getItems()){
                            if(item.getPrice() <= maxPrice){
                                System.out.println("Restaurant: " + r.getName() + " - " + item);
                                found = true;
                            }
                        }
                    }
                }catch(NumberFormatException e){
                    System.out.println("Invalid price input. Please enter a valid number.");
                    return;
                }
            }

            case "4" ->{
                System.out.println("Enter allergens to avoid (comma separated):");
                String[] allergens = scanner.nextLine().trim().split(",");
                for(Restaurant r : restaurant.getAll()){

                    if(!res.isEmpty() && !r.getName().equalsIgnoreCase(res)){
                        continue;
                    }

                    for(var a : allergens){

                    }
                }
            }
        }
    }

}