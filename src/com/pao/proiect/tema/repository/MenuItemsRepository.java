package com.pao.proiect.tema.repository;

import com.pao.proiect.tema.model.*;
import com.pao.proiect.tema.util.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuItemsRepository implements Repository<MenuItem, Integer> {
    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }

    public MenuItem mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String item_type = rs.getString("item_type");
        double calories = rs.getDouble("calories");
        double price = rs.getDouble("price");
        String description = rs.getString("description");
        boolean is_available = rs.getBoolean("is_available");
        boolean is_vegan = rs.getBoolean("is_vegan");
        int estimate_time = rs.getInt("estimate_time");
        boolean is_vegetarian = rs.getBoolean("is_vegetarian");
        MenuItem item;
        if ("FOOD".equalsIgnoreCase(item_type)) {
            double weight_grams = rs.getDouble("weight_grams");
            Spiciness spiciness;
            if(rs.getString("spiciness_level")!= null) {
                spiciness = Spiciness.valueOf(rs.getString("spiciness_level").toUpperCase());
            } else{
                spiciness = Spiciness.NONE;
            }

            CourseType courseType = null;
            if(rs.getString("course_type")!= null) {
                courseType = CourseType.valueOf(rs.getString("course_type").toUpperCase());
            }
            FoodItem foodItem = new FoodItem(name, calories, price, description, estimate_time, is_vegan, is_vegetarian, weight_grams, spiciness, courseType);
            item = foodItem;

        } else if ("DRINK".equalsIgnoreCase(item_type)) {
            boolean contains_alcohol = rs.getBoolean("contains_alcohol");
            double alcohol = rs.getDouble("alcohol_percentage");
            int volume = rs.getInt("volume_ml");
            boolean is_hot = rs.getBoolean("is_hot");
            DrinkItem drinkItem = new DrinkItem(name, calories, price, description, estimate_time, is_vegan, is_vegetarian, contains_alcohol, volume, is_hot, alcohol);
            item = drinkItem;
        }
        else{
            throw new SQLException("Unknown item type: " + item_type);
        }
        item.setId(id);
        item.setAvailable(is_available);
        return item;
    }

    public List<MenuItem> findByMenuId(int menuId) throws SQLException {
        String sql = "SELECT * FROM menu_items WHERE menu_id = ?";
        List<MenuItem> items = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, menuId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(mapRow(rs));
                }
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
        return items;
    }
    @Override
    public void save(MenuItem entity) throws SQLException {

    }

    @Override
    public Optional<MenuItem> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM menu_items WHERE id = ?";
        try(PreparedStatement ps = getConn().prepareStatement(sql)){
            ps.setInt(1,id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return Optional.of(mapRow(rs));
                return Optional.empty();
            }
        }catch(IOException e){
            throw new SQLException(e);
        }
    }

    @Override
    public List<MenuItem> findAll() throws SQLException {
       String sql = "SELECT * FROM menu_items ORDER BY id";
       List<MenuItem> list = new ArrayList<>();
       try(PreparedStatement ps = getConn().prepareStatement(sql);
       ResultSet rs = ps.executeQuery()){
           while(rs.next())
               list.add(mapRow(rs));
       }catch (IOException e){
           throw new SQLException(e);
       }
       return list;
    }

    @Override
    public void update(MenuItem entity) throws SQLException {

    }

    @Override
    public void delete(Integer integer) throws SQLException {

    }
}
