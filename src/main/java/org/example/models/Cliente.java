package org.example.models;

public class Cliente extends Persona {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private String domicilio;
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
//    </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="To String">
    @Override
    public String toString() {
        return super.toString() + "\nDomicilio = " + domicilio;
    }
//    </editor-fold>      
}
