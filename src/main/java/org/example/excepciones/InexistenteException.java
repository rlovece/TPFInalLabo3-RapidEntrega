package org.example.excepciones;


/**
 * <h2> Excepción Edad inválida</h2>
 *Excepcion a lanzar cuando se intente acceder a un codigo inexistente.
 *Ej: DNI, CodigoIndentificacioón
 *
 * @author Ruth Lovece
 *
 * */
public class InexistenteException extends Exception {
    public InexistenteException(String mensaje) {
        super(mensaje);
    }
}