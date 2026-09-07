package com.riwi.solid.solutions.srp.ex05;

/**
 * SOLUCIÓN SRP 05 — Cuándo NO dividir
 *
 * La solución correcta de este ejercicio es NO refactorizar.
 *
 * Área, perímetro y redimensionar no son razones de cambio distintas: las tres
 * dependen del mismo dato (ancho y alto) y cambian juntas. Separarlas en
 * AreaCalculator, PerimeterCalculator y RectangleResizer daría tres clases que
 * necesitan los mismos datos del rectángulo, sin ganar nada.
 *
 * SRP habla de razones para cambiar, no de cantidad de métodos.
 */
public final class Demo {

    private Demo() {
    }

    public static void run() {
        System.out.println("== SRP 05 — Cuándo NO dividir ==");
        Rectangle rectangle = new Rectangle(5, 4);
        System.out.println("Área: " + rectangle.calculateArea());
        System.out.println("Perímetro: " + rectangle.calculatePerimeter());

        rectangle.resize(10, 2);
        System.out.println("Tras redimensionar -> área: " + rectangle.calculateArea());
        System.out.println("La clase se queda entera: una sola razón de cambio.");
    }

    public static void main(String[] args) {
        run();
    }
}

/** Sin cambios frente al ejercicio: ya cumplía SRP. */
class Rectangle {
    private double width;
    private double height;

    Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    double calculateArea() {
        return width * height;
    }

    double calculatePerimeter() {
        return 2 * width + 2 * height;
    }

    void resize(double width, double height) {
        this.width = width;
        this.height = height;
    }
}
