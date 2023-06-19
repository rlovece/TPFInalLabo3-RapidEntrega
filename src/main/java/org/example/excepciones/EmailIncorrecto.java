package org.example.excepciones;

/**
 * <h1> Excepción Email Icorrecto</h1>
 * Excepcion a lanzar cuando el mail no tenga el formato adecuado.
 *
 * esta excepcion será lanzada en el método {@link org.example.recursos.EntradaSalida#validarEmail(String)} cuando
 * String ingresado por parametro no contecta formato aceptable.
 *  *
 * @author Ruth Lovece
 *
 * */

public class EmailIncorrecto extends Exception {
    public EmailIncorrecto (String mensaje) {
        super(mensaje);
    }
}
