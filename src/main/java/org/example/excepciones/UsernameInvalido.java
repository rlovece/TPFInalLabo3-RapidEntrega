package org.example.excepciones;

/**
 * <h2> Excepción username Inválido</h2>
 * Excepcion a lanzar cuando el username no tenga el formato adecuado.
 *
 * esta excepcion será lanzada en el método {@link org.example.recursos.EntradaSalida#validarUsername(String)} cuando
 * String ingresado por parametro no contecta formato aceptable.
 *  *
 * @author Ruth Lovece
 *
 * */
public class UsernameInvalido extends Exception{
    public UsernameInvalido(String message) {
        super(message);
    }
}
