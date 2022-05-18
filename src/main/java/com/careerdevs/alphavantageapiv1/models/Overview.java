package com.careerdevs.alphavantageapiv1.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Overview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private long Id;

    @JsonProperty("Symbol")
    @Column(name= "symbol", nullable = false, unique = true)
    private String symbol;

    @JsonProperty("AssetType")
    @Column(name= "asset_type", nullable = false)
    private String assetType;

    @JsonProperty("Name")
    @Column(name= "name", nullable = false, unique = true)
    private String name;

    // Exchange: "NASDAQ"
    @JsonProperty("Exchange")
    @Column(name= "exchange", nullable = false)
    private String exchange;

    // currency: USD
    @JsonProperty("Currency")
    @Column(name= "currency", nullable = false)
    private String currency;

    // country: USA
    @JsonProperty("Country")
    @Column(name= "country", nullable = false)
    private String country;

    // sector: "TECHNOLOGY"
    @JsonProperty("Sector")
    @Column(name= "sector", nullable = false)
    private String sector;

    // industry: Electronic Computers"
    @JsonProperty("Industry")
    @Column(name= "industry", nullable = false)
    private String industry;


    @JsonProperty("MarketCapitalization")
    @Column(name= "market_capitalization", nullable = false)
    private long marketCap;

    @JsonProperty("52WeekHigh")
    @Column(name= "year_high", nullable = false)
    private float yearHigh;

    @JsonProperty("52WeekLow")
    @Column(name= "year_low", nullable = false)
    private float yearLow;

    @JsonProperty("DividendDate")
    @Column(name= "dividend_date", nullable = false)
    private String DividendDate;


    public long getId() {
        return Id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getAssetType() {
        return assetType;
    }

    public String getName() {
        return name;
    }

    public String getExchange() {
        return exchange;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCountry() {
        return country;
    }

    public String getSector() {
        return sector;
    }

    public String getIndustry() {
        return industry;
    }

    public long getMarketCap() {
        return marketCap;
    }

    public float getYearHigh() {
        return yearHigh;
    }

    public float getYearLow() {
        return yearLow;
    }

    public String getDividendDate() {
        return DividendDate;
    }
}
