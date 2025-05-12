package com.company.exception;

public class InvalidPetAgeException extends Exception {
    public InvalidPetAgeException(int age) {
        super("Invalid pet age. Pet Age should be >0 " );
    }
}


