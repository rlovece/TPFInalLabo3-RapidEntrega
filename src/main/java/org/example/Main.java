package org.example;


import org.example.gestiones.GestionSupervisor;
import org.example.recursos.EntradaSalida;

public class Main {
    public static void main(String[] args) {

        EntradaSalida.entradaTelefono();

        GestionSupervisor gestion = new GestionSupervisor();

        gestion.registroPaquete();



    }
}