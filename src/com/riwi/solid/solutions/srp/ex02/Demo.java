package com.riwi.solid.solutions.srp.ex02;

/**
 * SOLUCIÓN SRP 02 — Registro de usuarios
 *
 * Pregunta del ejercicio: si mañana se cambia MySQL por PostgreSQL, ¿qué clase
 * cambia? Solo UserRepository. El registro y el correo ni se enteran.
 */
public final class Demo {

    private Demo() {
    }

    public static void run() {
        System.out.println("== SRP 02 — Registro de usuarios ==");
        new RegistrationFlow().register("Carlos", "carlos@riwi.co");
    }

    public static void main(String[] args) {
        run();
    }
}

/** Razón de cambio: las reglas del registro. */
class UserRegistrar {
    void register(String name, String email) {
        System.out.println("Registrando a " + name + " con email " + email);
    }
}

/** Razón de cambio: la tecnología de almacenamiento. */
class UserRepository {
    void save(String name, String email) {
        System.out.println("INSERT simulado en MySQL para " + name);
    }
}

/** Razón de cambio: el contenido y el canal del mensaje. */
class WelcomeMailer {
    void sendWelcome(String email) {
        System.out.println("Email de bienvenida enviado a " + email);
    }
}

class RegistrationFlow {
    private final UserRegistrar registrar = new UserRegistrar();
    private final UserRepository repository = new UserRepository();
    private final WelcomeMailer mailer = new WelcomeMailer();

    void register(String name, String email) {
        registrar.register(name, email);
        repository.save(name, email);
        mailer.sendWelcome(email);
    }
}
