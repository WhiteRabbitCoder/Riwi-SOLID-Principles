package com.riwi.solid.solutions.srp.ex04;

import java.util.List;

/**
 * SOLUCIÓN SRP 04 — Tienda y pedidos
 *
 * Cinco responsabilidades distintas se convierten en cinco piezas, y el
 * coordinador se limita a encadenarlas. Fíjate en que ninguna pieza sabe
 * de las demás.
 */
public final class Demo {

    private Demo() {
    }

    public static void run() {
        System.out.println("== SRP 04 — Pedidos ==");
        new PurchaseFlow().buy(List.of(20_000.0, 35_000.0), "cliente@riwi.co");
    }

    public static void main(String[] args) {
        run();
    }
}

class PriceCalculator {
    double total(List<Double> prices) {
        return prices.stream().mapToDouble(Double::doubleValue).sum();
    }
}

class StockValidator {
    boolean hasStock() {
        System.out.println("Validando stock...");
        return true;
    }
}

class OrderRepository {
    void save(double total) {
        System.out.println("Pedido guardado con total: " + total);
    }
}

class ConfirmationMailer {
    void send(String email) {
        System.out.println("Confirmación enviada a: " + email);
    }
}

class InvoicePrinter {
    void print(double total) {
        System.out.println("FACTURA - TOTAL: " + total);
    }
}

class PurchaseFlow {
    private final PriceCalculator calculator = new PriceCalculator();
    private final StockValidator stock = new StockValidator();
    private final OrderRepository repository = new OrderRepository();
    private final ConfirmationMailer mailer = new ConfirmationMailer();
    private final InvoicePrinter printer = new InvoicePrinter();

    void buy(List<Double> prices, String email) {
        if (!stock.hasStock()) {
            System.out.println("Sin stock disponible.");
            return;
        }
        double total = calculator.total(prices);
        repository.save(total);
        mailer.send(email);
        printer.print(total);
    }
}
