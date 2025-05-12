package com.company.exception;

public class AdpotionException extends Exception {
    public AdpotionException(String Name) {
    	super("Pet with name '" + Name + "' is not available for adoption.");
    }
}
