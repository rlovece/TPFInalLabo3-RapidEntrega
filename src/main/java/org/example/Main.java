package org.example;

import  org.example.gestiones.GestionEmpleadoLocal;
import org.example.gestiones.GestionAdmin;
import org.example.excepciones.InexistenteException;
import org.example.gestiones.GestionSupervisor;
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
                    /// gestionCliente.menuPrincipal();
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
            opcion = EntradaSalida.entradaInt("      ELIJA OPCION  \n" +
                    "\n 1 - Soy Supervisor" +
                    "\n 2 - Soy Empleado de Local" +
                    "\n 3 - Soy Repartidor" +
                    "\n 4 - Soy Administrador" +
                    "\n 0 - Salir \n\n");

            switch (opcion){
                case 1:
                    /// gestionSupervisor.logueo();
                    break;

                case 2:
                    /// gestionEmpleadoLocal.logueo();
                    break;

                case 3:
                    /// gestionRepartidor.logueo();
                    break;

                case 4:
                    GestionAdmin gestionAdmin = new GestionAdmin();
                    gestionAdmin.logueo();
                    break;

                default:
                    break;
            }
        } while (opcion!=0);
    }
}