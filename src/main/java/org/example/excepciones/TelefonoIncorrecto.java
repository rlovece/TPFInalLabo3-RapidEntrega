package org.example.excepciones;

/**
 * <h2> Excepción DNI Incorrecto</h2>
 * Excepcion a lanzar cuando el teléfono no tenga el formato adecuado.
 *
 * esta excepcion será lanzada en el método {@link org.example.recursos.EntradaSalida#validarTelefono(String)} cuando
 * String ingresado por parametro no contecta formato aceptable.
 *  *
 * @author Ruth Lovece
 *
 * */
public class TelefonoIncorrecto extends Exception{
    public TelefonoIncorrecto (String mensaje) {
        super(mensaje);
    }
}
