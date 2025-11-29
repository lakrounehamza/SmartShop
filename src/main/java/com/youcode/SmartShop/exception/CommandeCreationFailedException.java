package com.youcode.SmartShop.exception;

public class CommandeCreationFailedException extends RuntimeException {
    public CommandeCreationFailedException(String message) {
        super(message);
    }
}
