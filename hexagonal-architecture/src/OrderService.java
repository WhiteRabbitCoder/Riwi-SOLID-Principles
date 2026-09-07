import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Código inicial deliberadamente mal diseñado para la actividad.
 *
 * Esta clase mezcla reglas de negocio, decisiones de pago, persistencia,
 * notificación y presentación. El objetivo es migrarla sin cambiar el flujo
 * observable de la aplicación.
 */
public class OrderService {
    private final List<String> orderDatabase = new ArrayList<>();

    public void createOrder(String customerEmail, String product, int quantity, String paymentType) {
        if (customerEmail == null || customerEmail.isBlank()) {
            System.out.println("El correo del cliente es obligatorio.");
            return;
        }

        if (quantity <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
            return;
        }

        double unitPrice = "Curso SOLID".equals(product) ? 80_000 : 50_000;
        double total = unitPrice * quantity;
        String order = "Pedido=" + product + " | cantidad=" + quantity + " | total=" + total;

        if ("CARD".equals(paymentType)) {
            System.out.println("Cobro con tarjeta aprobado por $" + total);
        } else {
            System.out.println("Cobro en efectivo registrado por $" + total);
        }

        orderDatabase.add(LocalDateTime.now() + " | " + order);
        System.out.println("Pedido guardado en memoria.");
        System.out.println("EMAIL -> Compra confirmada para " + customerEmail);
        System.out.println("Pedido creado: " + order);
    }
}
