package adapters.in.console;

import domain.InvalidOrderException;
import domain.Order;
import ports.in.CreateOrderCommand;
import ports.in.CreateOrderUseCase;

/**
 * QUÉ ES: adaptador de entrada. El lado del hexágono por donde ENTRA la
 * solicitud: traduce datos de terminal a un {@link CreateOrderCommand} y
 * traduce de vuelta el resultado a texto para el usuario.
 *
 * POR QUÉ ASÍ:
 *
 * 1. Concentra toda la conversación con la consola del flujo principal. Si
 *    mañana el disparador es un endpoint REST, se escribe otro adaptador al
 *    lado de este y ninguna otra carpeta cambia. Esa sustituibilidad es la
 *    razón de ser de la arquitectura hexagonal.
 *
 * 2. Depende de {@link CreateOrderUseCase}, la interfaz, y no de
 *    {@code CreateOrderService}. Así el borde puede probarse con un caso de
 *    uso falso, y el núcleo puede evolucionar sin arrastrar al adaptador.
 *
 * 3. Aquí es donde {@link InvalidOrderException} se convierte en un mensaje
 *    en pantalla. Es exactamente la responsabilidad que en el código original
 *    estaba dentro del servicio: el dominio decide QUÉ está mal, el adaptador
 *    decide CÓMO se le comunica a este usuario en particular.
 *
 * 4. No recibe {@code Scanner} sino parámetros, porque el proyecto inicial
 *    tampoco leía de teclado. Cuando haga falta entrada interactiva, el
 *    {@code Scanner} entra aquí y en ningún otro lugar.
 */
public class OrderConsoleAdapter {
    private final CreateOrderUseCase createOrder;

    public OrderConsoleAdapter(CreateOrderUseCase createOrder) {
        this.createOrder = createOrder;
    }

    /**
     * Ejecuta el flujo de creación y presenta el resultado. El {@code catch}
     * es angosto a propósito: solo atrapa errores de regla de negocio. Un
     * fallo inesperado debe subir y hacerse visible, no quedar disfrazado de
     * mensaje amable.
     */
    public void run(String customerEmail, String product, int quantity, String paymentType) {
        try {
            Order order = createOrder.createOrder(
                    new CreateOrderCommand(customerEmail, product, quantity, paymentType));
            System.out.println("Pedido creado: " + order.describe());
        } catch (InvalidOrderException e) {
            System.out.println(e.getMessage());
        }
    }
}
