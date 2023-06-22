package org.example.gestiones;

import org.example.enums.EstadosEmpleado;
import org.example.enums.EstadosPaquete;
import org.example.excepciones.CodigoPaqueteExistente;
import org.example.excepciones.Excepciones;
import org.example.excepciones.InexistenteException;
import org.example.interfacesDeManejo.ManejoCliente;
import org.example.interfacesDeManejo.ManejoPaquete;
import org.example.models.Cliente;
import org.example.models.EmpleadoLocal;
import org.example.models.Paquete;
import org.example.recursos.EntradaSalida;
import org.example.repositorio.ClientesRepo;
import org.example.repositorio.PaqueteRepo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class ClienteGestion implements ManejoCliente, ManejoPaquete {

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



    public void verPerfil(Cliente cliente){

        EntradaSalida.SalidaInformacion(cliente.toString(),"MIS DATOS");


    }
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

    public void modificarDatosClientes(Cliente clienteAModificar){

        if(modificarCliente(clienteAModificar)){

            EntradaSalida.SalidaInformacion("El cliente se modifico con exito!","");

        }else{
            EntradaSalida.SalidaInformacion("No se pudo modificar el cliente","ERROR");
        }

    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Metodos Paquete">

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

    //seguimiento del paquete
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







    // <editor-fold defaultstate="collapsed" desc="Menu Principal">


    //logueo




    // <editor-fold defaultstate="collapsed" desc="Menu Principal">


    //logueo

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

    //menu



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