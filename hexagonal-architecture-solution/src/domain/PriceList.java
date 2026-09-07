package domain;

/**
 * QUÉ ES: la tarifa. Responde una sola pregunta: cuánto cuesta una unidad de
 * un producto.
 *
 * POR QUÉ ASÍ:
 *
 * 1. Es un archivo aparte porque el precio es la regla que más cambia. En el
 *    código original el {@code 80_000} estaba incrustado en medio del método
 *    que también cobraba y notificaba: subir el precio obligaba a abrir la
 *    clase que hacía todo. Ahora el cambio es local (SRP: una razón para
 *    cambiar por clase).
 *
 * 2. Es {@code final} con constructor privado y métodos estáticos porque no
 *    tiene estado ni identidad; instanciarla no aportaría nada y sí invitaría
 *    a inyectarla sin necesidad.
 *
 * 3. Sigue siendo un {@code if} y no una jerarquía de estrategias a
 *    propósito: con dos precios, una interfaz {@code PricingPolicy} sería
 *    ceremonia sin beneficio. Cuando aparezcan descuentos, promociones o
 *    precios por cliente, este es el único archivo que hay que abrir para
 *    convertirlo en un puerto.
 */
public final class PriceList {
    private static final double SOLID_COURSE_PRICE = 80_000;
    private static final double DEFAULT_PRICE = 50_000;

    /** Clase de utilidad: no se instancia. */
    private PriceList() {
    }

    /**
     * Precio unitario del producto. Producto desconocido cae en la tarifa por
     * defecto, igual que el código original, para no alterar el comportamiento
     * durante la migración.
     */
    public static double unitPriceOf(String product) {
        return "Curso SOLID".equals(product) ? SOLID_COURSE_PRICE : DEFAULT_PRICE;
    }
}
