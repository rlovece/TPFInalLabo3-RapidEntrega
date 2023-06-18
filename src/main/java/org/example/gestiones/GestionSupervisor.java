package org.example.gestiones;

import org.example.excepciones.InexistenteException;
import org.example.interfacesDeManejo.ManejoCliente;
import org.example.interfacesDeManejo.ManejoPaquete;
import org.example.models.*;
import org.example.recursos.EntradaSalida;
import org.example.repositorio.EmpleadoLocalRepo;
import org.example.repositorio.PaqueteRepo;
import org.example.repositorio.RepartidorRepo;
import org.example.repositorio.SupervisorRepo;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GestionSupervisor implements ManejoCliente, ManejoPaquete {


    ///region Atributos
    PaqueteRepo repoPaquete = new PaqueteRepo();
    ArrayList<Paquete> listadoPaquetes;
    RepartidorRepo repoRepartidor = new RepartidorRepo();
    ArrayList<Repartidor> listadoRepartidores;
    EmpleadoLocalRepo repoEmpLocal = new EmpleadoLocalRepo();
    ArrayList<EmpleadoLocal> listadoEmpLocal;
    SupervisorRepo repoSuper = new SupervisorRepo();
    ArrayList<Supervisor> listadoSupervisores;

    ArrayList<Empleado> empleadosAcargo;

    /// endregion

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

    ///endregion

    /// region Metodos Empleados a cargo
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


    public Empleado buscarEmpleadoAcargo (Supervisor supervisor)
    {
        this.empleadosAcargo = empleadosAcargo(supervisor);
        int legajo = EntradaSalida.entradaInt("Ingrese el numero de legajo");
        for (Empleado e: this.empleadosAcargo)
        {
            if (e.getLegajo()==legajo)
            {
                return e;
            }
        }
        return null;
    }

    public boolean modificarEmpleadoAcargo (Supervisor supervisor) throws InexistenteException {
        this.empleadosAcargo = empleadosAcargo(supervisor);
        Empleado aModificar = buscarEmpleadoAcargo(supervisor);

        if(aModificar!=null)
        {
            if(aModificar.getClass()==EmpleadoLocal.class)
            {
                aModificar = modificarEmpleadoLocal((EmpleadoLocal) aModificar);
            }else if(aModificar.getClass()==Repartidor.class)
            {
                aModificar = modificarRepartidor((Repartidor) aModificar);
            }
        }else {
            throw new InexistenteException("El legajo ingresado no corresponde a un empleado a cargo");
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

    Empleado modificarAtributosEmpleado(Empleado e)
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
                rep = (Repartidor) modificarAtributosEmpleado(rep);
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

        }continuar=EntradaSalida.entradaInt(" Continuar  \n 1 - Seguir modificando Repartidor \n 2 - Finalizar");

        }while(continuar==1);

        repoRepartidor.modificar(rep);

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
                    empLocal = (EmpleadoLocal) modificarAtributosEmpleado(empLocal);
                    break;
                case 3:

                    EntradaSalida.SalidaInformacion("Buscar nuevo supervisor a asignar", "Supervisor");
                    empLocal.setSupervisor(buscarSupervisor());

                    break;
                default:
                    EntradaSalida.SalidaError("Numero ingresado incorrecto", "ERROR");
                    break;
                }
            continuar=EntradaSalida.entradaInt(" Continuar  \n 1 - Seguir modificando empleado del local \n 2 - Finalizar");

        }while(continuar==1);

        repoEmpLocal.modificar(empLocal);

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
        Paquete nuevo = new Paquete();

        nuevo.setId((repoPaquete.buscarUltimoID())+1);
        nuevo.setFechaIngreso(LocalDateTime.now());
        //nuevo.setRemitente(EntradaSalida.entradaString("    NUEVO PAQUETE \nIngrese el remitente"));
        EntradaSalida.SalidaInformacion("Seleccione el tipo de paquete", "TIPO DE PAQUETE");
        nuevo.setTiposPaquete(EntradaSalida.entradaTipoPaquete());
        EntradaSalida.SalidaInformacion("Seleccione la Zona", "Zona");
        nuevo.setZonaEntrega(EntradaSalida.entradaZona());
        nuevo.setDestinatario(JOptionPane.showInputDialog("Ingrese el destinatario"));
        nuevo.setDomicilioEntrega(JOptionPane.showInputDialog("Ingrese el domicilio de entrega"));
        // nuevo.setEstado(EntradaSalida);  CONTINUAR CON VALIDACION DE ENTRADA

        repoPaquete.agregar(nuevo);
    }

    public Paquete buscarPaqueteID (int id)
    {
        this.listadoPaquetes = repoPaquete.listar();
        for(Paquete p: this.listadoPaquetes)
        {
            if(p.getId()== id)
            {
                return p;
            }
        }
        return null;
    }

    public Paquete buscarPaquete()
    {
        Paquete buscado = new Paquete();
        int opcion = EntradaSalida.entradaInt("  BUSCAR PAQUETE  \n 1 - Por codigo de identificacion" +
                "\n 2 - Por ID");
        switch(opcion)
        {
            case 1:
                buscado = repoPaquete.buscar((EntradaSalida.entradaString("Ingrese el codigo de identificacion")));
                break;
            case 2:
                buscado = buscarPaqueteID(EntradaSalida.entradaInt("Ingrede el numerdo de ID del paquete"));
                break;
            default:
                EntradaSalida.SalidaError("El numero ingresado es erroneo","ERROR");
                break;

        }
        return buscado;
    }

    public void verPaquete()
    {
        Paquete buscado = buscarPaquete();
        if (buscado!=null)
        {
            buscado.toString();
        }else {
            EntradaSalida.SalidaError("No se encontro el paquete buscado","ERROR");
        }
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
