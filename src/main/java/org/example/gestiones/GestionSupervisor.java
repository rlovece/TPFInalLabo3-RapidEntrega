package org.example.gestiones;
import org.example.enums.*;
import org.example.excepciones.*;
import org.example.interfacesDeManejo.*;
import org.example.models.*;
import org.example.recursos.EntradaSalida;
import org.example.repositorio.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oriana Dafne Lucero
 */
public class GestionSupervisor implements ManejoCliente, ManejoPaquete, ManejoEmpleado {


    ///region Atributos

    public Supervisor supervisor;
    public SupervisorRepo repoSuper = new SupervisorRepo();
    public ArrayList<Supervisor> listadoSupervisores;
    public PaqueteRepo repoPaquete = new PaqueteRepo();
    public ArrayList<Paquete> listadoPaquetes;
    public RepartidorRepo repoRepartidor = new RepartidorRepo();
    public ArrayList<Repartidor> listadoRepartidores;
    public EmpleadoLocalRepo repoEmpLocal = new EmpleadoLocalRepo();
    public ArrayList<EmpleadoLocal> listadoEmpLocal;
    public ArrayList<Empleado> empleadosAcargo;
    public ClientesRepo repoClientes = new ClientesRepo();
    public ArrayList<Cliente> listadoClientes;

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

    /**
     * <h2>Buscar supervisor por LEGAJO</h2>
     * Busca y retorna un Supervisor desde su numero de legajo
     * @param legajo Se debe ingresar el numero de legajo del supervisor buscado
     * @return un objeto Supervisor
     * Creado para ser usado en metodo {@link GestionSupervisor#buscarSupervisor()}
     */
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

    /**
     * <h2>Buscar supervisor por ID</h2>
     * Busca y retorna un Supervisor desde su numero de ID
     * @param id Se debe ingresar el ID del supervisor buscado
     * @return un objeto Supervisor
     * Creado para ser usado en metodo {@link GestionSupervisor#buscarSupervisor()}
     */
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

    /**
     * <h2>Buscar supervisor</h2>
     * Busqueda de un Supervisor por ID, legajo o DNI
     * Se debe seleccionar una opcion de busqueda
     * @return Si se encuentra en supervisor lo retorna
     * @throws InexistenteException si no encuentra el supervisor lanza un error
     */
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

    /**
     * <h2>Cambiar contraseña</h2>
     * El supervisor podra cambiar su contraseña ingresando su numero de DNI
     * @return true si se cambio la contrseña / false si no encontro el Supervisor por DNI
     */
    private boolean cambiarContrasenia()
    {
        EntradaSalida.SalidaInformacion("Reingrese su numero de DNI","CAMBIAR CONTRASEÑA");
        String dni= EntradaSalida.entradaDNI();
        try
        {
            Supervisor buscado = repoSuper.buscar(dni);
            EntradaSalida.SalidaInformacion("Ingresara a cambiar su contraseña","CAMBIAR CONTRASEÑA");
            buscado.setPassword(EntradaSalida.entradaGeneracionPassword());
            repoSuper.modificar(buscado);
            return true;
        }catch(InexistenteException e)
        {
            EntradaSalida.SalidaError(e.getMessage(),"ERROR");
        }
        return false;
    }

    ///endregion

    /// region Metodos Empleados a cargo

    /**
     * <h2>  Empleados del Local a cargo</h2>
     * Utiliza el atributo Supervisor de la clase para comparar los empleados
     * a su cargo y asignarlos a una lista
     * @return lista de tipo EmpleadoLocal con los empleados a cargo
     */
    private ArrayList<EmpleadoLocal> empLocalAcargo()
    {
        ArrayList<EmpleadoLocal> aCargo = new ArrayList<>();
        this.listadoEmpLocal = repoEmpLocal.listar();
        for(EmpleadoLocal r: this.listadoEmpLocal)
        {
            if(r.getSupervisor().equals(this.supervisor))
            {
                aCargo.add(r);
            }
        }
        return aCargo;
    }

    /**
     * <h2>Repartidores a cargo</h2>
     * Utiliza el atributo Supervisor de la clase para comparar los repartidores
     * a su cargo y asignarlos a una lista
     * @return lista de tipo Repartidor con los repartidores a su cargo
     */
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

    /**
     * <h2>Empleados a cargo</h2>
     * Utiliza el atributo Supervisor de la clase para llamar a los metodos
     * {@link GestionSupervisor#repartidoresAcargo()} y {@link GestionSupervisor#empLocalAcargo()}
     * y cargar en el mismo atributo la lista de empleados a cargo
     */
    private void empleadosAcargo ()
    {
        if(this.supervisor!=null)
        {
            this.empleadosAcargo = new ArrayList<>();
            this.empleadosAcargo.addAll(repartidoresAcargo());
            this.empleadosAcargo.addAll(empLocalAcargo());
            this.supervisor.setEmpleadosACargo(this.empleadosAcargo);
        }else {
            EntradaSalida.SalidaError("No tiene un supervisor asignado","ERROR SUPERVISOR");
        }
    }

