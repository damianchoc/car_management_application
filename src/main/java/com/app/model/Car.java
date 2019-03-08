package com.app.model;

import com.app.model.enums.Color;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {
    private String model;
    private Color color;
    private BigDecimal price;
    private BigDecimal mileage;
    private Set<String> components;
}
