package com.example.restaurant_rating.service;

import com.example.restaurant_rating.dto.ReviewRequestDTO;
import com.example.restaurant_rating.dto.ReviewResponseDTO;
import com.example.restaurant_rating.entity.Rating;
import com.example.restaurant_rating.repository.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RatingService ratingService;

    private Rating rating;
    private ReviewRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        rating = new Rating(1L, 100L, 200L, 5, "Отлично!");
        requestDTO = new ReviewRequestDTO(100L, 200L, 5, "Отлично!");
    }

    @Test
    void save_ShouldReturnReviewResponseDTO_AndRecalculateRating() {
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);
        doNothing().when(restaurantService).recalculateAverageRating(200L);

        ReviewResponseDTO result = ratingService.save(requestDTO);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.rating()).isEqualTo(5);
        verify(restaurantService, times(1)).recalculateAverageRating(200L);
    }

    @Test
    void remove_ShouldReturnTrue_WhenRatingExists() {
        when(ratingRepository.findById(1L)).thenReturn(Optional.of(rating));
        doNothing().when(ratingRepository).deleteById(1L);
        doNothing().when(restaurantService).recalculateAverageRating(200L);

        boolean result = ratingService.remove(1L);

        assertThat(result).isTrue();
        verify(ratingRepository, times(1)).deleteById(1L);
        verify(restaurantService, times(1)).recalculateAverageRating(200L);
    }

    @Test
    void remove_ShouldReturnFalse_WhenRatingNotFound() {
        when(ratingRepository.findById(99L)).thenReturn(Optional.empty());

        boolean result = ratingService.remove(99L);

        assertThat(result).isFalse();
        verify(ratingRepository, never()).deleteById(anyLong());
        verify(restaurantService, never()).recalculateAverageRating(anyLong());
    }

    @Test
    void findAll_ShouldReturnList() {
        when(ratingRepository.findAll()).thenReturn(List.of(rating));

        List<ReviewResponseDTO> result = ratingService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).rating()).isEqualTo(5);
    }
}