package com.farmer.backend.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @Column(length = 30)
    private String name;

    @Column(length = 50)
    private String address;

    @Column(length = 50)
    private String addressDetail;

    @Column(length = 20)
    private String hp;

    @Column(length = 20)
    private String zipcode;

    @Enumerated(EnumType.STRING)
    private AddressStatus status;

}
