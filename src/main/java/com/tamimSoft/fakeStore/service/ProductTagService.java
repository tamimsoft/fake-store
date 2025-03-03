package com.tamimSoft.fakeStore.service;

import com.tamimSoft.fakeStore.dto.ProductTagDTO;
import com.tamimSoft.fakeStore.entity.ProductTag;
import com.tamimSoft.fakeStore.exception.ResourceNotFoundException;
import com.tamimSoft.fakeStore.repository.ProductTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Automatically injects BrandRepository via constructor
public class ProductTagService {

    private final ProductTagRepository productTagRepository;


    public void createTag(ProductTagDTO productTagDTO) {
        ProductTag productTag = new ProductTag();
        productTag.setName(productTagDTO.getName());
        productTagRepository.save(productTag);
    }

    public Page<ProductTagDTO> getAllTagDTOs(Pageable pageable) {
        return productTagRepository.findAll(pageable).map(
                productTag -> new ProductTagDTO(
                        productTag.getId(),
                        productTag.getName()
                )
        );
    }


    public ProductTagDTO getTagDTOById(String tagId) {
        return productTagRepository.findById(tagId).map(
                productTag -> new ProductTagDTO(
                        productTag.getId(),
                        productTag.getName()
                )
        ).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + tagId));
    }

    public ProductTag getTagById(String tagId) {
        return productTagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + tagId));
    }


    public void updateTag(String id, ProductTagDTO productTagDTO) {
        ProductTag productTag = getTagById(id);
        productTag.setName(productTagDTO.getName() != null ? productTagDTO.getName() : productTag.getName());
        productTagRepository.save(productTag);
    }

    public void deleteTagById(String tagId) {
        if (!productTagRepository.existsById(tagId)) {
            throw new ResourceNotFoundException("Tag not found with id: " + tagId);
        }
        productTagRepository.deleteById(tagId);
    }
}