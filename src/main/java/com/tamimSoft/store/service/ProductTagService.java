package com.tamimSoft.store.service;

import com.tamimSoft.store.dto.ProductTagDto;
import com.tamimSoft.store.entity.Tag;
import com.tamimSoft.store.exception.ResourceNotFoundException;
import com.tamimSoft.store.repository.ProductRepository;
import com.tamimSoft.store.repository.ProductTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // Automatically injects BrandRepository via constructor
public class ProductTagService {

    private final ProductTagRepository productTagRepository;
    private final ProductRepository productRepository;


    public void createTag(ProductTagDto tagDTO) {
        Tag tag = new Tag();
        tag.setName(tagDTO.getName());
        productTagRepository.save(tag);
    }

    public Page<ProductTagDto> getAllActiveTagDTOs(Pageable pageable) {

        Page<Tag> productTagsPage = productTagRepository.findAll(pageable);

        List<ProductTagDto> filteredTags = productTagsPage.stream()
                .filter(tag -> productRepository.findByTagsContaining(tag).isPresent())
                .map(tag -> new ProductTagDto(tag.getId(), tag.getName()))
                .toList();

        return new PageImpl<>(filteredTags, pageable, productTagsPage.getTotalElements());
    }

    public Page<ProductTagDto> getAllTagDTOs(Pageable pageable) {
        return productTagRepository.findAll(pageable).map(
                tag -> new ProductTagDto(
                        tag.getId(),
                        tag.getName()
                )
        );

    }

    public ProductTagDto getTagDTOById(String tagId) {
        return productTagRepository.findById(tagId).map(
                tag -> new ProductTagDto(
                        tag.getId(),
                        tag.getName()
                )
        ).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + tagId));
    }

    public Tag getTagById(String tagId) {
        return productTagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + tagId));
    }


    public void updateTag(String id, ProductTagDto tagDTO) {
        Tag productTag = getTagById(id);
        productTag.setName(tagDTO.getName() != null ? tagDTO.getName() : productTag.getName());
        productTagRepository.save(productTag);
    }

    public void deleteTagById(String tagId) {
        if (!productTagRepository.existsById(tagId)) {
            throw new ResourceNotFoundException("Tag not found with id: " + tagId);
        }
        productTagRepository.deleteById(tagId);
    }
}