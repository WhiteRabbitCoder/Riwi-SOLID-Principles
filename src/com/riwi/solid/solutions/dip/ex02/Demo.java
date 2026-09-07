package com.riwi.solid.solutions.dip.ex02;

/**
 * SOLUCIÓN DIP 02 — Notificación al registrarse
 *
 * RegistrationService creaba un EmailSender por dentro. Ahora pide "algo que
 * sepa enviar mensajes" y el canal se decide afuera.
 */
public final class Demo {

    private Demo() {
    }

    public static void run() {
        System.out.println("== DIP 02 — Registro y aviso ==");
        new RegistrationService(new EmailSender()).register("Ana");
        new RegistrationService(new SmsSender()).register("Luis");
    }

    public static void main(String[] args) {
        run();
    }
}

interface MessageSender {
    void send(String message);
}

class EmailSender implements MessageSender {
    @Override public void send(String message) { System.out.println("Email: " + message); }
}

class SmsSender implements MessageSender {
    @Override public void send(String message) { System.out.println("SMS: " + message); }
}

class RegistrationService {
    private final MessageSender sender;

    RegistrationService(MessageSender sender) {
        this.sender = sender;
    }

    void register(String username) {
        System.out.println("Registrado: " + username);
        sender.send("Bienvenido " + username);
    }
}
