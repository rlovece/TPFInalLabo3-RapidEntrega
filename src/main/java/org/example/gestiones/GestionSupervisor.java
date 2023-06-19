package org.example.gestiones;

import org.example.enums.EstadosEmpleado;
import org.example.enums.EstadosPaquete;
import org.example.enums.TiposPaquete;
import org.example.enums.Zonas;
import org.example.excepciones.CodigoPaqueteExistente;
import org.example.excepciones.ExcepcionClienteExistente;
import org.example.excepciones.InexistenteException;
import org.example.interfacesDeManejo.ManejoCliente;
import org.example.interfacesDeManejo.ManejoPaquete;
import org.example.models.*;
import org.example.recursos.EntradaSalida;
import org.example.repositorio.*;

import javax.swing.*;
import java.net.PasswordAuthentication;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class GestionSupervisor implements ManejoCliente, ManejoPaquete {


    ///region Atributos

    Supervisor supervisor;
    SupervisorRepo repoSuper = new SupervisorRepo();
    ArrayList<Supervisor> listadoSupervisores;
    PaqueteRepo repoPaquete = new PaqueteRepo();
    ArrayList<Paquete> listadoPaquetes;
    RepartidorRepo repoRepartidor = new RepartidorRepo();
    ArrayList<Repartidor> listadoRepartidores;
    EmpleadoLocalRepo repoEmpLocal = new EmpleadoLocalRepo();
    ArrayList<EmpleadoLocal> listadoEmpLocal;
    ArrayList<Empleado> empleadosAcargo;
    ClientesRepo repoClientes = new ClientesRepo();
    ArrayList<Cliente> listadoClientes;

    /// endregion

    /// region Metodos Supervisor

    public GestionSupervisor() {
    }

    public GestionSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public void asignarSupervisor()
    {
        try{
            this.supervisor= buscarSupervisor();
        }catch(InexistenteException e)
        {
            EntradaSalida.SalidaError(e.getMessage(),"ERROR");
        }
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

    public Supervisor buscarSupervisor () throws InexistenteException {
        Supervisor buscado= new Supervisor();

        int opcion = EntradaSalida.entradaInt("    BUSCAR SUPERVISOR  \n  1 - Por ID" +
                                                 "\n  2 - Por Legajo\n  3 - Por DNI");

        switch(opcion)
        {
            case 1:
                buscado = buscarSupervisorID(EntradaSalida.entradaInt("Ingrese el numerdo de ID"));
                break;
            case 2:
                buscado = buscarSupervisorLegajo(EntradaSalida.entradaInt("Ingrese el numero de legajo"));
                break;
            case 3:
                buscado = repoSuper.buscar(EntradaSalida.entradaString("Ingrese el numero de DNI"));
                break;
            default:
                EntradaSalida.SalidaError("El numero ingresado es erroneo","ERROR");
                break;
        }
        if(buscado!=null)
        {
            return buscado;
        }else {
            throw new InexistenteException("Supervisor inexistente");
        }
    }

    ///endregion

    /// region Metodos Empleados a cargo
    public void empLocalAcargo()
    {
        ArrayList<EmpleadoLocal> aCargo = new ArrayList<>();
        this.listadoEmpLocal = repoEmpLocal.listar();
        for(EmpleadoLocal r: this.listadoEmpLocal)
        {
            if(r.getSupervisor().equals(this.supervisor))
            {
                if(!this.supervisor.getEmpleadosACargo().contains(r))
                {
                    aCargo.add(r);
                }
            }
        }
        this.supervisor.getEmpleadosACargo().addAll(aCargo);
    }
    public void repartidoresAcargo()
    {
        ArrayList<Repartidor> aCargo = new ArrayList<>();
        this.listadoRepartidores = repoRepartidor.listar();
        for(Repartidor r: this.listadoRepartidores)
        {
            if(r.getSupervisor().equals(this.supervisor))
            {
                if(!this.supervisor.getEmpleadosACargo().contains(r))
                {
                    aCargo.add(r);
                }
            }
        }
        this.supervisor.getEmpleadosACargo().addAll(aCargo);
    }

    public void empleadosAcargo ()
    {
        if(this.supervisor!=null)
        {
            empLocalAcargo();
            repartidoresAcargo();
        }else {
            EntradaSalida.SalidaError("No tiene un supervisor asignado","ERROR SUPERVISOR");
        }
    }

    public void verEmpledosAcargo ()
    {
        empleadosAcargo();
        for (Empleado e: this.supervisor.getEmpleadosACargo())
        {
            e.toString();
        }
    }


    public Empleado buscarEmpleadoAcargo ()
    {
        empleadosAcargo();
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

    public boolean modificarEmpleadoAcargo () throws InexistenteException {
        empleadosAcargo();
        Empleado aModificar = buscarEmpleadoAcargo();

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
        p.setDni(EntradaSalida.entradaDNI());
        p.setTelefono(EntradaSalida.entradaString("Ingrese el telefono"));
        p.setMail(EntradaSalida.entradaMail());
        int opcion=1;
        if(opcion == (EntradaSalida.entradaInt(" Modificar Usuario-Contraseña \n    1 - Si\n    2 - No")))
        {
            p.setUsername(EntradaSalida.entradaUsermane());
            p.setPassword(EntradaSalida.entradaString("Ingrese la nueva contraseña"));
        }
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
                   // rep.setSupervisor(buscarSupervisor());
                }
                if (1 == EntradaSalida.entradaInt(" Modificar zona:  \n 1 - Si \n 2 - No")) {
                    rep.setZona(EntradaSalida.entradaZona());
                }
                if (1 == EntradaSalida.entradaInt(" Modificar tipo de paquetes:  \n 1 - Si \n 2 - No")) {
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

    public Repartidor asignarArepartidorDisponible (Supervisor supervisor, Paquete paq)
    {
        Repartidor asignado = new Repartidor();
       // this.listadoRepartidores= filtrarRepartidoresDisponibles(repartidoresAcargo(supervisor));
        for(Repartidor r: this.listadoRepartidores)
        {
            if(r.getZona() == paq.getZonaEntrega() && r.getTiposPaquetes() == paq.getTiposPaquete())
            {
                if(r.getPaquetesAsignados().size() <=10)
                {
                    return r;
                }
            }

        }
        EntradaSalida.SalidaError("No se pudo asignar repartidor","ERROR");
        return asignado;
    }
    public Repartidor buscarRepartidorID (int id)
    {
        this.listadoRepartidores= repoRepartidor.listar();
        for(Repartidor r : this.listadoRepartidores)
        {
            if(r.getId()==id)
            {
                return r;
            }
        }
        return null;
    }

    public Repartidor buscarRepartidorLegajo (int legajo)
    {
        this.listadoRepartidores= repoRepartidor.listar();
        for(Repartidor r : this.listadoRepartidores)
        {
            if(r.getLegajo()==legajo)
            {
                return r;
            }
        }
        return null;
    }
    public Repartidor buscarRepartidor() throws InexistenteException {
        Repartidor buscado = new Repartidor();
        int opcion = EntradaSalida.entradaInt("  BUSCAR REPARTIDOR  \n  1 - Por DNI" +
                "\n  2 - Por ID\n  3 - Por Legajo");
        switch(opcion)
        {
            case 1:
                buscado = repoRepartidor.buscar((EntradaSalida.entradaDNI()));
                break;
            case 2:
                buscado = buscarRepartidorID(EntradaSalida.entradaInt("Ingrede el numerdo de ID"));
                break;
            case 3:
                buscado = buscarRepartidorLegajo(EntradaSalida.entradaInt("Ingrede el numerdo de legajo"));
                break;
            default:
                EntradaSalida.SalidaError("El numero ingresado es erroneo","ERROR");
                break;

        }
        if(buscado!=null)
        {
            return buscado;
        }else {
            throw new InexistenteException("Repartidor inexistente");
        }
    }

    public Repartidor buscarRepartidorAcargo (Supervisor supervisor)
    {
        repartidoresAcargo();
        Repartidor buscado= new Repartidor();
        try
        {
            buscado = buscarRepartidor();
        }catch(InexistenteException e)
        {
            EntradaSalida.SalidaError(e.getMessage(),"ERROR");
        }

        for(Repartidor r: this.listadoRepartidores)
        {
            if(r.equals(buscado))
            {
                return buscado;
            }
        }
        String msj= "El repartidor no esta a cargo del supervisor "+ supervisor.getNombre() +" "+ supervisor.getApellido();
        EntradaSalida.SalidaError(msj, "EMPLEADO NO A CARGO");
        return null;
    }

    public void asignarUnPaquete (Supervisor supervisor)
    {
        Paquete paq = new Paquete();
        try{
             paq = buscarPaquete();
        }catch(InexistenteException e)
        {
            EntradaSalida.SalidaError(e.getMessage(),"ERROR");
        }
       paq.setRepatidorAsignado(asignarArepartidorDisponible(supervisor,paq));
    }

    public void asignarPaquetesAutomaticamente (Supervisor supervisor)
    {
       // this.listadoRepartidores = filtrarRepartidoresDisponibles(repartidoresAcargo(supervisor));
        int cant = EntradaSalida.entradaInt("Ingrese cantidad de paquetes por repartidor");
        for(Repartidor r: this.listadoRepartidores)
        {
            asignarPaquetesRepartidor(r,cant);
            String mensaje = "Repartidor legajo: " + r.getLegajo() + " asignado";
            EntradaSalida.SalidaInformacion(mensaje,"ASIGNADO");
        }
    }

    public void asignarPaquetesRepartidor (Repartidor rep, int cant)
    {
        this.listadoPaquetes = filtrarPaquetesPorZona(listarPaquetesEnCorreo(),rep.getZona());
        this.listadoPaquetes = filtrarPaquetesPorTipo(this.listadoPaquetes,rep.getTiposPaquetes());
        int i=0;
        for(Paquete p: this.listadoPaquetes)
        {
            if(p.getRepatidorAsignado()==null)
            {
                if(cant <= i)
                {
                    p.setRepatidorAsignado(rep);
                    rep.getPaquetesAsignados().add(p);
                    cant++;
                }
            }
        }
        repoRepartidor.modificar(rep);
    }

    public boolean validarEstadoRepartidor (Repartidor rep) throws Exception {
        boolean validado=false;
        if(rep.getEstado()== EstadosEmpleado.DISPONIBLE)
        {
            validado=true;
        }else {
            throw new Exception("Repartidor no disponible");
        }
        return validado;
    }

    void asignarPaquetes (Supervisor supervisor)
    {
        int opcion = EntradaSalida.entradaInt(" ASIGNACION DE PAQUETES \n  1 - Asignacion automatica" +
                "\n  2 . Asignacion a un repartidor\n  3 . Asignacion manual");

        do{
            switch (opcion)
            {
                case 1:
                    String msj= "Confirmar asignacion automatica a repartidores de supervisor " +
                    supervisor.getNombre() +" "+ supervisor.getApellido() + "\n      1 - CONFIRMAR " +
                            "\n      2 - CANCELAR ";
                    if(1==EntradaSalida.entradaInt(msj))
                    {
                        asignarPaquetesAutomaticamente(supervisor);
                    }
                    break;
                case 2:
                    Repartidor buscado = buscarRepartidorAcargo(supervisor);
                    if(buscado!=null)
                    {
                        int cant = EntradaSalida.entradaInt("Ingrese la cantidad de paquetes a asignar");
                        asignarPaquetesRepartidor(buscado,cant);
                    }
                    break;
                case 3:
                    break;
                default:
                    EntradaSalida.SalidaError("El numero ingresado es erroneo","ERROR");
                    break;


            }
            opcion = EntradaSalida.entradaInt("CONTINUAR ASIGNANDO \n 1 - Si\n 2 - No");
        }while(opcion==1);

    }


    public ArrayList<Repartidor> filtrarRepartidoresDisponibles (ArrayList<Repartidor> listado)
    {
        ArrayList<Repartidor> repartidoresDisponibles = new ArrayList<>();
        for (Repartidor r: listado)
        {
            if(r.getEstado() == EstadosEmpleado.DISPONIBLE)
            {
                repartidoresDisponibles.add(r);
            }
        }
        return repartidoresDisponibles;
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
                   // empLocal.setSupervisor(buscarSupervisor());

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
    public ArrayList<Paquete> filtrarPaquetesPorTipo (ArrayList<Paquete> paquetes, TiposPaquete tipos)
    {
        ArrayList<Paquete> paquetesPorTipo = new ArrayList<>();
        for (Paquete p: paquetes)
        {
            if(p.getTiposPaquete() == tipos)
            {
                paquetesPorTipo.add(p);
            }
        }
        return paquetesPorTipo;
    }
    public ArrayList<Paquete> filtrarPaquetesPorZona (ArrayList<Paquete> paquetes, Zonas zona)
    {
        ArrayList<Paquete> paquetesEnZona = new ArrayList<>();
        for (Paquete p: paquetes)
        {
            if(p.getZonaEntrega() == zona)
            {
                paquetesEnZona.add(p);
            }
        }
        return paquetesEnZona;
    }

    public ArrayList<Paquete> filtrarPaquetesPorEstado (ArrayList<Paquete> paquetes, EstadosPaquete estado)
    {
        ArrayList<Paquete> paquetesEstado = new ArrayList<>();
        for (Paquete p: paquetes)
        {
            if(p.getEstado() == estado)
            {
                paquetesEstado.add(p);
            }
        }
        return paquetesEstado;
    }

    public ArrayList<Paquete> listarPaquetesEnCorreo ()
    {
        this.listadoPaquetes = repoPaquete.listar();
        ArrayList<Paquete> paquetesEnCorreo= new ArrayList<>();
        for (Paquete p: this.listadoPaquetes)
        {
            if(p.getEstado() == EstadosPaquete.EN_CORREO)
            {
                paquetesEnCorreo.add(p);
            }
        }
        return paquetesEnCorreo;
    }


    @Override
    public boolean modificarPaquete(int id) {

        Paquete aModificar = buscarPaqueteID(id);
        if(aModificar != null)
        {
            aModificar = modificarDatosPaquete(aModificar);
            return true;
        }else {
            try{
                aModificar = buscarPaquete();
                aModificar = modificarDatosPaquete(aModificar);
                return true;
            }catch(InexistenteException e)
            {
                EntradaSalida.SalidaError(e.getMessage(),"PAQUETE INEXISTENTE");
                return false;
            }
        }
    }

    public Paquete modificarDatosPaquete (Paquete aModificar)
    {
        int opcion=0;

        do {
            opcion = EntradaSalida.entradaInt("MODIFICAR PAQUETE \n  1- Cambiar remitente\n  " +
                    "2- Cambiar destinatario \n  3- Datos adicionales");
            switch (opcion) {
                case 1:
                    int continuar = 0;
                    do {
                        try {
                            aModificar.setRemitente(buscarCliente());
                            continuar = 1;

                        } catch (InexistenteException e) {

                            EntradaSalida.SalidaError("Ingrese un cliente valido", "ERROR");
                        }
                    } while (continuar == 1);
                    break;
                case 2:
                    aModificar.setDestinatario(EntradaSalida.entradaString("Ingrese el nuevo destinatario"));
                    aModificar.setDomicilioEntrega(EntradaSalida.entradaString("Ingrese nuevo domicilio de entrega"));
                    break;
                case 3:
                    aModificar = modificarDatosAdicionales(aModificar);
                    break;
                default:
                    EntradaSalida.SalidaError("Numero ingresado Erroneo", "ERROR");
                    break;
            }
            opcion = EntradaSalida.entradaInt("  CONTINUAR MODIFICANDO PAQUETE \n  1 - Si \n  2 - Finalizar");
        }while(opcion==1);
        return aModificar;
    }

    public Paquete modificarDatosAdicionales(Paquete paq)
    {
        if(1== EntradaSalida.entradaInt( " Modificar Tipo de Paquete:  \n 1 - Si \n 2 - No"))
        {
            paq.setTiposPaquete(EntradaSalida.entradaTipoPaquete());
        }
        if(1== EntradaSalida.entradaInt( " Modificar Zona de Entrega:  \n 1 - Si \n 2 - No"))
        {
            paq.setZonaEntrega(EntradaSalida.entradaZona());
        }
        if(1== EntradaSalida.entradaInt( " Modificar Estado de Paquete:  \n 1 - Si \n 2 - No"))
        {
            paq.setEstado(EntradaSalida.entradaEstadosPaquete());
        }
        if(1== EntradaSalida.entradaInt( " Modificar Repartidor Asignado:  \n 1 - Si \n 2 - No"))
        {
           // asignarArepartidorDisponible()
        }
        return paq;
    }

    @Override
    public void registroPaquete() {

        Paquete nuevo = new Paquete();
        int cliente=0;

        nuevo.setId((repoPaquete.buscarUltimoID())+1);
        nuevo.setCodigoIdentificacion(nuevoCogigoPaquete());
        nuevo.setFechaIngreso(LocalDateTime.now());
        do {
            try {
                nuevo.setRemitente(buscarCliente());
                cliente=1;

            } catch (InexistenteException e) {

                EntradaSalida.SalidaError("Ingrese un cliente valido","ERROR");
            }
        }while(cliente==1);
        nuevo.setTiposPaquete(EntradaSalida.entradaTipoPaquete());
        nuevo.setZonaEntrega(EntradaSalida.entradaZona());
        nuevo.setDestinatario(EntradaSalida.entradaString("Ingrese el destinatario"));
        nuevo.setDomicilioEntrega(EntradaSalida.entradaString("Ingrese el domicilio de entrega"));
        nuevo.setEstado(EntradaSalida.entradaEstadosPaquete());

        repoPaquete.agregar(nuevo);
    }

    @Override
    public void validacionCodigoPaquete(String codigo) throws CodigoPaqueteExistente {
        for (Paquete paquete: repoPaquete.listar()) {
            if (paquete.getCodigoIdentificacion().equals(codigo)){
                throw new CodigoPaqueteExistente("Codigo Paquete Existente");
            }
            break;
        }
    }

    @Override
    public String nuevoCogigoPaquete() {
        Boolean continuar = false;
        do
        {
            try{
                String codigo = EntradaSalida.CodigoPaquete();
                validacionCodigoPaquete(codigo);
                return codigo;
            }catch(CodigoPaqueteExistente e){

            }
        } while (!continuar);
        return null;
    }

    @Override
    public void verUnPaquete(String codigo) {

    }

    @Override
    public void verPaquetePorEstado(EstadosPaquete estadosPaquete) {

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

    public Paquete buscarPaquete() throws InexistenteException {
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
        if(buscado!=null)
        {
            return buscado;
        }else {
            throw new InexistenteException("Paquete inexistente");
        }
    }

    public void buscarVerPaquete()
    {
        try {
            Paquete buscado = buscarPaquete();
            String titulo = "                   P A Q U E T E : "+buscado.getCodigoIdentificacion();
            EntradaSalida.SalidaInformacion(buscado.toString(),titulo);
        }catch(InexistenteException e)
        {
            EntradaSalida.SalidaError(e.getMessage(),"ERROR");
         }
    }

    public void verPaquete(Paquete paq)
    {

        String titulo = "                   P A Q U E T E : "+paq.getCodigoIdentificacion();
        EntradaSalida.SalidaInformacion(paq.toString(),titulo);

    }
    public void mostrarListadoPaquetes (ArrayList<Paquete> listado)
    {
        for(Paquete p: listado)
        {
            verPaquete(p);
        }
    }
    public void mostrarPaquetes (Supervisor supervisor)
    {
        repartidoresAcargo();
        int opcion = EntradaSalida.entradaInt("  MOSTRAR PAQUETES  \n 1 - Buscar un paquete" +
                "\n 2 - Listar paquetes por estado\n 2 - Listar paquetes por repartidor");
        switch(opcion)
        {
            case 1:
                buscarVerPaquete();
                break;
            case 2:
                EstadosPaquete estado = EntradaSalida.entradaEstadosPaquete();
                mostrarListadoPaquetes(filtrarPaquetesPorEstado(this.listadoPaquetes,estado));
                break;
            case 3:
                Repartidor repartidor= buscarRepartidorAcargo(supervisor);
                mostrarListadoPaquetes(repartidor.getPaquetesAsignados());
                break;
            default:
                EntradaSalida.SalidaError("El numero ingresado es erroneo","ERROR");
                break;

        }
    }

    /// endregion

    /// region Metodos Cliente
    @Override
    public boolean modificarCliente(String dni) {

        int continuar=0;

        try {
            Cliente aModificar = buscarCliente();

            do {
                int opcion = EntradaSalida.entradaInt("      MODIFICAR " +
                        "\n 1 - Datos personales" +
                        "\n 2 - Direccion");

                switch (opcion) {
                    case 1:
                        aModificar = (Cliente) modificarPersona(aModificar);
                        break;

                    case 2:
                        aModificar.setDomicilio(EntradaSalida.entradaString("Ingrese el nuevo domicilio:"));
                        break;

                    default:
                        EntradaSalida.SalidaError("Numero ingresado incorrecto", "ERROR");
                        break;
                }
                continuar = EntradaSalida.entradaInt(" Continuar  \n 1 - Seguir modificando cliente \n 2 - Finalizar");

            } while (continuar == 1);

            repoClientes.modificar(aModificar);
        }catch(InexistenteException e) {

           EntradaSalida.SalidaError(e.getMessage(),"CLIENTE INEXISTENTE");
           return false;
        }
        return true;
    }

    public Cliente buscarCliente () throws InexistenteException {
        Cliente buscado = new Cliente();

        int opcion= EntradaSalida.entradaInt("BUSCAR CLIENTE \n 1 - Por DNI\n 2 - Por ID");
        switch (opcion)
        {
            case 1:
                String dni= EntradaSalida.entradaDNI();
                buscado = repoClientes.buscar(dni);
                break;

            case 2:

                int id= EntradaSalida.entradaInt("Ingrese el numero de ID");
                buscado = buscarClienteID(id);
                break;
            default:
                EntradaSalida.SalidaError("Numero ingresado incorrecto","ERROR");
                break;
          }
          if(buscado!=null)
          {
              return buscado;
          }else {
              throw new InexistenteException("Cliente inexistente");
          }
    }


    public Cliente buscarClienteID(int id)
    {
        this.listadoClientes = repoClientes.listar();
        for(Cliente c: this.listadoClientes)
        {
            if(c.getId()== id)
            {
                return c;
            }
        }
        return null;

    }

    @Override
    public void registroCliente() {
        Cliente nuevo = new Cliente();

        nuevo.setId(EntradaSalida.entradaInt("    NUEVO CLIENTE \nIngrese el ID:"));
        nuevo.setNombre(EntradaSalida.entradaString("Ingrese el nombre:"));
        nuevo.setApellido(EntradaSalida.entradaString("Ingrese el apellido:"));
        String dni =(EntradaSalida.entradaDNI());
        nuevo.setDni(dni);
        nuevo.setTelefono(EntradaSalida.entradaString("Ingrese el telefono:"));
        nuevo.setMail(EntradaSalida.entradaMail());
        nuevo.setTelefono(EntradaSalida.entradaString("Ingrese el telefono:"));
        nuevo.setUsername(EntradaSalida.entradaUsermane());
        nuevo.setPassword(dni);

        try
        {
            if(verificarClienteExistente(nuevo))
            {
                repoClientes.agregar(nuevo);
            }
        }catch(ExcepcionClienteExistente e)
        {
            EntradaSalida.SalidaError(e.getMessage(),"ERROR Cliente Existente");
        }

    }

    public boolean verificarClienteExistente (Cliente aVerificar) throws ExcepcionClienteExistente {
        this.listadoClientes = repoClientes.listar();

        if(!listadoClientes.contains(aVerificar))
        {
            return true;
        }else {
            throw new ExcepcionClienteExistente("“El cliente ya existe");
        }
    }



    /// endregion
}
