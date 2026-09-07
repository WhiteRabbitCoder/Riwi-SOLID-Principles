package com.riwi.solid;

/**
 * Punto de entrada: ejecuta las soluciones de los 25 ejercicios, agrupadas por
 * principio. Cada Demo también tiene su propio main para correrla suelta.
 */
public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        System.out.println("Riwi - SOLID Principles");
        System.out.println("Java: " + Runtime.version().feature());

        section("S — Single Responsibility Principle");
        com.riwi.solid.solutions.srp.ex01.Demo.run();
        com.riwi.solid.solutions.srp.ex02.Demo.run();
        com.riwi.solid.solutions.srp.ex03.Demo.run();
        com.riwi.solid.solutions.srp.ex04.Demo.run();
        com.riwi.solid.solutions.srp.ex05.Demo.run();

        section("O — Open/Closed Principle");
        com.riwi.solid.solutions.ocp.ex01.Demo.run();
        com.riwi.solid.solutions.ocp.ex02.Demo.run();
        com.riwi.solid.solutions.ocp.ex03.Demo.run();
        com.riwi.solid.solutions.ocp.ex04.Demo.run();
        com.riwi.solid.solutions.ocp.ex05.Demo.run();

        section("L — Liskov Substitution Principle");
        com.riwi.solid.solutions.lsp.ex01.Demo.run();
        com.riwi.solid.solutions.lsp.ex02.Demo.run();
        com.riwi.solid.solutions.lsp.ex03.Demo.run();
        com.riwi.solid.solutions.lsp.ex04.Demo.run();
        com.riwi.solid.solutions.lsp.ex05.Demo.run();

        section("I — Interface Segregation Principle");
        com.riwi.solid.solutions.isp.ex01.Demo.run();
        com.riwi.solid.solutions.isp.ex02.Demo.run();
        com.riwi.solid.solutions.isp.ex03.Demo.run();
        com.riwi.solid.solutions.isp.ex04.Demo.run();
        com.riwi.solid.solutions.isp.ex05.Demo.run();

        section("D — Dependency Inversion Principle");
        com.riwi.solid.solutions.dip.ex01.Demo.run();
        com.riwi.solid.solutions.dip.ex02.Demo.run();
        com.riwi.solid.solutions.dip.ex03.Demo.run();
        com.riwi.solid.solutions.dip.ex04.Demo.run();
        com.riwi.solid.solutions.dip.ex05.Demo.run();
    }

    private static void section(String title) {
        System.out.println("\n=======================================");
        System.out.println(title);
        System.out.println("=======================================");
    }
}
