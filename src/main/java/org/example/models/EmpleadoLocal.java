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
        return "       EMPLEADO LOCAL" +
                "\n\nID:                   " + this.getId() +
                "\nNombre:               " + this.getNombre() +
                "\nApellido:             " + this.getApellido() +
                "\nDNI:                  " + this.getDni() +
                "\nTelefono:             " + this.getTelefono() +
                "\nMail:                 " + this.getMail() +
                "\nLegajo:               " + this.getLegajo() +
                "\nJornada:              " + this.getJornada()  +
                "\nSupervisor:           " + this.getSupervisor().getNombre() + " " + this.getSupervisor().getApellido() + "\n\n";
    }

    public String toStringListar() {
        return "\nEMPLEADO LOCAL - Legajo: " + super.getLegajo() +
                " -  Nombre : " + super.getNombre() +
                " " + super.getApellido() +
                "   //  SUPERVISOR :  Legajo: " + supervisor.getLegajo() +
                " - Nombre : " + this.supervisor.getNombre() +
                " " + this.supervisor.getApellido();
    }
}