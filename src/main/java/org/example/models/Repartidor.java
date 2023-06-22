package org.example.models;

import org.example.enums.TiposPaquete;
import org.example.enums.Zonas;

import java.util.ArrayList;

public class Repartidor extends Empleado {


    /// region Atributos
    private Supervisor supervisor;
    private Zonas zona;
    private TiposPaquete tiposPaquetes;
    private ArrayList<Paquete> paquetesAsignados;
    /// endregion

    /// region Constructores

    public Repartidor() {
    }

    public Repartidor(Supervisor supervisor, Zonas zona, TiposPaquete tiposPaquetes, ArrayList<Paquete> paquetesAsignados) {
        this.supervisor = supervisor;
        this.zona = zona;
        this.tiposPaquetes = tiposPaquetes;
        this.paquetesAsignados = paquetesAsignados;
    }

    /// endregion

    /// region Getters&Setters

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public Zonas getZona() {
        return zona;
    }

    public void setZona(Zonas zona) {
        this.zona = zona;
    }

    public TiposPaquete getTiposPaquetes() {
        return tiposPaquetes;
    }

    public void setTiposPaquetes(TiposPaquete tiposPaquetes) {
        this.tiposPaquetes = tiposPaquetes;
    }

    public ArrayList<Paquete> getPaquetesAsignados() {
        return paquetesAsignados;
    }

    public void setPaquetesAsignados(ArrayList<Paquete> paquetesAsignados) {
        this.paquetesAsignados = paquetesAsignados;
    }

/// endregion

    /// region Metodos

    @Override
    public String toString() {
        String mensaje= "         R E P A R T I D O R" +
                "\n ID:                 " + this.getId() +
                "\n Nombre:             " + this.getNombre() +
                "\n Apellido:           " + this.getApellido() +
                "\n DNI:                " + this.getDni() +
                "\n Telefono:           " + this.getTelefono() +
                "\n Mail:               " + this.getMail() +
                "\n Legajo:             " + this.getLegajo() +
                "\n Jornada:            " + this.getJornada()  +
                "\n Supervisor:         " + supervisor.getNombre() + " " + supervisor.getApellido() +
                "\n Zona:               " + zona +
                "\n Tipo de Paquetes:   " + tiposPaquetes ;
        return mensaje;
    }

    public String toStringListar ()
    {
        return "\nREPARTIDOR - Legajo: " + super.getLegajo() +
                " -  Nombre : " + super.getNombre() +
                " " + super.getApellido() +
                "   // SUPERVISOR    Legajo: " + supervisor.getLegajo() +
                " - Nombre : " + this.supervisor.getNombre() +
                " " + this.supervisor.getApellido();
    }


/// endregion
}