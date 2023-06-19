package org.example.gestiones;

import org.example.excepciones.CodigoPaqueteExistente;
import org.example.excepciones.InexistenteException;
import org.example.interfacesDeManejo.ManejoCliente;
import org.example.interfacesDeManejo.ManejoPaquete;
import org.example.repositorio.ClientesRepo;
import org.example.repositorio.PaqueteRepo;

public class GestionEmpleadoLocal implements ManejoPaquete, ManejoCliente {

     PaqueteRepo paqueteRepo = new PaqueteRepo();
     ClientesRepo clientesRepo = new ClientesRepo();

     @Override
     public boolean modificarCliente(String dni) throws InexistenteException {
          return false;
     }

     @Override
     public void registroCliente() {

     }

     @Override
     public boolean modificarPaquete(int id) {
          return false;
     }

     @Override
     public void registroPaquete() {

     }

     @Override
     public void validacionCodigoPaquete(String codigo) throws CodigoPaqueteExistente {

     }

     @Override
     public String nuevoCogigoPaquete() {
          return null;
     }

     /**PaqueteRepo paqueteRepo = new PaqueteRepo();

     @Override
     public boolean modificarPaquete(int id) {
     return false;
     }

     @Override
     public void registroPaquete() {
     Paquete nuevo = new Paquete();
     nuevo.setId((paqueteRepo.buscarUltimoID())+1);

     nuevo.setFechaIngreso(LocalDateTime.now());
     //nuevo.setRemitente(EntradaSalida.entradaString("    NUEVO PAQUETE \nIngrese el remitente"));
     EntradaSalida.SalidaInformacion("Seleccione el tipo de paquete", "TIPO DE PAQUETE");
     nuevo.setTiposPaquete(EntradaSalida.entradaTipoPaquete());
     EntradaSalida.SalidaInformacion("Seleccione la Zona", "Zona");
     nuevo.setZonaEntrega(EntradaSalida.entradaZona());
     nuevo.setDestinatario(JOptionPane.showInputDialog("Ingrese el destinatario"));
     nuevo.setDomicilioEntrega(JOptionPane.showInputDialog("Ingrese el domicilio de entrega"));
     // nuevo.setEstado(EntradaSalida);  CONTINUAR CON VALIDACION DE ENTRADA

     paqueteRepo.agregar(nuevo);

     }

     public String nuevoCogigoPaquete (){
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

     @Override
     public void validacionCodigoPaquete(String codigo) throws CodigoPaqueteExistente {
     for (Paquete paquete: paqueteRepo.listar()) {
     if (paquete.getCodigoIdentificacion().equals(codigo)){
     throw new CodigoPaqueteExistente("Codigo Paquete Existente");
     }
     break;
     }
     }
     */

}
