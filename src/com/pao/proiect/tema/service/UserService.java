package com.pao.proiect.tema.service;

import com.pao.proiect.tema.exception.DuplicateEmailException;
import com.pao.proiect.tema.model.Customer;
import com.pao.proiect.tema.model.DeliveryPerson;
import com.pao.proiect.tema.model.RestaurantAdmin;
import com.pao.proiect.tema.model.User;
import com.pao.proiect.tema.repository.UsersRepository;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class UserService {
    private List<User> users;
    AuditService audit = AuditService.getInstance();
    UsersRepository usersRepository = new UsersRepository();

    private UserService() {
        this.users = new ArrayList<>();
    }

    private static class Holder {
        private static final UserService INSTANCE = new UserService();
    }

    public static UserService getInstance() {
        return UserService.Holder.INSTANCE;
    }

    public void registerUser(User newUser) throws SQLException {
        if (newUser == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        boolean emailUsed = users.stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(newUser.getEmail()));
        if (emailUsed) {
            throw new DuplicateEmailException("Email is already used");
        }
        usersRepository.save(newUser);
        audit.log("register_user");
        System.out.println("User registered successfully: " + newUser);
    }

    public List<User> getAllUsers(){
        audit.log("get_all_users");
        return Collections.unmodifiableList(users);
    }

    public List<Customer> getAllCustomers() {
        audit.log("get_all_customers");
        return users.stream().filter(user -> user instanceof Customer).map(user -> (Customer) user).toList();
    }

    public List<DeliveryPerson> getAllDeliveryPeople(){
        audit.log("get_all_deliver");
        return users.stream().filter(user -> user instanceof DeliveryPerson).map(user -> (DeliveryPerson) user).toList();
    }

    public List<RestaurantAdmin> getAllRestaurantAdmin(){
        audit.log("get_all_restaurant_admin");
        return users.stream().filter(user -> user instanceof RestaurantAdmin).map(user -> (RestaurantAdmin) user).toList();
    }

    public Optional<Customer> getCustomerByName(String name){
        audit.log("get_customer_by_name");
        return getAllCustomers().stream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst();
    }

    public Optional<DeliveryPerson> getDeliveryPersonByName(String name){
        audit.log("get_delivery_person_by_name");
        return getAllDeliveryPeople().stream().filter(d -> d.getName().equalsIgnoreCase(name)).findFirst();
    }

    public Optional<RestaurantAdmin> getRestaurantAdminByResName(String name){
        audit.log("get_restaurant_admin_by_restaurant_name");
        return getAllRestaurantAdmin().stream().filter(r -> r.getRestaurantName().equalsIgnoreCase(name)).findFirst();
    }

    public Map<String, User> getUserIdexByEmail(){
        audit.log("get_user_index_by_email");
        return users.stream().collect(Collectors.toMap(User::getEmail, user -> user));
    }
}