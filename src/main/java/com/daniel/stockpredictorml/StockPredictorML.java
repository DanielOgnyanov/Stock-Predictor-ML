package com.daniel.stockpredictorml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class StockPredictorML {

    public static void main(String[] args) {
        SpringApplication.run(StockPredictorML.class, args);
    }
}