package com.riwi.solid.solutions.dip.ex01;

/**
 * SOLUCIÓN DIP 01 — Reportes y base de datos
 *
 * ReportService creaba su propia MySqlDatabase, así que quedaba amarrado a
 * MySQL. Ahora depende de un contrato y recibe la implementación desde afuera.
 */
public final class Demo {

    private Demo() {
    }

    public static void run() {
        System.out.println("== DIP 01 — Reportes ==");
        new ReportService(new MySqlStorage()).generateAndSave();
        // Cambiar la tecnología no toca ReportService.
        new ReportService(new PostgresStorage()).generateAndSave();
    }

    public static void main(String[] args) {
        run();
    }
}

interface ReportStorage {
    void save(String report);
}

class MySqlStorage implements ReportStorage {
    @Override public void save(String report) { System.out.println("MySQL guarda: " + report); }
}

class PostgresStorage implements ReportStorage {
    @Override public void save(String report) { System.out.println("PostgreSQL guarda: " + report); }
}

class ReportService {
    private final ReportStorage storage;

    ReportService(ReportStorage storage) {
        this.storage = storage;
    }

    void generateAndSave() {
        storage.save("Reporte mensual");
    }
}
