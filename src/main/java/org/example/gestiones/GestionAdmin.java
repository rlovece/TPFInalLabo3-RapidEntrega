package org.example.gestiones;

import org.example.enums.EstadosEmpleado;
import org.example.enums.EstadosPaquete;
import org.example.excepciones.CodigoPaqueteExistente;
import org.example.excepciones.InexistenteException;
import org.example.excepciones.PasswordInvalida;
import org.example.interfacesDeManejo.ManejoEmpleado;
import org.example.interfacesDeManejo.ManejoPaquete;
import org.example.models.*;
import org.example.recursos.EntradaSalida;
import org.example.repositorio.*;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GestionAdmin implements ManejoPaquete, ManejoEmpleado {

    ///region Atributos (los repositorios)
    PaqueteRepo paqueteRepo = new PaqueteRepo();
    SupervisorRepo supervisorRepo = new SupervisorRepo();
    RepartidorRepo repartidorRepo = new RepartidorRepo();
    EmpleadoLocalRepo empleadoLocalRepo = new EmpleadoLocalRepo();
    ClientesRepo clientesRepo = new ClientesRepo();
    ArrayList<Empleado> listaEmpleados = new ArrayList<Empleado>();

    //endregion ()

    public GestionAdmin() {
        this.listaEmpleados.addAll(supervisorRepo.listar());
        this.listaEmpleados.addAll(repartidorRepo.listar());
        this.listaEmpleados.addAll(empleadoLocalRepo.listar());
    }


    //region Logueo
    /**
     * <h2>Logueo Admin</h2>
     *Solicita la contraseña que debe ser ingresada por teclado, es capturada con EntradaSalida,
     *y debe igual a 123Admin. Ppara verificar esto, se invoca al método
     *{@link GestionAdmin#validarPassword(String)}.
     * <p>
     * Se encierra el pedido de la contraseña en un try-catch y se muestra el error en caso de
     * capturarse una excepción
     *
     * @see EntradaSalida
     * return true o false
     * @author Ruth Lovece
     */
    public boolean logueo(){
        try {
            String password = EntradaSalida.entradaString("Ingrese la contraseña");
            validarPassword(password);
            menuPrincipal();
            return true;
        } catch (NullPointerException e) {
            return false;
        } catch (PasswordInvalida e) {
            EntradaSalida.SalidaError("\nContraseña incorrecta\n", "Error");
            return false;
        }
    }
    //endregion

    ///region Menu Principal
    /**
     * <h2>Menu Principal de Gestion Administrador</h2>
     *Muentra dentro de un ciclo do-while las opciones principales que tiene el administrador para realizar su gestión, lee el ingreso por teclado
     *con JOptionPane, e ingresa al switch correspondiente donde se invocan otros metodos para continuar con la gestión.
     *El ciclo se repite hasta que el usuario admin cierre sesión con la opción 0.
     *
     * @see EntradaSalida
     * @author Ruth Lovece
     */
    public void menuPrincipal(){
        int opcion = 0;
        do {
            opcion = EntradaSalida.entradaInt("      ELIJA UNA OPCION  \n" +
                    "\n 1 - Manejar Paquetes" +
                    "\n 2 - Manejar Empleados" +
                    "\n 3 - Manejar Clientes" +
                    "\n 0 - Cerrar Sesión\n\n");

            switch (opcion){
                case 1:
                    menuManejoPaquetes();
                    break;

                case 2:
                    menuManejoEmpleados();
                    break;

                case 3:
                    menuManejoClientes();
                    break;


                default:
                    break;
            }
        } while (opcion!=0);
    }
    ///endregion

    //region Manejo Paquetes
    /**
     * <h2>Menu Gestión de Paquetes para Administrador</h2>
     *Muentra dentro de un ciclo do-while las opciones que tiene el administrador sobre los paquetes,
     *lee el ingreso por teclado con EntradaSalida,
     * e ingresa al switch correspondiente donde se invocan otros metodos para continuar con la gestión.
     *El ciclo se repite hasta que el usuario admin ingrese la opción 0 para volver al
     * {@link GestionAdmin#menuPrincipal()}.
     *El administrador no tiene opciones que tenga el supervisor, salvo las de ver o listar.
     *
     * @see EntradaSalida
     * @author Ruth Lovece
     */
    public void menuManejoPaquetes(){
        int opcion = 0;
        String codigoPaquete = null;
        do {
            opcion = EntradaSalida.entradaInt("      ELIJA UNA OPCION  \n" +
                    "\n 1 - Ver Paquetes por estado" +
                    "\n 2 - Ver un Paquete" +
                    "\n 3 - Modificar un Paquete" +
                    "\n 0 - Volver\n\n");

            switch (opcion){
                case 1:
                    EstadosPaquete estado = EntradaSalida.entradaEstadosPaquete();
                    verPaquetePorEstado(estado);
                    break;

                case 2:
                    codigoPaquete = EntradaSalida.entradaString("Ingrese codigo del paquete\n\n");
                    verUnPaquete(codigoPaquete);
                    break;

                case 3:
                    codigoPaquete = EntradaSalida.entradaString("Ingrese codigo del paquete\n\n");
                    try {
                        Paquete aModificar = paqueteRepo.buscar(codigoPaquete);
                        modificarPaquete(aModificar);
                    } catch (InexistenteException e){
                        EntradaSalida.SalidaError("Codigo incorrecto\n\n", "Error");
                    }
                    break;

                default:
                    break;
            }
        } while (opcion!=0);
    }

    /**
     * <h2>Ver paquetes por estado</h2>
     * Muestra el listado de todos los paquetes que se encuentren en la lista del archivo según método
     * {@link PaqueteRepo#listar()}, que tengan como atributo el estado ingresado por parametro utilizando EntradaSalida
     *
     * @see EntradaSalida
     * @see PaqueteRepo
     * @param estadoPaquetes
     * @author Ruth Lovece
     */
    @Override
    public void verPaquetePorEstado(EstadosPaquete estadoPaquetes) {
        ArrayList<Paquete> paquetesEstado = new ArrayList<>();
        for (Paquete paquete : paqueteRepo.listar()) {
            if (paquete.getEstado() == estadoPaquetes) {
                paquetesEstado.add(paquete);
            }
        }
        EntradaSalida.SalidaInformacion(estadoPaquetes.toString(), ("Paquetes " + estadoPaquetes.toString()));
    }

    /**
     * <h2>Ver un paquete</h2>
     * Muestra un paquete con EntradaSalida según el codigo ingresado por pamametro utilizando el método
     * {@link PaqueteRepo#buscar(String)}
     *
     * @see EntradaSalida
     * @see PaqueteRepo
     * @param codigoIdentificacion
     * @author Ruth Lovece
     */
    @Override
    public void verUnPaquete(String codigoIdentificacion) {
        try {
            Paquete buscado = paqueteRepo.buscar(codigoIdentificacion);
            String titulo = "Paquete " + buscado.getCodigoIdentificacion();
            EntradaSalida.SalidaInformacion(buscado.toString(), titulo);
        } catch (InexistenteException e) {
            EntradaSalida.SalidaError("Codigo incorrecto\n\n", "Error");
        }

    }


    ///region ModificarPaquete
    /**
     * <h2Modificar Paquete por Administrador</h2>
     * Método implementado de interfaz manejoDePaquetes según su roll, se utiliza un menú con EntradaSalida
     * para darles las opciones. Además se ivoca a través de su instancia a PaqueteRepo para que se reflejen
     * las modificaciones en el archivo.
     *
     * @see EntradaSalida
     * @see PaqueteRepo
     * @see ManejoPaquete
     * @param aModificar
     * @author Ruth Lovece
     */
    @Override
    public boolean modificarPaquete(Paquete aModificar){
        int opcion = EntradaSalida.entradaInt("      ELIJA UNA OPCION  \n" +
                "\n 1 - Regresar a Correo" +
                "\n 2 - Quitar del archivo" +
                "\n 3 - Marcar como Entregado" +
                "\n 0 - Volver\n\n");

        switch (opcion){
            case 1:
                aModificar.setEstado(EstadosPaquete.EN_CORREO);
                aModificar.setRepatidorAsignado(null);
                paqueteRepo.modificar(aModificar);
                break;

            case 2:
                /// habría que agregar msj alerta
                paqueteRepo.eliminar(aModificar.getId());
                break;

            case 3:
                aModificar.setEstado(EstadosPaquete.ENTREGADO);
                paqueteRepo.modificar(aModificar);
                break;

            default:
                break;
        }
        return true;
    }

    /**
     * <h2Validación Codigo de Paquete</h2>
     * Método que lanza exepción en caso de que se quiera asginar un código de paquete que ya exista en el archivo.
     * El codigo a verificar se ingresa por parámetro.
     * En caso de ya existir se lanza la expción.
     *
     * @param codigo
     * @exception CodigoPaqueteExistente
     * @author Ruth Lovece
     */
    @Override
    public void validacionCodigoPaquete(String codigo) throws CodigoPaqueteExistente {
        for (Paquete paquete: paqueteRepo.listar()) {
            if (paquete.getCodigoIdentificacion().equals(codigo)){
                throw new CodigoPaqueteExistente("Codigo Paquete Existente");
            }
            break;
        }
    }
    ///endregion

    //regionNO Implementadas porque no es tarea de administrador
    @Override
    public void registroPaquete() {}
    @Override
    public String nuevoCogigoPaquete (){return null;}
    //endregion

    //endregion

    //region Manejo Empleados
    /**
     * <h2>Menu Gestión de Empleados para Administrador</h2>
     *Muentra dentro de un ciclo do-while las opciones que tiene el administrador sobre los empleados,
     *lee el ingreso por teclado con EntradaSalida,
     *e ingresa al switch correspondiente donde se invocan otros metodos para continuar con la gestión.
     *El ciclo se repite hasta que el usuario admin ingrese la opción 0 para volver al
     *{@link GestionAdmin#menuPrincipal()}.
     *
     *
     * @see EntradaSalida
     * @author Ruth Lovece
     */
    public void menuManejoEmpleados(){
        int opcion = 0;
        String codigoPaquete = null;
        do {
            opcion = EntradaSalida.entradaInt("      ELIJA UNA OPCION  \n" +
                    "\n 1 - Ver Empleado" +
                    "\n 2 - Lista Completa Empleado" +
                    "\n 3 - Cargar Empleado" +
                    "\n 4 - Dar de Baja Empleado" +
                    "\n 5 - Ascender Empleado" +
                    "\n 6 - Modificar Empleado" +
                    "\n 0 - Volver\n\n");
            switch (opcion){
                case 1:
                    buscarEmpleado();
                    break;

                case 2:
                    listaCompletaEmpleados();
                    break;

                case 3:
                    registroEmpleado();
                    break;

                case 4:
                    bajaEmpleado(EntradaSalida.entradaInt("Ingrese número de legajo"));
                    break;

                case 5:
                    ascenderEmpleado();
                    break;

                case 6:
                    modificarEmpleado(EntradaSalida.entradaDNI());
                    break;

                default:
                    break;
            }
        } while (opcion!=0);
    }

    ///regionVer Un empleado
    /**
     * <h2>Menu Buscar un empleado para Administrador</h2>
     *Muentra las opciones que tiene el administrador para buscar un empleado,
     *lee el ingreso por teclado con EntradaSalida,
     *e ingresa al switch correspondiente donde se invocan otros metodos para continuar con la gestión.
     *Luego se vuelve al menú anterior.
     *
     * @see EntradaSalida
     * @author Ruth Lovece
     */
    public void buscarEmpleado(){
        Empleado aMostrar;
        int opcion = EntradaSalida.entradaInt("      ELIJA UNA OPCION  \n" +
                "\n 1 - Buscar por DNI" +
                "\n 2 - Buscar por Legajo" +
                "\n 0 - Volver\n\n");
        switch (opcion) {
            case 1:
                String dni = EntradaSalida.entradaDNI();
                try {
                    aMostrar = buscarEmpleadoPorDNI(dni);
                    EntradaSalida.SalidaInformacion(aMostrar.toString(),"El empleado");
                } catch (InexistenteException e) {
                    EntradaSalida.SalidaError(e.getMessage(), "Error");
                }

                break;

            case 2:
                int legajo = EntradaSalida.entradaInt("Ingrese el número de Legajo");
                try {
                    aMostrar= buscarEmpleadoPorLegajo(legajo);
                    EntradaSalida.SalidaInformacion(aMostrar.toString(),"El empleado");
                } catch (InexistenteException e) {
                    EntradaSalida.SalidaError(e.getMessage(), "Error");
                }
                break;

            default:
                break;
        }
    }

    /**
     * <h2>Lista completa Empleads</h2>
     *Método mostrar la lista completa de empleados.
     *
     * @author Ruth Lovece
     */
    public void listaCompletaEmpleados(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Empleado empleado: this.listaEmpleados) {
            stringBuilder.append(empleado.toStringListar());
        }
        EntradaSalida.SalidaInformacion(String.valueOf(stringBuilder), "Listado completo de empleados");
    }

    /**
     * <h2>Buscar empleado por DNI para Administrador</h2>
     *Método para buscar un empleado por DNI dentro de la lista de empleados generada a partir de los archivos.
     *Si no logra encontrar el empleado que coincida con el DNI ingresado por parametro, lanza excepción.
     *
     * @return empleado encontrado
     * @exception InexistenteException
     * @author Ruth Lovece
     */
    public Empleado buscarEmpleadoPorDNI (String dni) throws InexistenteException{
        for (Empleado empleado: this.listaEmpleados) {
            if (dni.equals(empleado.getDni())){
                return empleado;
            }
        }
        throw new InexistenteException("DNI no registrado");
    }

    /**
     * <h2>Buscar empleado por Legajo para Administrador</h2>
     *Método para buscar un empleado por Legajo dentro de la lista de empleados generada a partir de los archivos.
     *Si no logra encontrar el empleado que coincida con el Legajo ingresado por parametro, lanza excepción.
     *
     * @return empleado encontrado
     * @exception InexistenteException
     * @author Ruth Lovece
     */
    public Empleado buscarEmpleadoPorLegajo (int legajo) throws InexistenteException{
        for (Empleado empleado: this.listaEmpleados) {
            if (legajo == empleado.getLegajo()){
                return empleado;
            }
        }
        throw new InexistenteException("legajo no registrado");
    }

    ///endregion

    ///regionRegistro empleado
    /**
     * <h2>Registro de nuevo empleado Administrador</h2>
     *Muentra las opciones que tiene el administrador registrar un empleado,
     *lee el ingreso por teclado con EntradaSalida,e ingresa al switch correspondiente donde se invocan otros metodos para continuar con la gestión.
     *Luego se vuelve al menú anterior.
     *
     * @see EntradaSalida
     * @author Ruth Lovece
     */
    @Override
    public void registroEmpleado() {
        int opcion = EntradaSalida.entradaInt("      ELIJA UNA OPCION  \n" +
                "\n 1 - Cargar Supervisor" +
                "\n 2 - Cargar Empleado Local" +
                "\n 3 - Cargar Repartidor" +
                "\n 0 - Volver\n\n");
        switch (opcion) {
            case 1:
                registrarSupervisor();
                break;

            case 2:
                registrarEmpleadoLocal();
                break;

            case 3:
                registrarRepartidor();
                break;

            default:
                break;
        }
    }

    /**
     * <h2>Registro Supervisor</h2>
     *Solicita los datos para registrar un supervisor utilizando los metodos
     * {@link GestionAdmin#pedirDatosPersonales(Persona)}  y
     * {@link GestionAdmin#pedirDatosEmpleado(Empleado)} y EntradaSalida
     *
     * @see EntradaSalida
     * @author Ruth Lovece
     */
    public void registrarSupervisor(){
        Supervisor nuevo = new Supervisor();
        nuevo.setDni(EntradaSalida.entradaDNI());
        try {
            supervisorRepo.buscar(nuevo.getDni());
            EntradaSalida.SalidaError("DNI ya registrado como supervisor\n\n", "Error");
        } catch (InexistenteException e) {
            pedirDatosPersonales(nuevo);
            pedirDatosEmpleado(nuevo);
            nuevo.setId(supervisorRepo.buscarUltimoID()+1);
            listaEmpleados.add(nuevo);
            supervisorRepo.agregar(nuevo);
        }
    }

    /**
     * <h2>Registro Empleado Local</h2>
     *Solicita los datos para registrar un empleado Local utilizando los metodos
     * {@link GestionAdmin#pedirDatosPersonales(Persona)}  y
     * {@link GestionAdmin#pedirDatosEmpleado(Empleado)} y EntradaSalida
     *
     * @see EntradaSalida
     * @author Ruth Lovece
     */
    public void registrarEmpleadoLocal(){
        EmpleadoLocal nuevo = new EmpleadoLocal();
        Supervisor supervisor = null;

        nuevo.setDni(EntradaSalida.entradaDNI());
        try {
            empleadoLocalRepo.buscar(nuevo.getDni());
            EntradaSalida.SalidaError("DNI ya registrado como empleado Local\n\n", "Error");
        } catch (InexistenteException e) {
            pedirDatosPersonales(nuevo);
            pedirDatosEmpleado(nuevo);
            boolean continuar = false;
            EntradaSalida.SalidaAdvertencia("El proximo dato corresponde al supervisor\n\n", "Importante");
            do {
                try {
                    supervisor = supervisorRepo.buscar(EntradaSalida.entradaDNI());
                    continuar = true;
                } catch (InexistenteException e2){
                    EntradaSalida.SalidaError("Supervisor no registrado", "Error");
                }
            } while (!continuar);
            nuevo.setSupervisor(supervisor);
        }
        nuevo.setId(empleadoLocalRepo.buscarUltimoID()+1);
        listaEmpleados.add(nuevo);
        empleadoLocalRepo.agregar(nuevo);

        supervisor.setCantEmpleadosACargo(supervisor.getCantEmpleadosACargo()+1);
        supervisor.getEmpleadosACargo().add(nuevo);
        supervisorRepo.modificar(supervisor);
    }

    /**
     * <h2>Registro Repartidor</h2>
     *Solicita los datos para registrar un repartidor utilizando los metodos
     * {@link GestionAdmin#pedirDatosPersonales(Persona)}  y
     * {@link GestionAdmin#pedirDatosEmpleado(Empleado)} y EntradaSalida
     *
     * @see EntradaSalida
     * @author Ruth Lovece
     */
    public void registrarRepartidor(){
        Repartidor nuevo = new Repartidor();
        Supervisor supervisor = null;
        nuevo.setDni(EntradaSalida.entradaDNI());
        try {
            repartidorRepo.buscar(nuevo.getDni());
            EntradaSalida.SalidaError("DNI ya registrado como Repartidor\n\n", "Error");
        } catch (InexistenteException e) {
            pedirDatosPersonales(nuevo);
            pedirDatosEmpleado(nuevo);
            boolean continuar = false;
            EntradaSalida.SalidaAdvertencia("El proximo dato corresponde al supervisor\n\n", "Importante");
            do {
                try {
                    supervisor = supervisorRepo.buscar(EntradaSalida.entradaDNI());
                    continuar = true;
                } catch (InexistenteException e2){
                    EntradaSalida.SalidaError("Supervisor no registrado", "Error");
                }
            } while (!continuar);
            nuevo.setSupervisor(supervisor);
            nuevo.setZona(EntradaSalida.entradaZona());
            nuevo.setTiposPaquetes(EntradaSalida.entradaTipoPaquete());
        }

        nuevo.setId(repartidorRepo.buscarUltimoID()+1);
        listaEmpleados.add(nuevo);
        repartidorRepo.agregar(nuevo);

        supervisor.setCantEmpleadosACargo(supervisor.getCantEmpleadosACargo()+1);
        supervisor.getEmpleadosACargo().add(nuevo);
        supervisorRepo.modificar(supervisor);
    }

    /**
     * <h2>Pedir datos personales</h2>
     *Solicita los datos personales y los setea en una subClase de Persona utilizando EntradaSalida
     *
     * @see EntradaSalida
     * @author Ruth Lovece
     */
    public void pedirDatosPersonales (Persona nuevo){
        boolean continuar;
        nuevo.setNombre(EntradaSalida.entradaString("Ingrese el nombre\n\n"));
        nuevo.setApellido(EntradaSalida.entradaString("Ingrese el apellido\n\n"));
        nuevo.setTelefono(EntradaSalida.entradaTelefono());
        nuevo.setMail(EntradaSalida.entradaMail());

        do {
            continuar = false;
            nuevo.setUsername(EntradaSalida.entradaUsermane());
            for (Empleado empleado: this.listaEmpleados) {
                if (nuevo.getUsername().equals(empleado.getUsername())){
                    EntradaSalida.SalidaError("Usuario ya existente", "Error");
                    continuar = true;
                    break;
                }
            }
        } while (!continuar);

        EntradaSalida.SalidaInformacion("Se asigno su DNI como contraseña","CONTRASEÑA\n\n");
        nuevo.setPassword(nuevo.getDni());
    }

    /**
     * <h2>Pedir adicionales de empleado</h2>
     *Solicita los datos adicionales a personales que tiene un empleado,
     *y los setea en una subClase de Persona utilizando EntradaSalida
     *
     * @see EntradaSalida
     * @author Ruth Lovece
     */
    public void pedirDatosEmpleado (Empleado nuevo){
        nuevo.setLegajo(buscarMayorLegajo()+1);
        nuevo.setEstado(EstadosEmpleado.DISPONIBLE);
        nuevo.setJornada(EntradaSalida.entradaString("Ingrese Jornada\n\n"));
    }

    /**
     * <h2>Buscar legajo mayor</h2>
     *Método para buscar y retornar el mayor legajo dentro de los archivos de empleados.
     *Los cuales fueron todos incluidos en un ArrayLista de empleados.
     *
     * @return mayor legajo en archivos
     * @author Ruth Lovece
     */
    public int buscarMayorLegajo(){
        int legajoMayor = 0;
        for (Empleado empleado: this.listaEmpleados) {
            if (empleado.getLegajo()>legajoMayor){
                legajoMayor = empleado.getLegajo();
            }
        }
        return legajoMayor;
    }
    ///endregion

    ///region Baja empleado
    /**
     * <h2>Baja Empleado</h2>
     *Cambia atributo EstadoEmpleado a BAJA, es decir, se realiza una baja lógica del empleado.
     *En caso de ser un empleado con supervisor, también se cambia atributo supervisor a null.
     *En caso de ser un Supervisor, se cambia a null todos los empleados que tengan a ese supervisor como
     *atributo, esto se hace con método {@link GestionAdmin#quitarSupervisor(Supervisor)}
     *
     * @author Ruth Lovece
     */
    public void bajaEmpleado(int legajo){
        try {
            Empleado empleado = buscarEmpleadoPorLegajo(legajo);
            empleado.setEstado(EstadosEmpleado.BAJA);
            if (empleado.getClass() == Repartidor.class) {
                ((Repartidor) empleado).setSupervisor(null);
                repartidorRepo.modificar((Repartidor) empleado);
            } else if (empleado.getClass() == EmpleadoLocal.class) {
                ((EmpleadoLocal) empleado).setSupervisor(null);
                empleadoLocalRepo.modificar((EmpleadoLocal) empleado);
            } else {
                quitarSupervisor((Supervisor) empleado);
                supervisorRepo.modificar((Supervisor) empleado);
            }
        } catch (InexistenteException e){
            EntradaSalida.SalidaError("Legajo no registrado", "Error");
        }
    }

    /**
     * <h2>Quitar supervisor</h2>
     *Cambia a null todos los empleados que tengan al Supervisor ingresado por parámetro como
     *atributo
     *
     * @param supervisor
     * @author Ruth Lovece
     */
    void quitarSupervisor (Supervisor supervisor){
        for (Empleado empleado: this.listaEmpleados) {
            if (empleado.getClass() == Repartidor.class) {
                ((Repartidor) empleado).setSupervisor(null);
                repartidorRepo.modificar((Repartidor) empleado);
            } else if (empleado.getClass() == EmpleadoLocal.class) {
                ((EmpleadoLocal) empleado).setSupervisor(null);
                empleadoLocalRepo.modificar((EmpleadoLocal) empleado);
            }
        }
    }
    ///endregion

    ///region Ascenso Empleado
    /**
     * <h2>Menú Asceso de empleado</h2>
     *Muentra las opciones con EntradaSalida que tiene el administrador para asender a un Repartidor o empleado Local
     *a supervisor.
     *Luego se invoca el método según el empleado corresponda a un grupo o al otro.
     *{@link GestionAdmin#ascensoRepartidor(int)} o {@link GestionAdmin#ascenderEmpleado()}
     *
     * @see EntradaSalida
     * @author Ruth Lovece
     */
    public void ascenderEmpleado (){
        int opcion = EntradaSalida.entradaInt("      ELIJA UNA OPCION  \n" +
                "\n 1 - Un Repartidor" +
                "\n 2 - Un Empleado de Local" +
                "\n 0 - Volver\n\n");
        switch (opcion) {
            case 1:
                ascensoRepartidor(EntradaSalida.entradaInt("Ingrese Legajo"));
                break;

            case 2:
                ascensoEmpleadoLocal (EntradaSalida.entradaInt("Ingrese Legajo"));
                break;

            default:
                break;
        }
    }

    /**
     * <h2>Ascenso de Repartidor</h2>
     *Quita del archivo y la lista de empleados al repartidor según legajo ingresado por parámetro.
     *Solicita datos adicionales para registrarlo como Supervisor y lo carga en archivo y lista con su nuevo roll
     *En caso de no registrarse ese legajo en el archivo de Repartidores se lanza la excepcion.
     *
     * @exception InexistenteException
     * @see EntradaSalida
     * @author Ruth Lovece
     */
    public void ascensoRepartidor (int legajo){
        try {
            Empleado empleado = buscarEmpleadoPorLegajo(legajo);
            repartidorRepo.eliminar(empleado.getId());
            Supervisor nuevoSupervisor = new Supervisor();
            nuevoSupervisor.setId(repartidorRepo.buscarUltimoID()+1);
            nuevoSupervisor.setLegajo(empleado.getLegajo());
            nuevoSupervisor.setNombre(empleado.getNombre());
            nuevoSupervisor.setApellido(empleado.getApellido());
            nuevoSupervisor.setTelefono(empleado.getTelefono());
            nuevoSupervisor.setMail(empleado.getMail());
            nuevoSupervisor.setUsername(empleado.getUsername());
            nuevoSupervisor.setPassword(empleado.getPassword());
            nuevoSupervisor.setJornada(EntradaSalida.entradaString("Ingrese Jornada"));
            nuevoSupervisor.setEstado(EstadosEmpleado.DISPONIBLE);
            supervisorRepo.agregar(nuevoSupervisor);
            listaEmpleados.remove(empleado);
            listaEmpleados.add(nuevoSupervisor);
            EntradaSalida.SalidaInformacion("Asceso exitoso", "Gestion exitosa");
        } catch (InexistenteException e){
            EntradaSalida.SalidaError("Legajo no registrado como Repartidor", "Error");
        }
    }

    /**
     * <h2>Ascenso de Empleado Local</h2>
     *Quita del archivo y la lista de empleados al Empleado de local según legajo ingresado por parámetro.
     *Solicita datos adicionales para registrarlo como Supervisor y lo carga en archivo y lista con su nuevo roll
     * En caso de no registrarse ese legajo en el archivo de Repartidores se lanza la excepcion.
     *
     * @exception InexistenteException
     * @see EntradaSalida
     * @author Ruth Lovece
     */
    public void ascensoEmpleadoLocal (int legajo){
        try {
            Empleado empleado = buscarEmpleadoPorLegajo(legajo);
            empleadoLocalRepo.eliminar(empleado.getId());
            Supervisor nuevoSupervisor = new Supervisor();
            nuevoSupervisor.setId(repartidorRepo.buscarUltimoID()+1);
            nuevoSupervisor.setLegajo(empleado.getLegajo());
            nuevoSupervisor.setNombre(empleado.getNombre());
            nuevoSupervisor.setApellido(empleado.getApellido());
            nuevoSupervisor.setTelefono(empleado.getTelefono());
            nuevoSupervisor.setMail(empleado.getMail());
            nuevoSupervisor.setUsername(empleado.getUsername());
            nuevoSupervisor.setPassword(empleado.getPassword());
            nuevoSupervisor.setJornada(EntradaSalida.entradaString("Ingrese Jornada"));
            nuevoSupervisor.setEstado(EstadosEmpleado.DISPONIBLE);
            supervisorRepo.agregar(nuevoSupervisor);
            listaEmpleados.remove(empleado);
            listaEmpleados.add(nuevoSupervisor);
            EntradaSalida.SalidaInformacion("Asceso exitoso", "Gestion exitosa");
        } catch (InexistenteException e){
            EntradaSalida.SalidaError("Legajo no registrado como Empleado Local", "Error");
        }
    }

    ///endregion

    ///region ModificarEmpleado
    @Override
    public Empleado modificarEmpleado(String dni) {
        int opcion = EntradaSalida.entradaInt("      ELIJA UNA OPCION  \n" +
                "\n 1 - Modificar Repartidor" +
                "\n 2 - Modificar Empleado Local" +
                "\n 3 - Modificar Supervisor" +
                "\n 0 - Volver\n\n");

        switch (opcion) {
            case 1:
                modificarRepartidor(dni);
                break;

            case 2:
                modificarEmpleadoLocal(dni);
                break;

            case 3:
                modificarSupervisor(dni);
                break;

            default:
                break;
        }
        return null;
    }

    public void modificarRepartidor(String dni) {
        try {
            Repartidor aModificar = repartidorRepo.buscar(dni);
            int opcion = EntradaSalida.entradaInt("      ELIJA UNA OPCION  \n" +
                    "\n 1 - Modificar Nombre" +
                    "\n 2 - Modificar Apellido" +
                    "\n 3 - Modificar DNI" +
                    "\n 4 - Modificar Usuario" +
                    "\n 0 - Volver\n\n");

            switch (opcion) {
                case 1:
                    aModificar.setNombre(EntradaSalida.entradaString("Ingrese nombre\n\n"));
                    repartidorRepo.modificar(aModificar);
                    listaEmpleados.remove(aModificar);
                    listaEmpleados.add(aModificar);
                    EntradaSalida.SalidaInformacion("Modificación", "Gestio exitosa");
                    break;

                case 2:
                    aModificar.setApellido(EntradaSalida.entradaString("Ingrese apellido\n\n"));
                    repartidorRepo.modificar(aModificar);
                    listaEmpleados.remove(aModificar);
                    listaEmpleados.add(aModificar);
                    EntradaSalida.SalidaInformacion("Modificación", "Gestio exitosa");
                    break;

                case 3:
                    aModificar.setDni(EntradaSalida.entradaDNI());
                    repartidorRepo.modificar(aModificar);
                    listaEmpleados.remove(aModificar);
                    listaEmpleados.add(aModificar);
                    EntradaSalida.SalidaInformacion("Modificación", "Gestio exitosa");
                    break;

                case 4:
                    boolean continuar;
                    do {
                        continuar = false;
                        aModificar.setUsername(EntradaSalida.entradaUsermane());
                        for (Empleado empleado: this.listaEmpleados) {
                            if (aModificar.getUsername().equals(empleado.getUsername())){
                                EntradaSalida.SalidaError("Usuario ya existente", "Error");
                                continuar = true;
                                break;
                            }
                        }
                    } while (!continuar);
                    repartidorRepo.modificar(aModificar);
                    listaEmpleados.remove(aModificar);
                    listaEmpleados.add(aModificar);
                    EntradaSalida.SalidaInformacion("Modificación", "Gestio exitosa");
                    break;

                default:
                    break;
            }
        } catch (InexistenteException e){
            EntradaSalida.SalidaError("DNI no registrado como Repartidor", "Error");
        }
    }

    public void modificarEmpleadoLocal(String dni) {
        try {
            EmpleadoLocal aModificar = empleadoLocalRepo.buscar(dni);
            int opcion = EntradaSalida.entradaInt("      ELIJA UNA OPCION  \n" +
                    "\n 1 - Modificar Nombre" +
                    "\n 2 - Modificar Apellido" +
                    "\n 3 - Modificar DNI" +
                    "\n 4 - Modificar Usuario" +
                    "\n 0 - Volver\n\n");

            switch (opcion) {
                case 1:
                    aModificar.setNombre(EntradaSalida.entradaString("Ingrese nombre\n\n"));
                    empleadoLocalRepo.modificar(aModificar);
                    listaEmpleados.remove(aModificar);
                    listaEmpleados.add(aModificar);
                    EntradaSalida.SalidaInformacion("Modificación", "Gestio exitosa");
                    break;

                case 2:
                    aModificar.setApellido(EntradaSalida.entradaString("Ingrese apellido\n\n"));
                    empleadoLocalRepo.modificar(aModificar);
                    listaEmpleados.remove(aModificar);
                    listaEmpleados.add(aModificar);
                    EntradaSalida.SalidaInformacion("Modificación", "Gestio exitosa");
                    break;

                case 3:
                    aModificar.setDni(EntradaSalida.entradaDNI());
                    empleadoLocalRepo.modificar(aModificar);
                    listaEmpleados.remove(aModificar);
                    listaEmpleados.add(aModificar);
                    EntradaSalida.SalidaInformacion("Modificación", "Gestio exitosa");
                    break;

                case 4:
                    boolean continuar;
                    do {
                        continuar = false;
                        aModificar.setUsername(EntradaSalida.entradaUsermane());
                        for (Empleado empleado: this.listaEmpleados) {
                            if (aModificar.getUsername().equals(empleado.getUsername())){
                                EntradaSalida.SalidaError("Usuario ya existente", "Error");
                                continuar = true;
                                break;
                            }
                        }
                    } while (!continuar);
                    empleadoLocalRepo.modificar(aModificar);
                    listaEmpleados.remove(aModificar);
                    listaEmpleados.add(aModificar);
                    EntradaSalida.SalidaInformacion("Modificación", "Gestio exitosa");
                    break;

                default:
                    break;
            }
        } catch (InexistenteException e){
            EntradaSalida.SalidaError("DNI no registrado como Empleado Local", "Error");
        }
    }

    public void modificarSupervisor(String dni) {
        try {
            Supervisor aModificar = supervisorRepo.buscar(dni);
            int opcion = EntradaSalida.entradaInt("      ELIJA UNA OPCION  \n" +
                    "\n 1 - Modificar Nombre" +
                    "\n 2 - Modificar Apellido" +
                    "\n 3 - Modificar DNI" +
                    "\n 4 - Modificar Usuario" +
                    "\n 5 - Modificar Estado" +
                    "\n 0 - Volver\n\n");

            switch (opcion) {
                case 1:
                    aModificar.setNombre(EntradaSalida.entradaString("Ingrese nombre\n\n"));
                    supervisorRepo.modificar(aModificar);
                    listaEmpleados.remove(aModificar);
                    listaEmpleados.add(aModificar);
                    EntradaSalida.SalidaInformacion("Modificación", "Gestio exitosa");
                    break;

                case 2:
                    aModificar.setApellido(EntradaSalida.entradaString("Ingrese apellido\n\n"));
                    supervisorRepo.modificar(aModificar);
                    listaEmpleados.remove(aModificar);
                    listaEmpleados.add(aModificar);
                    EntradaSalida.SalidaInformacion("Modificación", "Gestio exitosa");
                    break;

                case 3:
                    aModificar.setDni(EntradaSalida.entradaDNI());
                    supervisorRepo.modificar(aModificar);
                    listaEmpleados.remove(aModificar);
                    listaEmpleados.add(aModificar);
                    EntradaSalida.SalidaInformacion("Modificación", "Gestio exitosa");
                    break;

                case 4:
                    boolean continuar;
                    do {
                        continuar = false;
                        aModificar.setUsername(EntradaSalida.entradaUsermane());
                        for (Empleado empleado: this.listaEmpleados) {
                            if (aModificar.getUsername().equals(empleado.getUsername())){
                                EntradaSalida.SalidaError("Usuario ya existente", "Error");
                                continuar = true;
                                break;
                            }
                        }
                    } while (!continuar);
                    supervisorRepo.modificar(aModificar);
                    listaEmpleados.remove(aModificar);
                    listaEmpleados.add(aModificar);
                    EntradaSalida.SalidaInformacion("Modificación", "Gestio exitosa");
                    break;

                case 5:
                    EstadosEmpleado nuevoEstado = EntradaSalida.entradaEstadoEmpleado();
                    if (nuevoEstado == EstadosEmpleado.BAJA) {
                        EntradaSalida.SalidaError("Para dar de baja ingrese a menú Dar de Baja Empleado", "Error");
                    } else {
                        aModificar.setEstado(nuevoEstado);
                        supervisorRepo.modificar(aModificar);
                        listaEmpleados.remove(aModificar);
                        listaEmpleados.add(aModificar);
                        EntradaSalida.SalidaInformacion("Modificación", "Gestio exitosa");
                    }
                    break;

                default:
                    break;
            }
        } catch (InexistenteException e){
            EntradaSalida.SalidaError("DNI no registrado como Supervisor", "Error");
        }
    }
    ///endregion

    ///endregion

    ///regionManejo Clientes
    /**
     * <h2>Menu Gestión de Clientes para Administrador</h2>
     *Muentra dentro de un ciclo do-while las opciones que tiene el administrador sobre los clientes,
     *lee el ingreso por teclado con EntradaSalida,
     * e ingresa al switch correspondiente donde se invocan otros metodos para continuar con la gestión.
     *El ciclo se repite hasta que el usuario admin ingrese la opción 0 para volver al
     * {@link GestionAdmin#menuPrincipal()}.
     *
     * @see EntradaSalida
     * @author Ruth Lovece
     */
    public void menuManejoClientes(){
        int opcion = 0;
        do {
            opcion = EntradaSalida.entradaInt("      ELIJA UNA OPCION  \n" +
                    "\n 1 - Ver Cliente" +
                    "\n 2 - Modificar Usuario" +
                    "\n 3 - Modificar DNI" +
                    "\n 4 - Eliminar del archivo" +
                    "\n 0 - Volver\n\n");

            String codigoPaquete;

            switch (opcion){
                case 1:
                    verCliente();
                    break;

                case 2:
                    modificarUsernameCliente();
                    break;

                case 3:
                    modificarDNICliente();
                    break;

                case 4:
                    elimiarCliente();
                    break;

                default:
                    break;
            }
        } while (opcion!=0);
    }

    /**
     * <h2>Ver cliente para Supervisor</h2>
     *Método para buscar un empleado por DNI dentro de la lista de empleados generada a partir de los archivos.
     *Si no logra encontrar el empleado que coincida con el DNI ingresado por parametro, lanza excepción.
     *
     * @return empleado encontrado
     * @exception InexistenteException
     * @author Ruth Lovece
     */
    public void verCliente(){
        try {
            Cliente cliente = clientesRepo.buscar(EntradaSalida.entradaString("Ingrese DNI\n\n"));
            EntradaSalida.SalidaInformacion((cliente.toString() + "\n\n"), "Cliente buscado");
        } catch (InexistenteException e){
            EntradaSalida.SalidaError("No existe Cliente con ese DNI", "Error");
        }
    }
    public void modificarUsernameCliente(){
        try {
            Cliente aModificar =clientesRepo.buscar(EntradaSalida.entradaDNI());
            boolean continuar = true;
            String nuevoUsername;
            do {
                nuevoUsername = EntradaSalida.entradaUsermane();
                for (Cliente cliente: clientesRepo.listar()) {
                    if (nuevoUsername.equals(cliente.getUsername())){
                        continuar = false;
                        break;
                    }
                }
            } while (continuar);
            aModificar.setUsername(nuevoUsername);
            clientesRepo.modificar(aModificar);
            EntradaSalida.SalidaInformacion("Usuario modificado con exito", "Gestion exitosa");
        } catch (InexistenteException e){
            EntradaSalida.SalidaError("No existe Cliente con ese DNI", "Error");
        }
    }

    public void modificarDNICliente(){
        try {
            Cliente aModificar =clientesRepo.buscar(EntradaSalida.entradaDNI());
            boolean continuar = true;
            String nuevoDNI;
            do {
                nuevoDNI = EntradaSalida.entradaDNI();
                for (Cliente cliente: clientesRepo.listar()) {
                    if (nuevoDNI.equals(cliente.getDni())){
                        continuar = false;
                        break;
                    }
                }
            } while (continuar);
            aModificar.setDni(nuevoDNI);
            clientesRepo.modificar(aModificar);
            EntradaSalida.SalidaInformacion("DNI modificado con éxito", "Gestion exitosa");
        } catch (InexistenteException e){
            EntradaSalida.SalidaError("No existe Cliente con ese DNI", "Error");
        }
    }

    public void elimiarCliente(){
        try {
            Cliente aModificar =clientesRepo.buscar(EntradaSalida.entradaDNI());
            clientesRepo.eliminar(aModificar.getId());
            EntradaSalida.SalidaInformacion("Cliente eliminado definitivamente con éxito", "Gestion exitosa");
        } catch (InexistenteException e){
            EntradaSalida.SalidaError("No existe Cliente con ese DNI", "Error");
        }
    }


    ///EntradaSalida.SalidaInformacion("Asceso exitoso", "Gestion exitosa");
    ///endregion

    static void validarPassword (String contraseña) throws PasswordInvalida {
        if (!contraseña.equals("123Admin")){
            throw new PasswordInvalida("Contraseña incorrecta!");
        }
    }


}
