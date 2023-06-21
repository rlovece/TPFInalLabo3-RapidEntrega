package org.example;



import org.example.enums.EstadosEmpleado;
import org.example.gestiones.GestionEmpleadoLocal;


import org.example.gestiones.GestionAdmin;
import org.example.excepciones.InexistenteException;

import org.example.gestiones.GestionRepartidor;
import org.example.gestiones.GestionSupervisor;
import org.example.models.EmpleadoLocal;
import org.example.models.Repartidor;
import org.example.recursos.EntradaSalida;
import org.example.repositorio.EmpleadoLocalRepo;
import org.example.repositorio.RepartidorRepo;

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
                    "\n\n 0 - Salir \n\n");

            switch (opcion){
                case 1:
                    GestionSupervisor gestionSupervisor=new GestionSupervisor();
                    gestionSupervisor.logueo();
                    break;

                case 2:
                    GestionEmpleadoLocal gestionEmpleadoLocal = new GestionEmpleadoLocal();
                    gestionEmpleadoLocal.logueo();
                    break;

                case 3:
                    GestionRepartidor gestionRepartidor = new GestionRepartidor();
                    gestionRepartidor.logueo();
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