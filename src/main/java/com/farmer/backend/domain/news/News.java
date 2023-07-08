package com.farmer.backend.domain.news;

import com.farmer.backend.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class News extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long id;

    @NotNull
    private String newsSubject;

    @NotNull
    private String newsContent;

    @NotNull
    private String newsImgUrl;

    @Enumerated(EnumType.STRING)
    private NewsExposure exposure;
}
