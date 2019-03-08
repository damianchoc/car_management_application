package com.app.validation;

import com.app.model.Car;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CarValidator {
    private Map<String, String> errors = new HashMap<>();

    public Map<String, String> validate(Car car) {
        errors.clear();

        if (car == null) {
            errors.put("car", "car object is null");
        }

        if (!isModelValid(car)) {
            errors.put("model", "model is not valid");
        }

        if (!isPriceValid(car)) {
            errors.put("price","price is not valid");
        }

        if (!isMileageValid(car)) {
            errors.put("mileage","mileage is not valid");
        }

        if (!areComponentsValid(car)) {
            errors.put("components","components are not valid");
        }

        if (!isColorValid(car)) {
            errors.put("color","color is not valid");
        }

        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    private boolean isModelValid(Car car) {
        return car.getModel() != null && car.getModel().matches("[A-Z\\s]+");
    }

    private boolean isPriceValid(Car car) {
        return car.getPrice().doubleValue()>0;
    }

    private boolean isMileageValid(Car car) {
        return car.getMileage().doubleValue()>0;
    }

    private boolean areComponentsValid(Car car) {
        return car.getComponents().stream().allMatch(element -> element.matches("[A-Z\\s]+"));
    }

    private boolean isColorValid(Car car){
        return Objects.nonNull(car.getColor());
    }
}
