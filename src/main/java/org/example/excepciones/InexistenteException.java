package org.example.excepciones;


/**
 * <h1> Excepción Edad inválida</h1>
 * Excepcion a lanzar cuando la edad no esté dentro de los valores acpetados.
 *
 * esta excepcion será lanzada en el método {@link org.example.recursos.EntradaSalida#validarEdad(int)} cuando el número entero ingresado como edad no se encuentre dentro de los límites admitidos.
 * En este caso se admiten enteros mayores entre 18 y 120.
 *
 * @author Ruth Lovece
 *
 * */
public class InexistenteException extends Exception {
    public InexistenteException(String mensaje) {
        super(mensaje);
    }
}