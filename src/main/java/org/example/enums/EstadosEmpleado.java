package org.example.enums;

public enum EstadosEmpleado {

    DISPONIBLE ("Disponible"),
    BAJA ("Dado de Baja"),
    VACACIONES ("De vacaciones"),
    private final String descripcion;

    EstadosEmpleado(String descripcion) {
        this.descripcion = descripcion;
    }
}
