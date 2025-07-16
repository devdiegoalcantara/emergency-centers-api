package br.com.emergency.exception;

public class InvalidNegotiationException extends RuntimeException {
    public InvalidNegotiationException(String message) {
        super(message);
    }
}
