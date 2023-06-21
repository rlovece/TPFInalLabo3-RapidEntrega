package org.example.models;


import java.io.Serializable;

public class EmpleadoLocal extends Empleado implements Serializable {

    private Supervisor supervisor;

    //region Constructores
    public EmpleadoLocal() {
    }

    public EmpleadoLocal(Supervisor supervisor) {this.supervisor = supervisor;
    }

    //endregion

    //region Getters and Setters

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    //endregion

    @Override
    public String toString() {
        return " FALTA PONER BONITO . UNO CON TODOS LOS DATOS";
    }

    public String toStringListar() {
        return "\nEmpleado Local - Legajo: " + super.getLegajo() +
                " - " + super.getNombre() +
                " " + super.getApellido() +
                " Supervirsor: " + supervisor.getLegajo() +
                " " + this.supervisor.getNombre() +
                " " + this.supervisor.getApellido();
    }
}