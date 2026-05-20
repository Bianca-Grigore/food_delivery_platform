package com.pao.proiect.tema.repository;

import com.pao.proiect.tema.model.Order;
import com.pao.proiect.tema.util.DatabaseConnection;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrdersRepository implements  Repository<Order, Integer>{
    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Order entity) throws SQLException {

    }

    @Override
    public Optional<Order> findById(Integer integer) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Order> findAll() throws SQLException {
        return List.of();
    }

    @Override
    public void update(Order entity) throws SQLException {

    }

    @Override
    public void delete(Integer integer) throws SQLException {

    }
}
