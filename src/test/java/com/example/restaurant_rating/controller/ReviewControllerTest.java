package com.example.restaurant_rating.controller;

import com.example.restaurant_rating.dto.ReviewRequestDTO;
import com.example.restaurant_rating.dto.ReviewResponseDTO;
import com.example.restaurant_rating.service.RatingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllReviews_ShouldReturnList() throws Exception {
        List<ReviewResponseDTO> reviews = List.of(
                new ReviewResponseDTO(1L, 10L, 20L, 5, "Отлично")
        );
        when(ratingService.findAll()).thenReturn(reviews);

        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].rating").value(5));
    }

    @Test
    void createReview_ShouldReturnCreated() throws Exception {
        ReviewRequestDTO request = new ReviewRequestDTO(10L, 20L, 5, "Хорошо");
        ReviewResponseDTO response = new ReviewResponseDTO(1L, 10L, 20L, 5, "Хорошо");
        when(ratingService.save(any(ReviewRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void deleteReview_ShouldReturnNoContent_WhenExists() throws Exception {
        when(ratingService.remove(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/reviews/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteReview_ShouldReturnNotFound_WhenNotExists() throws Exception {
        when(ratingService.remove(99L)).thenReturn(false);

        mockMvc.perform(delete("/api/reviews/99"))
                .andExpect(status().isNotFound());
    }
}