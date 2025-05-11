package com.tamimSoft.store.controller.guest;

import com.tamimSoft.store.dto.SlideDto;
import com.tamimSoft.store.response.ApiResponse;
import com.tamimSoft.store.service.SlideService;
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
@RequestMapping("/slides")
@RequiredArgsConstructor // Automatically injects dependencies via constructor
@Tag(name = "Public APIs")
public class SlideController {

    private final SlideService slideService;

    @GetMapping()
    @Operation(summary = "Get all slides", description = "Retrieve a list of all slides.")
    public ResponseEntity<ApiResponse<Page<SlideDto>>> getAllSlides(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<SlideDto> slideDTO = slideService.getAllSlideDTOs(PageRequest.of(page, size));
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "slides retrieved successfully", slideDTO));
    }

    @GetMapping("/id")
    @Operation(summary = "Get a slide by ID", description = "Retrieve a slide by its unique ID.")
    public ResponseEntity<ApiResponse<SlideDto>> getSlideById(@RequestParam String id) {
        SlideDto slideDTO = slideService.getSlideDTOById(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "slide retrieved successfully", slideDTO));
    }
}
