package com.dmba.dao;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long productId;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Float price;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "cost")
    private Float cost;
}