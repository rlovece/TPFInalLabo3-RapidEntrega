package org.example.gestiones;

import org.example.enums.EstadosPaquete;
import org.example.excepciones.InexistenteException;
import org.example.interfacesDeManejo.ManejoEmpleado;
import org.example.models.*;
import org.example.recursos.EntradaSalida;
import org.example.repositorio.ClientesRepo;
import org.example.repositorio.EmpleadoLocalRepo;
import org.example.repositorio.PaqueteRepo;
import org.example.repositorio.RepartidorRepo;

import java.util.ArrayList;

public class GestionRepartidor implements ManejoEmpleado {

    //region Atributos
    private PaqueteRepo paqueteRepo = new PaqueteRepo();
    private RepartidorRepo repartidorRepo = new RepartidorRepo();
    private ArrayList<Paquete> paquetes;
    private ArrayList<Repartidor> repartidores;

    //endregion

    //region Metodos Paquetes

    //ver paquetes asignados
    public void verPaquetesAsignados(String dni){
        this.paquetes = paqueteRepo.listar();

        for(Paquete paquete : this.paquetes){

            if(paquete.getRepatidorAsignado().getDni().equalsIgnoreCase(dni)){

                EntradaSalida.SalidaInformacion(paquete.toString(),"PAQUETES ASIGNADOS");

            }
        }

    }

    //cambiar estado de paquete
    public void cambiarEstadoPaquete(String dni){
        this.paquetes = paqueteRepo.listar();

        String codigo = EntradaSalida.entradaString("Ingrese el codigo de identificacion del paquete");

        try{

            Paquete paqueteAModificar = paqueteRepo.buscar(codigo);

            if(paqueteAModificar.getRepatidorAsignado().getDni().equalsIgnoreCase(dni)){

                EstadosPaquete estado = EntradaSalida.entradaEstadosPaquete();

                paqueteAModificar.setEstado(estado);

                EntradaSalida.SalidaInformacion("El paquete se modifico con exito!","");

            }else {
                EntradaSalida.SalidaAdvertencia("El paquete no le fue asignado a usted","ERROR");
            }

        }catch (InexistenteException e){

            EntradaSalida.SalidaAdvertencia(e.getMessage(),"ERROR");
        }
    }

    //endregion

    //region Metodos Repartidor

    //modificar datos

    @Override
    public void registroEmpleado() {
        //el repartidor no tiene el permiso para dar de alta a nuevos empleados
    }

    @Override
    public Empleado modificarEmpleado(String dni) {

        int opcion;
        //zona , tipo de paquete NO TIENE PERMISO PARA MODIFICAR

        try{

            Repartidor repartidorAModificar = repartidorRepo.buscar(dni);

            do{

                opcion = EntradaSalida.entradaInt("   INGRESE LA OPCION DESEADA\n" +
                        "\n1 - Modificar nombre" +
                        "\n2 - Modificar apellido" +
                        "\n3 - Modificar telefono" +
                        "\n4 - Modificar mail" +
                        "\n5 - Modificar contraseña" +
                        "\n\n0 - Salir\n\n");

                while(opcion < 0 || opcion > 5){

                    opcion = EntradaSalida.entradaInt("Ingrese una opcion valida");
                }

                switch (opcion){
                    case 1://nombre
                        repartidorAModificar.setNombre(EntradaSalida.entradaString("Ingrese el nuevo nombre"));
                        break;
                    case 2: //apellido
                        repartidorAModificar.setApellido(EntradaSalida.entradaString("Ingrese el nuevo apellido"));
                        break;
                    case 3: // telefono
                        repartidorAModificar.setTelefono(EntradaSalida.entradaTelefono());
                        break;
                    case 4: //mail
                        repartidorAModificar.setMail(EntradaSalida.entradaMail());
                        break;
                    case 5: // contraseña
                        repartidorAModificar.setPassword(EntradaSalida.entradaGeneracionPassword());
                        break;

                }

            }while(opcion != 0 );

            repartidorRepo.modificar(repartidorAModificar);

            return repartidorAModificar;

        }catch (InexistenteException e){
            EntradaSalida.SalidaAdvertencia(e.getMessage(),"ERROR");
        }

        return null;

    }


    //ver perfil
    public void verPerfil(String dni){
        this.repartidores = repartidorRepo.listar();

        for(Repartidor repartidor : this.repartidores){

            if(repartidor.getDni().equalsIgnoreCase(dni)){

                EntradaSalida.SalidaInformacion(repartidor.toString(),"DATOS REPARTIDOR");

                break;
            }
        }


    }

    //endregion

    //region Log in

    public void logueo(){
        this.repartidores = repartidorRepo.listar();

        String dni = EntradaSalida.entradaDNI();

        try{
            Repartidor repartidor = repartidorRepo.buscar(dni);

            String pass = EntradaSalida.entradaString("Ingrese la contraseña");

            if(repartidor.getPassword().equals(pass)){

                menuPrincipal(dni);

            }else{
                EntradaSalida.SalidaAdvertencia("Contraseña incorrecta","ERROR");
            }

        }catch(InexistenteException e){
            EntradaSalida.SalidaAdvertencia(e.getMessage(),"ERROR");
        }
    }

    //endregion

    //region Menu Principal

    public void menuPrincipal(String dni){

        int opcion;

        do {

            opcion = EntradaSalida.entradaInt("INGRESE LA OPCION DESEADA\n" +
                    "\n1 - Ver paquetes asigados" +
                    "\n2 - Cambiar estado paquete" +
                    "\n3 - Ver perfil" +
                    "\n4 - Modificar datos personales" +
                    "\n\n0 - Salir\n\n"  );

            while(opcion < 0 || opcion >  5){

                opcion = EntradaSalida.entradaInt("Ingrese una opcion valida");

            }

            switch (opcion){
                case 1: //ver paquetes asignados
                    verPaquetesAsignados(dni);
                    break;
                case 2: // cambiar estado paquete
                    cambiarEstadoPaquete(dni);
                    break;
                case 3: // ver perfil
                    verPerfil(dni);
                    break;
                case 4: // cambiar datos
                    modificarEmpleado(dni);
                    break;

            }

        }while(opcion!=0);

    }

    //endregion

}
