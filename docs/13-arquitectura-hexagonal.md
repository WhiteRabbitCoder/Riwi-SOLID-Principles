# 13 — Arquitectura hexagonal: límites antes que tecnología

## Por qué hablar de arquitectura antes de DIP

SOLID ayuda a diseñar responsabilidades y dependencias dentro del código. La arquitectura decide dónde viven esas responsabilidades y en qué dirección pueden depender unas de otras.

Sin límites claros, es fácil que un caso de uso conozca la consola, una base de datos, un cliente HTTP y una librería de correo al mismo tiempo. DIP se vuelve entonces una interfaz aislada, no una decisión de diseño. La arquitectura hexagonal da un lugar concreto a esa inversión de dependencias.

## Idea central

La aplicación contiene las reglas que aportan valor al negocio. Todo lo externo —una consola, una base de datos, un API de pagos o un correo— se conecta desde afuera.

```text
Adaptador de entrada        Núcleo de aplicación        Adaptador de salida
consola / HTTP       ->     caso de uso          ->     memoria / BD / correo
                                   |
                                puertos
```

Un **puerto de entrada** expresa una acción que la aplicación ofrece, por ejemplo `CreateOrderUseCase`. Un **puerto de salida** expresa una necesidad del núcleo, por ejemplo `OrderRepository` o `PaymentGateway`. Los adaptadores implementan esos puertos para una tecnología concreta.

La forma del diagrama importa menos que la dirección de las dependencias: los detalles dependen del núcleo; el núcleo no importa detalles.

## Cómo se alinea con SOLID

| Principio | Aporte dentro de una arquitectura hexagonal |
| --- | --- |
| SRP | Cada adaptador, caso de uso y objeto de dominio tiene una razón de cambio más clara. |
| OCP | Se puede agregar otro adaptador —por ejemplo, correo SMS o repositorio SQL— sin reescribir el caso de uso. |
| LSP | Cualquier implementación de un puerto debe respetar el comportamiento que la aplicación espera. |
| ISP | Los puertos son pequeños y representan necesidades concretas, no interfaces técnicas gigantes. |
| DIP | El núcleo declara las abstracciones que necesita y los adaptadores dependen de ellas. |

No es una receta para crear muchas carpetas o interfaces. Si una parte no cambia, una abstracción adicional puede ser ruido. La arquitectura sirve cuando protege una decisión importante: que la lógica de negocio pueda evolucionar y probarse sin quedar amarrada a sus detalles externos.

## Actividad: migrar una aplicación acoplada

Abre [hexagonal-architecture](../hexagonal-architecture/README.md). El proyecto funciona, pero `OrderService` calcula precios, decide pagos, guarda pedidos, notifica clientes y presenta mensajes por consola.

La meta es migrarlo de forma incremental:

1. Identifica qué regla pertenece al dominio y qué parte es detalle externo.
2. Define el caso de uso que orquesta la creación del pedido.
3. Declara únicamente los puertos de salida que ese caso de uso necesita.
4. Mueve la consola, la persistencia en memoria y las notificaciones a adaptadores.
5. Conecta las implementaciones desde el punto de entrada y conserva el comportamiento observable.

El ejercicio no pide usar Spring, una base de datos ni un framework de inyección. Una implementación en memoria y una consola son suficientes para comprobar las fronteras.

## Preguntas de cierre

- ¿Qué puede cambiar sin obligar a modificar el caso de uso?
- ¿Cuál dependencia apunta en la dirección equivocada?
- ¿Qué puerto representa una necesidad real de la aplicación?
- ¿Qué parte puede probarse sin consola, archivos, red o base de datos?
