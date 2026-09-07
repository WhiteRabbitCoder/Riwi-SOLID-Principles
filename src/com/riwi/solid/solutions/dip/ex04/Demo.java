package com.riwi.solid.solutions.dip.ex04;

/**
 * SOLUCIÓN DIP 04 — Logging
 *
 * InventoryService escribía directo a archivo. Ahora depende de "algo que sepa
 * registrar mensajes"; el destino se elige por fuera.
 */
public final class Demo {

    private Demo() {
    }

    public static void run() {
        System.out.println("== DIP 04 — Registro de eventos ==");
        new InventoryService(new FileLogger()).reduceStock("Camiseta");
        new InventoryService(new ConsoleLogger()).reduceStock("Camiseta");
    }

    public static void main(String[] args) {
        run();
    }
}

interface Logger {
    void log(String message);
}

class FileLogger implements Logger {
    @Override public void log(String message) { System.out.println("[archivo] " + message); }
}

class ConsoleLogger implements Logger {
    @Override public void log(String message) { System.out.println("[consola] " + message); }
}

class InventoryService {
    private final Logger logger;

    InventoryService(Logger logger) {
        this.logger = logger;
    }

    void reduceStock(String product) {
        logger.log("Stock reducido para " + product);
    }
}
