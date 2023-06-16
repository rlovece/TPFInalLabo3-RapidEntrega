package org.example.models;

public class Empleado extends Persona{
    private int legajo;
    private String jornada;

    public Empleado() {
    }

    public Empleado(int legajo, String jornada) {
        this.legajo = legajo;
        this.jornada = jornada;
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
