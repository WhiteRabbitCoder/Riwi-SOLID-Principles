# 15 — Ejercicios DIP

## 1. Reportes y base de datos
Ruta: `exercises/dip/ex01`

`ReportService` crea directamente una base de datos concreta. Haz que la lógica dependa de un contrato.

## 2. Notificaciones
Ruta: `exercises/dip/ex02`

Un servicio de registro crea directamente un `EmailSender`. Permite cambiar el canal sin modificar el caso de uso.

## 3. Órdenes y pagos
Ruta: `exercises/dip/ex03`

`OrderService` está amarrado a pagos con tarjeta. Introduce una dependencia apropiada.

## 4. Logging
Ruta: `exercises/dip/ex04`

Una clase de negocio escribe directamente a un archivo. Permite sustituir el destino de logs.

## 5. Clima externo
Ruta: `exercises/dip/ex05`

La lógica de recomendaciones depende directamente de un proveedor meteorológico externo. Aísla ese detalle.
