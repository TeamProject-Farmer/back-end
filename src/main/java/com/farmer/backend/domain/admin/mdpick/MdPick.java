package com.farmer.backend.domain.admin.mdpick;

import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.product.Product;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class MdPick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pick_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @Column(length = 50)
    private String name;

    @Column(length = 255)
    private String linkUrl;

    @NotNull
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime createdDate;

}
