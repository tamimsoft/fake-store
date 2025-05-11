package com.tamimSoft.store.controller.admin;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/tag")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin APIs", description = "Admin-only operations")
public class AdminTagController {

    private final TagService tagService;

    @GetMapping()
    @Operation(summary = "Get all tags", description = "Retrieve a list of all tags.")
    public ResponseEntity<ApiResponse<Page<TagDto>>> getAllTags(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<TagDto> allTagDTOs = tagService.getAllTagsDto(PageRequest.of(page, size));
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Tags retrieved successfully", allTagDTOs));
    }

    @PostMapping()
    @Operation(summary = "Create a tag", description = "Allows admin to create a new product tag.")
    public ResponseEntity<ApiResponse<Void>> createTag(@RequestBody TagDto tagDto) {
        tagService.createTag(tagDto);
        log.info("Tag created successfully");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED, "Tag created successfully", null));
    }


    @PatchMapping()
    @Operation(summary = "Update a product tag", description = "Allows admin to update a product tag.")
    public ResponseEntity<ApiResponse<Void>> updateTag(@RequestParam String id, @RequestBody TagDto tagDto) {
        tagService.updateTag(id, tagDto);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Tag updated successfully", null));
    }


    @DeleteMapping()
    @Operation(summary = "Delete a Tag", description = "Allows admin to delete a Tag.")
    public ResponseEntity<Void> deleteTag(@RequestParam String tagId) {
        tagService.deleteById(tagId);
        return ResponseEntity.noContent().build();
    }
}
