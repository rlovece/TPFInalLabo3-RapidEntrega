package org.example.enums;

public enum EstadosEmpleado {

    DISPONIBLE ("Disponible"),
    VACACIONES ("De vacaciones"),
    BAJA ("Dado de Baja");

    private final String descripcion;

    EstadosEmpleado(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}

