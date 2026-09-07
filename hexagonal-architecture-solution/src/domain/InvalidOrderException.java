package domain;

/**
 * QUÉ ES: la forma en que el dominio dice "este pedido rompe una regla".
 *
 * POR QUÉ ASÍ:
 *
 * 1. Existe para reemplazar los {@code System.out.println(...) + return} del
 *    código original. Aquel patrón obligaba al dominio a conocer la consola y
 *    dejaba al llamador sin saber si el pedido se creó o no.
 *
 * 2. Es un tipo propio y no {@code IllegalArgumentException} para que los
 *    adaptadores puedan distinguir "el usuario se equivocó" (mensaje amable)
 *    de un error de programación (que debe explotar). El adaptador de consola
 *    atrapa exactamente esta y nada más.
 *
 * 3. Es {@code RuntimeException} (no chequeada) porque un pedido inválido es
 *    un caso de uso normal del borde, no algo que cada capa intermedia deba
 *    declarar y propagar ensuciando firmas.
 */
public class InvalidOrderException extends RuntimeException {
    public InvalidOrderException(String message) {
        super(message);
    }
}
