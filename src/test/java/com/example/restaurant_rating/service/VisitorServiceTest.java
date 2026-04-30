package com.example.restaurant_rating.service;

import com.example.restaurant_rating.dto.UserRequestDTO;
import com.example.restaurant_rating.dto.UserResponseDTO;
import com.example.restaurant_rating.entity.Visitor;
import com.example.restaurant_rating.repository.VisitorRepository;
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
class VisitorServiceTest {

    @Mock
    private VisitorRepository visitorRepository;

    @InjectMocks
    private VisitorService visitorService;

    private Visitor visitor;
    private UserRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        visitor = new Visitor(1L, "Иван", 25, "M");
        requestDTO = new UserRequestDTO("Иван", 25, "M");
    }

    @Test
    void save_ShouldReturnUserResponseDTO() {
        when(visitorRepository.save(any(Visitor.class))).thenReturn(visitor);

        UserResponseDTO result = visitorService.save(requestDTO);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Иван");
        verify(visitorRepository, times(1)).save(any(Visitor.class));
    }

    @Test
    void remove_ShouldReturnTrue_WhenVisitorExists() {
        when(visitorRepository.existsById(1L)).thenReturn(true);
        doNothing().when(visitorRepository).deleteById(1L);

        boolean result = visitorService.remove(1L);

        assertThat(result).isTrue();
        verify(visitorRepository, times(1)).deleteById(1L);
    }

    @Test
    void remove_ShouldReturnFalse_WhenVisitorNotFound() {
        when(visitorRepository.existsById(99L)).thenReturn(false);

        boolean result = visitorService.remove(99L);

        assertThat(result).isFalse();
        verify(visitorRepository, never()).deleteById(anyLong());
    }

    @Test
    void findAll_ShouldReturnListOfUserResponseDTO() {
        when(visitorRepository.findAll()).thenReturn(List.of(visitor));

        List<UserResponseDTO> result = visitorService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("Иван");
    }
}