package com.example.restaurant_rating.controller;

import com.example.restaurant_rating.dto.RestaurantRequestDTO;
import com.example.restaurant_rating.dto.RestaurantResponseDTO;
import com.example.restaurant_rating.entity.CuisineType;
import com.example.restaurant_rating.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllRestaurants_ShouldReturnList() throws Exception {
        List<RestaurantResponseDTO> restaurants = List.of(
                new RestaurantResponseDTO(1L, "Пушкин", "Русская", CuisineType.RUSSIAN, new BigDecimal("2500"), BigDecimal.ZERO)
        );
        when(restaurantService.findAll()).thenReturn(restaurants);

        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Пушкин"));
    }

    @Test
    void getRestaurantById_ShouldReturnRestaurant() throws Exception {
        RestaurantResponseDTO dto = new RestaurantResponseDTO(1L, "Пушкин", "Русская", CuisineType.RUSSIAN, new BigDecimal("2500"), BigDecimal.valueOf(4.2));
        when(restaurantService.findById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/restaurants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Пушкин"))
                .andExpect(jsonPath("$.averageRating").value(4.2));
    }

    @Test
    void getRestaurantById_ShouldReturnNotFound() throws Exception {
        when(restaurantService.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/restaurants/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createRestaurant_ShouldReturnCreated() throws Exception {
        RestaurantRequestDTO request = new RestaurantRequestDTO("Новый", "Описание", CuisineType.RUSSIAN, new BigDecimal("3000"));
        RestaurantResponseDTO response = new RestaurantResponseDTO(1L, "Новый", "Описание", CuisineType.RUSSIAN, new BigDecimal("3000"), BigDecimal.ZERO);
        when(restaurantService.save(any(RestaurantRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void updateRestaurant_ShouldReturnOk() throws Exception {
        RestaurantRequestDTO request = new RestaurantRequestDTO("Обновлённый", "Описание", CuisineType.RUSSIAN, new BigDecimal("3500"));
        RestaurantResponseDTO response = new RestaurantResponseDTO(1L, "Обновлённый", "Описание", CuisineType.RUSSIAN, new BigDecimal("3500"), BigDecimal.valueOf(4.8));
        when(restaurantService.update(any(RestaurantRequestDTO.class), eq(1L))).thenReturn(response);

        mockMvc.perform(put("/api/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Обновлённый"));
    }

    @Test
    void deleteRestaurant_ShouldReturnNoContent() throws Exception {
        doNothing().when(restaurantService).remove(1L);

        mockMvc.perform(delete("/api/restaurants/1"))
                .andExpect(status().isNoContent());
    }
}