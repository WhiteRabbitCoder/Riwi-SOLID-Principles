package com.riwi.solid.exercises.ocp.ex02;

import com.riwi.solid.exercises.ocp.ex02.solution.PaymentInterface;

/**
 * EJERCICIO OCP 02
 *
 * Añadir un nuevo método de pago requiere modificar processPayment().
 * Diseña una alternativa extensible.
 */
public class PaymentService {
    public void processPayment(PaymentInterface paymentMethod, double amount) {
        paymentMethod.processPayment(amount);
    }
}
