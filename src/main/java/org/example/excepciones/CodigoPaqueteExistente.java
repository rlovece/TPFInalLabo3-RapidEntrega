package org.example.excepciones;

import java.security.spec.ECFieldF2m;

public class CodigoPaqueteExistente extends Exception {
    public CodigoPaqueteExistente (String mensaje) {
        super(mensaje);
    }
}
