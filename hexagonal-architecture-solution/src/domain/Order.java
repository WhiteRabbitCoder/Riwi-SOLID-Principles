package domain;

/**
 * QUÉ ES: el pedido como concepto de negocio. Es el corazón del hexágono:
 * los datos de un pedido válido y las reglas que lo hacen válido.
 *
 * POR QUÉ ASÍ:
 *
 * 1. Es un {@code record} porque un pedido es un valor, no una entidad
 *    mutable: una vez creado no debe poder cambiarle la cantidad ni el
 *    precio a espaldas del caso de uso. La inmutabilidad elimina toda una
 *    clase de errores sin escribir código.
 *
 * 2. La validación vive en el constructor compacto, no en el servicio. En el
 *    código original {@code OrderService} validaba e imprimía el error; eso
 *    mezclaba regla de negocio con presentación. Aquí es imposible construir
 *    un {@code Order} inválido: si el objeto existe, cumple las reglas.
 *
 * 3. Lanza {@link InvalidOrderException} en vez de imprimir o devolver
 *    {@code null}. El dominio no sabe si lo está usando una consola, una API
 *    REST o un test; solo señala "esto no es válido" y deja que el adaptador
 *    de entrada decida cómo mostrarlo (DIP: el dominio no depende de la E/S).
 *
 * 4. No importa nada de {@code ports}, {@code application} ni
 *    {@code adapters}. Esa ausencia de imports es la prueba visible de que
 *    el núcleo no depende de nada externo: las flechas apuntan hacia adentro.
 */
public record Order(String customerEmail, String product, int quantity, double unitPrice) {

    /**
     * Constructor compacto: única puerta de entrada al tipo, por lo tanto
     * único lugar donde pueden estar las invariantes. Los mensajes son los
     * mismos del código original para conservar el flujo observable.
     */
    public Order {
        if (customerEmail == null || customerEmail.isBlank()) {
            throw new InvalidOrderException("El correo del cliente es obligatorio.");
        }
        if (quantity <= 0) {
            throw new InvalidOrderException("La cantidad debe ser mayor que cero.");
        }
    }

    /**
     * Fábrica que arma el pedido consultando la tarifa vigente.
     *
     * Existe para que quien crea un pedido no tenga que conocer los precios:
     * el caso de uso pide "un pedido de este producto" y el dominio resuelve
     * cuánto vale. El constructor completo queda disponible para escenarios
     * donde el precio venga de afuera (una promoción, un test).
     */
    public static Order of(String customerEmail, String product, int quantity) {
        return new Order(customerEmail, product, quantity, PriceList.unitPriceOf(product));
    }

    /**
     * Cálculo de negocio, no de presentación. Está aquí y no en el servicio
     * porque el total es una propiedad del pedido: cualquiera que tenga el
     * objeto obtiene el mismo número, sin poder equivocarse en la fórmula.
     */
    public double total() {
        return unitPrice * quantity;
    }

    /**
     * Representación textual del pedido tal como la esperaba el proyecto
     * inicial. Se mantiene en el dominio porque el repositorio y la consola
     * la comparten; duplicarla en dos adaptadores haría que se desincronicen.
     */
    public String describe() {
        return "Pedido=" + product + " | cantidad=" + quantity + " | total=" + total();
    }
}
