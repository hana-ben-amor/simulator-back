package com.example.simulator.data;
import java.util.Map;

public class CityRepository {
    public static final Map<String, CityData> citySlug = Map.ofEntries(
            Map.entry("PARIS",        new CityData("paris-75000", 29.50, "fallback")),
            Map.entry("LYON",         new CityData("lyon-69000", 19.80, "fallback")),
            Map.entry("MARSEILLE",    new CityData("marseille-13000", 14.20, "fallback")),
            Map.entry("TOULOUSE",     new CityData("toulouse-31000", 13.90, "fallback")),
            Map.entry("BORDEAUX",     new CityData("bordeaux-33000", 15.80, "fallback")),
            Map.entry("LILLE",        new CityData("lille-59000", 14.70, "fallback")),
            Map.entry("NANTES",       new CityData("nantes-44000", 14.90, "fallback")),
            Map.entry("STRASBOURG",   new CityData("strasbourg-67000", 13.50, "fallback")),
            Map.entry("MONTPELLIER",  new CityData("montpellier-34000", 15.20, "fallback")),
            Map.entry("RENNES",       new CityData("rennes-35000", 14.80, "fallback")),
            Map.entry("GRENOBLE",     new CityData("grenoble-38000", 12.90, "fallback")),
            Map.entry("DIJON",        new CityData("dijon-21000", 11.50, "fallback")),
            Map.entry("CLERMONT-FERRAND", new CityData("clermont-ferrand-63000", 11.00, "fallback")),
            Map.entry("ANGERS",       new CityData("angers-49000", 12.80, "fallback")),
            Map.entry("TOURS",        new CityData("tours-37000", 12.60, "fallback")),
            Map.entry("ORLÉANS",      new CityData("orleans-45000", 12.40, "fallback")),
            Map.entry("AMIENS",       new CityData("amiens-80000", 11.70, "fallback")),
            Map.entry("METZ",         new CityData("metz-57000", 11.80, "fallback")),
            Map.entry("NANCY",        new CityData("nancy-54000", 12.10, "fallback")),
            Map.entry("REIMS",        new CityData("reims-51100", 13.20, "fallback")),
            Map.entry("LE HAVRE",     new CityData("le-havre-76600", 11.40, "fallback")),
            Map.entry("SAINT-ÉTIENNE",new CityData("saint-etienne-42000", 9.80, "fallback")),
            Map.entry("ROUEN",        new CityData("rouen-76000", 12.20, "fallback")),
            Map.entry("CAEN",         new CityData("caen-14000", 12.10, "fallback")),
            Map.entry("BREST",        new CityData("brest-29200", 11.30, "fallback")),
            Map.entry("PERPIGNAN",    new CityData("perpignan-66000", 10.50, "fallback")),
            Map.entry("BESANÇON",     new CityData("besancon-25000", 11.20, "fallback")),
            Map.entry("POITIERS",     new CityData("poitiers-86000", 11.00, "fallback")),
            Map.entry("PAU",          new CityData("pau-64000", 10.80, "fallback")),
            Map.entry("TOULON",       new CityData("toulon-83000", 12.50, "fallback"))
    );
}
