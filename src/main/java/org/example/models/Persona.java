package org.example.models;

import org.example.enums.EstadosEmpleado;

import java.util.Objects;

public abstract class Persona {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    protected int id;
    protected String nombre, apellido, dni, telefono, mail, username, password;

//    </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    public Persona(int id, String nombre, String apellido, String dni, String telefono, String mail, String username, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.mail = mail;
        this.username = username;
        this.password = password;
    }
    public Persona() {
    }
//    </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

//    </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="To String">
    @Override
    public String toString() {
        return "Id = " + id
                + "\nNombre = " + nombre
                + "\nApellido = " + apellido
                + "\nDni = " + dni
                + "\nTelefono = " + telefono
                + "\nCorreo = " + mail
                + "\nUsername = " + username
                + "\nPassword = " + password;
    }
//    </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Equals & Hashcode">
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona persona)) return false;
        return Objects.equals(dni, persona.dni);
    }
    @Override
    public int hashCode() {
        int resultado = 17;
        resultado = 31 * resultado + (dni != null ? dni.hashCode() : 0);
        return resultado;
    }
    //    </editor-fold>

}