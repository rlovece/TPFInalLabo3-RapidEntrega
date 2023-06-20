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

import java.time.LocalDateTime;
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
                    System.out.println(paqueteBuscado);
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

          EntradaSalida.SalidaInformacion("Ingrese el DNI del remitente","");
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
          EntradaSalida.SalidaInformacion("Ingrese el DNI del remitente","");
          String dni = entradaDNI();



          try{

               Cliente clienteBuscado = clientesRepo.buscar(dni);

               this.paquetes = paqueteRepo.listar();

               boolean tienePaquetes = false;

               for(Paquete paquete : this.paquetes){

                    if(paquete.getRemitente().getDni().equalsIgnoreCase(dni)){

                         System.out.println(paquete);

                         tienePaquetes = true;
                    }
               }

               if(!tienePaquetes){
                    EntradaSalida.SalidaInformacion("El cliente no ha realizado ningun envio aun","Informacion");
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

               nuevoPaquete.setId(paqueteRepo.buscarUltimoID() + 1);
               nuevoPaquete.setCodigoIdentificacion(nuevoCogigoPaquete());
               nuevoPaquete.setFechaIngreso(LocalDateTime.now());
               nuevoPaquete.setTiposPaquete(EntradaSalida.entradaTipoPaquete());
               nuevoPaquete.setZonaEntrega(EntradaSalida.entradaZona());
               nuevoPaquete.setDestinatario(EntradaSalida.entradaString("Ingrese el destinatario"));
               nuevoPaquete.setDomicilioEntrega(EntradaSalida.entradaString("Ingrese el domicilio de entrega"));
               //nuevoPaquete.setEstado(EntradaSalida.entradaEstadosPaquete());
               nuevoPaquete.setEstado(EstadosPaquete.EN_CORREO); //el paquete que ingresa esta en el correo, esperando a que se le asigne repartidor
               nuevoPaquete.setRepatidorAsignado(null);

               paqueteRepo.agregar(nuevoPaquete);

          }catch(InexistenteException e){

               EntradaSalida.SalidaAdvertencia(e.getMessage(),"ERROR");
          }

     }


     public void verPaquetesPorEstado(){
          int opcion;
          EstadosPaquete estadoPaquete = EstadosPaquete.ANULADO;

          this.paquetes = paqueteRepo.listar();

          opcion = EntradaSalida.entradaInt("1. En el correo\n2. Asiganado para reparto\n3. 1er visita fallida\n4. 2da visita fallida\n5. Entregado\n6. Anulado");

          while (opcion < 1 || opcion > 6){
               opcion = EntradaSalida.entradaInt("ERROR - Ingrese una opcion valida");
          }

          switch(opcion){
               case 1:
                    estadoPaquete = EstadosPaquete.EN_CORREO;
                    break;
               case 2:
                    estadoPaquete = EstadosPaquete.ASIGNADO_PARA_REPARTO;
                    break;
               case 3:
                    estadoPaquete = EstadosPaquete.PRIMER_VISITA_FALLIDA;
                    break;
               case 4:
                    estadoPaquete = EstadosPaquete.SEGUNDA_VISITA_FALLIDA;
                    break;
               case 5:
                    estadoPaquete = EstadosPaquete.ENTREGADO;
                    break;
          }

          verPaquetePorEstado(estadoPaquete);

     }

     @Override
     public void verPaquetePorEstado(EstadosPaquete estadosPaquete) {

          for(Paquete paquete : this.paquetes){

               EntradaSalida.SalidaInformacion(String.valueOf(estadosPaquete),"Lista de Paquetes");

               if(paquete.getEstado().equals(estadosPaquete)){

                    System.out.println(paquete);

               }
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
                    return codigo;
               } catch (CodigoPaqueteExistente e){

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

               EntradaSalida.SalidaAdvertencia("El cliente ya existe","ERROR");
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

          for(Cliente cliente : this.clientes){

               System.out.println(cliente);
               EntradaSalida.SalidaInformacion(cliente.toString(),"Datos cliente");
          }
     }

     //buscar cliente por DNI y modificarlo

     @Override
     public boolean modificarCliente(String dni)  {

          try{

               Cliente clienteAModificar = clientesRepo.buscar(dni);
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


               }while(opcion != 0);

               clientesRepo.modificar(clienteAModificar);

               return true;


          }catch (InexistenteException e){

               EntradaSalida.SalidaAdvertencia(e.getMessage(),"ERROR");
          }

          return false;
     }

     public void modificarDatosClientes(){

          String dni;

          //EntradaSalida.SalidaInformacion("Ingrese el DNI del cliente a modificar","");
          dni = EntradaSalida.entradaDNI();

          if(modificarCliente(dni)){

               EntradaSalida.SalidaInformacion("El cliente se modifico con exito!","");

          }else{
               EntradaSalida.SalidaInformacion("No se pudo modificar el cliente","ERROR");
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
                       "\n6 - Cargar un cliente" +
                       "\n7 - Modificar cliente" +
                       "\n8 - Ver perfil" +
                       "\n9 - Modificar mis datos" +
                       "\n\n 0 - Salir\n\n");


               while(opcion < 0 || opcion > 9){
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
                    case 6: // cargar un cliente
                         registroCliente();
                         break;
                    case 7: //modificar cliente
                         modificarDatosClientes();
                         break;
                    case 8: // ver perfil
                         verPerfil(dni);
                         break;
                    case 9: // modificar datos empleadoLocal
                         modificarDatosEmpleado(dni);
                         break;

               }

          }while(opcion != 0);

     }

     public void verPerfil(String dni){
          this.empleadosLocal = empleadoLocalRepo.listar();

          for(EmpleadoLocal empleado : this.empleadosLocal){

               if(empleado.getDni().equalsIgnoreCase(dni)){

                    System.out.println(empleado);

                    break;

               }
          }
     }

     //endregion

}
