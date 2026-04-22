package com.pao.proiect.tema;
import com.pao.proiect.tema.model.*;
import com.pao.proiect.tema.service.MenuService;
import com.pao.proiect.tema.service.OrderService;
import com.pao.proiect.tema.service.UserService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataLoader {
    public static void loadData(UserService userService, MenuService menuService, OrderService orderService) throws FileNotFoundException {
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
                String[] data = line.split(",");
                switch(category){
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

                        Restaurant restaurant = new Restaurant(name, address, new RestaurantAdmin(adminName, emailAdmin, adminPhone, adminPassword, name, AccessLevel.valueOf(accesLevel)));


                }
            }
        }
    }
}
