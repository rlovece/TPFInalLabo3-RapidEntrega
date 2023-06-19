package org.example.models;

import org.example.enums.EstadosEmpleado;

public class Empleado extends Persona{
    private int legajo;
    private String jornada;
    private EstadosEmpleado estado = EstadosEmpleado.DISPONIBLE;
    public Empleado() {
    }

    public Empleado(int legajo, String jornada) {
        this.legajo = legajo;
        this.jornada = jornada;
        this.estado = EstadosEmpleado.DISPONIBLE;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public String getJornada() {
        return jornada;
    }

    public EstadosEmpleado getEstado() {
        return estado;
    }

    public void setEstado(EstadosEmpleado estado) {
        this.estado = estado;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "legajo=" + legajo +
                ", jornada='" + jornada + '\'' +
                '}';
    }
}
