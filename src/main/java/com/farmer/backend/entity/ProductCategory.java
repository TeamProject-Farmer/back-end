package com.farmer.backend.entity;

import com.sun.istack.NotNull;
<<<<<<< HEAD
import lombok.Getter;
import lombok.Setter;
=======
import lombok.*;
>>>>>>> feature/admin/board

import javax.persistence.*;

@Entity
@Getter
<<<<<<< HEAD
=======
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
>>>>>>> feature/admin/board
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pc_id")
    private Long id;

    @NotNull
    @Column(length = 20)
    private String name;
}
