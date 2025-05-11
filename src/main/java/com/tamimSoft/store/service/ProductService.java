package com.tamimSoft.store.service;

import com.tamimSoft.store.dto.ProductDto;
import com.tamimSoft.store.entity.*;
import com.tamimSoft.store.exception.ResourceNotFoundException;
import com.tamimSoft.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    private static Product arrangeProduct(ProductDto productDTO, Brand brand, Category category, Set<Tag> tags) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setSlug(productDTO.getName().toLowerCase().replaceAll("\\s+", "-"));
        product.setDescription(productDTO.getDescription());
        product.setRegularPrice(productDTO.getRegularPrice());
        product.setCurrentPrice(productDTO.getCurrentPrice());
        product.setDiscountPercentage(productDTO.getDiscountPercentage());
        product.setQuantity(productDTO.getQuantity());
        product.setAverageRating(productDTO.getAverageRating());
        product.setTotalReviews(productDTO.getTotalReviews());
        product.setColors(productDTO.getColors());
        product.setSizes(productDTO.getSizes());
        product.setMaterial(productDTO.getMaterial());
        product.setImageUrls(productDTO.getImageUrls());
        product.setBrand(brand);
        product.setCategory(category);
        product.setTags(tags);
        return product;
    }

    public void createProduct(ProductDto productDTO, String userName) {
        Brand brand = new Brand();
        Category category = new Category();
        Set<Tag> tags = new HashSet<>();

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

    public Page<ProductDto> getAllProductDTOs(Pageable pageable, String categoryId, String brandId, Set<String> tagIds) {
        log.warn("getAllProductDTOs called with categoryId: {}, brandId: {}, tagIds: {}", categoryId, brandId, tagIds);
        return productRepository.findProductsByFilters(categoryId, brandId, tagIds, pageable).map(
                this::getProductDto
        );
    }

    public ProductDto getProductDTOById(String productId) {
        return productRepository.findById(productId).map(
                this::getProductDto
        ).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
    }

    public Product getProductById(String productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
    }

    public void updateProduct(String id, ProductDto productDTO) {
        Set<Tag> tags = new HashSet<>();

        if (productDTO.getTagIds() != null) {
            for (String tagId : productDTO.getTagIds()) {
                tags.add(productTagService.getTagById(tagId));
            }
        }
        Product product = getProductById(id);

        modelMapper.getConfiguration().setSkipNullEnabled(true); // Prevent overwriting existing values
        modelMapper.map(productDTO, product);

    }

    public void deleteById(String productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }
        productRepository.deleteById(productId);
    }

    private ProductDto getProductDto(Product product) {
        ProductDto pDto = modelMapper.map(product, ProductDto.class);

        // Manually set IDs instead of full objects

        pDto.setName(product.getName());
        pDto.setDescription(product.getDescription());
        pDto.setImageUrls(product.getImageUrls());
        pDto.setRegularPrice(product.getRegularPrice() != null ? product.getRegularPrice() : null);
        pDto.setCurrentPrice(product.getCurrentPrice() != null ? product.getCurrentPrice() : null);
        pDto.setColors(product.getColors());
        pDto.setMaterial(product.getMaterial());
        pDto.setQuantity(product.getQuantity());
        pDto.setSizes(product.getSizes() != null ? product.getSizes() : null);
        pDto.setId(product.getId() != null ? product.getId() : null);

        pDto.setDiscountPercentage(product.getDiscountPercentage());
        pDto.setAverageRating(product.getAverageRating());
        pDto.setTotalReviews(product.getTotalReviews());

        pDto.setBrandId(product.getBrand().getId());
        pDto.setCategoryId(product.getCategory().getId());
        pDto.setTagIds(product.getTags().stream().map(
                Tag::getId
        ).collect(Collectors.toSet()));

        return pDto;
    }

}

