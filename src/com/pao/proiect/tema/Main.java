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
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                case "10":
                case "11":
                case "12":
                case "13":
                case "14":
                case "15":
                case "16":
                case "17":
                case "18":
                case "19":

                case "20":
                    active = false;
                    break;

            }


        }
    }
    private static void interactiveMenu(){
        System.out.println("1.Register");
        System.out.println("2.Login");
        System.out.println("3.Restaurant display");
        System.out.println("4.Menu display for a restaurant");
        System.out.println("5.Menu filtering");
        System.out.println("6.Add product");
        System.out.println("7.Place order");
        System.out.println("8.Add card");
        System.out.println("9.Card payment processing");
        System.out.println("10.Order history display");
        System.out.println("11.Add product in shopping cart");
        System.out.println("12.Logout");
        System.out.println("13.Rate a delivery person");
        System.out.println("14.View shopping cart");
        System.out.println("15.Update product price (restaurant admin only)");
        System.out.println("16.Update order status (delivery person only)");
        System.out.println("17.Search for a specific product");
        System.out.println("18.Add another delivery address");
        System.out.println("19.Remove product from menu");
        System.out.println("20.Exit");
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

}