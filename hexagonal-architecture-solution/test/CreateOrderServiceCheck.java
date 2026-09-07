import application.CreateOrderService;
import domain.InvalidOrderException;
import domain.Order;
import java.util.ArrayList;
import java.util.List;
import ports.in.CreateOrderCommand;
import ports.out.NotificationSender;
import ports.out.OrderRepository;
import ports.out.PaymentGateway;
import ports.out.PaymentGatewaySelector;

/**
 * QUÉ ES: la prueba del caso de uso y, al mismo tiempo, la EVIDENCIA de que
 * la migración funcionó. Ejercita {@link CreateOrderService} completo sin
 * {@code Scanner}, sin {@code System.out} y sin almacenamiento concreto.
 *
 * POR QUÉ ASÍ:
 *
 * 1. Este archivo no habría podido existir con el código original: aquel
 *    {@code OrderService} imprimía y guardaba por su cuenta, así que
 *    verificarlo exigía leer la salida de la terminal. Que hoy se pueda
 *    escribir es la prueba de que el núcleo quedó aislado.
 *
 * 2. Los dobles son clases propias de tres líneas y no un framework de mocks,
 *    porque los puertos tienen un solo método: implementarlos a mano es más
 *    corto que configurar una librería y no agrega dependencias al proyecto.
 *
 * 3. Los dobles guardan lo que reciben en una lista en vez de solo contar
 *    llamadas, para poder afirmar que se guardó el pedido correcto y no solo
 *    que "se llamó a alguien".
 *
 * 4. Usa {@code assert} de Java y no JUnit para que la actividad corra con el
 *    JDK pelado, igual que el resto del proyecto. Requiere el flag
 *    {@code -ea}: sin él las aserciones se desactivan y la prueba pasaría
 *    siempre.
 *
 * Ejecutar: {@code java -cp out -ea CreateOrderServiceCheck}
 */
public class CreateOrderServiceCheck {

    /**
     * Repositorio falso: reemplaza la persistencia. Guarda los pedidos para
     * poder afirmar QUÉ se almacenó, y demuestra que el caso de uso funciona
     * sin ninguna base de datos detrás.
     */
    static class FakeRepository implements OrderRepository {
        final List<Order> saved = new ArrayList<>();

        public void save(Order order) {
            saved.add(order);
        }
    }

    /**
     * Doble que hace de pasarela y de selector a la vez: siempre se elige a sí
     * mismo. Se combinan en una sola clase porque a esta prueba no le importa
     * cómo se resuelve el tipo de pago —eso se decide en el punto de
     * composición—, sino que el pedido efectivamente se cobre.
     */
    static class FakeGateway implements PaymentGateway, PaymentGatewaySelector {
        final List<Order> charged = new ArrayList<>();

        public void charge(Order order) {
            charged.add(order);
        }

        public PaymentGateway select(String paymentType) {
            return this;
        }
    }

    /**
     * Notificador falso: captura los avisos en vez de enviarlos. Es la
     * contraparte de prueba de {@code ConsoleNotificationSender} y demuestra
     * que el canal de notificación es intercambiable.
     */
    static class FakeNotifications implements NotificationSender {
        final List<Order> sent = new ArrayList<>();

        public void notifyOrderCreated(Order order) {
            sent.add(order);
        }
    }

    public static void main(String[] args) {
        // Composición de prueba: mismo caso de uso, otras implementaciones.
        // Es el mismo gesto de OrderApplication, y por eso es posible.
        FakeRepository repository = new FakeRepository();
        FakeGateway gateway = new FakeGateway();
        FakeNotifications notifications = new FakeNotifications();
        CreateOrderService service = new CreateOrderService(repository, gateway, notifications);

        // Camino feliz: precio de tarifa especial y los tres efectos ocurren.
        Order order = service.createOrder(new CreateOrderCommand("ana@riwi.io", "Curso SOLID", 2, "CARD"));

        assert order.total() == 160_000 : "precio del Curso SOLID incorrecto: " + order.total();
        assert repository.saved.size() == 1 : "el pedido no se guardó";
        assert gateway.charged.size() == 1 : "el pedido no se cobró";
        assert notifications.sent.size() == 1 : "no se notificó al cliente";

        // Producto sin tarifa propia: verifica el precio por defecto y que un
        // medio de pago distinto recorra exactamente el mismo flujo.
        Order other = service.createOrder(new CreateOrderCommand("ana@riwi.io", "Camiseta", 1, "CASH"));
        assert other.total() == 50_000 : "precio por defecto incorrecto: " + other.total();

        // Reglas del dominio: un pedido inválido debe cortar el flujo antes de
        // cobrar, guardar o notificar.
        assertRejects(service, new CreateOrderCommand("", "Curso SOLID", 1, "CARD"));
        assertRejects(service, new CreateOrderCommand("ana@riwi.io", "Curso SOLID", 0, "CARD"));

        // Siguen siendo 2: la excepción se lanzó antes de llegar al repositorio.
        assert repository.saved.size() == 2 : "se guardó un pedido inválido";

        System.out.println("OK: todas las verificaciones pasaron.");
    }

    /**
     * Afirma que el caso de uso rechaza un comando inválido.
     *
     * Está extraído en un método porque el patrón "esperaba una excepción y no
     * llegó" es fácil de escribir mal: sin el {@code throw} de adentro, un
     * pedido aceptado por error pasaría desapercibido y la prueba mentiría.
     */
    private static void assertRejects(CreateOrderService service, CreateOrderCommand command) {
        try {
            service.createOrder(command);
            throw new AssertionError("se aceptó un pedido inválido: " + command);
        } catch (InvalidOrderException expected) {
            // esperado: el dominio hizo su trabajo
        }
    }
}
