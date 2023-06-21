package org.example.gestiones;

import org.example.enums.EstadosPaquete;
import org.example.excepciones.CodigoPaqueteExistente;
import org.example.excepciones.ExcepcionClienteExistente;
import org.example.excepciones.InexistenteException;
import org.example.interfacesDeManejo.ManejoCliente;
import org.example.interfacesDeManejo.ManejoEmpleado;
import org.example.interfacesDeManejo.ManejoPaquete;
import org.example.models.Cliente;
import org.example.models.Empleado;
import org.example.models.EmpleadoLocal;
import org.example.models.Paquete;
import org.example.recursos.EntradaSalida;
import org.example.repositorio.ClientesRepo;
import org.example.repositorio.EmpleadoLocalRepo;
import org.example.repositorio.PaqueteRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.example.recursos.EntradaSalida.entradaDNI;

public class GestionEmpleadoLocal implements ManejoPaquete, ManejoCliente, ManejoEmpleado {

     //region Atributos
     private PaqueteRepo paqueteRepo = new PaqueteRepo();
     private ClientesRepo clientesRepo = new ClientesRepo();
     private EmpleadoLocalRepo empleadoLocalRepo = new EmpleadoLocalRepo();
     private ArrayList<Paquete> paquetes;
     private ArrayList<Cliente> clientes;
     private ArrayList<EmpleadoLocal> empleadosLocal;

     //endregion

     //region Metodos Paquete


     /**
      * Metodo que imprime por pantalla la informacion de un paquete, buscado por el codigo de identificacion
      * */
     @Override
     public void verUnPaquete(String codigo) {

          try{
          Paquete paqueteBuscado = paqueteRepo.buscar(codigo);

               if(paqueteBuscado != null){
                    EntradaSalida.SalidaInformacion(paqueteBuscado.toString(),"DATOS PAQUETE");
               }
          }catch(InexistenteException e){
               EntradaSalida.SalidaInformacion(e.getMessage(),"ERROR");
          }
     }




     /**
      * Metodo que imprime por pantalla la informacion de un paquete que pertenezca al cliente que lo solicita
      * @author Angeles Higa
      * */

     public void verUnPaqueteCliente(){

          String dni = entradaDNI();

          try{

               Cliente clienteBuscado = clientesRepo.buscar(dni);

               String codigo = EntradaSalida.entradaString("Ingrese el Codigo de Identificacion del paquete");

               try{
                    Paquete paqueteBuscado = paqueteRepo.buscar(codigo);

                    if(paqueteBuscado.getRemitente().getDni().equalsIgnoreCase(dni)){
                         verUnPaquete(codigo);
                    }else{
                         EntradaSalida.SalidaAdvertencia("El paquete ingresado no pertenece al cliente ingresado","Advertencia");
                    }
               }catch (InexistenteException e){
                    EntradaSalida.SalidaInformacion(e.getMessage(),"ERROR");
               }
          }catch(InexistenteException e){
               EntradaSalida.SalidaAdvertencia(e.getMessage(),"ERROR");
          }

     }

     /**
      * Muestra por pantalla todos los paquetes pertenecientes a un cliente
      * */

     public void verPaquetesDeUnCliente(){

          String dni = entradaDNI();



          try{

               Cliente clienteBuscado = clientesRepo.buscar(dni);
               this.paquetes = paqueteRepo.listar();

              if(paquetes != null){


                    boolean tienePaquetes = false;

                    for(Paquete paquete : this.paquetes){

                         if(paquete.getRemitente().getDni().equalsIgnoreCase(dni)){

                              EntradaSalida.SalidaInformacion(paquete.toString(),"DATOS PAQUETE");

                              tienePaquetes = true;
                         }
                    }

                    if(!tienePaquetes){
                         EntradaSalida.SalidaInformacion("El cliente no tiene paquetes registrados","Informacion");
                    }
               }else{
                   EntradaSalida.SalidaInformacion("No hay paquetes registrados","!");
              }



          }catch(InexistenteException e){
               EntradaSalida.SalidaAdvertencia(e.getMessage(),"ERROR");
          }
     }


