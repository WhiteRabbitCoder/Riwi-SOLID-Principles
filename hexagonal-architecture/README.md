# Actividad — Migración a arquitectura hexagonal

Este es un proyecto Java independiente y deliberadamente acoplado. El código inicial funciona: crea un pedido, calcula su precio, simula el cobro, guarda un registro y notifica al cliente. El problema es que una sola clase conoce todos esos detalles.

## Punto de partida

```text
src/
├── OrderConsoleApp.java
└── OrderService.java
```

`OrderService` contiene reglas de negocio, persistencia simulada, selección de pago, notificación y mensajes de consola. No lo corrijas agregando condiciones ni moviendo métodos al azar: primero identifica los límites.

## Ejecutar el proyecto inicial

Desde esta carpeta:

```powershell
$sourceFiles = Get-ChildItem src -Recurse -Filter *.java | ForEach-Object FullName
javac --release 17 -encoding UTF-8 -d out $sourceFiles
java -cp out OrderConsoleApp
```

En Linux:

```bash
find src -type f -name '*.java' -print0 | xargs -0 javac --release 17 -encoding UTF-8 -d out
java -cp out OrderConsoleApp
```

## Objetivo de la migración

Lleva el proyecto a un diseño donde el caso de uso no conozca la consola, la forma de guardar pedidos, el proveedor de pagos ni el canal de notificación.

Una posible organización final es:

```text
src/
├── domain/                 # Order y reglas puras
├── application/            # CreateOrderService
├── ports/
│   ├── in/                 # caso de uso expuesto
│   └── out/                # repositorio, pago y notificación
└── adapters/
    ├── in/console/         # interacción con la terminal
    └── out/                # memoria, pago y notificación simulados
```

No copies esta estructura sin pensar. Crea un tipo o carpeta solo cuando ayude a expresar una frontera o una dependencia.

## Criterios de éxito

- El caso de uso se puede probar sin `Scanner`, `System.out` ni almacenamiento concreto.
- Cambiar de notificación por consola a correo no obliga a modificar la lógica de creación del pedido.
- La aplicación conserva su flujo observable.
- Cada adaptador implementa un puerto pequeño que el núcleo realmente necesita.
- La composición de dependencias ocurre en un punto de entrada, no dentro del caso de uso.

Consulta la explicación de [arquitectura hexagonal](../docs/13-arquitectura-hexagonal.md) para conectar estas decisiones con SOLID.
