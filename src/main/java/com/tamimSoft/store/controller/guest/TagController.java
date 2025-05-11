package com.tamimSoft.store.controller.guest;

import com.tamimSoft.store.dto.TagDto;
import com.tamimSoft.store.response.ApiResponse;
import com.tamimSoft.store.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/product-tags")
@RequiredArgsConstructor // Automatically injects dependencies via constructor
@Tag(name = "Public APIs")
public class TagController {

    private final TagService tagService;

    @GetMapping()
    @Operation(summary = "Get all product-tags", description = "Retrieve a list of all product-tags.")
    public ResponseEntity<ApiResponse<Page<TagDto>>> getAllTags(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<TagDto> productTagDTOS = tagService.getAllActiveTagsDto(PageRequest.of(page, size));
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Product-tags retrieved successfully", productTagDTOS));
    }

    @GetMapping("/id")
    @Operation(summary = "Get a product-tag by ID", description = "Retrieve a product-tag by its unique ID.")
    public ResponseEntity<ApiResponse<TagDto>> getTagById(@RequestParam String tagId) {
        TagDto tagDTO = tagService.getTagDtoById(tagId);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Product-tag retrieved successfully", tagDTO));
    }
}
