package org.example.gestiones;

import org.example.enums.EstadosPaquete;
import org.example.excepciones.CodigoPaqueteExistente;
import org.example.excepciones.InexistenteException;
import org.example.interfacesDeManejo.ManejoCliente;
import org.example.interfacesDeManejo.ManejoEmpleado;
import org.example.interfacesDeManejo.ManejoPaquete;
import org.example.models.Cliente;
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
     PaqueteRepo paqueteRepo = new PaqueteRepo();
     ClientesRepo clientesRepo = new ClientesRepo();
     EmpleadoLocalRepo empleadoLocalRepo = new EmpleadoLocalRepo();
     ArrayList<Paquete> paquetes;
     ArrayList<Cliente> clientes;
     ArrayList<EmpleadoLocal> empleadosLocal;

     //endregion

     //region Metodos Paquete


     /**
      * Metodo que imprime por pantalla la informacion de un paquete, buscado por el codigo de identificacion
      * */
     @Override
     public void verUnPaquete(String codigo) {

          Paquete paqueteBuscado = paqueteRepo.buscar(codigo);

          if(paqueteBuscado != null){
               System.out.println(paqueteBuscado);
          }else{
               EntradaSalida.SalidaAdvertencia("El codigo ingresado no existe","ERROR");

          }
     }




     /**
      * Metodo que imprime por pantalla la informacion de un paquete que pertenezca al cliente que lo solicita
      *
      * */

     public void verUnPaqueteCliente(){

          EntradaSalida.SalidaInformacion("Ingrese el DNI del remitente","");
          String dni = entradaDNI();

          Cliente clienteBuscado = clientesRepo.buscar(dni);

          if(clienteBuscado != null){

               String codigo = EntradaSalida.entradaString("Ingrese el Codigo de Identificacion del paquete");

               Paquete paqueteBuscado = paqueteRepo.buscar(codigo);

               if(paqueteBuscado.getRemitente().getDni().equalsIgnoreCase(dni)){
                    verUnPaquete(codigo);
               }else{
                    EntradaSalida.SalidaAdvertencia("El paquete ingresado no pertenece al cliente ingresado","Advertencia");
               }

               verUnPaquete(codigo);

          }else{
               EntradaSalida.SalidaAdvertencia("El DNI ingresado no existe","ERROR");
          }
     }

     /**
      * Muestra por pantalla todos los paquetes pertenecientes a un cliente
      * */

     public void verPaquetesDeUnCliente(){
          EntradaSalida.SalidaInformacion("Ingrese el DNI del remitente","");
          String dni = entradaDNI();

          Cliente clienteBuscado = clientesRepo.buscar(dni);

          if(clienteBuscado != null){

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

          }else{
               EntradaSalida.SalidaAdvertencia("El DNI ingresado no existe","ERROR");
          }
     }


     //cargar paquete
     @Override
     public void registroPaquete() {

          Paquete nuevoPaquete = new Paquete();

          int opcion;

          opcion = EntradaSalida.entradaInt("1. Cliente nuevo\n2.Cliente existente");

          while(opcion < 1 || opcion > 2){
               opcion = EntradaSalida.entradaInt("ERROR - Ingrese una opcion valida");
          }

          if(opcion == 1){ //cliente nuevo
               registroCliente();
          }

          String dni = EntradaSalida.entradaDNI();

          Cliente remitente = clientesRepo.buscar(dni);

          if(remitente != null){
               nuevoPaquete.setId(paqueteRepo.buscarUltimoID() + 1);
               nuevoPaquete.setCodigoIdentificacion(nuevoCogigoPaquete());
               nuevoPaquete.setFechaIngreso(LocalDateTime.now());
               nuevoPaquete.setTiposPaquete(EntradaSalida.entradaTipoPaquete());
               nuevoPaquete.setZonaEntrega(EntradaSalida.entradaZona());
               nuevoPaquete.setDestinatario(EntradaSalida.entradaString("Ingrese el destinatario"));
               nuevoPaquete.setDomicilioEntrega(EntradaSalida.entradaString("Ingrese el domicilio de entrega"));
               nuevoPaquete.setEstado(EntradaSalida.entradaEstadosPaquete());

               paqueteRepo.agregar(nuevoPaquete);
          }else{
               EntradaSalida.SalidaAdvertencia("El DNI ingresado no existe","ERROR");
          }

     }


     public void verPaquetesPorEstado(){
          int opcion;
          EstadosPaquete estadoPaquete = EstadosPaquete.ANULADO;

          this.paquetes = paqueteRepo.listar();


          menuEstadosPaquete();

          opcion = EntradaSalida.entradaInt("Ingrese la opcion deseada");

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

     public void menuEstadosPaquete(){
          EntradaSalida.SalidaInformacion("1. En el correo\n2. Asiganado para reparto\n3. 1er visita fallida\n4. 2da visita fallida\n5. Entregado\n6. Anulado","Estados del Paquete");
     }

     //EmpleadoLocal no tiene el permiso para modificar un paquete
     @Override
     public boolean modificarPaquete(int id) {
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
          EntradaSalida.SalidaInformacion("Ingrese el DNI del cliente","Registro Cliente");

          String dni = EntradaSalida.entradaDNI();

          Cliente nuevoCliente = clientesRepo.buscar(dni);

          if(nuevoCliente == null){

               nuevoCliente.setId(clientesRepo.buscarUltimoID() + 1);
               nuevoCliente.setNombre(EntradaSalida.entradaString("Ingrese el nombre"));
               nuevoCliente.setApellido(EntradaSalida.entradaString("Ingrese el apellido"));
               nuevoCliente.setDni(dni);
               nuevoCliente.setTelefono(EntradaSalida.entradaTelefono());
               nuevoCliente.setMail(EntradaSalida.entradaMail());
               nuevoCliente.setUsername(EntradaSalida.entradaUsermane("Ingrese el nombre de usuario"));
               nuevoCliente.setPassword(nuevoCliente.getDni()); // se genera el usuario con el dni como constraseña, despues el cliente se cambia la contraseña si quiere
               nuevoCliente.setDomicilio(EntradaSalida.entradaString("Ingrese el domicilio"));
               nuevoCliente.setEstadoCliente(true);

               clientesRepo.agregar(nuevoCliente);

          }else{
               EntradaSalida.SalidaInformacion("Ya existe un cliente con el DNI ingresado","Advertencia");
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
          }
     }

     //buscar cliente por DNI y modificarlo

     @Override
     public boolean modificarCliente(String dni) throws InexistenteException {

          Cliente clienteAModificar = clientesRepo.buscar(dni);
          int opcion;

          if(clienteAModificar != null){

               do{
                    menuModificarCliente();

                    opcion = EntradaSalida.entradaInt("Ingrese la ocpcion deseada: ");

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

               clientesRepo.modificar(clienteAModificar);

               return true;


          }else{
               throw new InexistenteException("El DNI ingresado no existe!");

          }


     }

     public void modificarDatosClientes(){

          String dni;

          EntradaSalida.SalidaInformacion("Ingrese el DNI del cliente a modificar","");
          dni = EntradaSalida.entradaDNI();

          try{

               if(modificarCliente(dni)){
                    EntradaSalida.SalidaInformacion("El cliente se modifico con exito!","");
               }

          }catch (InexistenteException e){
               EntradaSalida.SalidaInformacion(e.getMessage(),"ERROR");
          }
     }

     public void menuModificarCliente(){

          //id - NO LO PUEDE MODIFICAR
          //DNI - NO LO PUEDE MODIFICAR -> DEBE ELIMINAR CUENTA Y HACER UNA NUEVA
          //username - NO LO PUEDE MODIFICAR -> DEBE ELIMINAR LA CUENTA Y HACER UNA NUEVA

          //nuevoCliente.setEstadoCliente(true); //puede eliminar clientes ??

          EntradaSalida.SalidaInformacion("1. Modificar nombre" +
                  "\n2. Modificar apellido" +
                  "\n3. Modificar telefono" +
                  "\n4. Modificar mail" +
                  "\n5. Modificar domicilio" +
                          "\n6. Modificar contraseña" +
                          "\n\n0. Salir"
                  , "Modificar Cliente");
     }

     //endregion

     //region Metodos del EmpleadoLocal

     //el EmpleadoLocal no puede dar de alta a otros EmpleadoLocal
     @Override
     public void registroEmpleado() {

     }


     @Override
     public void modificarEmpleado(String dni) {

          EmpleadoLocal empleadoAModificar = empleadoLocalRepo.buscar(dni);
          int opcion;

          if(empleadoAModificar != null){

               do {
                    menuModificarEmpleadoLocal();

                    opcion = EntradaSalida.entradaInt("Ingrese la opcion deseada");

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

          }else{
               EntradaSalida.SalidaAdvertencia("El dni ingresado no existe","ERROR");
          }



     }

     public void menuModificarEmpleadoLocal(){

          EntradaSalida.SalidaInformacion("\n1. Modificar nombre" +
                  "\n2. Modificar apellido" +
                  "\n3. Modificar telefono" +
                  "\n4. Modificar mail" +
                          "\n5. Modificar contraseña" +
                  "\n\n0. Salir"
                  ,"Modificar datos personales");
     }




     //endregion

     //region Menu Principal EmpleadoLocal

     //logueo

     public void logueo(){

          this.empleadosLocal = empleadoLocalRepo.listar();

          EntradaSalida.SalidaInformacion("Ingrese su DNI","LOG IN");
          String dni = EntradaSalida.entradaDNI();

          EmpleadoLocal empleadoLocal = empleadoLocalRepo.buscar(dni);

          if(empleadoLocal != null){

               String pass = EntradaSalida.entradaString("Ingrese la contraseña");

               if(empleadoLocal.getPassword().equals(pass)){

                    menuPrincipal(dni);

               }else{
                    EntradaSalida.SalidaAdvertencia("Contraseña incorrecta","ERROR");
               }

          }else{
               EntradaSalida.SalidaAdvertencia("El DNI ingresado no existe","ERROR");
          }

     }

     //menu
     public void menuPrincipalOpciones(){
          EntradaSalida.SalidaInformacion("1. Buscar paquete de un cliente" +
                  "\n2. Cargar un paquete" +
                  "\n3. Ver paquetes de un cliente" +
                  "\n4. Ver paquetes por estado" +
                  "\n5. Ver clientes" +
                  "\n6. Cargar un cliente" +
                  "\n7. Modificar cliente" +
                  "\n8. Ver perfil" +
                  "\n9. Modificar mis datos" +
                  "\n\n0. Salir"

                  ,"MENU PRINCIPAL EMPLEADO LOCAL");
     }


     public void menuPrincipal(String dni){

          int opcion;

          do {

               menuPrincipalOpciones();

               opcion = EntradaSalida.entradaInt("Ingrese la opcion deseada");

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
                         modificarEmpleado(dni);
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
