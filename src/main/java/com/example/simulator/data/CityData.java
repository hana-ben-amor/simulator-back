package com.example.simulator.data;

public class CityData {
    public final String slug;
    public final double rentPerM2;
    public final String source;

    public CityData(String slug, double rentPerM2, String source) {
        this.slug = slug;
        this.rentPerM2 = rentPerM2;
        this.source = source;
    }
}
