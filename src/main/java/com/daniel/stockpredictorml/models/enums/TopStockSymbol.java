package com.daniel.stockpredictorml.models.enums;

import java.util.Arrays;
import java.util.List;

public enum TopStockSymbol {

    AAPL;
    //MSFT,
    //NVDA,
    //GOOGL,
   //AMZN;


    public String getSymbol() {
        return name();
    }

    public static List<String> symbols() {
        return Arrays.stream(values()).map(Enum::name).toList();
    }
}
