package ports.in;

/**
 * QUÉ ES: los datos crudos de una solicitud de creación de pedido, tal como
 * llegan desde afuera.
 *
 * POR QUÉ ASÍ:
 *
 * 1. Es un objeto y no cuatro parámetros sueltos para que agregar un dato
 *    (un cupón, una dirección) no cambie la firma del puerto ni obligue a
 *    tocar a todos los que lo implementan o lo llaman.
 *
 * 2. Es distinto de {@link domain.Order} a propósito. El comando es entrada
 *    sin validar y con vocabulario del exterior — nótese {@code paymentType}
 *    como texto —; el {@code Order} es un pedido ya válido con precio
 *    calculado. Mezclarlos metería datos crudos y decisiones de
 *    configuración dentro del dominio.
 *
 * 3. Vive en {@code ports.in} y no en {@code adapters} porque forma parte del
 *    contrato: cualquier adaptador de entrada debe poder construirlo.
 */
public record CreateOrderCommand(String customerEmail, String product, int quantity, String paymentType) {
}
