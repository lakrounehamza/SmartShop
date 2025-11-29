package com.youcode.SmartShop.exception;

public class ProductStockUnavailableException extends RuntimeException {
    public ProductStockUnavailableException(String message) {
        super(message);
    }
}
