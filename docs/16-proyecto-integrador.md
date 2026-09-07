# 16 — Proyecto integrador: tienda por consola

## Contexto

Recibes una aplicación de consola que ya funciona. El objetivo NO es agregar un framework ni convertirla en API. El objetivo es mejorar su diseño manteniendo la misma experiencia de usuario.

Clase de entrada:

```text
com.riwi.solid.integrator.StoreConsoleApp
```

## Problemas intencionales

La versión inicial mezcla:

- menú y entrada por consola;
- catálogo;
- carrito;
- descuentos;
- pagos;
- persistencia;
- notificaciones.

También contiene `if/else` por tipo, capacidades no soportadas y dependencias concretas.

## Misión 1 — SRP

Identifica responsabilidades distintas y separa aquellas que tengan razones independientes para cambiar.

No dividas por dividir. Justifica cada clase nueva.

## Misión 2 — OCP

Los métodos de pago, descuentos y canales de notificación son puntos de variación. Permite agregar nuevas alternativas con el menor número posible de modificaciones al código estable.

Prueba agregando una variante nueva.

## Misión 3 — LSP

Revisa contratos que prometen comportamientos que ciertas implementaciones no pueden cumplir. Elimina la necesidad de excepciones por operaciones "no soportadas".

## Misión 4 — ISP

Separa contratos demasiado amplios. Un componente debe depender únicamente de las capacidades que usa.

## Misión 5 — DIP

La lógica de negocio no debería crear directamente sus detalles de persistencia, pago o notificación. Introduce abstracciones e inyección por constructor donde aporte valor.

## Restricciones

- Mantener Java 17+.
- Mantener aplicación de consola.
- No usar Spring ni frameworks de DI.
- No introducir bases de datos reales.
- No cambiar el comportamiento funcional esperado.
- Evitar `switch`/`if` por tipo cuando el polimorfismo sea una mejor representación del dominio.

## Entrega sugerida

1. Diagrama simple del diseño inicial.
2. Lista de problemas encontrados y principio relacionado.
3. Refactor por commits separados: `SRP`, `OCP`, `LSP`, `ISP`, `DIP`.
4. Diagrama final.
5. Breve justificación de las abstracciones introducidas.
