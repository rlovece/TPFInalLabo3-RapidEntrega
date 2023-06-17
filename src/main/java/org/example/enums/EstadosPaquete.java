package org.example.enums;

public enum EstadosPaquete {
    EN_CORREO ("En el correo"),
    ASIGNADO_PARA_REPARTO ("Asignado para reparto"),
    PRIMER_VISITA_FALLIDA ("1er Visita Fallida"),

    SEGUNDA_VISITA_FALLIDA ("2da Visita Fallida"),

    ENTREGADO ("Entregado"),

    ANULADO ("Anulado");

    private final String descripcion;

    EstadosPaquete(String descripcion) {
        this.descripcion = descripcion;
    }

}
