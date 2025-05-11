package com.tamimSoft.store.repository;

import com.tamimSoft.store.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Product> findProductsByFilters(String categoryId, String brandId, Set<String> tagIds, Pageable pageable) {
        List<Criteria> criteriaList = new ArrayList<>();

        // Add category filter if categoryId is provided
        if (categoryId != null && !categoryId.isEmpty()) {
            criteriaList.add(new Criteria().orOperator(
                    Criteria.where("category.id").is(categoryId),
                    Criteria.where("category.id").isNull()
            ));
        }

        // Add brand filter if brandId is provided
        if (brandId != null && !brandId.isEmpty()) {
            criteriaList.add(new Criteria().orOperator(
                    Criteria.where("brand.id").is(brandId),
                    Criteria.where("brand.id").isNull()
            ));
        }

        // Add tags filter if tagIds are provided and not empty
        if (tagIds != null && !tagIds.isEmpty()) {
            criteriaList.add(new Criteria().orOperator(
                    Criteria.where("tags").in(tagIds),  // No "$id" needed, assuming tags is an array of Strings
                    Criteria.where("tags").isNull()
            ));
        }

        // Combine all criteria with an AND condition
        Criteria criteria = criteriaList.isEmpty() ? new Criteria() : new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));

        // Build and execute the query
        Query query = new Query(criteria).with(pageable);
        List<Product> products = mongoTemplate.find(query, Product.class);
        long total = mongoTemplate.count(query, Product.class);

        // Return paginated results
        return new PageImpl<>(products, pageable, total);
    }


}


