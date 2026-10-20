package com.juju.review.service;


import com.juju.review.model.RestaurantEntity;
import com.juju.review.model.ReviewEntity;
import com.juju.review.repository.RestaurantRepository;
import com.juju.review.repository.ReviewRepository;
import com.juju.review.service.dto.ReviewDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public void createReview(Long restaurantId, String content, double score) {
        restaurantRepository.findById(restaurantId).orElseThrow();


        ReviewEntity review = ReviewEntity.builder()
                .restaurantId(restaurantId)
                .content(content)
                .score(score)
                .createdAt(ZonedDateTime.now())
                .build();

        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        ReviewEntity review = reviewRepository.findById(reviewId).orElseThrow();

        reviewRepository.delete(review);
    }

    public ReviewDto getRestaurantReview(Long restaurantId, Pageable page) {
        Double avgScore = reviewRepository.getAvgScoreByRestaurantId(restaurantId);
        Slice<ReviewEntity> reviews= reviewRepository.findSliceByRestaurantId(restaurantId, page);

        return ReviewDto.builder()
                .avgScore(avgScore)
                .reviews(reviews.getContent())
                .page(
                        ReviewDto.ReviewDtoPage.builder()
                                .offset((int) page.getOffset())
                                .limit(page.getPageSize())
                                .build()
                )
                .build();
    }
}
