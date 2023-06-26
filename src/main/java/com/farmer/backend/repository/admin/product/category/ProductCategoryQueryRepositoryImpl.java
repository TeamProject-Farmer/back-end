package com.farmer.backend.repository.admin.product.category;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.farmer.backend.entity.QProductCategory.productCategory;

@Repository
public class ProductCategoryQueryRepositoryImpl implements ProductCategoryQueryRepository{

    private final JPAQueryFactory query;

    public ProductCategoryQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Integer findDescMaxValOfProductCategory() {
        Integer integer = query
                .select(productCategory.sortOrder.max())
                .from(productCategory)
                .fetchOne();
        return integer;
    }
}
