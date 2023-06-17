package org.example.gestiones;

import org.example.interfacesDeManejo.ManejoCliente;
import org.example.interfacesDeManejo.ManejoPaquete;
import org.example.models.*;
import org.example.recursos.EntradaSalida;
import org.example.repositorio.EmpleadoLocalRepo;
import org.example.repositorio.PaqueteRepo;
import org.example.repositorio.RepartidorRepo;
import org.example.repositorio.SupervisorRepo;

import java.util.ArrayList;
import java.util.Scanner;

public class GestionSupervisor implements ManejoCliente, ManejoPaquete {


    PaqueteRepo repoPaquete = new PaqueteRepo();
    ArrayList<Paquete> listadoPaquetes;
    RepartidorRepo repoRepartidor = new RepartidorRepo();
    ArrayList<Repartidor> listadoRepartidores;
    EmpleadoLocalRepo repoEmpLocal = new EmpleadoLocalRepo();
    ArrayList<EmpleadoLocal> listadoEmpLocal;
    SupervisorRepo repoSuper = new SupervisorRepo();
    ArrayList<Supervisor> listadoSupervisores;

    ArrayList<Empleado> empleadosAcargo;

    /// region Metodos Supervisor

    public ArrayList<Supervisor> listarSupervisores()
    {
        return this.listadoSupervisores= repoSuper.listar();
    }

    public Supervisor buscarSupervisorLegajo(int legajo)
    {
        this.listadoSupervisores= repoSuper.listar();
        for(Supervisor s : this.listadoSupervisores)
        {
            if(s.getLegajo()==legajo)
            {
                return s;
            }
        }
        return null;
    }

    public Supervisor buscarSupervisorID (int id)
    {
        this.listadoSupervisores= repoSuper.listar();
        for(Supervisor s : this.listadoSupervisores)
        {
            if(s.getId()==id)
            {
                return s;
            }
        }
        return null;
    }

    public Supervisor buscarSupervisor ()
    {
        int opcion= EntradaSalida.entradaInt("BUSCAR SUPERVISOR \n 1 - Por ID\n 2 - Por Legajo\n 3 - Por DNI");
        if(opcion==1)
        {
            int id= EntradaSalida.entradaInt("Ingrese el numero de ID");
            return buscarSupervisorID(id);
        }else if(opcion==2)
        {
            int legajo= EntradaSalida.entradaInt("Ingrese el numero de legajo");
            return buscarSupervisorLegajo(legajo);
        }else if(opcion==3)
        {
            String dni= EntradaSalida.entradaString("Ingrese el DNI");
            return repoSuper.buscar(dni);
        }
        return null;
    }
    public ArrayList<EmpleadoLocal> empLocalAcargo(Supervisor supervisor)
    {
        ArrayList<EmpleadoLocal> aCargo = new ArrayList<>();
        this.listadoEmpLocal = repoEmpLocal.listar();
        for(EmpleadoLocal r: this.listadoEmpLocal)
        {
            if(r.getSupervisor().equals(supervisor))
            {
                aCargo.add(r);
            }
        }
        return aCargo;
    }
    public ArrayList<Repartidor> repartidoresAcargo(Supervisor supervisor)
    {
        ArrayList<Repartidor> aCargo = new ArrayList<>();
        this.listadoRepartidores = repoRepartidor.listar();
        for(Repartidor r: this.listadoRepartidores)
        {
            if(r.getSupervisor().equals(supervisor))
            {
                aCargo.add(r);
            }
        }
        return aCargo;
    }

    public ArrayList<Empleado> empleadosAcargo (Supervisor supervisor)
    {
        this.empleadosAcargo.addAll(empLocalAcargo(supervisor));
        this.empleadosAcargo.addAll(repartidoresAcargo(supervisor));

        return this.empleadosAcargo;
    }

    public void verEmpledosAcargo (Supervisor supervisor)
    {
        for (Empleado e: empleadosAcargo(supervisor))
        {
            e.toString();
        }
    }

    public Empleado buscarEmpleadoAcargo (Supervisor supervisor, int legajo)
    {
        this.empleadosAcargo = empleadosAcargo(supervisor);
        for (Empleado e: this.empleadosAcargo)
        {
            if (e.getLegajo()==legajo)
            {
                return e;
            }
        }
        return null;
    }

    public boolean modificarEmpleadoAcargo (Supervisor supervisor, int legajo)
    {
        this.empleadosAcargo = empleadosAcargo(supervisor);
        Empleado aModificar = buscarEmpleadoAcargo(supervisor,legajo);
        if(aModificar.getClass()==EmpleadoLocal.class)
        {

        }else
        {

        }
        return false;

    }
    ///endregion

    ///region Metodos Persona-Empleado

