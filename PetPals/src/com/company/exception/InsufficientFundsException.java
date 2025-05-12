package com.company.exception;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(double amount) {
       super("Insufficient Funds. Donation should be >$10");
    }
}
