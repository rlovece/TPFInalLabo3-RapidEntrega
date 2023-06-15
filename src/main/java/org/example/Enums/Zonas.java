package org.example.Enums;

/**
 * <h1> Enumerador Zona</h1>
 * Clase enumeradora para identificar zonas de la región geográfica donde se desarrolla la actividad.
 * <p>
 * Cada enum tiene un atributo descripción que será lo que se muestre al usuario al momento de seleccionar la zona
 *
 * @author Ruth Lovece
 *
 * */
public enum Zonas {
    MDP_SUR ("MdP Sur"),
    MDP_CENTRO ("MdP Centro"),
    MDP_NORTE ("MdP Norte"),
    MIRAMAR ("Miramar"),
    PINAMAR ("Pinamar"),
    NECOCHEA ("Necochea");

    private final String descripcion;
    Zonas(String descripcion) {
        this.descripcion = descripcion;
    }
}