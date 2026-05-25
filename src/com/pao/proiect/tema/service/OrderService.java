package com.pao.proiect.tema.service;

import com.pao.proiect.tema.exception.EmptyCartException;
import com.pao.proiect.tema.model.*;
import com.pao.proiect.tema.repository.OrdersRepository;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class OrderService {
    private final List<Order> orders;
    AuditService audit = AuditService.getInstance();
    OrdersRepository ordersRepository = new OrdersRepository();

    private OrderService(){
        this.orders = new ArrayList<>();
    }

    private static class Holder{
        private static final OrderService INSTANCE = new OrderService();
    }

    public static OrderService getInstance(){
        return Holder.INSTANCE;
    }

    public Order placeOrder(ShoppingCart cart, double deliveryFee, Payment paymentMethod, String notes) throws EmptyCartException, SQLException {
        System.out.println("\n Placing order...");
        Order newOrder = cart.checkout(deliveryFee, paymentMethod, notes);
        ordersRepository.save(newOrder);
        audit.log("place_order");
        System.out.println("Order placed successfully. \n");
        return newOrder;
    }

    public void processOrderPayment(int orderId){
        Order order = findOrderById(orderId).orElseThrow(() -> new IllegalArgumentException("Order with id " + orderId + " not found."));
        order.processOrderPayment();
        audit.log("process_order_payment");
        System.out.println("Payment processed for order id: " + orderId);
    }

    public void assignDriver(int orderId, DeliveryPerson driver){
        Order order = findOrderById(orderId).orElseThrow(() -> new IllegalArgumentException("Order with id " + orderId + " not found."));
        order.assignDriver(driver);
        audit.log("assign_driver");
        System.out.println("Driver " + driver.getName() + " assigned to order id: " + orderId);
    }

    public void deliveryOrder(int orderId){
        Order order = findOrderById(orderId).orElseThrow(() -> new IllegalArgumentException("Order with id " + orderId + " not found."));
        order.markDelivered();
        audit.log("delivery_order");
        System.out.println("Order id: " + orderId + " marked as delivered.");
    }

    public Optional<Order> findOrderById(int id){
        audit.log("find_order_by_id");
        return orders.stream().filter(o -> o.getId() == id).findFirst();
    }

    public List<Order> getAllOrders(){
        audit.log("get_all_orders");
        return Collections.unmodifiableList(orders);
    }

    public List<Order> getOrderByCustomer(Customer customer){
        audit.log("get_order_by_customer");
        return orders.stream().filter(o -> o.getCustomer().equals(customer)).toList();
    }

    public double calculateTotal(){
        audit.log("calculate_total");
        return orders.stream().filter(o -> o.getStatus() == OrderStatus.DELIVERED).mapToDouble(Order::getTotal).sum();
    }

    public List<Order> getOrdersByRestaurants(String restaurantName){
        audit.log("get_orders_by_restaurants");
        return orders.stream().filter(o -> o.getRestaurant().getName().equalsIgnoreCase(restaurantName)).toList();
    }

    public Map<Customer, List<Order>> getOrdersGroupedByCustomer(){
        audit.log("get_orders_grouped_by_customer");
        return orders.stream().collect(Collectors.groupingBy(Order::getCustomer));
    }
}