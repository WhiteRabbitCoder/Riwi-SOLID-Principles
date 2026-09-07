import adapters.in.console.OrderConsoleAdapter;
import adapters.out.CardPaymentGateway;
import adapters.out.CashPaymentGateway;
import adapters.out.ConsoleNotificationSender;
import adapters.out.InMemoryOrderRepository;
import adapters.out.MapPaymentGatewaySelector;
import application.CreateOrderService;
import java.util.Map;
import ports.in.CreateOrderUseCase;
import ports.out.PaymentGateway;

/**
 * QUÉ ES: el punto de composición (composition root). El único archivo del
 * proyecto que sabe qué implementación concreta se usa para cada puerto, y el
 * único que ejecuta {@code new} sobre infraestructura.
 *
 * POR QUÉ ASÍ:
 *
 * 1. Es el criterio "la composición ocurre en un punto de entrada, no dentro
 *    del caso de uso". Si el ensamblaje estuviera repartido, cambiar de
 *    almacenamiento obligaría a buscar {@code new} por todo el código; aquí
 *    es una línea, en un archivo, a la vista.
 *
 * 2. Fíjate en la dirección de los imports: este archivo conoce a todos, y
 *    nadie lo conoce a él. Es la capa más externa, la que se puede tirar y
 *    reescribir un {@code main}, un test, un contenedor de Spring sin tocar
 *    nada de lo demás.
 *
 * 3. Registrar aquí el mapa de pasarelas es lo que hace real el OCP: sumar
 *    PSE es una clase nueva más una entrada en este mapa; ni el caso de uso
 *    ni el dominio se abren.
 *
 * 4. El efectivo se guarda en una variable porque cumple dos papeles: es un
 *    medio de pago registrado y, además, el medio por defecto para tipos
 *    desconocidos. Esa doble función es una decisión de configuración, y este
 *    es el lugar donde debe verse.
 *
 * 5. La variable se declara como {@link CreateOrderUseCase} y no como
 *    {@code CreateOrderService} para que quede explícito que el adaptador
 *    solo depende del puerto.
 */
public class OrderApplication {
    public static void main(String[] args) {
        PaymentGateway cash = new CashPaymentGateway();
        CreateOrderUseCase createOrder = new CreateOrderService(
                new InMemoryOrderRepository(),
                new MapPaymentGatewaySelector(Map.of("CARD", new CardPaymentGateway(), "CASH", cash), cash),
                new ConsoleNotificationSender());

        new OrderConsoleAdapter(createOrder).run("ana@riwi.io", "Curso SOLID", 2, "CARD");
    }
}
