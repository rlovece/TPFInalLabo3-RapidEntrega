package org.example.models;

import org.example.enums.Zonas;

import java.util.ArrayList;

/**
 * @author Oriana Dafne Lucero
 */
public class Supervisor extends Empleado {

    /// region Atributos
    private int cantEmpleadosACargo;
    private transient ArrayList<Empleado> empleadosACargo;
    /// endregion

    /// region Constructores

    public Supervisor ()
    {}

    public Supervisor(int cantEmpleadosACargo, ArrayList<Empleado> empleadosACargo) {
        this.cantEmpleadosACargo = cantEmpleadosACargo;
        this.empleadosACargo = empleadosACargo;
    }


    /// endregion

    /// region Getters&Setters

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
                "\n  Empleados a cargo:    " + cantEmpleadosACargo;
        return mensaje;
    }

    public String toStringListar()
    {
        String mensaje = "SUPERVISOR ID: " +this.getId() + " - Legajo: " + this.getLegajo()+" - Nombre: " +this.getNombre()+ " - Apellido: " +this.getApellido()+
                "\n     DNI: " +this.getDni()+ " - Telefono: " +this.getTelefono() + " - Mail: " + this.getMail();

        return mensaje;
    }
    /// endregion

}
