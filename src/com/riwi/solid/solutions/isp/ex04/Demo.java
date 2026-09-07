package com.riwi.solid.solutions.isp.ex04;

/**
 * SOLUCIÓN ISP 04 — Almacenamiento en nube
 *
 * CloudStorage exigía leer, escribir y borrar. Un archivo público solo se lee.
 * Cada capacidad se firma por separado y nadie lanza excepciones.
 */
public final class Demo {

    private Demo() {
    }

    public static void run() {
        System.out.println("== ISP 04 — Almacenamiento en nube ==");
        PublicArchive archive = new PublicArchive();
        PrivateBucket bucket = new PrivateBucket();

        // Quien solo consulta acepta ambos.
        show(archive, "manual.pdf");
        show(bucket, "contrato.pdf");

        // Escribir y borrar se le piden solo a quien lo promete.
        bucket.write("contrato.pdf", "versión 2");
        bucket.delete("contrato.pdf");
    }

    static void show(ReadableStorage storage, String key) {
        System.out.println(key + " -> " + storage.read(key));
    }

    public static void main(String[] args) {
        run();
    }
}

interface ReadableStorage {
    String read(String key);
}

interface WritableStorage {
    void write(String key, String value);
}

interface DeletableStorage {
    void delete(String key);
}

class PublicArchive implements ReadableStorage {
    @Override public String read(String key) { return "contenido público"; }
}

class PrivateBucket implements ReadableStorage, WritableStorage, DeletableStorage {
    @Override public String read(String key) { return "contenido privado"; }
    @Override public void write(String key, String value) { System.out.println("Guardado " + key + " = " + value); }
    @Override public void delete(String key) { System.out.println("Eliminado " + key); }
}
