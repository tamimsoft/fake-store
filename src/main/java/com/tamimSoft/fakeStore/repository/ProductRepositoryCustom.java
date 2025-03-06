package com.tamimSoft.fakeStore.repository;


import com.tamimSoft.fakeStore.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

interface ProductRepositoryCustom {
    Page<Product> findProductsByFilters(String categoryId, String brandId, Set<String> tagIds, Pageable pageable);
}
