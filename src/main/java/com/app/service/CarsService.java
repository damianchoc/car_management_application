package com.app.service;

import com.app.converters.CarsJsonConverter;
import com.app.exception.AppException;
import com.app.model.Car;
import com.app.model.enums.Color;
import com.app.service.enums.SortType;
import com.app.validation.CarValidator;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class CarsService {
    private final List<Car> cars;

    public CarsService(String filename) {
        cars = getCarsFromJson(filename);
    }

    private List<Car> getCarsFromJson(String filename) {
        CarValidator carValidator = new CarValidator();
        List<Car> result =  new CarsJsonConverter(filename)
                .fromJson()
                .orElseThrow(() -> new AppException("CAR SERVICEJSON O+EXCEPTION"))
                .stream()
                .filter(car -> {
                    carValidator.validate(car).forEach((k, v) -> System.out.println(k + " " + v));
                    return !carValidator.hasErrors();
                })
                .collect(Collectors.toList());
        return result;
    }

    public String toString() {
        return
                cars
                        .stream()
                        .map(Car::toString)
                        .collect(Collectors.joining("\n"));
    }

    public List<Car> getSortedCars(SortType sortType, boolean sortDescending) {
        List<Car> result = null;
        switch (sortType) {
            case MODEL:
                result = cars.stream().sorted(Comparator.comparing(Car::getModel)).collect(Collectors.toList());
                break;
            case COLOR:
                result = cars.stream().sorted(Comparator.comparing(Car::getColor)).collect(Collectors.toList());
                break;
            case PRICE:
                result = cars.stream().sorted(Comparator.comparing(Car::getPrice)).collect(Collectors.toList());
                break;
            case MILEAGE:
                result = cars.stream().sorted(Comparator.comparing(Car::getMileage)).collect(Collectors.toList());
        }
        if (sortDescending) {
            Collections.reverse(result);
        }
        return result;
    }

    public List<Car> getCarsWithMileageMoreThan(BigDecimal milleage) {
        return cars
                .stream()
                .filter(car -> car.getMileage().compareTo(milleage)==1)
                .collect(Collectors.toList());
    }

    public Map<Color, Long> getColorsAndTheirCarCount() {
        return cars
                .stream()
                .collect(Collectors.groupingBy(Car::getColor, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }

    public Map<String,Car> getMostExpensiveCarsByModels(){
         return cars
                .stream()
                .collect(Collectors.groupingBy(Car::getModel, Collectors.toList()))
                .entrySet()
                .stream()
                 .collect(Collectors.toMap(
                         Map.Entry::getKey,
                         p -> p.getValue()
                                 .stream()
                                 .min((e1,e2) -> e2.getPrice().compareTo(e1.getPrice())).get()));
    }

    public Map<String, List<Car>> getComponentsAndCarsMap(){
        return cars
                .stream()
                .flatMap(car -> car.getComponents().stream())
                .distinct()
                .collect(Collectors.toMap(
                        component -> component,
                        component -> cars.stream().filter(car -> car.getComponents().contains(component)).collect(Collectors.toList())
                ))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }

    public void printStatistics() {
        System.out.println("Average mileage: " + getAverage(cars.stream().map(car -> car.getMileage().doubleValue()).collect(Collectors.toList())));
        System.out.println("Max mileage: " + cars.stream().max((x,y)  -> x.getMileage().compareTo(y.getMileage())).get().getMileage().doubleValue());
        System.out.println("Min mileage: " + cars.stream().min((x,y)  -> x.getMileage().compareTo(y.getMileage())).get().getMileage().doubleValue());
        System.out.println("All mileage: " + getAverage(cars.stream().map(element -> element.getMileage().doubleValue()).collect(Collectors.toList())));
    }

    public List<Car> getMostExpensiveCar(){
        BigDecimal max = cars.stream().map(element -> element.getPrice()).max((x,y) -> x.compareTo(y)).get();
        return cars.stream().filter(element -> element.getPrice().equals(max)).collect(Collectors.toList());
    }

    public List<Car> getCarsWithSortedComponents(){
        List<Car> result = new ArrayList<>();
        result.addAll(cars);
        result.stream().forEach(car -> car.setComponents(car.getComponents().stream().sorted((x,y) -> x.compareTo(y)).collect(Collectors.toSet())));
        return result;
    }

    public List<Car> getCarsByPrices(double minPrice, double maxPrice){
        return cars.stream().filter(car -> car.getPrice().doubleValue() >= minPrice && car.getPrice().doubleValue() <= maxPrice).sorted((x,y) -> x.getModel().compareTo(y.getModel())).collect(Collectors.toList());
    }



    private double getAverage(List<Double> doubleList){
        double sum = 0;
        for(double element : doubleList) {
            sum+=element;
        }
        return sum/doubleList.size();
    }

}
