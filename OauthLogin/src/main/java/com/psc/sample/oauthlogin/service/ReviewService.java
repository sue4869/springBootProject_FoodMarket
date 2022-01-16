package com.psc.sample.oauthlogin.service;

import com.psc.sample.oauthlogin.domain.Product;
import com.psc.sample.oauthlogin.domain.Review;
import com.psc.sample.oauthlogin.domain.User;
import com.psc.sample.oauthlogin.dto.GoodDetailReviewDTO;
import com.psc.sample.oauthlogin.dto.PageRequestDTOforGoodDetailReview;
import com.psc.sample.oauthlogin.dto.PageResultDTOforGoodDetailReview;
import com.psc.sample.oauthlogin.repository.ProductRepository;
import com.psc.sample.oauthlogin.repository.ReviewRepository;
import com.psc.sample.oauthlogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    public List<Review> findReview(Long productId) {
        List<Review> reviews = reviewRepository.findAllByProduct_Id(productId);

        return reviews;
    }

    public boolean reviewCountup(Long reviewId, boolean close) {
        Review review1 = reviewRepository.findById(reviewId).get();
        if(close == true) { //후기내용이 닫혀있을때 //211031
            review1.setClicknumber(review1.getClicknumber() + 1);
            reviewRepository.save(review1);
        } else { // 후기내용이 펼쳐져있을때 //211031
            return false;
        }

        return true;
    }

    //최신등록순
    @Transactional
    public PageResultDTOforGoodDetailReview<GoodDetailReviewDTO, Review> GoodDetailbyregDate(PageRequestDTOforGoodDetailReview requestDTO , Long productId) {
        Pageable pageable = requestDTO.getPageableforGoodDetailReview(Sort.by("registerDate").descending());

        Page<Review> result = reviewRepository.findAllByProduct_Id(productId,pageable);

        Function<Review, GoodDetailReviewDTO> fn = (entity -> entitytoDetailReviewDto(entity));

        return  new PageResultDTOforGoodDetailReview<>(result,fn);
    }

    //좋아요많은순
    @Transactional
    public PageResultDTOforGoodDetailReview<GoodDetailReviewDTO, Review> GoodDetailbyheartnumber(PageRequestDTOforGoodDetailReview requestDTO , Long productId) {
        Pageable pageable = requestDTO.getPageableforGoodDetailReview(Sort.by("reviewGoodnumber").descending());

        Page<Review> result = reviewRepository.findAllByProduct_Id(productId,pageable);

        Function<Review, GoodDetailReviewDTO> fn = (entity -> entitytoDetailReviewDto(entity));

        return  new PageResultDTOforGoodDetailReview<>(result,fn);
    }

    //조회많은순
    @Transactional
    public PageResultDTOforGoodDetailReview<GoodDetailReviewDTO, Review> GoodDetailbyclicknumber(PageRequestDTOforGoodDetailReview requestDTO , Long productId) {
        Pageable pageable = requestDTO.getPageableforGoodDetailReview(Sort.by("clicknumber").descending());

        Page<Review> result = reviewRepository.findAllByProduct_Id(productId,pageable);

        Function<Review, GoodDetailReviewDTO> fn = (entity -> entitytoDetailReviewDto(entity));

        return  new PageResultDTOforGoodDetailReview<>(result,fn);
    }


    public GoodDetailReviewDTO entitytoDetailReviewDto(Review review) {

        GoodDetailReviewDTO dto = GoodDetailReviewDTO.builder()
                .reviewId(review.getId())
                .reviewTitle(review.getReviewTitle())
                .reviewContents(review.getReviewContents())
                .reviewImage(review.getReviewImage())
                .reviewGoodnumber(review.getReviewGoodnumber())
                .clicknumber(review.getClicknumber())
                .registerDate(review.getRegisterDate())
                .productId(review.getProduct().getId())
                .build();

        return  dto;
    }

    public void insertReview(Long userId, Long productId, String imageNameForInsert, String reviewText, String titleForReview) {

        Product product = productRepository.findById(productId).get();

        User user = userRepository.findById(userId).get();

        Review review = Review.builder()
                .id(0L)
                .reviewImage(imageNameForInsert)
                .reviewContents(reviewText)
                .reviewTitle(titleForReview)
                .reviewGoodnumber(0)
                .clicknumber(0)
                .product(product)
                .registerDate(LocalDate.now())
                .user(user)
                .build();

        List<Review> reviewList = new ArrayList<>();

        reviewList.add(review);

        reviewRepository.save(review);

//        user.setReview(reviewList);
//        user.setReview((List<Review>) review);

//        userRepository.save(user);

    }

    public List<Review> findAllByUserId(Long id) {

        return reviewRepository.findAllByUserId(id);

    }
}
