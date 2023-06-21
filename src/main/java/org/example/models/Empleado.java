package org.example.models;

import org.example.enums.EstadosEmpleado;

public class Empleado extends Persona{

    //region Atributos
    private int legajo;
    private String jornada;
    private EstadosEmpleado estado = EstadosEmpleado.DISPONIBLE;

    //endregion

    //region Constructores
    public Empleado() {
    }

    public Empleado(int legajo, String jornada) {
        this.legajo = legajo;
        this.jornada = jornada;
        this.estado = EstadosEmpleado.DISPONIBLE;
    }

    //endregion


    //region Getters and Setters
    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public EstadosEmpleado getEstado() {
        return estado;
    }

    public void setEstado(EstadosEmpleado estado) {
        this.estado = estado;
    }

    //endregion

    @Override
    public String toString() {
        return "Empleado{" +
                "legajo=" + legajo +
                " " + super.getNombre() +
                " " + super.getApellido() +
                ", jornada='" + jornada + '\'' +
                '}';
    }
}
