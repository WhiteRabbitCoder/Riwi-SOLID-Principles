/**
 * Punto de entrada del proyecto inicial. La aplicación funciona, pero el
 * servicio que invoca conoce demasiados detalles para un caso de uso.
 */
public class OrderConsoleApp {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        orderService.createOrder("ana@riwi.io", "Curso SOLID", 2, "CARD");
    }
}
