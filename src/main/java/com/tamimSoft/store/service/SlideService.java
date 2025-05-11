package com.tamimSoft.store.service;

import com.tamimSoft.store.dto.SlideDTO;
import com.tamimSoft.store.entity.Brand;
import com.tamimSoft.store.entity.Category;
import com.tamimSoft.store.entity.Product;
import com.tamimSoft.store.entity.Slide;
import com.tamimSoft.store.exception.ResourceNotFoundException;
import com.tamimSoft.store.repository.SlideRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Automatically injects BrandRepository via constructor
public class SlideService {

    private final SlideRepository slideRepository;
    private final ModelMapper modelMapper;

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    public void createSlide(SlideDTO slideDTO) {

        // Map basic fields using ModelMapper
        Slide slide = modelMapper.map(slideDTO, Slide.class);

        // Manually set relationships
        // And Fetch DBRef entities manually
        if (slideDTO.getCategoryId() != null) {
            Category category = categoryService.getCategoryById(slideDTO.getCategoryId());
            slide.setCategory(category);
        }
        if (slideDTO.getBrandId() != null) {
            Brand brand = brandService.getBrandById(slideDTO.getBrandId());
            slide.setBrand(brand);
        }
        if (slideDTO.getProductId() != null) {
            Product product = productService.getProductById(slideDTO.getProductId());
            slide.setProduct(product);
        }
        // Save the slide entity
        slideRepository.save(slide);
    }

    public Page<SlideDTO> getAllSlideDTOs(Pageable pageable) {
        return slideRepository.findAll(pageable).map(
                // Convert to DTO
                this::getSlideDTO
        );
    }


    public SlideDTO getSlideDTOById(String slideId) {
        Slide slide = getSlideById(slideId);
        // Convert to DTO
        return getSlideDTO(slide);

    }

    private SlideDTO getSlideDTO(Slide slide) {
        SlideDTO slideDTO = modelMapper.map(slide, SlideDTO.class);

        // Manually set IDs instead of full objects
        slideDTO.setProductId(slide.getProduct() != null ? slide.getProduct().getId() : null);
        slideDTO.setCategoryId(slide.getCategory() != null ? slide.getCategory().getId() : null);
        slideDTO.setBrandId(slide.getBrand() != null ? slide.getBrand().getId() : null);

        return slideDTO;
    }

    public Slide getSlideById(String slideId) {
        return slideRepository.findById(slideId).orElseThrow(() -> new ResourceNotFoundException("Slide not found with id: " + slideId));
    }

    public void updateSlide(String slideId, SlideDTO slideDTO) {
        Slide slide = getSlideById(slideId);
        modelMapper.getConfiguration().setSkipNullEnabled(true); // Prevent overwriting existing values
        modelMapper.map(slideDTO, slide);

        slideRepository.save(slide);
    }

    public void deleteById(String slideId) {
        if (!slideRepository.existsById(slideId)) {
            throw new ResourceNotFoundException("Slide not found with id: " + slideId);
        }
        slideRepository.deleteById(slideId);
    }

}