package com.riwi.solid.solutions.ocp.ex04;

/**
 * SOLUCIÓN OCP 04 — Cálculo de envíos
 *
 * La fórmula es lo que varía, así que la fórmula es lo que se vuelve pieza.
 * ShippingCalculator solo pide un precio; no conoce ninguna tarifa.
 */
public final class Demo {

    private Demo() {
    }

    public static void run() {
        System.out.println("== OCP 04 — Envíos ==");
        ShippingCalculator calculator = new ShippingCalculator();
        System.out.println("Estándar: " + calculator.quote(new StandardShipping(), 2));
        System.out.println("Express:  " + calculator.quote(new ExpressShipping(), 2));
        // Modalidad agregada sin tocar ShippingCalculator.
        System.out.println("Same Day: " + calculator.quote(new SameDayShipping(), 2));
    }

    public static void main(String[] args) {
        run();
    }
}

interface ShippingRate {
    double costFor(double weightInKg);
}

class StandardShipping implements ShippingRate {
    @Override public double costFor(double weightInKg) { return weightInKg * 3_000; }
}

class ExpressShipping implements ShippingRate {
    @Override public double costFor(double weightInKg) { return weightInKg * 7_000; }
}

class SameDayShipping implements ShippingRate {
    @Override public double costFor(double weightInKg) { return weightInKg * 12_000 + 5_000; }
}

class ShippingCalculator {
    double quote(ShippingRate rate, double weightInKg) {
        return rate.costFor(weightInKg);
    }
}
