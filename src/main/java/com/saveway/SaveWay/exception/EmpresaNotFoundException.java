package com.saveway.SaveWay.exception;

public class EmpresaNotFoundException extends RuntimeException{
    public EmpresaNotFoundException(String message) {
        super(message);
    }

    public EmpresaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
