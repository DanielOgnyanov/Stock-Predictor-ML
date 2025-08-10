package main.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class StockPredictorML {

    public static void main(String[] args) {
        SpringApplication.run(StockPredictorML.class, args);
    }
}