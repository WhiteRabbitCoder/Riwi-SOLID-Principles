package com.riwi.solid.solutions.lsp.ex05;

/**
 * SOLUCIÓN LSP 05 — Archivos de solo lectura
 *
 * El error era heredar al revés: ReadOnlyFile extendía WritableFile y tenía que
 * rechazar la escritura. Leer es lo común; escribir es la capacidad extra.
 */
public final class Demo {

    private Demo() {
    }

    public static void run() {
        System.out.println("== LSP 05 — Archivos ==");
        ReadOnlyFile manual = new ReadOnlyFile("Manual de la tienda");
        EditableFile draft = new EditableFile();

        draft.write("Borrador del pedido");

        // Leer funciona con cualquiera de los dos.
        show(manual);
        show(draft);
    }

    static void show(ReadableFile file) {
        System.out.println("Contenido: " + file.read());
    }

    public static void main(String[] args) {
        run();
    }
}

interface ReadableFile {
    String read();
}

interface WritableFile extends ReadableFile {
    void write(String content);
}

class ReadOnlyFile implements ReadableFile {
    private final String content;

    ReadOnlyFile(String content) {
        this.content = content;
    }

    @Override public String read() { return content; }
}

class EditableFile implements WritableFile {
    private String content = "";

    @Override public String read() { return content; }
    @Override public void write(String content) { this.content = content; }
}
