package com.farmer.backend.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pc_id")
    private Long id;

    @NotNull
    @Column(length = 20)
    private String name;
}
