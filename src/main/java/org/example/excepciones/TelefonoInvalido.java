package org.example.excepciones;

/**
 * <h2> Excepción Teléfono Inválido</h2>
 * Excepcion a lanzar cuando el teléfono no tenga el formato adecuado.
 *
 * esta excepcion será lanzada en el método {@link org.example.recursos.EntradaSalida#validarTelefono(String)} cuando
 * String ingresado por parametro no contecta formato aceptable.
 *  *
 * @author Ruth Lovece
 *
 * */
public class TelefonoInvalido extends Exception{
    public TelefonoInvalido (String mensaje) {
        super(mensaje);
    }
}
