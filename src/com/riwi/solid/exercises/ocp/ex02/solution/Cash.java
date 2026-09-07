package com.riwi.solid.exercises.ocp.ex02.solution;

public class Cash implements PaymentInterface {

    @Override
    public void processPayment(double amount) {
        System.out.println("Pagando " + amount + " en efectivo");
    }

}
