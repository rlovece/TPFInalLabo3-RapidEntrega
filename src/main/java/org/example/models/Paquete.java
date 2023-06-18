package org.example.models;

import org.example.enums.EstadosPaquete;
import org.example.enums.TiposPaquete;
import org.example.enums.Zonas;

import java.time.LocalDateTime;

public class Paquete {

    private int id;
    private String codigoIdentificacion;
    private LocalDateTime fechaIngreso;
    private Cliente remitente;
    private TiposPaquete tiposPaquete;
    private Zonas zonaEntrega;
    private String destinatario;
    private String domicilioEntrega;
    private EstadosPaquete estado;
    private Persona repatidorAsignado; /// Tipo persona Hasta que esté clase Repartidor

    ///region Constructores

    public Paquete()
    {}

    public Paquete(int id, String codigoIdentificacion, LocalDateTime fechaIngreso, Cliente remitente, TiposPaquete tipoPaquete, Zonas zonaEntrega, String destinatario, String domicilioEntrega) {
        this.id = id;
        this.codigoIdentificacion = codigoIdentificacion;
        this.fechaIngreso = fechaIngreso;
        this.remitente = remitente;
        this.tiposPaquete = tipoPaquete;
        this.zonaEntrega = zonaEntrega;
        this.destinatario = destinatario;
        this.domicilioEntrega = domicilioEntrega;
        this.estado = EstadosPaquete.EN_CORREO;
        this.repatidorAsignado = null;
    }
    ///endregion

    ///region Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public TiposPaquete getTiposPaquete() {
        return tiposPaquete;
    }

    public void setTiposPaquete(TiposPaquete tiposPaquete) {
        this.tiposPaquete = tiposPaquete;
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

    public EstadosPaquete getEstado() {
        return estado;
    }

    public void setEstado(EstadosPaquete estado) {
        this.estado = estado;
    }

    public Persona getRepatidorAsignado() {
        return repatidorAsignado;
    }

    public void setRepatidorAsignado(Persona repatidorAsignado) {
        this.repatidorAsignado = repatidorAsignado;
    }

    public String getCodigoIdentificacion() {
        return codigoIdentificacion;
    }

    public void setCodigoIdentificacion(String codigoIdentificacion) {
        this.codigoIdentificacion = codigoIdentificacion;
    }


    ///endregion
}