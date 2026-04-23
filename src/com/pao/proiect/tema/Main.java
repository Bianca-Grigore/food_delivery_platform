package com.pao.proiect.tema;

import com.pao.proiect.tema.service.RestaurantService;
import com.pao.proiect.tema.service.OrderService;
import com.pao.proiect.tema.service.UserService;

import java.io.IOException;
import java.util.Scanner;

public class Main{
    private static final UserService user = UserService.getInstance();
    private static final OrderService order = OrderService.getInstance();
    private static final RestaurantService restaurant = RestaurantService.getInstance();

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
}