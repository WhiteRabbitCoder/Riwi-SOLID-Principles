package com.riwi.solid.solutions.dip.ex03;

/**
 * SOLUCIÓN DIP 03 — Órdenes y pagos
 *
 * OrderService estaba amarrado a CardGateway. Ahora depende de la idea de
 * "pasarela de pago", no de una pasarela concreta.
 */
public final class Demo {

    private Demo() {
    }

    public static void run() {
        System.out.println("== DIP 03 — Órdenes y pagos ==");
        new OrderService(new CardGateway()).checkout(150_000);
        new OrderService(new PseGateway()).checkout(150_000);
    }

    public static void main(String[] args) {
        run();
    }
}

interface PaymentGateway {
    void charge(double amount);
}

class CardGateway implements PaymentGateway {
    @Override public void charge(double amount) { System.out.println("Cobrando tarjeta: " + amount); }
}

class PseGateway implements PaymentGateway {
    @Override public void charge(double amount) { System.out.println("Cobrando por PSE: " + amount); }
}

class OrderService {
    private final PaymentGateway gateway;

    OrderService(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    void checkout(double total) {
        gateway.charge(total);
    }
}