     //cargar paquete
     @Override
     public void registroPaquete() {

          Paquete nuevoPaquete = new Paquete();

          int opcion;

          opcion = EntradaSalida.entradaInt("     INGRESE LA OPCION DESEADA\n" +
                  "\n1 - Cliente nuevo" +
                          "\n2 - Cliente existente\n\n");

          while(opcion < 1 || opcion > 2){
               opcion = EntradaSalida.entradaInt("ERROR - Ingrese una opcion valida");
          }

          if(opcion == 1){ //cliente nuevo
               registroCliente();
          }

          String dni = EntradaSalida.entradaDNI();

          try{
               Cliente remitente = clientesRepo.buscar(dni);

               LocalDate localDate = LocalDate.now();
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
               String formattedString = localDate.format(formatter);

               nuevoPaquete.setRemitente(remitente);

               nuevoPaquete.setId(paqueteRepo.buscarUltimoID() + 1);
               nuevoPaquete.setCodigoIdentificacion(nuevoCogigoPaquete());
               nuevoPaquete.setFechaIngreso(formattedString);
               nuevoPaquete.setTiposPaquete(EntradaSalida.entradaTipoPaquete());
               nuevoPaquete.setZonaEntrega(EntradaSalida.entradaZona());
               nuevoPaquete.setDestinatario(EntradaSalida.entradaString("Ingrese el destinatario"));
               nuevoPaquete.setDomicilioEntrega(EntradaSalida.entradaString("Ingrese el domicilio de entrega"));
               nuevoPaquete.setEstado(EstadosPaquete.EN_CORREO); //el paquete que ingresa esta en el correo, esperando a que se le asigne repartidor
               nuevoPaquete.setRepatidorAsignado(null);

               paqueteRepo.agregar(nuevoPaquete);

          }catch(InexistenteException e){

               EntradaSalida.SalidaAdvertencia(e.getMessage(),"ERROR");
          }

     }


     public void verPaquetesPorEstado(){

          EstadosPaquete estadoPaquete = EntradaSalida.entradaEstadosPaquete();

          this.paquetes = paqueteRepo.listar();

          verPaquetePorEstado(estadoPaquete);

     }

     @Override
     public void verPaquetePorEstado(EstadosPaquete estadosPaquete) {

          boolean existe = false;

          StringBuilder stringBuilder = new StringBuilder();

          for(Paquete paquete : this.paquetes){


               if(paquete.getEstado().equals(estadosPaquete)){

                    stringBuilder.append(paquete.toStringListar());

                    existe = true;

               }
          }

          if(existe){
               EntradaSalida.SalidaInformacion(stringBuilder.toString(),"PAQUETES " + estadosPaquete.toString());
          }else{
               EntradaSalida.SalidaInformacion("No hay paquetes " + estadosPaquete.toString(),"!");
          }

     }



     //EmpleadoLocal no tiene el permiso para modificar un paquete
     @Override
     public boolean modificarPaquete(Paquete aModificar) {
          return false;
     }


