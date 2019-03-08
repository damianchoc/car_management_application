package com.app;

import com.app.model.Car;
import com.app.service.CarsService;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        final String fileName = "car.json";
        CarsService carsService = new CarsService(fileName);
//        carsService.toString();
        Map<String, Car> map = carsService.getMostExpensiveCarsByModels();
        map.entrySet().forEach(entry -> System.out.println(entry.getKey() + " -- " + entry.getValue()));

/*        BigDecimal d1 = new BigDecimal("100");
        BigDecimal d2 = new BigDecimal("100");
        if (d1.equals(d2)) {
            System.out.println("OK 1");
        }*/

        // [(ngModel)] = "product.name"

    }
}