    /**
     * <h2>Ver empleados a cargo</h2>
     * Muestra toda la lista de empleados a cargo que contenga el atributo
     * Supervisor en esta clase, llama a {@link GestionSupervisor#empleadosAcargo}
     * para asignar dicha lista si se encuentra cargado el supervisor
     */
    private void verEmpleadosAcargo () {

        StringBuilder listadoEmp = new StringBuilder();
        if (this.supervisor != null) {
            empleadosAcargo();
            for (Empleado e : this.supervisor.getEmpleadosACargo())
            {

                listadoEmp.append(e.toStringListar());

            }
        } else {
            EntradaSalida.SalidaError("No tiene un supervisor asignado", "ERROR SUPERVISOR");
        }
        EntradaSalida.SalidaInformacion(listadoEmp.toString(), "     E M P L E A D O S");
    }

    /**
     * <h2>Buscar un empleado a cargo</h2>
     * Busca un empleado a cargo del supervisor indicado en el atributo de esta clase
     * debe elegir la opcion de buscarlo por ID, legajo o DNI, llama a los metodos
     * {@link GestionSupervisor#buscarEmpleadoAcargoID} {@link GestionSupervisor#buscarEmpleadoAcargoLegajo(int)}
     * @return objeto clase Empleado con el empleado buscado
     * @throws InexistenteException si no lo encuentra lanza una excepcion
     */
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

    /**
     * <h2>Buscar empleado a cargo por LEGAJO</h2>
     * Busca un empleado a cargo del supervisor asignado a traves de su legajo
     * Se llama al metodo {@link GestionSupervisor#empleadosAcargo()} para
     * asegurar que solo se busque dentro de los empleados del supervisor
     * @param legajo del empleado buscado
     * @return objeto clase Empleado
     */
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

    /**
     * <h2>Buscar empleado a cargo por ID</h2>
     * Busca un empleado a cargo del supervisor asignado a traves de su ID
     * Se llama al metodo {@link GestionSupervisor#empleadosAcargo()} para
     * asegurar que solo se busque dentro de los empleados del supervisor
     * @param id del empleado buscado
     * @return objeto clase Empleado
     */
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

    /**
     * <h2>Buscar empleado a cargo por DNI</h2>
     * Busca un empleado a cargo del supervisor asignado a traves de su DNI
     * Se llama al metodo {@link GestionSupervisor#empleadosAcargo()} para
     * asegurar que solo se busque dentro de los empleados del supervisor
     * @param dni Ingresar el DNI del empleado
     * @return objeto clase Empleado
     */
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

