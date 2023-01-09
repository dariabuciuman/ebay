package com.ebay.server.exception;

public class ProductException extends CustomException{
    public static final String INCORRECT_BID = "You can not place a bid on your own product";

    public ProductException(String message) {
        super(message);
    }
}
