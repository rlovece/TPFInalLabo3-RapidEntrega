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
      * <h2>Ver Paquete</h2>
      * Muestra los datos de un paquete.
      * Verifica que el codigo de identificacion del paquete exista en el archivo.
      *
      * @param codigo Recibe el Codigo de Identificacion del paquete
      * @author Angeles Higa
      */
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
      * <h2>Ver Paquete de un Cliente</h2>
      * Muestra los datos de un paquete de un Cliente.
      * <br><br>
      * Solicita el DNI del Cliente que solicita la busqueda del paquete. Verifica que el DNI exista en el archivo correspondiente.
      * <br><br>
      * Solicita el Codigo de Identificacion del Paquete. Verifica que el Codigo exista en el archivo correspondiente.
      * <br><br>
      * Si existe el Paquete, verifica que pertenezca al Cliente.
      *
      * @author Angeles Higa
      */

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
      * <h2>Ver Paquetes de un Cliente</h2>
      * Muestra todos los paquetes de un cliente.
      * <br><br>
      * Solicita el DNI del Cliente. Verifica que el DNI exista en el archivo correspondiente.
      *
      * @author Angeles Higa
      */

     public void verPaquetesDeUnCliente(){

          String dni = entradaDNI();

          try{

               Cliente clienteBuscado = clientesRepo.buscar(dni);
               this.paquetes = paqueteRepo.listar();

              if(paquetes != null){

                    StringBuilder stringBuilder = new StringBuilder();

                    boolean tienePaquetes = false;

                    for(Paquete paquete : this.paquetes){

                         if(paquete.getRemitente().getDni().equalsIgnoreCase(dni)){

                              stringBuilder.append(paquete.toStringListarCliente());

                              tienePaquetes = true;
                         }
                    }

                    if(tienePaquetes){
                         EntradaSalida.SalidaInformacion(stringBuilder.toString(),"LISTA DE PAQUETES");
                    }else{
                         EntradaSalida.SalidaInformacion("El cliente no tiene paquetes registrados","Informacion");
                    }


               }else{
                   EntradaSalida.SalidaInformacion("No hay paquetes registrados","!");
              }



          }catch(InexistenteException e){
               EntradaSalida.SalidaAdvertencia(e.getMessage(),"ERROR");
          }
     }


     /**
      * <h2>Registrar un Paquete</h2>
      * Registra un nuevo paquete en el sistema.
      * Permite cargar un paquete a un cliente nuevo o a un cliene existente.
      * <br><br>
      * Solicita el DNI del cliente que solicita el envio de un paquete.
      * Verifica que el DNI exista en el archivo correspondiente.
      * <br><br>
      * Se solicita el ingreso de los datos por teclado con JOptionPane.
      * <br><br>
      * Mediante la clase LocalDateTime se le asigna al paquete su fecha de ingreso al sistema.
      * <br><br>
      * Mediante el metodo nuevoCodigoPaquete se le asigna el codigo de identificaicon al paquete.
      *
      * @see EntradaSalida
      * @author Angeles Higa
      */
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
               EntradaSalida.SalidaInformacion("Cliente registrado con exito!","");
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

     /**
      * <h2>Ver Paquetes por Estado</h2>
      * Solicita la eleccion del estado del paquete mediante JOptionPane. Se invoca al metodo para que muestre el listado.
      *
      * @see EntradaSalida
      * @author Angeles Higa
      */

     public void verPaquetesPorEstado(){

          EstadosPaquete estadoPaquete = EntradaSalida.entradaEstadosPaquete();

          this.paquetes = paqueteRepo.listar();

          verPaquetePorEstado(estadoPaquete);

     }

     /**
      * <h2>Ver Paquete por Estado</h2>
      * Muestra un listado de paquetes segun el estado seleccionado.
      *
      * @param estadosPaquete Recibe el estado de los paquetes seleccionado
      * @author Angeles Higa
      */

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

     /**
      * <h2>Validacion Codigo Paquete</h2>
      * Valida si el codigo generado automaticamente ya existe para otro paquete dentro del archivo correspondiente.
      *
      * @param codigo Recibe el codigo de identifiacion del paquete
      * @throws CodigoPaqueteExistente
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

     /**
      * <h2>Nuevo Codigo Paquete</h2>
      * Genera un codigo de identificacion de paquete. Verifica que no exista el codigo en el archivo correspondiente.
      *
      * @return codigo
      * @see EntradaSalida
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
                         EntradaSalida.SalidaAdvertencia(e.getMessage(),"ERROR");
               }
          } while (!continuar);
          return null;
     }

     //endregion

     //region Metodos Cliente

     /**
      * <h2>Registro Cliente</h2>
      * Solicita el DNI del cliente a dar de alta. Verifica que no exista en el archivo correspondiente.
      * <br><br>
      * Si el DNI pertenece a un cliente dado de baja, se lo da de alta nuevamente.
      * <br><br>
      * Caso contrario se solicitan los datos por teclado con JOptionPane.
      * @see EntradaSalida
      * @author Angeles Higa
      */


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
      * <h2>Ver Clientes</h2>
      * Muestra una lista de todos los clientes activos.
      *
      *
      * @author Angeles Higa
      */
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

     /**
      * <h2>Ver Cliente</h2>
      * Muestra un cliente buscado por DNI.
      *<br><br>
      * Solicita el ingreso del DNI buscado por teclado conJOptionPane. Verifica que el DNI exista en el arhcivo correspondiente.
      *
      * @author Angeles Higa
      */

     public void verCliente(){

          String dni = EntradaSalida.entradaDNI();

          try{

               Cliente clienteBuscado = clientesRepo.buscar(dni);

               if(clienteBuscado.isEstadoCliente()){
                    this.clientes = clientesRepo.listar();

                    for(Cliente cliente : this.clientes){

                         if(cliente.getDni().equalsIgnoreCase(dni)){
                              EntradaSalida.SalidaInformacion(cliente.toString(),"DATOS CLIENTE");
                              break;
                         }

                    }
               }else{
                    EntradaSalida.SalidaAdvertencia("El cliente esta deshabilitado","!");
               }



          }catch (InexistenteException e){
               EntradaSalida.SalidaAdvertencia(e.getMessage(),"ERROR");
          }
     }

     /**
      * <h2>Modificar Cliente</h2>
      * Muestra las opciones con los atributos a modificar del cliente mediante un bucle do-while.
      * Se ingresa la opcion deseada por teclado con JOptionPane y se ingresa al switch correspondiente.
      * Se sale del bucle al ingresar la opcion 0 o al dar de baja al cliente.
      *
      * @param clienteAModificar Recibe el objeto a modificar
      * @author Angeles Higa
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

     /**
      * <h2>Modificar Datos Cliente</h2>
      *
      * Solicita el ingreso del DNI buscado por teclado conJOptionPane. Verifica que el DNI exista en el archivo correspondiente.
      * Invoca al metodo modificarCliente.
      *
      * @author Angeles Higa
      */

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

     /**
      * <h2>Modificar Empleado Local</h2>
      * Muestra las opciones con los atributos a modificar del cliente mediante un bucle do-while.
      * Se ingresa la opcion deseada por teclado con JOptionPane y se ingresa al switch correspondiente.
      * Se sale del bucle al ingresar la opcion 0.
      * <br><br>
      * Verifica que el DNI del empleado exista en el archivo correspondiente.
      *
      *
      * @param dni - Recibe por parametro el DNI del EmpleadoLocal logueado.
      * @author Angeles Higa
      */
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
                              EntradaSalida.SalidaInformacion("Ingrese la nueva contraseña","");
                              empleadoAModificar.setPassword(EntradaSalida.entradaGeneracionPassword());
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

     /**
      * <h2>Logueo Empleado Local</h2>
      * Se solicita el DNI del EmpleadoLocal para poder ingresar. Verifica que el DNI exista en el archivo correspondiente.
      * <br><br>
      * Se solicita la contraseña. Verifica que la contraseña sea correcta. De ser correcta se permite ingresar al Menu Principal.
      * @author Angeles Higa
      */

     public void logueo(){

          this.empleadosLocal = empleadoLocalRepo.listar();

          String dni = EntradaSalida.entradaString("Ingrese el DNI");


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

     /**
      * <h2>Menu Principal de Gestion Empleado Local</h2>
      * Muestra dentro de un ciclo do-while las opciones principales que tiene un EmpleadoLocal para realizar su gestion.
      * Lee el ingreso por teclado con JOptionPane e ingresa al switch correspondiente donde se invocan otros metodos para continuar con la gestion.
      * El ciclo se repite hasta que el usuario Repartidor cierre sesion con la opcion 0.
      *
      * @see EntradaSalida
      * @param dni - Recibe el DNI del EmpleadoLocal logueado
      * @author Angeles Higa
      */

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

     /**
      * <h2>Ver Datos del EmpleadoLocal</h2>
      * Muestra los datos personales del EmpleadoLocal logueado.
      *
      * @param dni Recibe el DNI del EmpleadoLocal logueado
      * @author Angeles Higa
      */

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
