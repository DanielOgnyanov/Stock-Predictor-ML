package com.daniel.stockpredictorml.models.enums;

import java.util.Arrays;
import java.util.List;

public enum TopStockSymbol {

    AAPL("Apple Inc."),
    MSFT("Microsoft Corporation"),
    NVDA("NVIDIA Corporation"),
    GOOGL("Alphabet Inc. Class A"),
    AMZN("Amazon.com, Inc."),
    META("Meta Platforms, Inc."),
    AVGO("Broadcom Inc."),
    TSM("Taiwan Semiconductor Mfg."),
    BRK_B("Berkshire Hathaway Inc. Class B"),
    LLY("Eli Lilly and Company"),
    JPM("JPMorgan Chase & Co."),
    V("Visa Inc."),
    UNH("UnitedHealth Group Incorporated"),
    XOM("Exxon Mobil Corporation"),
    WMT("Walmart Inc."),
    MA("Mastercard Incorporated"),
    PG("Procter & Gamble Company"),
    NVO("Novo Nordisk A/S"),
    ORCL("Oracle Corporation"),
    COST("Costco Wholesale Corporation"),
    JNJ("Johnson & Johnson"),
    HD("The Home Depot, Inc."),
    ASML("ASML Holding N.V."),
    BAC("Bank of America Corporation"),
    KO("The Coca-Cola Company"),
    PEP("PepsiCo, Inc."),
    MRK("Merck & Co., Inc."),
    TMO("Thermo Fisher Scientific Inc."),
    ABBV("AbbVie Inc."),
    ADBE("Adobe Inc."),
    CRM("Salesforce, Inc."),
    MCD("McDonald’s Corporation"),
    NFLX("Netflix, Inc."),
    CSCO("Cisco Systems, Inc."),
    LIN("Linde plc"),
    AMD("Advanced Micro Devices, Inc."),
    NKE("NIKE, Inc."),
    QCOM("QUALCOMM Incorporated"),
    ACN("Accenture plc"),
    CMCSA("Comcast Corporation"),
    TXN("Texas Instruments Incorporated"),
    IBM("International Business Machines Corporation"),
    CAT("Caterpillar Inc."),
    AMAT("Applied Materials, Inc."),
    GE("GE Aerospace (General Electric Company)"),
    DHR("Danaher Corporation"),
    PM("Philip Morris International Inc."),
    UPS("United Parcel Service, Inc."),
    DIS("The Walt Disney Company"),
    BA("The Boeing Company");

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
