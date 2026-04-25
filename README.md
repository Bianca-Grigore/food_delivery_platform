# 🍔 Food Delivery Platform - Etapa 1

## 1. Definirea Sistemului

## Listă Acțiuni
Sistemul permite următoarele operațiuni principale prin intermediul meniului interactiv: 

1.  **Înregistrare utilizator:** Crearea de conturi noi pentru Clienți, Admini sau Livratori.
2.  **Autentificare utilizator:** Sistem de login și logout bazat pe email și parolă.
3.  **Afișare Restaurante:** Listarea tuturor unităților disponibile în sistem.
4.  **Vizualizare Meniu:** Afișarea produselor disponibile pentru un restaurant ales.
5.  **Căutare Produs:** Identificarea produselor în toate restaurantele după un cuvânt cheie.
6.  **Filtrare Avansată:** Filtrarea produselor după dietă (Vegan/Vegetarian), Preț maxim sau Alergeni.
7.  **Gestionare Coș:** Adăugarea produselor în coșul de cumpărături (cu validarea restaurantului unic).
8.  **Vizualizarea coșului:** Afișarea listei de produse selectate, cantitățile aferente și calcularea dinamică a subtotalului.
9.  **Plasare Comandă:** Finalizarea comenzii cu calcul de taxe și selectarea metodei de plată (Cash/Card).
10.  **Gestionare Meniu (Admin):** Adăugarea de produse noi (Food/Drink) în meniul propriu.
11. **Actualizare Prețuri (Admin):** Modificarea prețurilor produselor existente pe baza ID-ului unic.
12. **Eliminare Produse (Admin):** Ștergerea unui produs din meniu.
13. **Actualizare Status (Livrator):** Preluarea comenzilor și marcarea ca "Delivered" a celor aflate în "Preparing" sau "Ready For Pickup".
14. **Istoric Comenzi:** Afișarea polimorfica a istoricului: clienții pot vedea propriile comenzi plasate; administratorii pot vedea comenzile primite de către restaurantul lor.

## Listă Tipuri de Obiecte
1. User (abstract sealed) - clasă de bază care definește atributele comune (nume, email, telefon, parolă).
2. Customer (extends User)
3. RestaurantAdmin (extends User)
4. DeliveryPerson (extends User)
5. Address (record) - obiect imutabil care stochează detaliile geografice legate de livrare.
6. MenuItem (abstract sealed sortată folosind Comparable) 
7. FoodItem (extends MenuItem)
8. DrinkItem (extends MenuItem)
9. Menu
10. Restaurant
11. ShoppingCart
12. BasePayment (abstract implements Payment - interface)
13. CashPayment (extends BasePayment)
14. CardPayment (extends BasePayment)
15. CustomerCard (record) - stochează datele cardului unui utilizator.
16. Order
17. Enums: Allergen, AccessLevel ( OWNER, MANAGER pentru admini), CourseType (pentru tipurile de mancare), DeliveryStatus, OrderStatus, PaymentStatus, Spiciness, VehicleType.
    
## Excepții custom
-DuplicateEmailException
-EmptyCartException
-InvalidAllergenException
-InvalidDetailsException -> pentru plata folosind cardul.
-InvalidOrderStatusException
-ItemUnavailableException

## Services - Singleton
### -OrderService (gestionarea plasării unei comenzi, procesarea plății pentru comanda respectivă, asignarea livratorului, filtrarea istoricului de comenzi pentru rapoarte specifice fiecărui tip de utilizator,  Map<Customer, List<Order>> pentru comenzi grupate după client etc.).
### -RestaurantService (Adăugarea/eliminarea de restaurante, căutarea unui restaurant după nume, gestionarea produselor din meniuri (adăugare, ștergere, actualizare preț) și oferirea de metode pentru filtrarea globală a produselor.).
### -UserService (gestionarea tuturor clienților, susține operații precum înregistrarea unui utilizator cu validarea unicității emailului, verificarea credențialelor pentru autentificare, returnarea utilizatorilor indexați după email (Map<String, User>), a clienților/livratorilor după nume etc.).   


### 2 — Organizare 
* **Pachete:** Codul este organizat în `com.pao.proiect.tema.model`, `.service`, `.exception`.
* **Fără Cod Duplicat:** Logica de calcul a prețurilor și validările sunt centralizate.
* **Fără NPE:** Input-urile din consolă sunt trimite prin `trim()`, iar obiectele sunt verificate pentru `null` sau `isPresent()` (Optional) înainte de utilizare.
