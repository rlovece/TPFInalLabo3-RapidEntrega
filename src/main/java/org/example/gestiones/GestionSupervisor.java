package org.example.gestiones;

import org.example.enums.EstadosEmpleado;
import org.example.enums.EstadosPaquete;
import org.example.enums.TiposPaquete;
import org.example.enums.Zonas;
import org.example.excepciones.CodigoPaqueteExistente;
import org.example.excepciones.ExcepcionClienteExistente;
import org.example.excepciones.InexistenteException;
import org.example.interfacesDeManejo.ManejoCliente;
import org.example.interfacesDeManejo.ManejoEmpleado;
import org.example.interfacesDeManejo.ManejoPaquete;
import org.example.models.*;
import org.example.recursos.EntradaSalida;
import org.example.repositorio.*;

import javax.swing.*;
import java.net.PasswordAuthentication;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class GestionSupervisor implements ManejoCliente, ManejoPaquete, ManejoEmpleado {


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

    /// region Constructores

    private GestionSupervisor() {
    }

    private GestionSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    /// endregion

    /// region Metodos Supervisor

    private void asignarSupervisor()
    {
        try{
            this.supervisor= buscarSupervisor();
        }catch(InexistenteException e)
        {
            EntradaSalida.SalidaError(e.getMessage(),"ERROR");
        }
    }
    private Supervisor buscarSupervisorLegajo(int legajo)
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

    private Supervisor buscarSupervisorID (int id)
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

    private Supervisor buscarSupervisor () throws InexistenteException {
        Supervisor buscado= new Supervisor();

        int opcion = EntradaSalida.entradaInt("""
                    BUSCAR SUPERVISOR \s
                  1 - Por ID
                  2 - Por Legajo
                  3 - Por DNI\
                """);

        switch (opcion) {
            case 1 -> buscado = buscarSupervisorID(EntradaSalida.entradaInt("Ingrese el numerdo de ID"));
            case 2 -> buscado = buscarSupervisorLegajo(EntradaSalida.entradaInt("Ingrese el numero de legajo"));
            case 3 -> buscado = repoSuper.buscar(EntradaSalida.entradaString("Ingrese el numero de DNI"));
            default -> EntradaSalida.SalidaError("El numero ingresado es erroneo", "ERROR");
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

    private ArrayList<EmpleadoLocal> empLocalAcargo()
    {
        ArrayList<EmpleadoLocal> aCargo = new ArrayList<>();
        this.listadoEmpLocal = repoEmpLocal.listar();
        for(EmpleadoLocal r: this.listadoEmpLocal)
        {
            if(r.getSupervisor().equals(this.supervisor))
            {
                if(this.supervisor.getEmpleadosACargo().contains(r))
                {

                }
                    aCargo.add(r);
            }
        }
        return aCargo;
    }
    private ArrayList<Repartidor> repartidoresAcargo()
    {
        ArrayList<Repartidor> aCargo = new ArrayList<>();
        this.listadoRepartidores = repoRepartidor.listar();
        for(Repartidor r: this.listadoRepartidores)
        {
            if(r.getSupervisor().equals(this.supervisor))
            {
                    aCargo.add(r);
            }
        }
        return aCargo;
    }

    private void empleadosAcargo ()
    {
        if(this.supervisor!=null)
        {
            ArrayList<Empleado> empleados = new ArrayList<>();
            empleados.addAll(repartidoresAcargo());
            empleados.addAll(empLocalAcargo());
            this.supervisor.setEmpleadosACargo(empleados);
        }else {
            EntradaSalida.SalidaError("No tiene un supervisor asignado","ERROR SUPERVISOR");
        }
    }

    private void verEmpleadosAcargo ()
    {
        if(this.supervisor!=null)
        {
            empleadosAcargo();
            for (Empleado e: this.supervisor.getEmpleadosACargo())
            {
                EntradaSalida.SalidaInformacion(e.toString(),"     E M P L E A D O ");
            }
        }else {
            EntradaSalida.SalidaError("No tiene un supervisor asignado","ERROR SUPERVISOR");
        }
    }


    private Empleado buscarEmpleadoAcargo () throws InexistenteException {
        Empleado buscado= new Empleado();

        int opcion = EntradaSalida.entradaInt("""
                    BUSCAR EMPLEADO \s
                  1 - Por ID
                  2 - Por Legajo
                  3 - Por DNI\
                """);

        switch (opcion) {
            case 1 -> buscado = buscarEmpleadoAcargoID(EntradaSalida.entradaInt("Ingrese el numero de ID"));
            case 2 -> buscado = buscarEmpleadoAcargoLegajo(EntradaSalida.entradaInt("Ingrese el numero de legajo"));
            case 3 -> buscado = buscarEmpleadoAcargoDNI(EntradaSalida.entradaString("Ingrese el numero de DNI"));
            default -> EntradaSalida.SalidaError("El numero ingresado es erroneo", "ERROR");
        }
        if(buscado!=null)
        {
            return buscado;
        }else {
            throw new InexistenteException("Empleado inexistente o no esta a su cargo");
        }
    }
    private Empleado buscarEmpleadoAcargoLegajo (int legajo)
    {
        empleadosAcargo ();
        for (Empleado e: this.supervisor.getEmpleadosACargo())
        {
            if (e.getLegajo()==legajo)
            {
                return e;
            }
        }
        return null;
    }
    private Empleado buscarEmpleadoAcargoID (int id)
    {
        empleadosAcargo ();
        for (Empleado e: this.supervisor.getEmpleadosACargo())
        {
            if (e.getId()==id)
            {
                return e;
            }
        }
        return null;
    }

    private Empleado buscarEmpleadoAcargoDNI (String dni)
    {
        empleadosAcargo ();
        for (Empleado e: this.supervisor.getEmpleadosACargo())
        {
            if (e.getDni().equals(dni))
            {
                return e;
            }
        }
        return null;
    }

    @Override
    public Empleado modificarEmpleado(String dni) {

        Empleado buscado = buscarEmpleadoAcargoDNI(dni);

        int opcion = EntradaSalida.entradaInt("""
                    MODIFICAR EMPLEADO \s
                  1 - Modificar nombre
                  2 - Modificar apellido
                  3 - Modificar telefono
                  4 - Modificar mail
                  5 - Modificar contraseña
                """);

        switch (opcion) {
            case 1 -> buscado.setNombre(EntradaSalida.entradaString("Ingrese el nuevo nombre"));
            case 2 -> buscado.setApellido(EntradaSalida.entradaString("Ingrese el nuevo apellido"));
            case 3 -> buscado.setTelefono(EntradaSalida.entradaString("Ingrese el nuevo telefono"));
            case 4 -> buscado.setMail(EntradaSalida.entradaMail());
            case 5 -> buscado.setPassword(EntradaSalida.entradaGeneracionPassword());
            default -> EntradaSalida.SalidaError("El numero ingresado es erroneo", "ERROR");
        }
        return buscado;
    }
    private boolean modificarEmpleadoAcargo () {

        int opcion = (EntradaSalida.entradaInt("MODIFICAR \n 1- Repartidor\n 2- Empleado local"));

        switch (opcion) {
            case 1 -> {
                Repartidor rep = modificarRepartidor();
                if (rep != null) {
                    repoRepartidor.modificar(rep);
                    return true;
                }
            }
            case 2 -> {
                EmpleadoLocal emp = modificarEmpleadoLocal();
                if (emp != null) {
                    repoEmpLocal.modificar(emp);
                    return true;
                }
            }
            default -> EntradaSalida.SalidaError("Numero ingresado incorrecto", "ERROR");
        }
        return false;
    }
    ///endregion

    ///region Metodos EmpleadoLocal
    private EmpleadoLocal modificarEmpleadoLocal ()
    {
        EmpleadoLocal empLocal = new EmpleadoLocal();
        int continuar=0;

        try{
            empLocal = (EmpleadoLocal) buscarEmpleadoAcargo();

        }catch(InexistenteException e)
        {
            String msj="El empleado no existe o no esta a su cargo";
            EntradaSalida.SalidaError(msj,"ERROR");
            return null;
        }

        do{
            int opcion = EntradaSalida.entradaInt("""
                      MODIFICAR\s
                 1 - Datos personales
                 2 - Modificar supervisor\
                """);

            switch (opcion) {
                case 1 -> empLocal = (EmpleadoLocal) modificarEmpleado(empLocal.getDni());
                case 2 -> {EntradaSalida.SalidaInformacion("Buscar nuevo supervisor a asignar", "Supervisor");
                    try {
                        empLocal.setSupervisor(buscarSupervisor());
                    } catch (InexistenteException e) {
                        EntradaSalida.SalidaError(e.getMessage(), "ERROR");
                    }}
                default -> EntradaSalida.SalidaError("Numero ingresado incorrecto", "ERROR");
            }
            continuar=EntradaSalida.entradaInt(" Continuar  \n 1 - Seguir modificando empleado \n 2 - Finalizar");

        }while(continuar==1);
        return empLocal;
    }
    /// endregion

    /// region Metodos Repartidor

    private Repartidor modificarDatosRepartidor (Repartidor rep)
    {
        int continuar =0;
        do{
                int opcion = EntradaSalida.entradaInt("""
                              MODIFICAR\s
                         1 - Cambiar supervisor
                         2 - Cambiar Zona de reparto
                         3 - Tipo de paquetes a entregar\
                        """);
            switch (opcion) {
                case 1 -> {
                    EntradaSalida.SalidaInformacion("Buscar nuevo supervisor a asignar", "Supervisor");
                    try {
                        rep.setSupervisor(buscarSupervisor());
                    } catch (InexistenteException e) {
                        EntradaSalida.SalidaError(e.getMessage(), "ERROR");
                    }
                }
                case 2 -> rep.setZona(EntradaSalida.entradaZona());
                case 3 -> rep.setTiposPaquetes(EntradaSalida.entradaTipoPaquete());
                default -> EntradaSalida.SalidaError("Numero ingresado incorrecto", "ERROR");
            }
            continuar=EntradaSalida.entradaInt(" Continuar  \n 1 - Seguir modificando Repartidor \n 2 - Finalizar");

        }while(continuar==1);

        return rep;
    }

    private Repartidor modificarRepartidor () //para usar en modificarEmpleadoAcargo
    {
        Repartidor rep = new Repartidor();
        int continuar=0, opcion =0;

        try{
            rep = (Repartidor) buscarEmpleadoAcargo();

        }catch(InexistenteException e)
        {
            String msj="El repartidor no existe o no esta a su cargo";
            EntradaSalida.SalidaError(msj,"ERROR");
            return null;
        }

        do{
           opcion = EntradaSalida.entradaInt("""
                      MODIFICAR\s
                 1 - Datos personales
                 2 - Datos repartidor\
                """);

            switch (opcion) {
                case 1 -> rep = (Repartidor) modificarEmpleado(rep.getDni());
                case 2 -> rep= modificarDatosRepartidor(rep);
                default -> EntradaSalida.SalidaError("Numero ingresado incorrecto", "ERROR");
            }
            continuar=EntradaSalida.entradaInt(" Continuar  \n 1 - Seguir modificando Repartidor \n 2 - Finalizar");

        }while(continuar==1);
        return rep;
    }

    private Repartidor buscarRepartidorAcargo ()
    {
        Repartidor buscado= new Repartidor();
        try{
            buscado = (Repartidor) buscarEmpleadoAcargo();
            return buscado;
        }catch(InexistenteException e)
        {
            EntradaSalida.SalidaError(e.getMessage(),"ERROR");
        }
        return null;
    }

    public ArrayList<Repartidor> filtrarRepartidoresDisponibles ()
    {
        ArrayList<Repartidor> repartidoresDisponibles = new ArrayList<>();
        empleadosAcargo();
        for (Empleado r: this.supervisor.getEmpleadosACargo())
        {
            if(r.getClass() == Repartidor.class)
            {
                if(r.getEstado() == EstadosEmpleado.DISPONIBLE)
                {
                    repartidoresDisponibles.add((Repartidor) r);
                }
            }
        }
        return repartidoresDisponibles;
    }

    ///endregion

    /// region Asignacion Paquetes-Repartidores

    private void asignarRepartidorDisponible (Paquete paq)
    {
        Repartidor asignado = new Repartidor();
        for(Repartidor r: filtrarRepartidoresDisponibles())
        {
            if(r.getZona() == paq.getZonaEntrega() && r.getTiposPaquetes() == paq.getTiposPaquete())
            {
                if(r.getPaquetesAsignados().size() <=10)
                {
                    r.getPaquetesAsignados().add(paq);
                    paq.setRepatidorAsignado(r);
                    repoRepartidor.modificar(r);
                    repoPaquete.modificar(paq);
                }
            }
        }
        EntradaSalida.SalidaError("No se pudo asignar repartidor","ERROR");
    }

    private void asignarUnPaquete ()
    {
        Paquete paq = new Paquete();
        try{
             paq = buscarPaquete();
             asignarRepartidorDisponible(paq);

        }catch(InexistenteException e)
        {
            EntradaSalida.SalidaError(e.getMessage(),"ERROR");
        }
    }

    private void asignarPaquetesRepartidor (Repartidor rep, int cant)
    {
        this.listadoPaquetes = new ArrayList<>();
        this.listadoPaquetes = filtrarPaquetesPorZona(filtrarPaquetesPorEstado(this.listadoPaquetes));
        this.listadoPaquetes = filtrarPaquetesPorTipo(this.listadoPaquetes);
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
    private void asignarPaquetesAutomaticamente ()
    {
        int cant = EntradaSalida.entradaInt("Ingrese cantidad de paquetes por repartidor");
        for(Repartidor r: filtrarRepartidoresDisponibles())
        {
            asignarPaquetesRepartidor(r,cant);
            String mensaje = "Repartidor legajo: " + r.getLegajo() + " asignado";
            EntradaSalida.SalidaInformacion(mensaje,"ASIGNADO");
        }
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
                        asignarPaquetesAutomaticamente();
                    }
                    break;
                case 2:
                    Repartidor buscado = buscarRepartidorAcargo();
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





    /// endregion

    /// region Metodos Paquete
    private ArrayList<Paquete> filtrarPaquetesPorTipo (ArrayList<Paquete> paquetes)
    {
        TiposPaquete tipos = EntradaSalida.entradaTipoPaquete();
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
    private ArrayList<Paquete> filtrarPaquetesPorZona (ArrayList<Paquete> paquetes)
    {
        Zonas zona= EntradaSalida.entradaZona();
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

    private ArrayList<Paquete> filtrarPaquetesPorEstado (ArrayList<Paquete> paquetes)
    {
        EstadosPaquete estado = EntradaSalida.entradaEstadosPaquete();
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

    @Override
    public boolean modificarPaquete(int id) {

        Paquete aModificar = buscarPaqueteID(id);
        if(aModificar != null)
        {
            aModificar = modificarDatosPaquete(aModificar);
            repoPaquete.modificar(aModificar);
            return true;
        }else {
            try{
                aModificar = buscarPaquete();
                aModificar = modificarDatosPaquete(aModificar);
                repoPaquete.modificar(aModificar);
                return true;
            }catch(InexistenteException e)
            {
                EntradaSalida.SalidaError(e.getMessage(),"PAQUETE INEXISTENTE");
                return false;
            }
        }
    }

    private Paquete modificarDatosPaquete (Paquete aModificar)
    {
        int opcion=0, continuar=0;

        do {
            opcion = EntradaSalida.entradaInt("""
                         MODIFICAR PAQUETE\s
                      1- Cambiar remitente
                      2- Cambiar destinatario\s
                      3- Datos adicionales""");
            switch (opcion) {
                case 1 -> {
                    do {
                        try {
                            aModificar.setRemitente(buscarCliente());
                            continuar = 1;

                        } catch (InexistenteException e) {

                            EntradaSalida.SalidaError("Ingrese un cliente valido", "ERROR");
                        }
                    } while (continuar == 1);
                }
                case 2 -> {
                    aModificar.setDestinatario(EntradaSalida.entradaString("Ingrese el nuevo destinatario"));
                    aModificar.setDomicilioEntrega(EntradaSalida.entradaString("Ingrese el nuevo domicilio de entrega"));
                }
                case 3 -> aModificar = modificarDatosAdicionales(aModificar);
                default -> EntradaSalida.SalidaError("Numero ingresado Erroneo", "ERROR");
            }
            opcion = EntradaSalida.entradaInt("  CONTINUAR MODIFICANDO PAQUETE \n  1 - Si \n  2 - Finalizar");
        }while(opcion==1);
        return aModificar;
    }

    private Paquete modificarDatosAdicionales(Paquete paq)
    {
        int opcion =0, continuar =0;

        do{
            opcion= EntradaSalida.entradaInt("    MODIFICAR \n 1 - Tipo de Paquete \n 2 - Zona de entrega \n 3 - Estado del paquete \n 4 - Asignar repartidor disponible");

            switch (opcion) {
                case 1 -> paq.setTiposPaquete(EntradaSalida.entradaTipoPaquete());
                case 2 -> paq.setZonaEntrega(EntradaSalida.entradaZona());
                case 3 -> paq.setEstado(EntradaSalida.entradaEstadosPaquete());
                case 4 -> asignarRepartidorDisponible(paq);
                default -> EntradaSalida.SalidaError("El numero ingresado es incorrecto", "ERROR");
            }
            continuar=EntradaSalida.entradaInt("CONTINUAR \n 1 - Continuar modificando paquete\n 2 - Finalizar");
        }while(continuar ==1);
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
        nuevo.setEstado(EstadosPaquete.EN_CORREO);
        repoPaquete.agregar(nuevo);
        if(1== EntradaSalida.entradaInt("Asignar repartidor disponible\n 1 - AHORA \n 2 - LUEGO"))
        {
            asignarRepartidorDisponible(nuevo);
        }
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

    public void verPaquete(Paquete paq)
    {
        String titulo = "                   P A Q U E T E : "+paq.getCodigoIdentificacion();
        EntradaSalida.SalidaInformacion(paq.toString(),titulo);

    }
    @Override
    public void verUnPaquete(String codigo) {
         Paquete paq = repoPaquete.buscar(codigo);

        if(paq!=null) {
            verPaquete(paq);
        }else {
            EntradaSalida.SalidaError("Paquete inexistente","ERROR");
        }
    }
    @Override
    public void verPaquetePorEstado(EstadosPaquete estadosPaquete) {
        for(Paquete p : filtrarPaquetesPorEstado(repoPaquete.listar()))
        {
            verPaquete(p);
        }
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

    private Paquete buscarPaquete() throws InexistenteException {
        Paquete buscado = new Paquete();
        int opcion = EntradaSalida.entradaInt("""
                  BUSCAR PAQUETE \s
                 1 - Por codigo de identificacion
                 2 - Por ID\
                """);
        switch (opcion) {
            case 1 ->
                    buscado = repoPaquete.buscar((EntradaSalida.entradaString("Ingrese el codigo de identificacion")));
            case 2 -> buscado = buscarPaqueteID(EntradaSalida.entradaInt("Ingrese el numero de ID del paquete"));
            default -> EntradaSalida.SalidaError("El numero ingresado es erroneo", "ERROR");
        }
        if(buscado!=null)
        {
            return buscado;
        }else {
            throw new InexistenteException("Paquete inexistente");
        }
    }

    private void mostrarListadoPaquetes (ArrayList<Paquete> listado)
    {
        for(Paquete p: listado)
        {
            verPaquete(p);
        }
    }
    private void mostrarPaquetes ()
    {
        empleadosAcargo();
        int opcion = EntradaSalida.entradaInt("""
                          MOSTRAR PAQUETES \s
                 1 - Buscar un paquete
                 2 - Listar paquetes por estado
                 2 - Listar paquetes por repartidor\
                """);
        switch (opcion) {
            case 1 -> {
                try {
                    Paquete paq = buscarPaquete();
                    verPaquete(paq);
                } catch (InexistenteException e) {
                    EntradaSalida.SalidaError(e.getMessage(), "ERROR");
                }
            }
            case 2 -> mostrarListadoPaquetes(filtrarPaquetesPorEstado(this.listadoPaquetes));
            case 3 -> {
                try {
                    Repartidor repartidor = (Repartidor) buscarEmpleadoAcargo();
                    mostrarListadoPaquetes(repartidor.getPaquetesAsignados());
                } catch (InexistenteException e) {
                    EntradaSalida.SalidaError(e.getMessage(), "ERROR");
                }
            }
            default -> EntradaSalida.SalidaError("El numero ingresado es erroneo", "ERROR");
        }
    }

    /// endregion

    /// region Metodos Cliente
    @Override
    public void registroCliente() {
        Cliente nuevo = new Cliente();

        nuevo.setId((repoClientes.buscarUltimoID())+1);
        nuevo.setNombre(EntradaSalida.entradaString("    NUEVO CLIENTE \nIngrese el nombre:"));
        nuevo.setApellido(EntradaSalida.entradaString("Ingrese el apellido:"));
        String dni =(EntradaSalida.entradaDNI());
        nuevo.setDni(dni);
        nuevo.setTelefono(EntradaSalida.entradaString("Ingrese el telefono:"));
        nuevo.setMail(EntradaSalida.entradaMail());
        nuevo.setUsername(EntradaSalida.entradaUsermane("Ingrese el nombre de usuario:"));
        EntradaSalida.SalidaInformacion("Se asigno su DNI como contraseña","CONTRASEÑA");
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

    private boolean verificarClienteExistente (Cliente aVerificar) throws ExcepcionClienteExistente {

        if(repoClientes.buscar(aVerificar.getDni())!=null)
        {
            return true;
        }else {
            throw new ExcepcionClienteExistente("“El cliente ya existe");
        }
    }

    private Cliente modificarDatosCliente(Cliente buscado) {

        int opcion = EntradaSalida.entradaInt("""
                       MODIFICAR DATOS \s
                  1 - Modificar nombre
                  2 - Modificar apellido
                  3 - Modificar telefono
                  4 - Modificar mail
                  5 - Modificar contraseña
                """);

        switch (opcion) {
            case 1 -> buscado.setNombre(EntradaSalida.entradaString("Ingrese el nuevo nombre"));
            case 2 -> buscado.setApellido(EntradaSalida.entradaString("Ingrese el nuevo apellido"));
            case 3 -> buscado.setTelefono(EntradaSalida.entradaString("Ingrese el nuevo telefono"));
            case 4 -> buscado.setMail(EntradaSalida.entradaMail());
            case 5 -> buscado.setPassword(EntradaSalida.entradaGeneracionPassword());
            default -> EntradaSalida.SalidaError("El numero ingresado es erroneo", "ERROR");
        }
        return buscado;
    }
    @Override
    public boolean modificarCliente(String dni) {

        int continuar=0, opcion=0;
        Cliente aModificar;

        try {
            aModificar = buscarCliente();
        }catch(InexistenteException e) {

            EntradaSalida.SalidaError(e.getMessage(),"CLIENTE INEXISTENTE");
            return false;
        }

            do {
                opcion = EntradaSalida.entradaInt("""
                              MODIFICAR\s
                         1 - Datos personales
                         2 - Direccion\
                        """);

                switch (opcion) {
                    case 1 -> aModificar = modificarDatosCliente(aModificar);
                    case 2 -> aModificar.setDomicilio(EntradaSalida.entradaString("Ingrese el nuevo domicilio:"));
                    default -> EntradaSalida.SalidaError("Numero ingresado incorrecto", "ERROR");
                }
                continuar = EntradaSalida.entradaInt(" Continuar  \n 1 - Seguir modificando cliente \n 2 - Finalizar");

            } while (continuar == 1);
            repoClientes.modificar(aModificar);

        return true;
    }

    private Cliente buscarCliente () throws InexistenteException {
        Cliente buscado = new Cliente();

        int opcion= EntradaSalida.entradaInt("BUSCAR CLIENTE \n 1 - Por DNI\n 2 - Por ID");
        switch (opcion) {
            case 1 -> buscado = repoClientes.buscar( EntradaSalida.entradaDNI());
            case 2 -> buscado = buscarClienteID(EntradaSalida.entradaInt("Ingrese el numero de ID"));
            default -> EntradaSalida.SalidaError("Numero ingresado incorrecto", "ERROR");
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
    public void registroEmpleado() {

    }

    /// endregion

    /// region METODOS DESCARTADOS

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

    private Repartidor buscarRepartidorID (int id)
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

    private Repartidor buscarRepartidorLegajo (int legajo)
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
    private Repartidor buscarRepartidor() throws InexistenteException {
        Repartidor buscado = new Repartidor();
        int opcion = EntradaSalida.entradaInt("""
                   BUSCAR REPARTIDOR \s
                  1 - Por DNI
                  2 - Por ID
                  3 - Por Legajo\
                """);
        switch (opcion) {
            case 1 -> buscado = repoRepartidor.buscar((EntradaSalida.entradaDNI()));
            case 2 -> buscado = buscarRepartidorID(EntradaSalida.entradaInt("Ingrese el numerdo de ID"));
            case 3 -> buscado = buscarRepartidorLegajo(EntradaSalida.entradaInt("Ingrese el numero de legajo"));
            default -> EntradaSalida.SalidaError("El numero ingresado es erroneo", "ERROR");
        }
        if(buscado!=null)
        {
            return buscado;
        }else {
            throw new InexistenteException("Repartidor inexistente");
        }
    }

    /// endregion
}
