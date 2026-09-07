# 14 — Dependency Inversion Principle (DIP)

## Idea central

> Los módulos de alto nivel no deberían depender directamente de módulos de bajo nivel. Ambos deberían depender de abstracciones.

Además:

> Las abstracciones no deberían depender de los detalles; los detalles deberían depender de las abstracciones.

## Ejemplo mental

`OrderService` representa reglas del negocio. No debería necesitar saber cómo MySQL abre una conexión o cómo Gmail envía un mensaje.

En lugar de crear directamente:

```java
new MySqlOrderRepository();
```

puede recibir un contrato como:

```java
OrderRepository repository;
```

## Inyección de dependencias

Una manera sencilla de aplicar DIP es entregar las dependencias por constructor.

No necesitas Spring para aprender ni aplicar este principio.

## Pregunta de diagnóstico

> ¿La lógica central del negocio está amarrada a una tecnología o implementación concreta?

## Señales frecuentes

- `new MySql...` dentro de servicios de negocio.
- `new Email...` dentro del caso de uso.
- Imposibilidad de probar una clase sin conectarse a recursos externos.
- Cambiar una tecnología obliga a editar reglas de negocio.
