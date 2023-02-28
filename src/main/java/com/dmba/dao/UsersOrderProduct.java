package com.dmba.dao;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name = "Users_Order_Product")
@Builder
public class UsersOrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private UsersOrder usersOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    // constructors, getters, and setters
}