    Persona modificarPersona(Persona p)
    {
        p.setNombre(EntradaSalida.entradaString("Ingrese el nombre"));
        p.setApellido(EntradaSalida.entradaString("Ingrese el apellido"));
        p.setDni(EntradaSalida.entradaString("Ingrese el dni"));
        p.setTelefono(EntradaSalida.entradaString("Ingrese el telefono"));
        p.setMail(EntradaSalida.entradaString("Ingrese el mail"));
       // p.setUsername(EntradaSalida.entradaString("Ingrese el usuario"));
       // p.setPassword(EntradaSalida.entradaString("Ingrese la contraseña"));
        return p;
    }

    Empleado modificarEmpleado(Empleado e)
    {
        if(1== EntradaSalida.entradaInt( " Modificar legajo:  \n 1 - Si \n 2 - No"))
        {
            e.setLegajo(EntradaSalida.entradaInt("Ingrese el numero de legajo"));
        }
        if(1== EntradaSalida.entradaInt( " Modificar jornada:  \n 1 - Si \n 2 - No"))
        {
            e.setJornada(EntradaSalida.entradaString("Ingrese la jornada"));
        }
      return e;
    }
    ///endregion

    /// region Metodos Repartidor


    public Repartidor modificarRepartidor (Repartidor rep)
    {
        int continuar=0;

        do{
        int opcion = EntradaSalida.entradaInt("      MODIFICAR " +
                                                "\n 1 - Datos personales" +
                                                "\n 2 - Datos generales" +
                                                "\n 3 - Datos repartidor");
        switch(opcion) {
            case 1:
                rep = (Repartidor) modificarPersona(rep);
                break;

            case 2:
                rep = (Repartidor) modificarEmpleado(rep);
                break;
            case 3:
                if (1 == EntradaSalida.entradaInt(" Modificar supervisor:  \n 1 - Si \n 2 - No")) {
                    EntradaSalida.SalidaInformacion("Buscar nuevo supervisor a asignar", "Supervisor");
                    rep.setSupervisor(buscarSupervisor());
                }
                if (1 == EntradaSalida.entradaInt(" Modificar licencia:  \n 1 - Si \n 2 - No")) {
                    rep.setLicencia(EntradaSalida.entradaString("Ingrese licencia"));
                }
                if (1 == EntradaSalida.entradaInt(" Modificar zona:  \n 1 - Si \n 2 - No")) {
                    EntradaSalida.SalidaInformacion("Seleccione la Zona", "Zona");
                    rep.setZona(EntradaSalida.entradaZona());
                }
                if (1 == EntradaSalida.entradaInt(" Modificar tipo de paquetes:  \n 1 - Si \n 2 - No")) {
                    EntradaSalida.SalidaInformacion("Seleccione el tipo de paquetes", "Paquetes");
                    rep.setTiposPaquetes(EntradaSalida.entradaTipoPaquete());
                }
                break;

            default:
                EntradaSalida.SalidaError("Numero ingresado incorrecto", "ERROR");
                break;

        }continuar=EntradaSalida.entradaInt(" Continuar  \n 1 - Seguir modificando Repartidor \n 2 - Finalizar")

        }while(continuar==1);

        return rep;
    }



    /// endregion

    ///region Metodos EmpleadoLocal
    public EmpleadoLocal modificarEmpleadoLocal (EmpleadoLocal empLocal)
    {
        int continuar=0;

        do{
            int opcion = EntradaSalida.entradaInt("      MODIFICAR " +
                    "\n 1 - Datos personales" +
                    "\n 2 - Datos generales" +
                    "\n 3 - Modificar supervisor asignado");
            switch(opcion) {
                case 1:
                    empLocal = (EmpleadoLocal) modificarPersona(empLocal);
                    break;

                case 2:
                    empLocal = (EmpleadoLocal) modificarEmpleado(empLocal);
                    break;
                case 3:

                    EntradaSalida.SalidaInformacion("Buscar nuevo supervisor a asignar", "Supervisor");
                    empLocal.setSupervisor(buscarSupervisor());

                    break;
                default:
                    EntradaSalida.SalidaError("Numero ingresado incorrecto", "ERROR");
                    break;
                }
            continuar=EntradaSalida.entradaInt(" Continuar  \n 1 - Seguir modificando empleado del local \n 2 - Finalizar")

        }while(continuar==1);

        return empLocal;
    }
    /// endregion

    /// region Metodos Paquete

    @Override
    public boolean modificarPaquete(int id) {
        return false;
    }


    @Override
    public void registroPaquete() {

    }

    /// endregion

    /// region Metodos Cliente
    @Override
    public boolean modificarCliente(String dni) {
        return false;
    }

    @Override
    public void registroCliente() {

    }

    /// endregion
}
