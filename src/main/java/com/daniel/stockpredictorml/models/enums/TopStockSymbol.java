package com.daniel.stockpredictorml.models.enums;

import java.util.Arrays;
import java.util.List;

public enum TopStockSymbol {

    AAPL("Apple Inc."),
    MSFT("Microsoft Corporation"),
    NVDA("NVIDIA Corporation"),
    GOOGL("Alphabet Inc. Class A"),
    AMZN("Amazon.com, Inc.");

    private final String companyName;

    TopStockSymbol(String companyName) {
        this.companyName = companyName;
    }

    public String getSymbol() {
        return name();
    }

    public String getCompanyName() {
        return companyName;
    }

    public static List<String> symbols() {
        return Arrays.stream(values()).map(Enum::name).toList();
    }
}
