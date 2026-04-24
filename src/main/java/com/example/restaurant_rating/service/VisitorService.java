package com.example.restaurant_rating.service;

import com.example.restaurant_rating.dto.UserRequestDTO;
import com.example.restaurant_rating.dto.UserResponseDTO;
import com.example.restaurant_rating.entity.Visitor;
import com.example.restaurant_rating.repository.VisitorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitorService {
    private final VisitorRepository visitorRepository;

    // Конструктор для внедрения зависимости
    public VisitorService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    public UserResponseDTO save(UserRequestDTO dto) {
        Visitor visitor = new Visitor(null, dto.name(), dto.age(), dto.gender());
        Visitor saved = visitorRepository.save(visitor);
        return mapToDTO(saved);
    }

    public boolean remove(Long id) {
        if (visitorRepository.existsById(id)) {
            visitorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<UserResponseDTO> findAll() {
        return visitorRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private UserResponseDTO mapToDTO(Visitor visitor) {
        return new UserResponseDTO(visitor.getId(), visitor.getName(), visitor.getAge(), visitor.getGender());
    }
}