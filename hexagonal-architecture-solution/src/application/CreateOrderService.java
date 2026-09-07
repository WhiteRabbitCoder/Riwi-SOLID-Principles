package application;

import domain.Order;
import ports.in.CreateOrderCommand;
import ports.in.CreateOrderUseCase;
import ports.out.NotificationSender;
import ports.out.OrderRepository;
import ports.out.PaymentGatewaySelector;

/**
 * QUÉ ES: el caso de uso "crear pedido". Es el reemplazo directo del
 * {@code OrderService} original, pero reducido a lo único que le corresponde:
 * decidir el ORDEN de los pasos.
 *
 * POR QUÉ ASÍ:
 *
 * 1. Su método principal se lee como el enunciado del negocio: armar el
 *    pedido, cobrarlo, guardarlo, notificar. Cuatro líneas. Todo el "cómo"
 *    qué base de datos, qué pasarela, qué canal está detrás de puertos, así
 *    que este archivo casi nunca cambia. Cambia solo si cambian los pasos.
 *
 * 2. Implementa {@link CreateOrderUseCase} para que los adaptadores de
 *    entrada dependan de la interfaz y no de esta clase (DIP hacia adentro).
 *
 * 3. Recibe sus tres colaboradores por constructor y no los construye. Un
 *    {@code new InMemoryOrderRepository()} aquí ataría el núcleo a la
 *    infraestructura y haría imposible probarlo con dobles. Por eso el
 *    ensamblaje ocurre en {@code OrderApplication} y no aquí (DIP hacia
 *    afuera).
 *
 * 4. No tiene un solo {@code System.out}. Esa ausencia es lo que permite
 *    ejecutarlo desde un test, desde una API o desde la consola sin
 *    modificarlo, y es el criterio de éxito central de la actividad.
 *
 * 5. No atrapa {@link domain.InvalidOrderException}: si el pedido es
 *    inválido, el flujo se detiene solo y nunca se cobra ni se guarda nada.
 *    Traducir ese error a un mensaje es tarea del adaptador de entrada, que
 *    es quien sabe si al otro lado hay una terminal o un cliente HTTP.
 */
public class CreateOrderService implements CreateOrderUseCase {
    private final OrderRepository repository;
    private final PaymentGatewaySelector paymentGateways;
    private final NotificationSender notifications;

    /**
     * Dependencias explícitas y {@code final}: la firma declara exactamente
     * qué necesita este caso de uso para funcionar. Si algún día pide seis
     * colaboradores, el constructor será la primera señal de que hay más de
     * un caso de uso escondido aquí.
     */
    public CreateOrderService(OrderRepository repository,
                              PaymentGatewaySelector paymentGateways,
                              NotificationSender notifications) {
        this.repository = repository;
        this.paymentGateways = paymentGateways;
        this.notifications = notifications;
    }

    /**
     * El flujo completo. El orden importa y es la única regla que aporta esta
     * clase: se cobra antes de guardar, y solo se notifica lo que ya quedó
     * registrado.
     */
    @Override
    public Order createOrder(CreateOrderCommand command) {
        Order order = Order.of(command.customerEmail(), command.product(), command.quantity());
        paymentGateways.select(command.paymentType()).charge(order);
        repository.save(order);
        notifications.notifyOrderCreated(order);
        return order;
    }
}
