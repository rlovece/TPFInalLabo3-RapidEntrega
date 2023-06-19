package org.example.models;

import org.example.enums.Zonas;

import java.util.ArrayList;

public class Supervisor extends Empleado {

    /// region Atributos
    private Zonas zona;
    private int cantEmpleadosACargo;
    private transient ArrayList<Empleado> empleadosACargo;
    /// endregion

    /// region Constructores

    public Supervisor ()
    {}

    public Supervisor(Zonas zona, int cantEmpleadosACargo, ArrayList<Empleado> empleadosACargo) {
        this.zona = zona;
        this.cantEmpleadosACargo = cantEmpleadosACargo;
        this.empleadosACargo = empleadosACargo;
    }


    /// endregion

    /// region Getters&Setters

    public Zonas getZona() {
        return zona;
    }

    public void setZona(Zonas zona) {
        this.zona = zona;
    }

    public int getCantEmpleadosACargo() {
        return cantEmpleadosACargo;
    }

    public void setCantEmpleadosACargo(int cantEmpleadosACargo) {
        this.cantEmpleadosACargo = cantEmpleadosACargo;
    }

    public ArrayList<Empleado> getEmpleadosACargo() {
        return empleadosACargo;
    }

    public void setEmpleadosACargo(ArrayList<Empleado> empleadosACargo) {
        this.empleadosACargo = empleadosACargo;
    }


    /// endregion

    /// region Metodos

    @Override
    public String toString() {
        String mensaje= "           S U P E R V I S O R    " +
                "\n  ID:                   " + this.getId() +
                "\n  Nombre:               " + this.getNombre() +
                "\n  Apellido:             " + this.getApellido() +
                "\n  DNI:                  " + this.getDni() +
                "\n  Telefono:             " + this.getTelefono() +
                "\n  Mail:                 " + this.getMail() +
                "\n  Legajo:               " + this.getLegajo() +
                "\n  Jornada:              " + this.getJornada()  +
                "\n  Zona:                 " + zona +
                "\n  Empleados a cargo:    " + cantEmpleadosACargo;
        return mensaje;
    }
    /// endregion

}
