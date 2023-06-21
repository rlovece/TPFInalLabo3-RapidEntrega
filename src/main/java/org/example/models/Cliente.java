package org.example.models;

import java.io.Serializable;

public class Cliente extends Persona implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private String domicilio;

    private boolean estadoCliente;
//    </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    public Cliente(int id, String nombre, String apellido, String dni, String telefono, String email, String username, String password, String domicilio) {
        super(id, nombre, apellido, dni, telefono, email, username, password);
        this.domicilio = domicilio;
    }
    public Cliente() {
    }
//    </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public String getDomicilio() {
        return domicilio;
    }
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public boolean isEstadoCliente() {
        return estadoCliente;
    }

    public void setEstadoCliente(boolean estadoCliente) {
        this.estadoCliente = estadoCliente;
    }

    //    </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="To String">
    @Override
    public String toString() {

        return "          C L I E N T E\n" +
                "\nID:                 " + this.getId() +
                "\nNombre:               " + this.getNombre() +
                "\nApellido:             " + this.getApellido() +
                "\nDNI:                  " + this.getDni() +
                "\nTelefono:             " + this.getTelefono() +
                "\nMail:                 " + this.getMail() +
                "\nDomicilio:            " + this.getDomicilio()  + "\n";
    }

   public String toStringListar() {
        return "\nCliente - ID: " + this.getId() +
                " - " + this.getNombre() +
                " " + this.getApellido();

    }
//    </editor-fold>      
}
