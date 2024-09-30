package com.gangadhar.e_commerce.service;

import com.gangadhar.e_commerce.entity.Order;
import com.gangadhar.e_commerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Method to create an order and initiate payment
    public Order createOrder(Order order) {
        // Set order creation time
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());

        // Generate a transaction ID and set initial payment details
        order.setTxnId("TXN_" + System.currentTimeMillis()); // Simulate a unique transaction ID
        order.setPaymentStatus("PENDING"); // Set initial payment status
        order.setChecksumHash(generateChecksum(order)); // Generate checksum for security
        order.setCallbackUrl("http://localhost:8080/payment/callback"); // Set your callback URL

        // Save the order with payment details
        return orderRepository.save(order);
    }

    // Get order by ID
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Update order status
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    // Method to generate a checksum for the payment
    private String generateChecksum(Order order) {
        // Implement your checksum generation logic here
        // You may want to use a library or utility provided by PayTM
        return "CHECKSUM_HASH"; // Replace this with actual checksum generation logic
    }

    // Method to update payment status based on PayTM callback
    public void updatePaymentStatus(String orderId, String paymentStatus, String txnId) {
        Order order = orderRepository.findById(Long.valueOf(orderId)).orElse(null);
        if (order != null) {
            order.setPaymentStatus(paymentStatus);
            order.setTxnId(txnId); // Update the transaction ID if needed
            order.setUpdatedAt(new Date()); // Update the timestamp
            orderRepository.save(order);
        }
    }

}