    /**
     * <h2>Modificar empleado</h2>
     * Busca un empleado validado anteriormente y muestra un menu para modificar sus datos
     * generado para ser utilizada dentro de metodos {@link GestionSupervisor#modificarRepartidor()} y
     * {@link GestionSupervisor#modificarEmpleadoLocal()}
     * donde se debe validar con anterioridad la existencia del empleado indicado por parametro
     * @param dni del empleado a modificar
     * @return objeto de clase empleado con sus atributos modificados
     */
    @Override
    public Empleado modificarEmpleado(String dni) {

        Empleado buscado = buscarEmpleadoAcargoDNI(dni);
        int opcion=0;

        do {
             opcion = EntradaSalida.entradaInt("""
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
                case 3 -> buscado.setTelefono(EntradaSalida.entradaTelefono());
                case 4 -> buscado.setMail(EntradaSalida.entradaMail());
                case 5 -> buscado.setPassword(EntradaSalida.entradaGeneracionPassword());
                default -> EntradaSalida.SalidaError("El numero ingresado es erroneo", "ERROR");
            }
            opcion=EntradaSalida.entradaInt("CONTINUAR \n 1 - Continuar modificando empleado\n 2 - Finalizar");
        }while(opcion==1);
        return buscado;
    }

    /**
     *  <h2>Modificar empleado a cargo</h2>
     * Llama a los metodos {@link GestionSupervisor#modificarRepartidor()}} y
     * {@link GestionSupervisor#modificarEmpleadoLocal()} y a los repositorios
     * para guardar las modificaciones realizadas en los archivos
     * @return true si el empleado se modifico en el archivo
     */
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

    /**
     * <h2>Modificar empleado de local</h2>
     * Busca y modifica un empleado de local a cargo
     * @return objeto EmpleadoLocal modificado
     */
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
    /**
     * <h2>Modificar datos del repartidor</h2>
     * Modifica los datos de los atributos Repartidor
     * @return objeto Repartidor modificado
     * @see Repartidor
     * @author Oriana Dafne Lucero
     */

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

    /**
     * <h2>Modificar repartidor</h2>
     * Busca y modifica los datos de un repartidor a cargo, creado para ser
     * utilizado en metodo {@link GestionSupervisor#modificarEmpleadoAcargo()}
     * @return objeto Repartidor modificado
     * @see Repartidor
     * @author Oriana Dafne Lucero
     */
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

    /*** <h2>Buscar repartidor a cargo</h2>
     * Busca un repartidor a cargo del Supervisor asignado
     * en el atributo Supervisor de esta clase
     * @return objeto Repartidor si lo encuentra
     * @see Repartidor
     * @author Oriana Dafne Lucero
     */
    private Repartidor buscarRepartidorAcargo ()
    {
        Repartidor buscado= new Repartidor();
        try{
            String dni = EntradaSalida.entradaDNI();
            buscado = repoRepartidor.buscar(dni);
            if(buscado.getSupervisor().equals(this.supervisor))
            {
                EntradaSalida.SalidaInformacion("Empelado a su cargo","REPARTIDOR");
            }
            return buscado;
        }catch(InexistenteException e)
        {
            EntradaSalida.SalidaError(e.getMessage(),"ERROR");
        }
        return null;
    }

    ///endregion

    /// region Asignacion Paquetes-Repartidores

    /**
     * <h2>Asignar repartidor disponible</h2>
     * Recibe un paquete validado con anterioridad y le asigna un repartidor disponible
     * en la zona de entrega, que reparta el tipo de paquete y aun cuente con
     * capacidad disponible. Guarda la asignacion en los archivos de cada clase Repartidor y Paquete
     * @see Repartidor
     * @see Paquete
     * @return true si se pudo asignar repartidor / false si no se pudo asignar
     * @author Oriana Dafne Lucero
     */
    private boolean asignarRepartidorDisponible (Paquete paq)
    {
        ArrayList<Paquete> paquetes = new ArrayList<>();
        ArrayList<Repartidor> disponibles = repoRepartidor.listar();

        for(Repartidor r: disponibles)
        {
            if(r.getZona().equals(paq.getZonaEntrega()) && r.getTiposPaquetes().equals(paq.getTiposPaquete()))
            {

                    paq.setRepatidorAsignado(r);
                    paq.setEstado(EstadosPaquete.ASIGNADO_PARA_REPARTO);
                    paquetes.add(paq);
                    r.setPaquetesAsignados(paquetes);
                    repoPaquete.modificar(paq);
                    return true;

            }
        }
        EntradaSalida.SalidaError("No se pudo asignar repartidor","ERROR");
        return false;
    }

    /**
     * <h2>Asignar un paquete</h2>
     * Busca un paquete y le asigna un repartidor disponible. Indica por mensajes
     * si se pudo asignar correctamente. Llama a los metodos {@link GestionSupervisor#buscarPaquete()}
     * y {@link GestionSupervisor#asignarRepartidorDisponible(Paquete)}
     * @see Repartidor
     * @see Paquete
     * @author Oriana Dafne Lucero
     */
    private void asignarUnPaquete ()
    {
        Paquete paq = new Paquete();
        try{
            // paq = buscarPaquete();
            paq = repoPaquete.buscar(EntradaSalida.entradaString("Ingrese el codigo del paquete"));
             if(asignarRepartidorDisponible(paq))
             {
                 EntradaSalida.SalidaInformacion("Se asigno el paquete","PAQUETE ASIGNADO");
             }

        }catch(InexistenteException e)
        {
            EntradaSalida.SalidaError(e.getMessage(),"ERROR");
        }
    }

    /**
     * <h2>Asignar paquetes a un repartidor</h2>
     * Recibe un Repartidor y le asigna un listado de paquetes del estado indicado
     * por parametros que coincidan en su zona y tipo de paquetes que reparta.
     * Creado para ser usado en {@link GestionSupervisor#asignarPorRepartidor()}
     * @param rep Repartidor al que se le asignaran los paquetes
     * @param cant Cantidad de paquetes a asignar
     * @param estado Estado de los paquetes a buscar para la asignacion
     * @see Repartidor
     * @see Paquete
     * @author Oriana Dafne Lucero
     */
    private boolean asignarPaquetesRepartidor (Repartidor rep, int cant, EstadosPaquete estado)
    {
        ArrayList<Paquete> paquetes =new ArrayList<>();
        ArrayList<Paquete> listado = repoPaquete.listar();
        int i=0;

        for(Paquete p : listado)
        {
                if(p.getTiposPaquete().equals(rep.getTiposPaquetes()) && p.getZonaEntrega().equals(rep.getZona()))
                {
                    p.setEstado(EstadosPaquete.ASIGNADO_PARA_REPARTO);
                    p.setRepatidorAsignado(rep);
                    repoPaquete.modificar(p);
                    paquetes.add(p);
                    i++;
                    if(cant == i)
                    {
                        rep.setPaquetesAsignados(paquetes);
                        return true;
                    }
                }

        }
        if(0<cant && cant<i)
        {
            rep.setPaquetesAsignados(paquetes);
            return true;
        }
        return false;
    }

    /**
     * <h2>Asignar por repartidor</h2>
     * Busca un repartidor a cargo, valida que se encuentre en estado disponible
     * y le asigna una lista de paquetes de la cantidad y estado que le indiquemos
     * Lanzara mensaje de error en caso de no encontrar el repartidor o que no este en estado disponible
     * @see Repartidor
     * @see Paquete
     * @author Oriana Dafne Lucero
     */
    private void asignarPorRepartidor()
    {
            Repartidor buscar = buscarRepartidorAcargo();
            try {
                if(buscar.getEstado().equals(EstadosEmpleado.DISPONIBLE)){

                int cant = EntradaSalida.entradaInt("Ingrese la cantidad de paquetes a asignar");
                EstadosPaquete estado = EntradaSalida.entradaEstadosPaquete();
                if(asignarPaquetesRepartidor(buscar,cant,estado)){
                EntradaSalida.SalidaInformacion("Paquetes asignados correctamente","ASIGNACION");
                }}
            }catch(Exception e)
            {
                EntradaSalida.SalidaError(e.getMessage(),"ERROR");
            }
    }



    /**
     * <h2>Asignar paquetes automaticamente</h2>
     * Solicita ingresar cantidad de paquetes a asignar a cada repartidor y el estado
     * de los paquetes a asignar. Luego asigna a todos los repartidores disponibles a
     * cargo del Supervisor un listado de paquetes
     * @see Repartidor
     * @see Paquete
     * @author Oriana Dafne Lucero
     */
    private void asignarPaquetesAutomaticamente ()
    {
        int cant = EntradaSalida.entradaInt("Ingrese cantidad de paquetes por repartidor");
       // EstadosPaquete estado = EntradaSalida.entradaEstadosPaquete();
        ArrayList<Repartidor> listado = repoRepartidor.listar();
        for(Repartidor r: listado)
        {
            if(asignarPaquetesRepartidor(r,cant,EstadosPaquete.EN_CORREO)){
            String mensaje = "Repartidor legajo: " + r.getLegajo() + " asignado";
            EntradaSalida.SalidaInformacion(mensaje,"ASIGNADO");}
        }
    }

    /**
     * <h2>Menu para asignacion de paquetes</h2>
     * Brinda tres opciones de asignacion:
     * 1- Automatica a todos los repartidores a cargo
     * 2- Paquetes a un repartidor
     * 3- Buscar un paquete y asignarlo a un repartidor disponible
     * @param supervisor supervisor a cargo de los repartidores a asignar
     * @see Paquete
     * @author Oriana Dafne Lucero
     */
    void asignarPaquetes (Supervisor supervisor)
    {
        int opcion=0;
        do{
            opcion = EntradaSalida.entradaInt("""
                      ASIGNACION DE PAQUETES\s
                  1 - Asignacion automatica a repartidores
                  2 . Asignacion de paquetes a un repartidor
                  3 . Asignacion de un paquete\
                """);
            switch (opcion) {
                case 1 -> {
                    String msj = "Confirmar asignacion automatica a repartidores de supervisor " +
                            supervisor.getNombre() + " " + supervisor.getApellido() + "\n      1 - CONFIRMAR " +
                            "\n      2 - CANCELAR ";
                    int opc= EntradaSalida.entradaInt(msj);
                    if (1 == opc ) {
                        asignarPaquetesAutomaticamente();
                    }else if(2 == opc)
                    {
                        EntradaSalida.SalidaInformacion("Se cancela la asignacion","CANCELADA");
                    }
                }
                case 2 -> asignarPorRepartidor();
                case 3 -> asignarUnPaquete();
                default -> EntradaSalida.SalidaError("El numero ingresado es erroneo", "ERROR");
            }
            opcion = EntradaSalida.entradaInt("CONTINUAR ASIGNANDO \n 1 - Si\n 2 - No");
        }while(opcion==1);

    }





    /// endregion

    /// region Metodos Paquete

    /**
     * <h2>Filtrar paquetes por Tipo</h2>
     * Recibe un listado de paquetes y filtra los paquetes del listado que sean
     * del tipo indicado por parametro
     * @param paquetes Listado a filtrar
     * @param tipos Enum del Tipo de paquete a buscar
     * @return listado con los paquetes del tipo indicado
     * @author Oriana Dafne Lucero
     */
    private ArrayList<Paquete> filtrarPaquetesPorTipo (ArrayList<Paquete> paquetes, TiposPaquete tipos)
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

    /**
     * <h2>Filtrar paquetes por Zona</h2>
     * Recibe un listado de paquetes y filtra los paquetes del listado que sean
     * de la zona indicada por parametro
     * @param paquetes Listado a filtrar
     * @param zona Enum del Tipo de paquete a buscar
     * @return listado con los paquetes de la zona indicada
     * @author Oriana Dafne Lucero
     */
    private ArrayList<Paquete> filtrarPaquetesPorZona (ArrayList<Paquete> paquetes, Zonas zona)
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

    /**
     * <h2>Filtrar paquetes por Estado</h2>
     * Recibe un listado de paquetes y filtra los paquetes del listado que se encuentren
     * en el estado indicado por parametro
     * @param paquetes Listado a filtrar
     * @param estado Enum del Estado de paquete a buscar
     * @return listado con los paquetes del estado indicado
     * @see EstadosPaquete
     * @author Oriana Dafne Lucero
     */
    private ArrayList<Paquete> filtrarPaquetesPorEstado (ArrayList<Paquete> paquetes, EstadosPaquete estado)
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

    /**
     * <h2>Modificar Paquete</h2>
     * Menu con distintos datos del paquete a modificar que se recibe por parametro
     * validado con anterioridad. Guarda las modificaciones realizas en el paquete en el archivo
     * @param aModificar Paquete que sera modificado
     * @return true
     */
    @Override
    public boolean modificarPaquete(Paquete aModificar) {

        int opcion=0, continuar=0;

        do {
            opcion = EntradaSalida.entradaInt("""
                         MODIFICAR PAQUETE\s
                      1- Cambiar remitente
                      2- Cambiar destinatario\s
                      3- Datos adicionales""");
            switch (opcion) {
                case 1 -> cambiarRemitente(aModificar);
                case 2 -> {
                    aModificar.setDestinatario(EntradaSalida.entradaString("Ingrese el nuevo destinatario"));
                    aModificar.setDomicilioEntrega(EntradaSalida.entradaString("Ingrese el nuevo domicilio de entrega"));
                    repoPaquete.modificar(aModificar);
                }
                case 3 -> aModificar = modificarDatosAdicionales(aModificar);
                default -> EntradaSalida.SalidaError("Numero ingresado Erroneo", "ERROR");
            }
            opcion = EntradaSalida.entradaInt("  CONTINUAR MODIFICANDO PAQUETE \n  1 - Si \n  2 - Finalizar");
        }while(opcion==1);
        return true;
    }

    private void cambiarRemitente (Paquete paq)
    {
        int continuar =0;
        do {
            try {
                Cliente aBuscar= buscarCliente();
                paq.setRemitente(aBuscar);
                continuar = 1;
                repoPaquete.modificar(paq);

            } catch (InexistenteException e) {

                EntradaSalida.SalidaError("Ingrese un cliente valido", "ERROR");
            }
        } while (continuar != 1);
    }

    /**
     * <h2>Modificar datos adicionales PAQUETE</h2>
     * Recibe un Paquete por parametro y permite modificar su tipo,
     * zona de entrega, estado y repartidor asignado
     * @param paq Paquete validado con anterioridad
     * @return Paquete modificado
     * @see Paquete
     * @author Oriana Dafne Lucero
     */
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
        repoPaquete.modificar(paq);
        return paq;
    }

    /**
     * <h2>Alta de nuevo Paquete</h2>
     * Permite registrar un nuevo paquete. Asigna ID, Codigo de Paquete y fecha
     * de ingreso automaticamente. Consulta si desea asignar un repartidor disponible
     * al momento del alta o luego.
     * @see Paquete
     * @see Repartidor
     * @author Oriana Dafne Lucero
     */
    @Override
    public void registroPaquete() {

        String dni;
        int cliente=0;
        Paquete nuevo= new Paquete();
         do {
             EntradaSalida.SalidaInformacion("Debera ingresar el DNI del remitente para cargar el paquete","ATENCION");
             dni=EntradaSalida.entradaDNI();
             try {
                 nuevo.setRemitente(repoClientes.buscar(dni));
                 cargarPaquete(nuevo);
             }catch(InexistenteException e)
             {
             EntradaSalida.SalidaError("Ingrese un cliente valido","ERROR");
            }
             cliente = EntradaSalida.entradaInt("1 - Cargar otro paquete\n2 - Finalizar");
        }while(cliente==1);

    }

    private void cargarPaquete (Paquete nuevo)
    {
        int cliente=0;

        nuevo.setId((repoPaquete.buscarUltimoID())+1);
        nuevo.setCodigoIdentificacion(nuevoCogigoPaquete());

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String formattedString = localDate.format(formatter);

        nuevo.setFechaIngreso(formattedString);
        nuevo.setTiposPaquete(EntradaSalida.entradaTipoPaquete());
        nuevo.setZonaEntrega(EntradaSalida.entradaZona());
        nuevo.setDestinatario(EntradaSalida.entradaString("Ingrese el destinatario"));
        nuevo.setDomicilioEntrega(EntradaSalida.entradaString("Ingrese el domicilio de entrega"));
        nuevo.setEstado(EstadosPaquete.EN_CORREO);
        repoPaquete.agregar(nuevo);
        if(1== EntradaSalida.entradaInt("Asignar repartidor disponible\n 1 - AHORA \n 2 - LUEGO"))
        {
            if(asignarRepartidorDisponible(nuevo))
            {
                EntradaSalida.SalidaInformacion("El repartidor se asigno con exito","REPARTIDOR ASIGNADO");
            }
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

    @Override
    public void verUnPaquete(String codigo) {
        try {
            Paquete paq = repoPaquete.buscar(codigo);
            EntradaSalida.SalidaError(paq.toString(),"");
        } catch (InexistenteException e){
            EntradaSalida.SalidaError("Paquete inexistente","ERROR");
        }
    }

    /**
     * <h2>Ver listado Paquetes de un estado</h2>
     * Muestra en pantalla el listado de todos los paquetes en el estado
     * recibido por parametro
     * @param estadosPaquete Enum estado de los paquetes a listar
     * @see Paquete
     */
    @Override
    public void verPaquetePorEstado(EstadosPaquete estadosPaquete) {
        EstadosPaquete estado = EntradaSalida.entradaEstadosPaquete();

        StringBuilder listado=new StringBuilder();
        for(Paquete p : filtrarPaquetesPorEstado(repoPaquete.listar(),estado))
        {
            listado.append(p.toStringListar());

        }
        String mensaje= "PAQUETES EN ESTADO: "+ estadosPaquete;
        EntradaSalida.SalidaInformacion(listado.toString(),mensaje);
    }

    /**
     * <h2>Ver listado Paquetes de una Zona</h2>
     * Muestra en pantalla el listado de todos los paquetes en la zona
     * que es ingresada por teclado
     * @see Paquete
     * @author Oriana Dafne Lucero
     */
    private void verPaquetePorZona() {
        Zonas zona= EntradaSalida.entradaZona();
        StringBuilder listado=new StringBuilder();
        for(Paquete p : filtrarPaquetesPorZona(repoPaquete.listar(),zona))
        {
            listado.append(p.toStringListar());
        }
        String mensaje= "PAQUETES EN ZONA: "+ zona;
        EntradaSalida.SalidaInformacion(listado.toString(),mensaje);
    }

    /**
     * <h2>Ver listado Paquetes de un Tipo</h2>
     * Muestra en pantalla el listado de todos los paquetes del tipo
     * que es ingresado por teclado
     * @see Paquete
     * @author Oriana Dafne Lucero
     */
    private void verPaquetePorTipo() {
        TiposPaquete tipos = EntradaSalida.entradaTipoPaquete();
        StringBuilder listado=new StringBuilder();
        for(Paquete p : filtrarPaquetesPorTipo(repoPaquete.listar(),tipos))
        {
            listado.append(p.toStringListar());
        }
        String mensaje= "PAQUETES DEL TIPO: "+ tipos;
        EntradaSalida.SalidaInformacion(listado.toString(),mensaje);
    }

    /**
     * <h2>Ver listado Paquetes de un Repartidor</h2>
     * Busca un repartidor y muestra en pantalla el listado de todos los paquetes
     * que tiene asignados. Si no se encuentra el repartidor lanza un mensaje de error
     * Tambien lanzara mensaje de error si no tiene paquetes asignados
     * @see Paquete
     * @see Repartidor
     * @author Oriana Dafne Lucero
     */
    private void verPaquetesRepartidor ()
    {
        try {
            Repartidor aBuscar = (Repartidor) buscarEmpleadoAcargo();
            if (aBuscar.getPaquetesAsignados() != null) {
                StringBuilder listado=new StringBuilder();
                for (Paquete p : aBuscar.getPaquetesAsignados()) {
                    listado.append(p.toStringListar());
                }
                String mensaje= "PAQUETES ASIGNADOS";
                EntradaSalida.SalidaInformacion(listado.toString(),mensaje);
            } else {
                EntradaSalida.SalidaError("No contiene paquetes asignados", "ERROR");
            }
        }catch(InexistenteException e)
        {
            EntradaSalida.SalidaError(e.getMessage(),"ERROR");
        }
    }

    /**
     * <h2>Buscar Paquete por ID</h2>
     * Busca y retorna un paquete con el ID indicado por parametro, si no lo encuentra
     * retorna null. Creado para ser usado en {@link GestionSupervisor#buscarPaquete()}
     * @param id numero de ID del paquete a buscar
     * @return objeto Paquete encontrado
     * @see Paquete
     * @author Oriana Dafne Lucero
     */
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

    /**
     * <h2>Buscar Paquete </h2>
     * Busca y retorna un paquete, permite la busqueda por ID o por codigo
     * de identificacion del paquete. Si no se encuentra lanza una expecion
     * @return objeto Paquete encontrado
     * @see Paquete
     * @exception InexistenteException Si no encuentra en paquete buscado lanza una excpecion
     * @author Oriana Dafne Lucero
     */
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
        listado.toString();
    }
    /// endregion

    /// region Metodos Cliente

    /**
     * <h2>Alta nuevo Cliente</h2>
     * Solicita todos los datos necesarios para dar de alta un nuevo cliente. El numero de ID
     * y la contraseña son asignados automaticamente. Valida que no exista otro cliente con
     * el mismo numero de DNI. Si no existe guarda el nuevo cliente en el archivo
     * @see Cliente
     */
    @Override
    public void registroCliente() {
        Cliente nuevo = new Cliente();

        nuevo.setId((repoClientes.buscarUltimoID()) + 1);
        nuevo.setNombre(EntradaSalida.entradaString("    NUEVO CLIENTE \nIngrese el nombre:"));
        nuevo.setApellido(EntradaSalida.entradaString("Ingrese el apellido:"));
        String dni = (EntradaSalida.entradaDNI());
        nuevo.setDni(dni);
        nuevo.setTelefono(EntradaSalida.entradaTelefono());
        nuevo.setMail(EntradaSalida.entradaMail());
        nuevo.setDomicilio(EntradaSalida.entradaString("Ingrese la direccion"));
        nuevo.setUsername(EntradaSalida.entradaUsermane());
        EntradaSalida.SalidaInformacion("Se asigno su DNI como contraseña", "CONTRASEÑA");
        nuevo.setPassword(dni);



            if (!verificarClienteExistente(dni)) {
                repoClientes.agregar(nuevo);
                EntradaSalida.SalidaInformacion("El cliente se registro correctamente","CLIENTE DADO DE ALTA");
            } else {
                EntradaSalida.SalidaError("El cliente ya existe en el registro", "ERROR Cliente Existente");
            }

    }

    private boolean verificarClienteExistente(String dni){
        try{
            repoClientes.buscar(dni);
            return true;

        }catch(InexistenteException e){
            EntradaSalida.SalidaAdvertencia(e.getMessage(),"ERROR");
        }
        return false;
    }

    /**
     * <h2>Modificar datos Cliente</h2>
     * Permite modificar los datos ( Nombre - Apellido - Telefono - Mail - Contraseña) del
     * Cliente pasado por parametro. Usado en metodo {@link GestionSupervisor#modificarCliente}
     * @param buscado Cliente ya validado para modificar
     * @return objeto Cliente modificado
     * @see Cliente
     * @author Oriana Dafne Lucero
     */

    private Cliente modificarDatosCliente(Cliente buscado) {

        int opcion=0;

        do {
            opcion = EntradaSalida.entradaInt("""
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
                case 3 -> buscado.setTelefono(EntradaSalida.entradaTelefono());
                case 4 -> buscado.setMail(EntradaSalida.entradaMail());
                case 5 -> buscado.setPassword(EntradaSalida.entradaGeneracionPassword());
                default -> EntradaSalida.SalidaError("El numero ingresado es erroneo", "ERROR");
            }
            opcion = EntradaSalida.entradaInt("CONTINUAR \n 1 - Continuar modificando Cliente\n 2 - Finalizar");
        }while(opcion==1);
        return buscado;
    }

