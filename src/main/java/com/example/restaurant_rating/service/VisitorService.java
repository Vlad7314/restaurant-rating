package com.example.restaurant_rating.service;

import com.example.restaurant_rating.entity.Visitor;
import com.example.restaurant_rating.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitorService {
    private final VisitorRepository visitorRepository;

    public Visitor save(Visitor visitor) {
        return visitorRepository.save(visitor);
    }

    public boolean remove(Long id) {
        return visitorRepository.remove(id);
    }

    public List<Visitor> findAll() {
        return visitorRepository.findAll();
    }
}