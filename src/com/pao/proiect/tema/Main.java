package com.pao.proiect.tema;

import com.pao.proiect.tema.exception.InvalidAllergenException;
import com.pao.proiect.tema.exception.InvalidOrderStatusException;
import com.pao.proiect.tema.model.*;
import com.pao.proiect.tema.service.RestaurantService;
import com.pao.proiect.tema.service.OrderService;
import com.pao.proiect.tema.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static final UserService user = UserService.getInstance();
    private static final OrderService order = OrderService.getInstance();
    private static final RestaurantService restaurant = RestaurantService.getInstance();
    private static User userCurrent = null;

    public static void main(String[] args) {
        try {
            DataLoader.loadData(user, restaurant);
        } catch (IOException e) {
            System.err.println("Loading data error: " + e.getMessage());
            return;
        }
        System.out.println("Data loaded successfully!");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        Scanner scanner = new Scanner(System.in);
        boolean active = true;
        while (active) {
            interactiveMenu();
            String command = scanner.nextLine().trim();
            switch (command) {
                case "1" -> {
                    registerUser(scanner);
                    break;
                }

                case "2" -> {
                    login(scanner);
                    break;
                }

                case "3" -> {
                    logout();
                    break;
                }

                case "4" -> {
                    System.out.println("Restaurant display");
                    for (int i = 0; i < restaurant.getAll().size(); i++) {
                        System.out.println((i + 1) + ". " + restaurant.getAll().get(i));
                    }
                    break;
                }

                case "5" -> {
                    System.out.println("Menu display for a restaurant");
                    System.out.println("Enter restaurant name:");
                    String name = scanner.nextLine().trim();
                    restaurant.findByName(name).ifPresentOrElse(r -> {
                        r.getMenu().getItems().forEach(System.out::println);
                    }, () -> {
                        System.out.println("Restaurant not found: " + name);
                    });
                    break;
                }

                case "6" -> {
                    System.out.println("Search for a specific product");
                    System.out.println("Enter product name:");
                    String name = scanner.nextLine().trim().toLowerCase();
                    specificProduct(name);
                    break;
                }

                case "7" -> {
                    System.out.println("Menu filtering");
                    filterMenu(scanner);
                    break;
                }

                case "8" -> {
                    System.out.println("Shopping cart");
                    addToCart(scanner);
                    break;
                }

                case "9" -> {
                    System.out.println("View shopping cart");
                    viewShoppingCart();
                    break;
                }
                case "10" -> {
                    System.out.println("Place order");
                    break;
                }
                case "11" -> {
                    System.out.println("Order history display");
                    break;
                }
                case "12" -> {
                    System.out.println("Add product to menu");
                    addProductToMenu(scanner);
                    break;
                }

                case "13" -> {
                    updateProductPrice(scanner);
                    break;
                }
                case "14" -> {
                    System.out.println("Remove product from menu");
                    break;
                }
                case "15" -> {
                    updateOrderStatus(scanner);
                    break;
                }

                case "0" -> {
                    active = false;
                    break;
                }
            }
        }
    }

    private static void interactiveMenu() {
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
        System.out.println("10. Place order");

        System.out.println("-----History-----");
        System.out.println("11. Order history display");

        System.out.println("-----Restaurant management (restaurant admins only)-----");
        System.out.println("12. Add product to menu");
        System.out.println("13. Update product price");
        System.out.println("14. Remove product from menu");

        System.out.println("-----Deliveries (delivery person only)-----");
        System.out.println("15. Update order status");

        System.out.println("-----EXIT-----");
        System.out.println("0.Exit");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    private static void registerUser(Scanner scanner) {
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
        switch (choice) {
            case "1" -> {
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
            case "2" -> {
                System.out.println("Enter restaurant name:");
                String restaurant = scanner.nextLine().trim();
                System.out.println("Enter access level (OWNER, MANAGER)");
                String access = scanner.nextLine().trim();
                try {
                    AccessLevel accessLevel = AccessLevel.valueOf(access.toUpperCase());
                    user.registerUser(new RestaurantAdmin(name, email, phoneNum, password, restaurant, accessLevel));
                    System.out.println("Restaurant admin registered successfully!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid access level. Registration failed.");
                }
            }

            case "3" -> {
                System.out.println("Enter vehicle type (BICYCLE, SCOOTER, MOTORCYCLE, CAR): ");
                String vehicle = scanner.nextLine().trim();
                try {
                    VehicleType vehicleType = VehicleType.valueOf(vehicle.toUpperCase());
                    user.registerUser(new DeliveryPerson(name, email, phoneNum, password, vehicleType, true));
                    System.out.println("Delivery person registered successfully!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid vehicle type. Registration failed.");
                }
            }
        }
    }

    private static void login(Scanner scanner) {
        System.out.println("-----Login-----");
        if (userCurrent != null) {
            System.out.println("Already logged in as " + userCurrent.getName() + ". Please logout first.");
            return;
        }
        System.out.println("Enter email:");
        String email = scanner.nextLine().trim();
        System.out.println("Enter password:");
        String password = scanner.nextLine().trim();
        User userFromEmail = user.getUserIdexByEmail().get(email);
        if (userFromEmail != null && userFromEmail.getPassword().equals(password)) {
            userCurrent = userFromEmail;
            System.out.println("Login successful! Welcome, " + userCurrent.getName() + "!");
        } else {
            System.out.println("Invalid email or password. Login failed.");
        }
    }

    private static void logout() {
        System.out.println("-----Logout-----");
        if (userCurrent != null) {
            userCurrent = null;
            System.out.println("Logout successful!");
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    private static void specificProduct(String product) {
        boolean found = false;
        for (Restaurant r : restaurant.getAll()) {
            for (MenuItem item : r.getMenu().getItems()) {
                if (item.getName().toLowerCase().contains(product)) {
                    System.out.println("Restaurant: " + r.getName() + " - " + item);
                    found = true;
                }
            }
        }
        if (!found) {
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
                for (Restaurant r : restaurant.getAll()) {
                    if (!res.isEmpty() && !r.getName().equalsIgnoreCase(res)) {
                        continue;
                    }
                    var vegan = r.getMenu().getVeganMenu();
                    if (!vegan.isEmpty()) {
                        System.out.println("Restaurant: " + r.getName());
                        vegan.forEach(item -> System.out.println(" - " + item));
                        found = true;
                    }
                }
            }

            case "2" -> {
                System.out.println("Vegetarian menu items:");
                for (Restaurant r : restaurant.getAll()) {
                    if (!res.isEmpty() && !r.getName().equalsIgnoreCase(res)) {
                        continue;
                    }
                    var vegetarian = r.getMenu().getVegetarianMenu();
                    if (!vegetarian.isEmpty()) {
                        System.out.println("Restaurant: " + r.getName());
                        vegetarian.forEach(item -> System.out.println(" - " + item));
                        found = true;
                    }
                }
            }

            case "3" -> {
                System.out.println("Enter maximum price:");
                String price = scanner.nextLine().trim();
                try {
                    double maxPrice = Double.parseDouble(price);
                    System.out.println("Menu items with price less than or equal to " + maxPrice + ":");
                    for (Restaurant r : restaurant.getAll()) {
                        if (!res.isEmpty() && !r.getName().equalsIgnoreCase(res)) {
                            continue;
                        }

                        for (MenuItem item : r.getMenu().getItems()) {
                            if (item.getPrice() <= maxPrice) {
                                System.out.println("Restaurant: " + r.getName() + " - " + item);
                                found = true;
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price input. Please enter a valid number.");
                    return;
                }
            }

            case "4" -> {
                System.out.println("Enter allergens to avoid (comma separated):");
                String[] allergens = scanner.nextLine().trim().split(",");

                List<Allergen> allergenList = new ArrayList<>();
                for(String a : allergens){
                    try{
                        allergenList.add(Allergen.valueOf(a.trim().toUpperCase()));
                    }catch (IllegalArgumentException e){
                        System.out.println("Invalid allergen: " + a.trim() + ". Skipping.");
                    }
                }

                if(allergenList.isEmpty()){
                    System.out.println("No valid allergen.");
                    return;
                }

                System.out.println("Safe menu");

                for(Restaurant r : restaurant.getAll()){
                    if (!res.isEmpty() && !r.getName().equalsIgnoreCase(res)) {
                        continue;
                    }

                    List<MenuItem> safeItems = r.getMenu().getSafeMenuForManyAllergens(allergenList);
                    if(!safeItems.isEmpty()){
                        System.out.println("Restaurant: " + r.getName());
                        safeItems.forEach(item -> System.out.println(" - " + item));
                        found = true;
                    }
                }
            }
            default -> System.out.println("Invalid filter choice.");
        }
        if(!found){
            System.out.println("No menu items found.");
        }
    }

    private static void addToCart(Scanner scanner){
        if(userCurrent == null){
            System.out.println("Please login to add products to cart.");
            return;
        }
        if(!(userCurrent instanceof Customer)){
            System.out.println("Only customers can add products to cart.");
            return;
        }

        System.out.println("Enter restaurant name:");
        String restaurantName = scanner.nextLine().trim();

        restaurant.findByName(restaurantName).ifPresentOrElse(r ->{

            System.out.println("Menu available");
            r.getMenu().getAvailableMenu().forEach(System.out::println);
            System.out.println("Enter the ID of the product");
            try{
                int productId = Integer.parseInt(scanner.nextLine().trim());
                r.getMenu().getAvailableMenu().stream().filter(item -> item.getId() == productId)
                        .findFirst().ifPresentOrElse(item -> {
                            if(((Customer) userCurrent).getCart() == null || !((Customer) userCurrent).getCart().getRestaurant().getName().equals(r.getName())){
                                ((Customer) userCurrent).setCart(new ShoppingCart((Customer) userCurrent, r));
                            }
                            ((Customer) userCurrent).getCart().addItem(item, 1);
                            System.out.println("Subtotal: " + ((Customer) userCurrent).getCart().calculateSubtotal() + "RON");

                        }, () -> System.out.println("Product with ID " + productId + " not found in available menu."));
            }catch (NumberFormatException e){
                System.out.println("Invalid product ID. Please enter a valid number.");
            }
        }, ()-> System.out.println("Restaurant not found: " + restaurantName));
    }

    private static void updateProductPrice(Scanner scanner){

        if(!(userCurrent instanceof RestaurantAdmin)){
            System.out.println("Only restaurant admins can update product prices.");
            return;
        }

        System.out.println("Enter restaurant name:");
        String restaurantName = scanner.nextLine().trim();

        var res = restaurant.findByName(restaurantName);
        if(res.isEmpty()){
            System.out.println("Restaurant not found: " + restaurantName);
            return;
        }
        else{
            System.out.println("Menu for " + restaurantName);
            res.get().getMenu().getItems().forEach(System.out::println);

            try{
                System.out.println("Enter product ID:");
                int productId = Integer.parseInt(scanner.nextLine().trim());
                System.out.println("Enter new price: ");
                double newPrice = Double.parseDouble(scanner.nextLine().trim());
                boolean updated = res.get().getMenu().updatePrice(productId, newPrice);

            }catch (NumberFormatException e){
                System.out.println("Invalid input. Please enter valid numbers.");
            }catch (IllegalArgumentException e){
                System.out.println("Invalid price. Price cannot be negative.");
            }
        }
    }

    private static void updateOrderStatus(Scanner scanner){
        System.out.println("Update order status");
        if(!(userCurrent instanceof DeliveryPerson)){
            System.out.println("Please login to update order status.");
            return;
        }

        boolean activeOrders = false;
        List<Order> orderes = new ArrayList<>();

        for(Order o : order.getAllOrders()){
            if(o.getStatus() == OrderStatus.PREPARING || o.getStatus() == OrderStatus.READY_FOR_PICKUP){
                System.out.println(o);
                orderes.add(o);
                activeOrders = true;
            }
        }

        if(!activeOrders){
            System.out.println("No active orders to update.");
            return;
        }
        try{
            System.out.println("Enter order ID to update:");
            int orderId = Integer.parseInt(scanner.nextLine().trim());
            orderes.stream().filter(o -> o.getId() == orderId).findFirst().ifPresentOrElse(
                    ord ->{
                        try{
                            if(ord.getStatus() == OrderStatus.PREPARING){
                                ord.assignDriver((DeliveryPerson) userCurrent);
                            }else{
                                if(ord.getStatus() == OrderStatus.READY_FOR_PICKUP){
                                    ord.markDelivered();
                                }
                            }
                        }catch(InvalidOrderStatusException e){
                            System.out.println("Cannot update order status: " + e.getMessage());
                        }
                    }, () -> System.out.println("Order with ID " + orderId + " not found among active orders."));
        }catch (NumberFormatException e){
            System.out.println("Invalid order ID. Please enter a valid number.");
        }
    }

    private static void viewShoppingCart(){
        if(!(userCurrent instanceof Customer)){
            System.out.println("Only customers can have a shopping cart");
            return;
        }
        var customer = (Customer) userCurrent;

        if(customer.getCart() == null || customer.getCart().getItems().isEmpty()){
            System.out.println("Your shopping cart is empty.");
            return;
        }

        customer.getCart().getItems().forEach((item, quantity) -> {
            System.out.println(item.getName() + " - " + quantity);
        });
        System.out.println("Subtotal: " + customer.getCart().calculateSubtotal() + "RON");
    }

    private static void addProductToMenu(Scanner scanner){
        if(!(userCurrent instanceof RestaurantAdmin)){
            System.out.println("Only restaurant admins can add products to menu.");
            return;
        }
        var restaurantAdmin = (RestaurantAdmin) userCurrent;
        System.out.println("Choose 1 for adding a food item, choose 2 for a drink item.");
        String type = scanner.nextLine().trim();

        System.out.println("Enter product name: ");
        String name = scanner.nextLine().trim();
        System.out.println("Enter product price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        System.out.println("Is the product vegan? (true/false)");
        boolean isVegan = Boolean.parseBoolean(scanner.nextLine().trim());
        System.out.println("Is the product vegetarian? (true/false)");
        boolean isVegetarian = Boolean.parseBoolean(scanner.nextLine().trim());
        MenuItem newItem = null;
        switch(type){
            case "1" -> {
                System.out.println("You are adding a new food item.");
                System.out.println("Enter weight grams: ");
                double weight = Double.parseDouble(scanner.nextLine().trim());
                System.out.println("Enter spiciness level (NONE, MILD, MEDIUM, HOT, EXTRA_HOT");
                Spiciness spiciness = Spiciness.valueOf(scanner.nextLine().trim().toUpperCase());
                System.out.println("Enter course type (STARTER, MAIN, SIDE_DISH, DESSERT");
                CourseType courseType = CourseType.valueOf(scanner.nextLine().trim().toUpperCase());

                newItem = new FoodItem(name, price, isVegan, isVegetarian, weight, spiciness, courseType);
            }
            case "2" -> {
                System.out.println("You are adding a new drink item.");
                System.out.println("Is the drink alcoholic? (true/false)");
                boolean containsAlcohol = Boolean.parseBoolean(scanner.nextLine().trim());
                System.out.println("Enter volume in ML: ");
                int volume = Integer.parseInt(scanner.nextLine().trim());
                System.out.println("Is this drink hot (true/false)");
                boolean isHot = Boolean.parseBoolean(scanner.nextLine().trim());
                System.out.println("If the drink contains alcohol, enter alcohol percentage. Otherwise, enter 0.");
                double alcoholPercentage = Double.parseDouble(scanner.nextLine().trim());
                newItem = new DrinkItem(name, price, isVegan, isVegetarian, containsAlcohol, volume, isHot, alcoholPercentage);
            }
            default -> {
                System.out.println("Invalid product type choice. Product not added.");
                return;
            }
        }

        System.out.println("Adding allergens ('done' for exit)");
        while (true) {
            String command = scanner.nextLine().trim().toLowerCase();
            if (command.equals("done")) break;
            try {
                Allergen allergen = Allergen.valueOf(command.toUpperCase());
                newItem.addAllergen(allergen);
                System.out.println("Allergen " + allergen + " added.");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid allergen. Please enter a valid allergen or 'done' to finish.");
            } catch (InvalidAllergenException e) {
                System.out.println("Error: " + e.getMessage());
            }

        }

        var restaurantName = restaurantAdmin.getRestaurantName();
        MenuItem finalNewItem = newItem;
        restaurant.findByName(restaurantName).ifPresentOrElse(r -> {
            r.addProduct(finalNewItem);
            System.out.println("Product added to menu successfully!");
        }, () -> {
            System.out.println("Restaurant not found: " + restaurantName + ". Product not added to menu.");
        });

    }

}