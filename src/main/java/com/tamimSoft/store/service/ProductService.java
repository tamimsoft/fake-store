package com.tamimSoft.store.service;

import com.tamimSoft.store.dto.ProductDTO;
import com.tamimSoft.store.entity.*;
import com.tamimSoft.store.exception.ResourceNotFoundException;
import com.tamimSoft.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    final private BrandService brandService;
    final private CategoryService categoryService;
    final private ProductTagService productTagService;
    final private UserService userService;

    final private ProductRepository productRepository;

    private static Product arrangeProduct(ProductDTO productDTO, Brand brand, Category category, Set<ProductTag> tags) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setSlug(productDTO.getName().toLowerCase().replaceAll("\\s+", "-"));
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setDiscount(productDTO.getDiscount());
        product.setColors(productDTO.getColors());
        product.setSizes(productDTO.getSizes());
        product.setMaterial(productDTO.getMaterial());
        product.setImageUrls(productDTO.getImageUrls());
        product.setStock(productDTO.getStock());
        product.setBrand(brand);
        product.setCategory(category);
        product.setTags(tags);
        return product;
    }

    public void createProduct(ProductDTO productDTO, String userName) {
        Brand brand = new Brand();
        Category category = new Category();
        Set<ProductTag> tags = new HashSet<>();

        if (productDTO.getTagIds() != null) {
            for (String tagId : productDTO.getTagIds()) {
                tags.add(productTagService.getTagById(tagId));
            }
        }
        if (productDTO.getBrandId() != null) {
            brand = brandService.getBrandById(productDTO.getBrandId());
        }
        if (productDTO.getCategoryId() != null) {
            category = categoryService.getCategoryById(productDTO.getCategoryId());
        }
        Product product = arrangeProduct(productDTO, brand, category, tags);
        Product savedProduct = productRepository.save(product);

        User user = userService.getUserByUserName(userName);

        user.getProducts().add(savedProduct);
        userService.updateUser(user);
    }

    public Page<ProductDTO> getAllProductDTOs(Pageable pageable, String categoryId, String brandId, Set<String> tagIds) {
        log.warn("getAllProductDTOs called with categoryId: {}, brandId: {}, tagIds: {}", categoryId, brandId, tagIds);
        return productRepository.findProductsByFilters(categoryId, brandId, tagIds, pageable).map(
                product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getDiscount(),
                        product.getColors(),
                        product.getSizes(),
                        product.getMaterial(),
                        product.getStock(),
                        product.getImageUrls(),
                        product.getBrand().getId(),
                        product.getCategory().getId(),
                        product.getTags().stream().map(
                                ProductTag::getId
                        ).collect(Collectors.toSet())
                )
        );
    }

    public ProductDTO getProductDTOById(String productId) {
        return productRepository.findById(productId).map(
                product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getDiscount(),
                        product.getColors(),
                        product.getSizes(),
                        product.getMaterial(),
                        product.getStock(),
                        product.getImageUrls(),
                        product.getBrand().getId(),
                        product.getCategory().getId(),
                        product.getTags().stream().map(
                                ProductTag::getId
                        ).collect(Collectors.toSet())
                )
        ).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
    }

    public Product getProductById(String productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
    }

    public void updateProduct(String id, ProductDTO productDTO) {
        Set<ProductTag> tags = new HashSet<>();

        if (productDTO.getTagIds() != null) {
            for (String tagId : productDTO.getTagIds()) {
                tags.add(productTagService.getTagById(tagId));
            }
        }
        Product product = getProductById(id);
        product.setName(productDTO.getName() != null ? productDTO.getName() : product.getName());
        product.setDescription(productDTO.getDescription() != null ? productDTO.getDescription() : product.getDescription());
        product.setPrice(productDTO.getPrice() > 0 ? productDTO.getPrice() : product.getPrice());
        product.setDiscount(productDTO.getDiscount() > 0 ? productDTO.getDiscount() : product.getDiscount());
        product.setColors(productDTO.getColors() != null ? productDTO.getColors() : product.getColors());
        product.setSizes(productDTO.getSizes() != null ? productDTO.getSizes() : product.getSizes());
        product.setMaterial(productDTO.getMaterial() != null ? productDTO.getMaterial() : product.getMaterial());
        product.setImageUrls(productDTO.getImageUrls() != null ? productDTO.getImageUrls() : product.getImageUrls());
        product.setStock(productDTO.getStock() != null ? productDTO.getStock() : product.getStock());
        product.setBrand(productDTO.getBrandId() != null ? brandService.getBrandById(productDTO.getBrandId()) : product.getBrand());
        product.setCategory(productDTO.getCategoryId() != null ? categoryService.getCategoryById(productDTO.getCategoryId()) : product.getCategory());
        product.setTags(productDTO.getTagIds() != null ? tags : product.getTags());
    }

    public void deleteById(String productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }
        productRepository.deleteById(productId);
    }

}

