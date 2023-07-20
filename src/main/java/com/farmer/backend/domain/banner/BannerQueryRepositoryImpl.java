package com.farmer.backend.domain.banner;


import com.farmer.backend.api.controller.banner.response.ResponseBannerDtoList;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.farmer.backend.domain.banner.QBanner.banner;

@Repository
public class BannerQueryRepositoryImpl implements BannerQueryRepository{
    private final JPAQueryFactory query;
    public BannerQueryRepositoryImpl(EntityManager em){
        this.query=new JPAQueryFactory(em);


    }

    @Override
    public List<ResponseBannerDtoList> bannerList(){
        List<ResponseBannerDtoList> bannerList = query
                .select(Projections.constructor(ResponseBannerDtoList.class,
                        banner.id,
                        banner.name,
                        banner.imgUrl,
                        banner.linkUrl))
                .from(banner)
                .orderBy(banner.createdDate.desc())
                .limit(3)
                .fetch();
        return bannerList;
    }
}
