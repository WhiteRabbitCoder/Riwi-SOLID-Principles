package com.riwi.solid.solutions.dip.ex05;

/**
 * SOLUCIÓN DIP 05 — Clima externo
 *
 * La recomendación dependía de un proveedor externo real, así que no había
 * forma de probarla sin internet. Al depender de un contrato, se puede
 * sustituir por una fuente fija y comprobar cada caso.
 */
public final class Demo {

    private Demo() {
    }

    public static void run() {
        System.out.println("== DIP 05 — Recomendación de ropa ==");
        System.out.println(new OutfitRecommendationService(new ExternalWeatherApi()).recommend());
        // Fuente controlada: permite comprobar el caso frío sin depender del clima real.
        System.out.println(new OutfitRecommendationService(new FixedWeather(12)).recommend());
    }

    public static void main(String[] args) {
        run();
    }
}

interface WeatherSource {
    double currentTemperature();
}

class ExternalWeatherApi implements WeatherSource {
    @Override public double currentTemperature() { return 30.0; }
}

class FixedWeather implements WeatherSource {
    private final double temperature;

    FixedWeather(double temperature) {
        this.temperature = temperature;
    }

    @Override public double currentTemperature() { return temperature; }
}

class OutfitRecommendationService {
    private final WeatherSource weather;

    OutfitRecommendationService(WeatherSource weather) {
        this.weather = weather;
    }

    String recommend() {
        return weather.currentTemperature() > 25 ? "Usa ropa ligera" : "Lleva chaqueta";
    }
}
