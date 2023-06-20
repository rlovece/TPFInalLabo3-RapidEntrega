package org.example.excepciones;


/**
 * <h2> Codigo Paquete existente</h2>
 * Excepcion a lanzar cuando se está generando el código del paquete aleatorio pero este
 * ya existe en el archivo.
 *
 * @author Ruth Lovece
 *
 * */
public class CodigoPaqueteExistente extends Exception {
    public CodigoPaqueteExistente (String mensaje) {
        super(mensaje);
    }
}
