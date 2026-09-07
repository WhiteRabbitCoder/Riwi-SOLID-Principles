package com.riwi.solid.solutions.ocp.ex05;

/**
 * SOLUCIÓN OCP 05 — Ataques de videojuego
 *
 * Cada ataque es una pieza con su propio efecto. Sumar un elemento nuevo al
 * juego no obliga a revisar los ataques que ya existían.
 */
public final class Demo {

    private Demo() {
    }

    public static void run() {
        System.out.println("== OCP 05 — Ataques ==");
        Character hero = new Character("Héroe");
        hero.attackWith(new SwordAttack());
        hero.attackWith(new MagicAttack());
        hero.attackWith(new BowAttack());
        // Elemento agregado sin tocar Character ni los otros ataques.
        hero.attackWith(new IceAttack());
    }

    public static void main(String[] args) {
        run();
    }
}

interface Attack {
    String describe();
}

class SwordAttack implements Attack {
    @Override public String describe() { return "Ataque con espada"; }
}

class MagicAttack implements Attack {
    @Override public String describe() { return "Ataque mágico"; }
}

class BowAttack implements Attack {
    @Override public String describe() { return "Ataque con arco"; }
}

class IceAttack implements Attack {
    @Override public String describe() { return "Ataque de hielo"; }
}

class Character {
    private final String name;

    Character(String name) {
        this.name = name;
    }

    void attackWith(Attack attack) {
        System.out.println(name + ": " + attack.describe());
    }
}
