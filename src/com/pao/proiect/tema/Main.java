package com.pao.proiect.tema;

/*
Lista cu cel putin 8 obiecte din domeniu:

-customer
-restaurant admin
-fooditem/drinkitem/menuitem
-order
-shopping cart
-address
-customer card
-delivery person
-card payment
-cash payment

Lista cu cel putin 10 actiuni posibile:

1) inregistrare user
2) adaugare produs nou in meniul unui restaurant
3) afisarea meniului complet
4) filtrare meniu
5) autentificare utilizator
6) adaugare produse in cos
7) adaugare card bancar
8) plasare comanda
9) procesari plata cu cardul
10) afisare isctoric comenzi

 */


import com.pao.proiect.tema.service.MenuService;
import com.pao.proiect.tema.service.OrderService;
import com.pao.proiect.tema.service.UserService;

import java.util.Scanner;

public class Main{
    private static final UserService user = UserService.getInstance();
    private static final OrderService order = OrderService.getInstance();
    private static final MenuService menu = MenuService.getInstance();

    public static void main(String[] args) {
        Scanner scanner =  new Scanner(System.in);
        boolean isOpen = true;
        System.out.println("Welcome to the Food Delivery App!");
        while(isOpen){
            System.out.println("\nPlease select an option:");
            System.out.println("1. Register User");
            System.out.println("2. Menu");
            System.out.println("3. Login");
            System.out.println("0. Break");
            System.out.println("Enter your choice: ");
            String option = scanner.nextLine();
        }





    }
}