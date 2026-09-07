package com.riwi.solid.solutions.lsp.ex04;

/**
 * SOLUCIÓN LSP 04 — Pagos y reembolsos
 *
 * Payment obligaba a todo pago a devolver dinero. El efectivo no puede hacerlo
 * por el mismo canal, así que el reembolso deja de ser parte de "ser un pago".
 */
public final class Demo {

    private Demo() {
    }

    public static void run() {
        System.out.println("== LSP 04 — Pagos y reembolsos ==");
        CardPayment card = new CardPayment();
        CashPayment cash = new CashPayment();

        // Cobrar vale para cualquier medio.
        charge(card, 80_000);
        charge(cash, 25_000);

        // Devolver solo se le pide a quien lo prometió.
        giveBack(card, 80_000);
    }

    static void charge(Payment payment, double amount) {
        payment.pay(amount);
    }

    static void giveBack(Refundable payment, double amount) {
        payment.refund(amount);
    }

    public static void main(String[] args) {
        run();
    }
}

interface Payment {
    void pay(double amount);
}

interface Refundable {
    void refund(double amount);
}

class CardPayment implements Payment, Refundable {
    @Override public void pay(double amount) { System.out.println("Pago con tarjeta: " + amount); }
    @Override public void refund(double amount) { System.out.println("Reembolso a tarjeta: " + amount); }
}

/** No implementa Refundable: el reembolso digital nunca se prometió. */
class CashPayment implements Payment {
    @Override public void pay(double amount) { System.out.println("Pago en efectivo: " + amount); }
}
