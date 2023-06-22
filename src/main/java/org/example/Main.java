package org.example;

import org.example.enums.EstadosEmpleado;
import org.example.gestiones.*;


import org.example.excepciones.InexistenteException;

import org.example.models.EmpleadoLocal;
import org.example.models.Repartidor;

import org.example.gestiones.*;

import org.example.recursos.EntradaSalida;

public class Main {


    public static void main(String[] args) {

        menuIncial();


    }

    public static void menuIncial(){
        int opcion = 0;
        do {
            opcion = EntradaSalida.entradaInt("""
                          ELIJA UNA OPCION \s
                     1 - Soy Cliente
                     2 - Soy Empleado

                     0 - Cerrar Programa

                    """);

            switch (opcion) {
                case 1 -> {
                    GestionCliente clienteGestion = new GestionCliente();
                    clienteGestion.logueo();
                }
                case 2 -> menuEmpleadosOpcion();
                default -> {
                }
            }
        } while (opcion!=0);
    }

    public static void menuEmpleadosOpcion(){
        int opcion = 0;
        do {
            opcion = EntradaSalida.entradaInt("""
                          ELIJA UNA OPCION \s

                     1 - Soy Supervisor
                     2 - Soy Empleado de Local
                     3 - Soy Repartidor
                     4 - Soy Administrador

                     0 - Salir\s

                    """);

            switch (opcion) {
                case 1 -> {
                    GestionSupervisor gestionSupervisor = new GestionSupervisor();
                    gestionSupervisor.logueo();
                }
                case 2 -> {
                    GestionEmpleadoLocal gestionEmpleadoLocal = new GestionEmpleadoLocal();
                    gestionEmpleadoLocal.logueo();

                }
                case 3 -> {
                    GestionRepartidor gestionRepartidor = new GestionRepartidor();
                    gestionRepartidor.logueo();
                }
                case 4 -> {
                    GestionAdmin gestionAdmin = new GestionAdmin();
                    gestionAdmin.logueo();
                }
                default -> {
                }
            }
        } while (opcion!=0);
    }
}
