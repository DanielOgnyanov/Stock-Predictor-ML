package com.daniel.stockpredictorml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.daniel.stockpredictorml")
public class StockPredictorML {

    public static void main(String[] args) {
        SpringApplication.run(StockPredictorML.class, args);
    }
}