package com.tamimSoft.store.controller.admin;

import com.tamimSoft.store.dto.SlideDTO;
import com.tamimSoft.store.response.ApiResponse;
import com.tamimSoft.store.service.SlideService;
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
@RequestMapping("/admin/slide")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin APIs", description = "Admin-only operations")
public class AdminSlideController {
    private final SlideService slideService;

    @PostMapping()
    @Operation(summary = "Create a slide", description = "Allows admin to create a new slide.")
    public ResponseEntity<ApiResponse<Void>> createSlide(@RequestBody SlideDTO slideDTO) {
        slideService.createSlide(slideDTO);
        log.info("slide created successfully");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED, "slide created successfully", null));
    }

    @PatchMapping()
    @Operation(summary = "Update a slide", description = "Allows admin to update a slide.")
    public ResponseEntity<ApiResponse<Void>> updateSlide(@RequestParam String id, @RequestBody SlideDTO slideDTO) {
        slideService.updateSlide(id, slideDTO);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "slide updated successfully", null));
    }

    @DeleteMapping()
    @Operation(summary = "Delete a slide", description = "Allows admin to delete a slide.")
    public ResponseEntity<Void> deleteBrand(@RequestParam String id) {
        slideService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
