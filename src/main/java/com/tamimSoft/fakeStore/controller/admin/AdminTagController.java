package com.tamimSoft.fakeStore.controller.admin;

import com.tamimSoft.fakeStore.dto.ProductTagDTO;
import com.tamimSoft.fakeStore.response.ApiResponse;
import com.tamimSoft.fakeStore.service.ProductTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final ProductTagService tagService;
    @PostMapping()
    @Operation(summary = "Create a tag", description = "Allows admin to create a new product tag.")
    public ResponseEntity<ApiResponse<Void>> createTag(@RequestBody ProductTagDTO productTagDTO) {
        tagService.createTag(productTagDTO);
        log.info("Tag created successfully");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED, "Tag created successfully", null));
    }
    @PatchMapping()
    @Operation(summary = "Update a product tag", description = "Allows admin to update a product tag.")
    public ResponseEntity<ApiResponse<Void>> updateTag(@RequestParam String id, @RequestBody ProductTagDTO productTagDTO) {
        tagService.updateTag(id, productTagDTO);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Tag updated successfully", null));
    }


    @DeleteMapping()
    @Operation(summary = "Delete a Tag", description = "Allows admin to delete a Tag.")
    public ResponseEntity<Void> deleteTag(@RequestParam String tagId) {
        tagService.deleteTagById(tagId);
        return ResponseEntity.noContent().build();
    }
}
