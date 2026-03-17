package com.example.restaurantrating.repository;

import com.example.restaurantrating.entity.Visitor;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class VisitorRepository {
    private final List<Visitor> visitors = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Visitor save(Visitor visitor) {
        Visitor newVisitor = new Visitor(
                idGenerator.getAndIncrement(),
                visitor.getName(),
                visitor.getAge(),
                visitor.getGender()
        );
        visitors.add(newVisitor);
        return newVisitor;
    }

    public boolean remove(Long id) {
        return visitors.removeIf(v -> v.getId().equals(id));
    }

    public List<Visitor> findAll() {
        return new ArrayList<>(visitors);
    }
}