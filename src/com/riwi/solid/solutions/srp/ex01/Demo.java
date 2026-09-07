package com.riwi.solid.solutions.srp.ex01;

/**
 * SOLUCIÓN SRP 01 — Identificar responsabilidades
 *
 * UserManager creaba, guardaba y enviaba correo. Tres razones distintas para
 * cambiar la misma clase. Cada una vive ahora en su propia pieza y un
 * coordinador arma el flujo.
 */
public final class Demo {

    private Demo() {
    }

    public static void run() {
        System.out.println("== SRP 01 — Alta de usuarios ==");
        new UserOnboarding().onboard("Ana");
    }

    public static void main(String[] args) {
        run();
    }
}

/** Razón de cambio: las reglas de creación de un usuario. */
class UserCreator {
    String create(String name) {
        System.out.println("Usuario creado: " + name);
        return name;
    }
}

/** Razón de cambio: dónde se guardan los usuarios. */
class UserRepository {
    void save(String name) {
        System.out.println("Guardando usuario: " + name);
    }
}

/** Razón de cambio: cómo y qué se le comunica al usuario. */
class WelcomeMailer {
    void sendWelcome(String name) {
        System.out.println("Enviando correo de bienvenida a: " + name);
    }
}

/** No agrega reglas: solo ordena los pasos. */
class UserOnboarding {
    private final UserCreator creator = new UserCreator();
    private final UserRepository repository = new UserRepository();
    private final WelcomeMailer mailer = new WelcomeMailer();

    void onboard(String name) {
        String user = creator.create(name);
        repository.save(user);
        mailer.sendWelcome(user);
    }
}
