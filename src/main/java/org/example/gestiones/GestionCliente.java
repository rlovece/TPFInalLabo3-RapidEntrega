package org.example.gestiones;

import org.example.enums.EstadosPaquete;
import org.example.excepciones.CodigoPaqueteExistente;
import org.example.excepciones.InexistenteException;
import org.example.interfacesDeManejo.ManejoCliente;
import org.example.interfacesDeManejo.ManejoPaquete;
import org.example.models.Cliente;
import org.example.models.Paquete;
import org.example.recursos.EntradaSalida;
import org.example.repositorio.ClientesRepo;
import org.example.repositorio.PaqueteRepo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
/**
 * @author Cavallo, Pablo David
 */
public class GestionCliente implements ManejoCliente, ManejoPaquete {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private ClientesRepo repoClientes = new ClientesRepo();
    private PaqueteRepo repoPaquete = new PaqueteRepo();
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Paquete> listaPaquetes;
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Metodos Cliente">
    //cargar cliente
    @Override
    public void registroCliente() {
        //el cliente no puede dar de alta a otros clientes

    }
    /**
     * <h2>Metodo Ver Perfil</h2>
     * <p>El método verPerfil recibe un objeto de tipo
     * Cliente, obtiene su representación en forma de
     * cadena de texto y la muestra utilizando el método
     * SalidaInformacion de la clase EntradaSalida.
     * @see EntradaSalida
     * @param cliente  (Dato tipo Cliente)
     * @author Cavallo, Pablo David
     */
    public void verPerfil(Cliente cliente){
        EntradaSalida.SalidaInformacion(cliente.toString(),"MIS DATOS");
    }
    /**
     * <h2>Modificar Cliente</h2>
     *<p>El metodo modificar Cliente permite al usuario modificar los
     * atributos de un cliente, mostrando un menú de opciones y utilizando
     * métodos de entrada/salida para obtener la nueva información del
     * usuario. Los cambios se guardan en el repositorio de clientes y
     * se retorna un valor booleano para indicar el éxito de la operación.
     * @see EntradaSalida
     * @param clienteAModificar (Dato tipo Cliente)
     * @return boolean
     * @author Cavallo, Pablo David
     */
    @Override
    public boolean modificarCliente(Cliente clienteAModificar) {

        int opcion;

        do{
            //id - NO LO PUEDE MODIFICAR
            //DNI - NO LO PUEDE MODIFICAR -> DEBE ELIMINAR CUENTA Y HACER UNA NUEVA
            //username - NO LO PUEDE MODIFICAR -> DEBE ELIMINAR LA CUENTA Y HACER UNA NUEVA

            opcion = EntradaSalida.entradaInt("    INGRESE LA OPCION DESEADA\n"+
                    "\n1 - Modificar nombre" +
                    "\n2 - Modificar apellido" +
                    "\n3 - Modificar telefono" +
                    "\n4 - Modificar mail" +
                    "\n5 - Modificar domicilio" +
                    "\n6 - Modificar contraseña" +
                    "\n\n0 - Salir");

            while(opcion < 0 || opcion > 6){
                opcion = EntradaSalida.entradaInt("ERROR - Ingrese una opcion valida: ");
            }

            switch(opcion){
                case 1: //nombre
                    clienteAModificar.setNombre(EntradaSalida.entradaString("Ingrese el nuevo nombre"));
                    break;
                case 2: //apellido
                    clienteAModificar.setApellido(EntradaSalida.entradaString("Ingrese el nuevo apellido"));
                    break;
                case 3: //telefono
                    EntradaSalida.SalidaInformacion("Ingrese el nuevo telefono","");
                    clienteAModificar.setTelefono(EntradaSalida.entradaTelefono());
                    break;
                case 4: //mail
                    EntradaSalida.SalidaInformacion("Ingrese el nuevo mail","");
                    clienteAModificar.setMail(EntradaSalida.entradaMail());
                    break;
                case 5: //domicilio
                    clienteAModificar.setDomicilio(EntradaSalida.entradaString("Ingrese la nueva direccion"));
                    break;
                case 6: //contraseña
                    clienteAModificar.setPassword(EntradaSalida.entradaString("Ingrese la nueva contraseña"));
                    break;

            }


        }while(opcion != 0);

        repoClientes.modificar(clienteAModificar);

        return true;

    }
    /**
     * <h2>Modificar Cliente</h2>
     * <p>El método modificar datos Clientes llama a otro método
     * para modificar los datos de un cliente y luego muestra un mensaje
     * de éxito o error según el resultado de la modificación.
     * @see EntradaSalida
     * @param clienteAModificar (Dato tipo Cliente)
     * @author Cavallo, Pablo David
     */
    public void modificarDatosClientes(Cliente clienteAModificar){

        if(modificarCliente(clienteAModificar)){

            EntradaSalida.SalidaInformacion("El cliente se modifico con exito!","");

        }else{
            EntradaSalida.SalidaInformacion("No se pudo modificar el cliente","ERROR");
        }

    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Metodos Paquete">
    /**
     * <h2>Ver Mis Paquetes</h2>
     * <p>El método  ver Mis Paquetesbusca los paquetes asociados a un cliente específico
     * y los muestra utilizando la clase EntradaSalida para generar la salida.
     * @param cliente (Dato tipo Cliente)
     * @author Cavallo, Pablo David
     */
    public void verMisPaquetes(Cliente cliente){

        this.listaPaquetes = repoPaquete.listar();
        StringBuilder stringBuilder = new StringBuilder();
        boolean tienePaquetes = false;

        for(Paquete paquete : this.listaPaquetes){

            if(paquete.getRemitente().equals(cliente)){

                stringBuilder.append(paquete.toStringListarCliente());

                tienePaquetes = true;
            }
        }

        if(tienePaquetes){
            EntradaSalida.SalidaInformacion(stringBuilder.toString(),"MIS PAQUETES");
        }else{
            EntradaSalida.SalidaInformacion("No tiene paquetes registrados","!");
        }

    }
    @Override
    public boolean modificarPaquete(Paquete paquete) {
        //el cliente no puede modificar el paquete

        return false;
    }
    /**
     * <h2>Registro  de Paquetes</h2>
     * <p> El metodo registro de paquete, registra un nuevo paquete
     * asociado a un cliente en un sistema, guardando la información
     * relevante del paquete en un repositorio de paquetes.
     * @see LocalDate
     * @see EntradaSalida
     * @see DateTimeFormatter
     * @throws InexistenteException extends Exception
     * @author Cavallo, Pablo David
     */
    @Override
    public void registroPaquete() {
        Paquete nuevoPaquete = new Paquete();

        String dni = EntradaSalida.entradaDNI();

        try{
            Cliente remitente = repoClientes.buscar(dni);

            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
            String formattedString = localDate.format(formatter);

            nuevoPaquete.setRemitente(remitente);

            nuevoPaquete.setId(repoPaquete.buscarUltimoID() + 1);
            nuevoPaquete.setCodigoIdentificacion(nuevoCogigoPaquete());
            nuevoPaquete.setFechaIngreso(formattedString);
            nuevoPaquete.setTiposPaquete(EntradaSalida.entradaTipoPaquete());
            nuevoPaquete.setZonaEntrega(EntradaSalida.entradaZona());
            nuevoPaquete.setDestinatario(EntradaSalida.entradaString("Ingrese el destinatario"));
            nuevoPaquete.setDomicilioEntrega(EntradaSalida.entradaString("Ingrese el domicilio de entrega"));
            nuevoPaquete.setEstado(EstadosPaquete.EN_CORREO); //el paquete que ingresa esta en el correo, esperando a que se le asigne repartidor
            nuevoPaquete.setRepatidorAsignado(null);

            repoPaquete.agregar(nuevoPaquete);

        }catch(InexistenteException e){

            EntradaSalida.SalidaAdvertencia(e.getMessage(),"ERROR");
        }
    }
    /**
     * <h2>Validacion Codigo Paquete</h2>
     * <P>El metodo validacion Codigo Paquete este método busca en una lista
     * de paquetes si existe algún paquete con el mismo código de identificación
     * que se proporciona como argumento. Si encuentra uno, lanza una excepción.
     * Si no encuentra ningún paquete con el mismo código, no ocurre ninguna
     * excepción y el método termina su ejecución normalmente.
     * @throws CodigoPaqueteExistente extends Exception
     * @author Cavallo, Pablo David
     */
    @Override
    public void validacionCodigoPaquete(String codigo) throws CodigoPaqueteExistente {
        for (Paquete paquete: repoPaquete.listar()) {
            if (paquete.getCodigoIdentificacion().equals(codigo)){
                throw new CodigoPaqueteExistente("Codigo Paquete Existente");
            }
            break;
        }
    }
    /**
     * <h2>Nuevo Codigo Paquete</h2>
     * <p>El metodo nuevo Cogigo Paquete esta diseñado para obtener un
     * nuevo código de paquete, solicitarlo repetidamente hasta que se
     * cumplan ciertas condiciones de validación y luego devolver
     * el código válido obtenido.
     * @see EntradaSalida
     * @return un String o un null
     * @throws CodigoPaqueteExistente extends Exception
     * @author Cavallo, Pablo David
     */
    @Override
    public String nuevoCogigoPaquete() {
        boolean continuar = false;
        do {
            try {
                String codigo = EntradaSalida.CodigoPaquete();
                validacionCodigoPaquete(codigo);
                continuar = true;
                return codigo;
            } catch (CodigoPaqueteExistente e){

            }
        } while (!continuar);
        return null;
    }
    /**
     * <h2>Seguimiento del Paquete</h2>
     * <p>El metodo seguimiento Paquete permite al cliente ingresar
     * el código de identificación de un paquete y verificar si el
     * paquete pertenece a ese cliente. En caso afirmativo, se
     * muestra la información detallada del paquete. En caso
     * contrario, se muestra un mensaje de advertencia.
     * @see EntradaSalida
     * @throws InexistenteException extends Exception
     * @param cliente (Dato tipo Cliente)
     * @author Cavllo, Pablo David
     */
    public void seguimientoPaquete(Cliente cliente){
        String codigo = EntradaSalida.entradaString("Ingrese el codigo de identificacion");
        try{
            Paquete paqueteBuscado = repoPaquete.buscar(codigo);
            if(paqueteBuscado.getRemitente().equals(cliente)){
                verUnPaquete(codigo);
            }else{
                EntradaSalida.SalidaAdvertencia("El paquete buscado pertenece a otro cliente","ERROR");
            }
        }catch (InexistenteException e){
            EntradaSalida.SalidaInformacion(e.getMessage(),"ERROR");
        }
    }
    /**
     * <h2>Ver un Paquete</h2>
     * <p>el método Ver un Paquete busca un paquete en un repositorio
     * utilizando un código de identificación, y si lo encuentra,
     * muestra la información del paquete. Si no lo encuentra, muestra
     * un mensaje de error.
     * @see EntradaSalida
     * @param codigo (Dato tipo String)
     * @author Cavallo, Pablo David
     */
    @Override
    public void verUnPaquete(String codigo) {
        try{
            Paquete paqueteBuscado = repoPaquete.buscar(codigo);

            if(paqueteBuscado != null){
                String mensaje= "               P A Q U E T E\n" +
                        "\nCodigo de Identificacion:     " + paqueteBuscado.getCodigoIdentificacion() +
                        "\nFecha de Ingreso:             " + paqueteBuscado.getFechaIngreso() +
                        "\nTipo de paquete:              " + paqueteBuscado.getTiposPaquete() +
                        "\nZona de Entrega:              " + paqueteBuscado.getZonaEntrega() +
                        "\nDestinatario:                 " + paqueteBuscado.getDestinatario() +
                        "\nDomicilio de entrega:         " + paqueteBuscado.getDomicilioEntrega()  +
                        "\nEstado:                       " + paqueteBuscado.getEstado();

                EntradaSalida.SalidaInformacion(mensaje,"DATOS PAQUETE");
            }
        }catch(InexistenteException e){
            EntradaSalida.SalidaInformacion(e.getMessage(),"ERROR");
        }
    }
    @Override
    public void verPaquetePorEstado(EstadosPaquete estadosPaquete) {
        //el cliente no implementa este metodo
    }

    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Logeo">
    /**
     * <h2>Logeo</h2>
     * <p>El método logueo obtiene una lista de clientes, solicita al
     * usuario el DNI y la contraseña, verifica si el DNI corresponde a un
     * cliente existente y si la contraseña es correcta. Dependiendo de los
     * resultados, se muestra el menú principal del sistema o se muestra un
     * mensaje de advertencia en caso de errores.
     * @see EntradaSalida
     * @throws InexistenteException extends Exception
     * @author Cavallo, Pablo David
     */

    public void logueo(){

        this.listaClientes = repoClientes.listar();

        String dni = EntradaSalida.entradaString("Ingrese el DNI");

        try{
            Cliente cliente = repoClientes.buscar(dni);

            String pass = EntradaSalida.entradaString("Ingrese la contraseña");

            if(cliente.getPassword().equals(pass)){

                menuPrincipal(cliente);

            }else{
                EntradaSalida.SalidaAdvertencia("Contraseña incorrecta","ERROR");
            }

        }catch(InexistenteException e){
            EntradaSalida.SalidaAdvertencia(e.getMessage(),"ERROR");
        }

    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Menu Principal">
    /**
     * <h2>Menu Principal</h2>
     * <p>El metodo menu principal proporciona un menú interactivo para que los clientes
     * realicen diferentes acciones en función de la opción seleccionada.
     * Dependiendo de la opción elegida, se llamarán a otros métodos para
     * llevar a cabo las tareas correspondientes.
     * @see EntradaSalida
     * @param cliente (Tipo de dato Cliente)
     * @author Cavallo, Pablo David
     */
    public void menuPrincipal(Cliente cliente){

        int opcion;

        do {

            opcion = EntradaSalida.entradaInt(" INGRESE LA OPCION DESEADA  \n" +
                    "\n1 - Cargar un paquete" +
                    "\n2 - Seguimiento paquete" +
                    "\n3 - Ver mis paquetes" +
                    "\n4 - Modificar mis datos" +
                    "\n5 - Ver perfil" +
                    "\n6 - Eliminar cuenta" +
                    "\n\n 0 - Salir\n\n");


            while(opcion < 0 || opcion > 6){
                opcion = EntradaSalida.entradaInt("ERROR - Ingrese una opcion valida");
            }

            switch (opcion){
                case 1://cargar un paquete
                    registroPaquete();
                    break;
                case 2: // seguimiento paquete
                    seguimientoPaquete(cliente);
                    break;
                case 3: //ver mis paquetes
                    verMisPaquetes(cliente);
                    break;
                case 4: //modificar datos
                    modificarDatosClientes(cliente);
                    break;
                case 5: // ver perfil
                    verPerfil(cliente);
                    break;
                case 6:
                    cliente.setEstadoCliente(false);
                    repoClientes.modificar(cliente);
                    break;

            }

        }while(opcion != 0 && cliente.isEstadoCliente()) ;

    }
    //</editor-fold>
}