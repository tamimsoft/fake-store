package com.tamimSoft.store.repository;


import com.tamimSoft.store.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

interface ProductRepositoryCustom {
    Page<Product> findProductsByFilters(String categoryId, String brandId, Set<String> tagIds, Pageable pageable);
}
