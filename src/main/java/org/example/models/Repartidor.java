package org.example.models;

import org.example.enums.TiposPaquete;
import org.example.enums.Zonas;

import java.util.ArrayList;

public class Repartidor extends Empleado {


    /// region Atributos
    private Supervisor supervisor;
    private String licencia;
    private Zonas zona;
    private TiposPaquete tiposPaquetes;
    private ArrayList<Paquete> paquetesAsignados;
    /// endregion

    /// region Constructores

    public Repartidor() {
    }

    public Repartidor(Supervisor supervisor, String licencia, Zonas zona, TiposPaquete tiposPaquetes, ArrayList<Paquete> paquetesAsignados) {
        this.supervisor = supervisor;
        this.licencia = licencia;
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

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
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


/// endregion
}