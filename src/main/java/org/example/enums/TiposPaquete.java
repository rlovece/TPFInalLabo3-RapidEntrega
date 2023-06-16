package org.example.enums;


/**
 * <h1> Enumerador de Tipo de Paquetes</h1>
 * Clase enumeradora para identificar los distintos tipos de paquetes que se pueden enviar.
 * <p>
 * Cada enum tiene un atributo descripción que será lo que se muestre al usuario al momento de seleccionar el tipo de paquete.
 *
 * @author Ruth Lovece
 *
 * */
public enum TiposPaquete {
    GRANDE ("Grande"),
    MEDIANO ("Mediano"),
    PEQUEÑO ("Pequeño"),
    FRAGIL_Y_GRANDE ("Frágil y grande"),
    FRAGIL_Y_MEDIANO ("Frágil y mediano"),
    FRAGIL_Y_PEQUEÑO ("Fragil y pequeño");


    private final String descripcion;

    TiposPaquete(String descripcion) {
        this.descripcion = descripcion;
    }
}
