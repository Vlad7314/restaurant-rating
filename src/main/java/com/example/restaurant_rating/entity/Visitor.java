package com.example.restaurantrating.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data                     
@RequiredArgsConstructor   
public class Visitor {
    private final Long id;           
    private final String name;        
    private final Integer age;
    private final String gender;
}