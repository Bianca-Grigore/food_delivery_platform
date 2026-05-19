package com.pao.proiect.tema.repository;
import com.pao.proiect.tema.model.*;
import com.pao.proiect.tema.util.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsersRepository implements Repository<User, Integer>{

    private Connection getConn() throws SQLException, IOException{
        return DatabaseConnection.getInstance().getConnection();
    }

    private User mapRow(ResultSet rs) throws SQLException {
        String role = rs.getString("role");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String phoneNum = rs.getString("phone_num");
        String password = rs.getString("password");
        User u;

        if ("CUSTOMER".equalsIgnoreCase(role)) {
            String city = rs.getString("city");
            String street = rs.getString("street");
            String bldNum = rs.getString("building_number");
            String postal = rs.getString("postal_code");
            String details = rs.getString("details");
            Address address = new Address(city, street, bldNum, postal, details);

            Customer customer = new Customer(name, email, phoneNum, password, address);
            customer.setPoints(rs.getInt("points"));
            u = customer;
        } else if ("admin".equalsIgnoreCase(role)) {
            String restaurantName = rs.getString("restaurant_name");
            String accessLevelStr = rs.getString("access_level");

            AccessLevel accessLevel = AccessLevel.valueOf(accessLevelStr.toUpperCase());
            RestaurantAdmin admin = new RestaurantAdmin(name, email, phoneNum, password, restaurantName, accessLevel);
            if (!rs.getBoolean("is_active")) {
                admin.deactivateAccount();
            }
            u = admin;
        } else if ("Delivery".equalsIgnoreCase(role)) {
            String vehicleType = rs.getString("vehicle_type");
            VehicleType vType = VehicleType.valueOf(vehicleType.toUpperCase());
            boolean isavailable = rs.getBoolean("is_available");
            DeliveryPerson deliveryPerson = new DeliveryPerson(name, email, phoneNum, password, vType, isavailable);
            deliveryPerson.setTotalDeliveries(rs.getInt("total_deliveries"));
            deliveryPerson.setRating(rs.getDouble("rating"));
            u = deliveryPerson;
        } else{
            throw new SQLException("Unknown role: " + role);
        }
        u.setId(rs.getInt("id"));
        return u;
    }

    @Override
    public void save(User entity) throws SQLException {

    }

    @Override
    public Optional<User> findById(Integer aInteger) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() throws SQLException {
        return List.of();
    }

    @Override
    public void update(User entity) throws SQLException {

    }

    @Override
    public void delete(Integer aInteger) throws SQLException {

    }
}
