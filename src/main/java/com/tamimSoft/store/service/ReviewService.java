package com.tamimSoft.store.service;

import com.tamimSoft.store.dto.ReviewDto;
import com.tamimSoft.store.entity.Product;
import com.tamimSoft.store.entity.Review;
import com.tamimSoft.store.entity.User;
import com.tamimSoft.store.exception.ResourceNotFoundException;
import com.tamimSoft.store.repository.ProductRepository;
import com.tamimSoft.store.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public Page<ReviewDto> getAllReviewsDtoByUserName(String userName, Pageable pageable) {
        List<ReviewDto> reviewPage = reviewRepository.findAll(pageable).stream().map(
                review -> modelMapper.map(review, ReviewDto.class)
        ).toList();
        return new PageImpl<>(reviewPage, pageable, reviewPage.size());
    }

    public Page<ReviewDto> getAllReviewsDtoForAProduct(String productId, Pageable pageable) {
        List<ReviewDto> reviewPage = reviewRepository.findByProductId(productId, pageable).stream().map(
                review -> modelMapper.map(review, ReviewDto.class)
        ).toList();
        return new PageImpl<>(reviewPage, pageable, reviewPage.size());
    }

    public ReviewDto getReviewDtoById(String reviewId) {
        Review review = getReviewById(reviewId);
        return modelMapper.map(review, ReviewDto.class);
    }

    public Review getReviewById(String reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));
    }

    @Transactional
    public void addReview(String userName, ReviewDto reviewDto) {
        User user = userService.getUserByUserName(userName); // Use injected UserService

        Review review = modelMapper.map(reviewDto, Review.class);
        review.setUser(user);
        reviewRepository.save(review);

        // Update product's rating
        updateProductRating(reviewDto.getProductId());
    }

    @Transactional
    public void updateReview(String reviewId, ReviewDto reviewDto) {
        Review reviewToUpdate = getReviewById(reviewId);
        reviewToUpdate.setComment(reviewDto.getComment());

        reviewRepository.save(reviewToUpdate);

        // Update product's rating
        updateProductRating(reviewDto.getProductId());
    }


    private void updateProductRating(String productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        double avgRating = reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
        int totalReviews = reviews.size();

        Product product = productService.getProductById(productId);
        product.setAverageRating(avgRating);
        product.setTotalReviews(totalReviews);

        productRepository.save(product);
    }

    public void deleteReview(String userName, String reviewId) {
        Review reviewToDelete = getReviewById(reviewId);
        reviewRepository.deleteById(reviewId);
        updateProductRating(reviewToDelete.getProduct().getId());
    }
}
