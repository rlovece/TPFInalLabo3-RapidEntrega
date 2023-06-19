package org.example.models;

import org.example.enums.Zonas;

import java.util.ArrayList;

public class Supervisor extends Empleado {

    /// region Atributos
    private Zonas zona;
    private int cantEmpleadosACargo;
    private ArrayList<Empleado> empleadosACargo;
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
        return "Supervisor{" +
                "Nombre :" + this.getNombre()+
                "zona=" + zona +
                ", cantEmpleadosACargo=" + cantEmpleadosACargo +
                ", empleadosACargo=" + empleadosACargo +
                '}';
    }


    /// endregion

}
