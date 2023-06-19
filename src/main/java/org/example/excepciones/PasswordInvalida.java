package org.example.excepciones;

import java.rmi.server.ExportException;

/**
 * <h2> Excepción Password Inválida</h2>
 * Excepcion a lanzar cuando la contraseña que se desea generar no tenga el formato adecuado.
 *
 * esta excepcion será lanzada en el método {@link org.example.recursos.EntradaSalida#validarPassword(String)} cuando
 * String ingresado por parametro no contecta formato aceptable.
 *  *
 * @author Ruth Lovece
 *
 * */
public class PasswordInvalida extends ExportException {
    public PasswordInvalida(String s) {
        super(s);
    }
}