     @Override
     public void validacionCodigoPaquete(String codigo) throws CodigoPaqueteExistente {
          for (Paquete paquete: paqueteRepo.listar()) {
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
                         EntradaSalida.SalidaAdvertencia(e.getMessage(),"ERROR");
               }
          } while (!continuar);
          return null;
     }

     //endregion

     //region Metodos Cliente



     //cargar cliente
     @Override
     public void registroCliente() {


          String dni = EntradaSalida.entradaDNI();

          Cliente nuevoCliente = new Cliente();

          try
          {
               nuevoCliente = clientesRepo.buscar(dni);

               if(nuevoCliente.isEstadoCliente()){
                    EntradaSalida.SalidaAdvertencia("El cliente ya existe","ERROR");
               }else{
                    EntradaSalida.SalidaAdvertencia("El cliente se dio de alta nuevamente","!");
                    nuevoCliente.setEstadoCliente(true);
                    clientesRepo.modificar(nuevoCliente);
               }


          }catch(InexistenteException e)
          {
               nuevoCliente.setId(clientesRepo.buscarUltimoID() + 1);
               nuevoCliente.setNombre(EntradaSalida.entradaString("Ingrese el nombre"));
               nuevoCliente.setApellido(EntradaSalida.entradaString("Ingrese el apellido"));
               nuevoCliente.setDni(dni);
               nuevoCliente.setTelefono(EntradaSalida.entradaTelefono());
               nuevoCliente.setMail(EntradaSalida.entradaMail());
               nuevoCliente.setDomicilio(EntradaSalida.entradaString("Ingrese el domicilio"));
               nuevoCliente.setUsername(EntradaSalida.entradaUsermane());
               nuevoCliente.setPassword(nuevoCliente.getDni()); // se genera el usuario con el dni como constraseña, despues el cliente se cambia la contraseña si quiere
               EntradaSalida.SalidaInformacion("Se asigno su DNI como contraseña","CONTRASEÑA");
               nuevoCliente.setEstadoCliente(true);

               clientesRepo.agregar(nuevoCliente);
          }

     }





     //ver clientes

     /**
      * Me muestra una lista de todos los clientes
      * */
     public void verClientes(){

          this.clientes = clientesRepo.listar();
          boolean hayClientesActivos = false;
          StringBuilder stringBuilder = new StringBuilder();

          if(this.clientes != null) {

               for (Cliente cliente : this.clientes) {

                    if(cliente.isEstadoCliente()){
                         stringBuilder.append(cliente.toStringListar());
                         hayClientesActivos = true;
                    }

               }

               if(hayClientesActivos){
                    EntradaSalida.SalidaInformacion(stringBuilder.toString(), "CLIENTES");
               }



          }else{
               EntradaSalida.SalidaInformacion("No hay clientes registrados","!");
          }
     }

     public void verCliente(){

          String dni = EntradaSalida.entradaDNI();

          try{

               Cliente clienteBuscado = clientesRepo.buscar(dni);

               if(clienteBuscado.isEstadoCliente()){
                    this.clientes = clientesRepo.listar();

                    for(Cliente cliente : this.clientes){

                         if(cliente.getDni().equalsIgnoreCase(dni)){
                              EntradaSalida.SalidaInformacion(cliente.toString(),"DATOS CLIENTE");
                         }

                    }
               }else{
                    EntradaSalida.SalidaAdvertencia("El cliente esta deshabilitado","!");
               }



          }catch (InexistenteException e){
               EntradaSalida.SalidaAdvertencia(e.getMessage(),"ERROR");
          }
     }

     //buscar cliente por DNI y modificarlo

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
                            "\n7 - Eliminar cliente" +
                            "\n\n0 - Salir");

                    while(opcion < 0 || opcion > 7){
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
                         case 7: //dar de baja
                              clienteAModificar.setEstadoCliente(false);
                              break;

                    }


               }while(opcion != 0 && clienteAModificar.isEstadoCliente());

               clientesRepo.modificar(clienteAModificar);

               return true;

     }

     public void modificarDatosClientes(){

          String dni;

          dni = EntradaSalida.entradaDNI();

          try{

               Cliente clienteAModificar = clientesRepo.buscar(dni);

               if(modificarCliente(clienteAModificar)){

                    EntradaSalida.SalidaInformacion("El cliente se modifico con exito!","");

               }else{
                    EntradaSalida.SalidaInformacion("No se pudo modificar el cliente","ERROR");
               }

          }catch (InexistenteException e){
               EntradaSalida.SalidaAdvertencia(e.getMessage(),"ERROR");
          }


     }



     //endregion

     //region Metodos del EmpleadoLocal

     //el EmpleadoLocal no puede dar de alta a otros EmpleadoLocal
     @Override
     public void registroEmpleado() {

     }


     @Override
     public Empleado modificarEmpleado(String dni) {
          return null;
     }

     public void modificarDatosEmpleado(String dni){


          try{

               EmpleadoLocal empleadoAModificar = empleadoLocalRepo.buscar(dni);
               int opcion;

               do {

                    opcion = EntradaSalida.entradaInt("   INGRESE LA OPCION DESEADA\n" +
                            "\n1 - Modificar nombre" +
                                    "\n2 - Modificar apellido" +
                                    "\n3 - Modificar telefono" +
                                    "\n4 - Modificar mail" +
                                    "\n5 - Modificar contraseña" +
                                    "\n\n0 - Salir\n\n");

                    while(opcion < 0 || opcion > 5){
                         opcion = EntradaSalida.entradaInt("ERROR - Ingrese una opcion valida");
                    }

                    switch (opcion){
                         case 1: //nombre
                              empleadoAModificar.setNombre(EntradaSalida.entradaString("Ingrese el nuevo nombre"));
                              break;
                         case 2: //apellido
                              empleadoAModificar.setApellido(EntradaSalida.entradaString("Ingrese el nuevo apellido"));
                              break;
                         case 3:// telefono
                              EntradaSalida.SalidaInformacion("Ingrese el nuevo telefono","");
                              empleadoAModificar.setTelefono(EntradaSalida.entradaTelefono());
                              break;
                         case 4:// mail
                              EntradaSalida.SalidaInformacion("Ingrese el nuevo mail","");
                              empleadoAModificar.setMail(EntradaSalida.entradaMail());
                              break;
                         case 5:// contraseña
                              empleadoAModificar.setPassword("Ingrese la nueva contraseña");
                              break;
                    }



               }while(opcion != 0);

               empleadoLocalRepo.agregar(empleadoAModificar);

          }catch (InexistenteException e){
               EntradaSalida.SalidaAdvertencia(e.getMessage(),"ERROR");
          }
     }

     //endregion

     //region Menu Principal EmpleadoLocal

     //logueo

     public void logueo(){

          this.empleadosLocal = empleadoLocalRepo.listar();

          //EntradaSalida.SalidaInformacion("Ingrese su DNI","LOG IN");
          String dni = EntradaSalida.entradaDNI();


          try{
               EmpleadoLocal empleadoLocal = empleadoLocalRepo.buscar(dni);

               String pass = EntradaSalida.entradaString("Ingrese la contraseña");

               if(empleadoLocal.getPassword().equals(pass)){

                    menuPrincipal(dni);

               }else{
                    EntradaSalida.SalidaAdvertencia("Contraseña incorrecta","ERROR");
               }

          }catch(InexistenteException e){
               EntradaSalida.SalidaAdvertencia(e.getMessage(),"ERROR");
          }

     }

     //menu



     public void menuPrincipal(String dni){

          int opcion;

          do {

               opcion = EntradaSalida.entradaInt(" INGRESE LA OPCION DESEADA  \n" +
                       "\n1 - Buscar paquete de un cliente" +
                       "\n2 - Cargar un paquete" +
                       "\n3 - Ver paquetes de un cliente" +
                       "\n4 - Ver paquetes por estado" +
                       "\n5 - Ver clientes" +
                       "\n6 - Ver un cliente" +
                       "\n7 - Cargar un cliente" +
                       "\n8 - Modificar cliente" +
                       "\n9 - Ver perfil" +
                       "\n10 - Modificar mis datos" +
                       "\n\n 0 - Salir\n\n");


               while(opcion < 0 || opcion > 10){
                    opcion = EntradaSalida.entradaInt("ERROR - Ingrese una opcion valida");
               }

               switch (opcion){
                    case 1://buscar paquete de cliente
                         verUnPaqueteCliente();
                         break;
                    case 2: // cargar paquete
                         registroPaquete();
                         break;
                    case 3: //ver paquetes de un cliente
                         verPaquetesDeUnCliente();
                         break;
                    case 4: //ver paquetes por estado
                         verPaquetesPorEstado();
                         break;
                    case 5: // ver clientes
                         verClientes();
                         break;
                    case 6: //ver un cliente por dni
                         verCliente();
                         break;
                    case 7: // cargar un cliente
                         registroCliente();
                         break;
                    case 8: //modificar cliente
                         modificarDatosClientes();
                         break;
                    case 9: // ver perfil
                         verPerfil(dni);
                         break;
                    case 10: // modificar datos empleadoLocal
                         modificarDatosEmpleado(dni);
                         break;

               }

          }while(opcion != 0);

     }

     public void verPerfil(String dni){
          this.empleadosLocal = empleadoLocalRepo.listar();

          for(EmpleadoLocal empleado : this.empleadosLocal){

               if(empleado.getDni().equalsIgnoreCase(dni)){

                    EntradaSalida.SalidaInformacion(empleado.toString(),"DATOS EMPLEADO LOCAL");

                    break;

               }
          }
     }

     //endregion

}
