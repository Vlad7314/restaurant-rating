package com.example.restaurant_rating.controller;

import com.example.restaurant_rating.dto.UserRequestDTO;
import com.example.restaurant_rating.dto.UserResponseDTO;
import com.example.restaurant_rating.service.VisitorService;
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

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitorService visitorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllUsers_ShouldReturnList() throws Exception {
        List<UserResponseDTO> users = List.of(
                new UserResponseDTO(1L, "Иван", 25, "M"),
                new UserResponseDTO(2L, "Мария", 30, "F")
        );
        when(visitorService.findAll()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Иван"));
    }

    @Test
    void createUser_ShouldReturnCreated() throws Exception {
        UserRequestDTO request = new UserRequestDTO("Петр", 28, "M");
        UserResponseDTO response = new UserResponseDTO(1L, "Петр", 28, "M");
        when(visitorService.save(any(UserRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Петр"));
    }

    @Test
    void deleteUser_ShouldReturnNoContent_WhenExists() throws Exception {
        when(visitorService.remove(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_ShouldReturnNotFound_WhenNotExists() throws Exception {
        when(visitorService.remove(99L)).thenReturn(false);

        mockMvc.perform(delete("/api/users/99"))
                .andExpect(status().isNotFound());
    }
}