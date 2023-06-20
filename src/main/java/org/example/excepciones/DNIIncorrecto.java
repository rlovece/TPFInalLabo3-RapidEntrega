package org.example.excepciones;

/**
 * <h2> Excepción DNI Incorrecto</h2>
 * Excepcion a lanzar cuando el DNI no tenga el formato adecuado.
 *
 * esta excepcion será lanzada en el método {@link org.example.recursos.EntradaSalida#validarDNI(String)} cuando
 * String ingresado por parametro no contecta formato aceptable.
 *  *
 * @author Ruth Lovece
 *
 * */
public class DNIIncorrecto extends Exception{
    public DNIIncorrecto (String mensaje) {
        super(mensaje);
    }
}

