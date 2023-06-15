package org.example.Models;

import org.example.Enums.Zonas;

import java.time.LocalDateTime;

public class Paquete {

    private enum Tamanios {GRANDE, MEDIANO, PEQUEÑO};
    private int id;
    private LocalDateTime fechaIngreso;
    private Cliente remitente;
    private Tamanios tamanio;
    private Zonas zonaEntrega;
    private String destinatario;
    private String domicilioEntrega;
    private boolean estadoEntregado;
    private Persona repatidorAsignado; /// Tipo persona Hasta que esté clase Repartidor

    ///region Constructores
    public Paquete(int id, LocalDateTime fechaIngreso, Cliente remitente, Tamanios tamanio, Zonas zonaEntrega, String destinatario, String domicilioEntrega) {
        this.id = id;
        this.fechaIngreso = fechaIngreso;
        this.remitente = remitente;
        this.tamanio = tamanio;
        this.zonaEntrega = zonaEntrega;
        this.destinatario = destinatario;
        this.domicilioEntrega = domicilioEntrega;
        this.estadoEntregado = false;
        this.repatidorAsignado = null;
    }
    ///endregion

    ///region Getters y Setters
    public int getId() {
        return id;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Cliente getRemitente() {
        return remitente;
    }

    public void setRemitente(Cliente remitente) {
        this.remitente = remitente;
    }

    public Tamanios getTamanio() {
        return tamanio;
    }

    public void setTamanio(Tamanios tamanio) {
        this.tamanio = tamanio;
    }

    public Zonas getZonaEntrega() {
        return zonaEntrega;
    }

    public void setZonaEntrega(Zonas zonaEntrega) {
        this.zonaEntrega = zonaEntrega;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getDomicilioEntrega() {
        return domicilioEntrega;
    }

    public void setDomicilioEntrega(String domicilioEntrega) {
        this.domicilioEntrega = domicilioEntrega;
    }

    public boolean isEstadoEntregado() {
        return estadoEntregado;
    }

    public void setEstadoEntregado(boolean estadoEntregado) {
        this.estadoEntregado = estadoEntregado;
    }

    public Persona getRepatidorAsignado() {
        return repatidorAsignado;
    }

    public void setRepatidorAsignado(Persona repatidorAsignado) {
        this.repatidorAsignado = repatidorAsignado;
    }

    ///endregion
}
