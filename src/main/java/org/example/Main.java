package org.example;



import org.example.gestiones.*;


import org.example.recursos.EntradaSalida;

public class Main {


    public static void main(String[] args) {

        menuIncial();


    }

    public static void menuIncial(){
        int opcion = 0;
        do {
            opcion = EntradaSalida.entradaInt("      ELIJA OPCION  \n" +
                    "\n 1 - Soy Cliente" +
                    "\n 2 - Soy Empleado\n" +
                    "\n 0 - Cerrar Programa\n\n");

            switch (opcion){
                case 1:
                    GestionCliente gestionCliente=new GestionCliente();
                     gestionCliente.logueo();
                    break;

                case 2:
                    menuEmpleadosOpcion();
                    break;

                default:
                    break;
            }
        } while (opcion!=0);
    }

    public static void menuEmpleadosOpcion(){
        int opcion = 0;
        do {
            opcion = EntradaSalida.entradaInt("""
                          ELIJA OPCION \s

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