package ports.in;

import domain.Order;

/**
 * QUÉ ES: el puerto de entrada. La lista completa de lo que el mundo exterior
 * le puede pedir a esta aplicación.
 *
 * POR QUÉ ASÍ:
 *
 * 1. Es una interfaz y no la clase de servicio directamente para que los
 *    adaptadores de entrada (consola hoy, un controlador HTTP mañana)
 *    dependan de una intención — "crear un pedido" — y no de una
 *    implementación concreta (DIP).
 *
 * 2. Tiene un solo método porque es un solo caso de uso. Un puerto de entrada
 *    que crece a diez métodos suele ser la señal de que hay varios casos de
 *    uso disfrazados de uno (ISP).
 *
 * 3. Devuelve {@link Order} en vez de {@code void}: quien invoca necesita el
 *    pedido resultante para mostrarlo o serializarlo. Devolver el objeto de
 *    dominio evita que el caso de uso tenga que imprimir para "avisar" qué
 *    pasó, que era justo el problema del código original.
 */
public interface CreateOrderUseCase {
    Order createOrder(CreateOrderCommand command);
}
