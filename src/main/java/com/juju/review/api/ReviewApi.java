package com.juju.review.api;

import com.juju.review.api.request.CreateReviewRequest;
import com.juju.review.service.ReviewService;
import com.juju.review.service.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class ReviewApi {
    private final ReviewService reviewService;

    @PostMapping("/review")
    public void createReview(
            @RequestBody CreateReviewRequest request
            ){
        reviewService.createReview(request.getRestaurantId(), request.getContent(), request.getScore());
    }

    @DeleteMapping("/review/{reviewId}")
    public void deleteReview(@PathVariable("reviewId") Long reviewId){
        reviewService.deleteReview(reviewId);
    }

    @GetMapping("/restaurant/{restaurantId}/reviews")
    public ReviewDto getRestaurantReviews(
            @PathVariable("restaurantId") Long restaurantId,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ){
        // offset/limit 을 페이지 번호 기반의 Pageable 로 변환
        return reviewService.getRestaurantReview(restaurantId, PageRequest.of(offset / limit, limit));
    }
}
