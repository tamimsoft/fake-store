package com.tamimSoft.store.controller.customer;

import com.tamimSoft.store.dto.ReviewDto;
import com.tamimSoft.store.response.ApiResponse;
import com.tamimSoft.store.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/customer/review")
@PreAuthorize("hasRole('CUSTOMER')")
@Tag(name = "Customer APIs")
public class ReviewController {

    private final ReviewService reviewService;

    private String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping()
    @Operation(summary = "Get all reviews of this customer", description = "Retrieve all reviews of the authenticated customer.")
    public ResponseEntity<ApiResponse<Page<ReviewDto>>> getAllReviewsOfThisCustomer(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ReviewDto> reviewDtoPage = reviewService.getAllReviewsDtoByUserName(getAuthenticatedUsername(), PageRequest.of(page, size));
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Reviews retrieved successfully", reviewDtoPage));
    }

    @GetMapping("/product")
    @Operation(summary = "Get reviews for a product", description = "Retrieve reviews for a specific product.")
    public ResponseEntity<ApiResponse<Page<ReviewDto>>> getReviewsForProduct(
            @RequestParam String productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ReviewDto> reviewDtoPage = reviewService.getAllReviewsDtoForAProduct(productId, PageRequest.of(page, size));
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Reviews retrieved successfully", reviewDtoPage));
    }


    @PostMapping()
    @Operation(summary = "Create a review", description = "Create a new review for a product.")
    public ResponseEntity<ApiResponse<Void>> createReview(@RequestBody ReviewDto reviewDto) {
        reviewService.addReview(getAuthenticatedUsername(), reviewDto);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED, "Review created successfully", null));
    }

    @PatchMapping()
    @Operation(summary = "Update a review", description = "Update an existing review.")
    public ResponseEntity<ApiResponse<Void>> updateReview(
            @RequestParam String reviewId,
            @RequestBody ReviewDto reviewDto
    ) {
        reviewService.updateReview(reviewId, reviewDto);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Review updated successfully", null));
    }

    @DeleteMapping()
    @Operation(summary = "Delete a review", description = "Delete an existing review.")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@RequestParam String reviewId) {
        reviewService.deleteReview(getAuthenticatedUsername(), reviewId);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Review deleted successfully", null));
    }
}



