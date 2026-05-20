package com.pao.proiect.tema.repository;

import com.pao.proiect.tema.model.Menu;
import com.pao.proiect.tema.model.MenuItem;
import com.pao.proiect.tema.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenusRepository implements Repository<Menu, Integer>{
    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }

    private Menu mapRow(ResultSet rs) throws SQLException{
        int id=rs.getInt("id");
        int restaurantId = rs.getInt("restaurant_id");
        Menu menu = new Menu();
        menu.setId(id);
        menu.setRestaurantId(restaurantId);
        MenuItemsRepository menuItemsRepository = new MenuItemsRepository();
        List<MenuItem> items = menuItemsRepository.findByMenuId(id);

        for(MenuItem item : items){
            menu.addProduct(item);
        }
        return menu;
    }

    @Override
    public void save(Menu entity) throws SQLException {

    }

    @Override
    public Optional<Menu> findById(Integer id) throws SQLException {

    }

    @Override
    public List<Menu> findAll() throws SQLException {

    }

    @Override
    public void update(Menu entity) throws SQLException {

    }

    @Override
    public void delete(Integer id) throws SQLException {

}
}