package com.tamimSoft.fakeStore.service;

import com.tamimSoft.fakeStore.dto.ProductTagDTO;
import com.tamimSoft.fakeStore.entity.ProductTag;
import com.tamimSoft.fakeStore.exception.ResourceNotFoundException;
import com.tamimSoft.fakeStore.repository.ProductRepository;
import com.tamimSoft.fakeStore.repository.ProductTagRepository;
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


    public void createTag(ProductTagDTO tagDTO) {
        ProductTag tag = new ProductTag();
        tag.setName(tagDTO.getName());
        productTagRepository.save(tag);
    }

    public Page<ProductTagDTO> getAllActiveTagDTOs(Pageable pageable) {

        Page<ProductTag> productTagsPage = productTagRepository.findAll(pageable);

        List<ProductTagDTO> filteredTags = productTagsPage.stream()
                .filter(tag -> productRepository.findByTagsContaining(tag).isPresent())
                .map(tag -> new ProductTagDTO(tag.getId(), tag.getName()))
                .toList();

        return new PageImpl<>(filteredTags, pageable, productTagsPage.getTotalElements());
    }

    public Page<ProductTagDTO> getAllTagDTOs(Pageable pageable) {
        return productTagRepository.findAll(pageable).map(
                tag -> new ProductTagDTO(
                        tag.getId(),
                        tag.getName()
                )
        );

    }

    public ProductTagDTO getTagDTOById(String tagId) {
        return productTagRepository.findById(tagId).map(
                tag -> new ProductTagDTO(
                        tag.getId(),
                        tag.getName()
                )
        ).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + tagId));
    }

    public ProductTag getTagById(String tagId) {
        return productTagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + tagId));
    }


    public void updateTag(String id, ProductTagDTO tagDTO) {
        ProductTag productTag = getTagById(id);
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