package org.example.Exceptiones;

public class InexistenteException extends Exception {
    public InexistenteException(String mensaje) {
        super(mensaje);
    }
}