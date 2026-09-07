package com.riwi.solid.solutions.srp.ex03;

import java.util.List;

/**
 * SOLUCIÓN SRP 03 — Sistema de reportes
 *
 * Una frase por clase:
 *   StatsCalculator      -> calcula el dato.
 *   HtmlReportRenderer   -> lo convierte en algo presentable.
 *   ReportRepository     -> lo guarda.
 *   ReportMailer         -> lo envía.
 */
public final class Demo {

    private Demo() {
    }

    public static void run() {
        System.out.println("== SRP 03 — Reportes ==");
        new ReportFlow().publish(List.of(4.0, 5.0, 3.0), "jefatura@riwi.co");
    }

    public static void main(String[] args) {
        run();
    }
}

class StatsCalculator {
    double average(List<Double> values) {
        return values.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }
}

class HtmlReportRenderer {
    String render(double average) {
        return "<h1>Average: " + average + "</h1>";
    }
}

class ReportRepository {
    void save(String html) {
        System.out.println("Guardando reporte: " + html);
    }
}

class ReportMailer {
    void send(String email, String html) {
        System.out.println("Enviando reporte a " + email + ": " + html);
    }
}

class ReportFlow {
    private final StatsCalculator calculator = new StatsCalculator();
    private final HtmlReportRenderer renderer = new HtmlReportRenderer();
    private final ReportRepository repository = new ReportRepository();
    private final ReportMailer mailer = new ReportMailer();

    void publish(List<Double> values, String email) {
        String html = renderer.render(calculator.average(values));
        repository.save(html);
        mailer.send(email, html);
    }
}
