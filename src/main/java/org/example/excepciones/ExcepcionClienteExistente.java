package org.example.excepciones;

public class ExcepcionClienteExistente extends Exception{

    public ExcepcionClienteExistente(String mensaje)
    {
        super (mensaje);
    }
}