    /**
     * <h2>Modificar Cliente</h2>
     * Menu para modificar datos del cliente. Una vez modificado guarda
     * el cliente en el archivo
     * @param aModificar Cliente validado anteriormente para modificarlo
     * @return objeto Paquete encontrado
     * @see Cliente
     * @author Oriana Dafne Lucero
     */
    @Override
    public boolean modificarCliente(Cliente aModificar) {

        int continuar=0, opcion=0;
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

    /**
     * <h2>Buscar cliente</h2>
     * Permite la busqueda de un cliente por su DNI o ID
     * Si no lo encuentra lanza una excepcion
     * @return Cliente si es encontrado
     * @throws InexistenteException si no encuentra el cliente
     */
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

    /**
     * <h2>Busca Cliente ID</h2>
     * Busca y retorna un cliente a partir de su ID
     * generado para ser usado en {@link GestionSupervisor#buscarCliente()}
     * @param id ID del cliente a buscar
     * @return objeto Cliente si lo encuentra / null si no lo encuentra
     * @author Oriana Dafne Lucero
     */
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

    /**
     * <h2>Ver Cliente</h2>
     * Busca y muestra en pantalla un cliente
     */
    private void verCliente ()
    {
        try {
            Cliente buscar = buscarCliente();
            EntradaSalida.SalidaInformacion(buscar.toString(), "   C L I E N T E");
        }catch (InexistenteException e)
        {
            EntradaSalida.SalidaError(e.getMessage(),"ERROR");
        }
    }
    @Override
    public void registroEmpleado() {

    }

    /// endregion


    /// region MENUS

    /**
     * <h2>LOGUEO SUPERVISOR</h2>
     * Permite el ingreso al sistema de un supervisor ingresando su DNI
     * y su contraseña registrada en el sistema. Una vez validada ingresara
     * al menu supervisor
     * @return true si pudo acceder al menuSupervisor
     * @see GestionSupervisor#menuGestionSupervisor(Supervisor)
     * @author Oriana Dafne Lucero
     */
    public boolean logueo(){

        EntradaSalida.SalidaInformacion("Ingrese con su numero de DNI","   LOGUEO SUPERVISOR");
        String dni = EntradaSalida.entradaDNI();
        try {
            Supervisor sup= repoSuper.buscar(dni);
            String password = EntradaSalida.entradaString("Ingrese la contraseña");
            if(sup.getPassword().equals(password))
            {
                menuGestionSupervisor(sup);
                return true;
            }else
            {
                EntradaSalida.SalidaError("Contraseña erronea","ERROR");
            }
        } catch (InexistenteException e) {
            EntradaSalida.SalidaError(e.getMessage(),"DNI INEXISTENTE");
        }
        return false;
    }

    /**
     * <h2>Menu Gestion Supervisor</h2>
     * Una vez que el Supervisor se logue en el sistema llegara por parametro
     * para ser asignado al atributo Supervisor y permitir gestionar dentro del menu
     * Empleados - Clientes - Paquetes - Cambio de ocntraseña
     * @param sup Supervisor logueado en el sistema
     */
    public void menuGestionSupervisor(Supervisor sup){

        this.supervisor =sup;

        int opcion = 0;
        do {
            opcion = EntradaSalida.entradaInt("""
                          SELECCIONE UNA OPCION \s
                     1 - Gestionar Empleados
                     2 - Gestionar Paquetes
                     3 - Gestionar Clientes
                     4 - Cambiar contraseña

                     0 - Salir
                    """);

            switch (opcion) {
                case 1 -> menuEmpleados(sup);
                case 2 -> menuPaquetes(sup);
                case 3 -> menuClientes(sup);
                case 4 -> {
                    if (cambiarContrasenia()) {
                        EntradaSalida.SalidaInformacion("Constraseña cambiada existosamente", "CAMBIO CONTRASEÑA");
                    } else {
                        EntradaSalida.SalidaError("No se pudo cambiar la constraseña", "ERROR");
                    }
                }
                default -> {
                }
            }
        } while (opcion!=0);
    }

    public void menuEmpleados(Supervisor sup){

        this.supervisor =sup;

        int opcion = 0;
        do {
            opcion = EntradaSalida.entradaInt("""
                          SELECCIONE UNA OPCION \s
                     1 - Ver empleados a cargo
                     2 - Modificar datos empleados

                     0 - Salir
                    """);

            switch (opcion) {
                case 1 -> menuVerEmpleados(sup);
                case 2 -> {
                    if (modificarEmpleadoAcargo()) {
                        EntradaSalida.SalidaInformacion("El empleado se modifico correctamente", "EMPLEADO MODIFICADO");
                    } else {
                        EntradaSalida.SalidaError("No se pudo modificar empleado", "ERROR");
                    }
                }
                default -> {
                }
            }
        } while (opcion!=0);
    }

    public void menuVerEmpleados (Supervisor sup)
    {
        this.supervisor =sup;

        int opcion = 0;
        do {
            opcion = EntradaSalida.entradaInt("""
                          SELECCIONE UNA OPCION \s
                     1 - Buscar un empleado
                     2 - Ver todos los empleados a cargo

                     0 - Salir
                    """);
            switch (opcion) {
                case 1 -> {
                    try {
                        Empleado aBuscar = buscarEmpleadoAcargo();
                        EntradaSalida.SalidaInformacion(aBuscar.toString(), "   E M P L E A D O ");
                    } catch (InexistenteException e) {
                        EntradaSalida.SalidaError(e.getMessage(), "ERROR");
                    }
                }
                case 2 -> verEmpleadosAcargo();
                default -> {
                }
            }
        } while (opcion!=0);
    }

    public void menuPaquetes (Supervisor sup)
    {
        this.supervisor =sup;

        int opcion = 0;
        do {
            opcion = EntradaSalida.entradaInt("""
                          SELECCIONE UNA OPCION \s
                     1 - Ver paquetes
                     2 - Alta nuevo paquete
                     3 - Modificar Paquete
                     4 - Asignar paquetes

                     0 - Salir
                    """);

            switch (opcion) {
                case 1 -> menuVerPaquetes(sup);
                case 2 -> registroPaquete();
                case 3 -> {
                    try {
                        Paquete aModificar = buscarPaquete();
                        modificarPaquete(aModificar);
                    } catch (InexistenteException e) {
                        EntradaSalida.SalidaError(e.getMessage(), "PAQUETE INEXISTENTE");
                    }
                }
                case 4 -> asignarPaquetes(sup);
                default -> {
                }
            }
        } while (opcion!=0);

    }

    public void menuVerPaquetes (Supervisor sup)
    {
        this.supervisor =sup;

        int opcion = 0;
        do {
            opcion = EntradaSalida.entradaInt("""
                        V E R P A Q U E T E S \s
                     1 - Ver un paquete
                     2 - Ver paquetes por estado
                     3 - Ver paquetes por tipo
                     4 - Ver paquetes por zona
                     5 - Ver paquetes por repartidor

                     0 - Salir
                    """);

            switch (opcion) {
                case 1 -> {
                    try {
                        Paquete paq = buscarPaquete();
                        EntradaSalida.SalidaInformacion(paq.toString(),"");
                    } catch (InexistenteException e) {
                        EntradaSalida.SalidaError(e.getMessage(), "Error");
                    }
                }
                case 2 -> verPaquetePorEstado(EstadosPaquete.EN_CORREO);
                case 3 -> verPaquetePorTipo();
                case 4 -> verPaquetePorZona();
                case 5 -> verPaquetesRepartidor();
                default -> {
                }
            }
        } while (opcion!=0);
    }

    public void menuClientes (Supervisor sup)
    {
        this.supervisor =sup;

        int opcion = 0;
        do {
            opcion = EntradaSalida.entradaInt("""
                        SELECCIONE UNA OPCION \s
                     1 - Ver un cliente
                     2 - Dar de alta un cliente
                     3 - Modificar un cliente

                     0 - Salir
                    """);

            switch (opcion) {
                case 1 -> verCliente();
                case 2 -> registroCliente();
                case 3 -> { try {
                                 Cliente buscado = buscarCliente();
                   if(modificarCliente(buscado))
                   {
                       EntradaSalida.SalidaInformacion("El cliente fue modificado con existo","CLIENTE MODIFICADO");
                   }
                }catch(InexistenteException e)
                {
                    EntradaSalida.SalidaError(e.getMessage(),"ERROR");
                }
                }
                default -> {
                }
            }
        } while (opcion!=0);
    }

    ///endregion
}
