package com.example.restaurant_rating.service;

import com.example.restaurant_rating.dto.RestaurantRequestDTO;
import com.example.restaurant_rating.dto.RestaurantResponseDTO;
import com.example.restaurant_rating.entity.CuisineType;
import com.example.restaurant_rating.entity.Restaurant;
import com.example.restaurant_rating.repository.RestaurantRepository;
import com.example.restaurant_rating.repository.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RatingRepository ratingRepository; // нужно для recalculate

    @InjectMocks
    private RestaurantService restaurantService;

    private Restaurant restaurant;
    private RestaurantRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant(1L, "Пушкин", "Русская кухня", CuisineType.RUSSIAN, new BigDecimal("2500"));
        restaurant.setAverageRating(BigDecimal.ZERO);
        requestDTO = new RestaurantRequestDTO("Пушкин", "Русская кухня", CuisineType.RUSSIAN, new BigDecimal("2500"));
    }

    @Test
    void save_ShouldReturnRestaurantResponseDTO() {
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        RestaurantResponseDTO result = restaurantService.save(requestDTO);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Пушкин");
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    void update_ShouldReturnUpdatedRestaurantResponseDTO() {
        Restaurant updatedRestaurant = new Restaurant(1L, "Пушкин Updated", "Новое описание", CuisineType.RUSSIAN, new BigDecimal("3000"));
        updatedRestaurant.setAverageRating(BigDecimal.valueOf(4.5));
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(updatedRestaurant);

        RestaurantRequestDTO updateDTO = new RestaurantRequestDTO("Пушкин Updated", "Новое описание", CuisineType.RUSSIAN, new BigDecimal("3000"));
        RestaurantResponseDTO result = restaurantService.update(updateDTO, 1L);

        assertThat(result.name()).isEqualTo("Пушкин Updated");
        assertThat(result.averageBill()).isEqualByComparingTo(new BigDecimal("3000"));
        verify(restaurantRepository, times(1)).findById(1L);
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    void remove_ShouldDeleteRestaurant() {
        doNothing().when(restaurantRepository).deleteById(1L);

        restaurantService.remove(1L);

        verify(restaurantRepository, times(1)).deleteById(1L);
    }

    @Test
    void findAll_ShouldReturnList() {
        when(restaurantRepository.findAll()).thenReturn(List.of(restaurant));

        List<RestaurantResponseDTO> result = restaurantService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).id()).isEqualTo(1L);
    }

    @Test
    void findById_ShouldReturnDTO_WhenExists() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        RestaurantResponseDTO result = restaurantService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
    }

    @Test
    void findById_ShouldReturnNull_WhenNotExists() {
        when(restaurantRepository.findById(99L)).thenReturn(Optional.empty());

        RestaurantResponseDTO result = restaurantService.findById(99L);

        assertThat(result).isNull();
    }
}