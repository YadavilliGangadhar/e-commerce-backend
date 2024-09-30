package com.gangadhar.e_commerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "customer_id", nullable = false)
    private String customerId; // Identifier for the customer

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount; // Total amount of the order

    @Column(name = "order_status", nullable = false)
    private String orderStatus; // Status of the order (e.g., PENDING, COMPLETED)

    @Column(name = "txn_id", unique = true)
    private String txnId; // PayTM transaction ID

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus; // Status of the payment (e.g., SUCCESS, FAILED)

    @Column(name = "checksum_hash", nullable = false)
    private String checksumHash; // Checksum hash for security

    @Column(name = "callback_url", nullable = false)
    private String callbackUrl; // Callback URL for PayTM response

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt; // Timestamp of when the order was created

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt; // Timestamp of when the order was last updated

    @Column
    private String status; // Field for order status

    @Column
    private String paymentUrl; // Field for Paytm payment URL


}
