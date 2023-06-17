package org.example.models;

public class Persona {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private int Id;
    private String nombre, apellido, dni, telefono, username, password;
//    </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    public Persona(int id, String nombre,String apellido, String dni, String telefono, String username, String password) {
        this.Id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.username = username;
        this.password = password;
    }

    public Persona() {
    }
//    </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombre()
    {
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
        return "Id = " + Id
                + "\nNombre o = " + nombre
                + "\nApellido = " + apellido
                + "\nDni = " + dni
                + "\nTelefono = " + telefono
                + "\nUsername = " + username
                + "\nPassword = " + password;
    }
//    </editor-fold>
}
