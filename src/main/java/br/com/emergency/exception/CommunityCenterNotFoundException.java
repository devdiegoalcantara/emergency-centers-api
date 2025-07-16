package br.com.emergency.exception;

public class CommunityCenterNotFoundException extends RuntimeException {
    public CommunityCenterNotFoundException(String message) {
        super(message);
    }
}
