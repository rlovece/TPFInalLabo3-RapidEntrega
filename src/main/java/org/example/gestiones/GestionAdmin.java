package org.example.gestiones;

import org.example.enums.EstadosPaquete;
import org.example.excepciones.CodigoPaqueteExistente;
import org.example.excepciones.PasswordInvalida;
import org.example.interfacesDeManejo.ManejoPaquete;
import org.example.models.Cliente;
import org.example.models.Paquete;
import org.example.models.Supervisor;
import org.example.recursos.EntradaSalida;
import org.example.repositorio.*;

import javax.swing.*;
import java.time.LocalDateTime;

public class GestionAdmin implements ManejoPaquete {

    PaqueteRepo paqueteRepo = new PaqueteRepo();
    SupervisorRepo supervisorRepo = new SupervisorRepo();
    RepartidorRepo repartidorRepo = new RepartidorRepo();
    EmpleadoLocalRepo empleadoLocalRepo = new EmpleadoLocalRepo();
    ClientesRepo clientesRepo = new ClientesRepo();

    //region Logueo
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

    public void menuPrincipal(){
        int opcion = 0;
        do {
            opcion = EntradaSalida.entradaInt("      ELIJA OPCION  \n" +
                    "\n 1 - Manejar Paquetes" +
                    "\n 2 - Manejar Empleados" +
                    "\n 0 - Cerrar Sesión\n\n");

            switch (opcion){
                case 1:
                    menuManejoPaquetes();
                    break;

                case 2:
                    menuManejoEmpleados();
                    break;

                default:
                    break;
            }
        } while (opcion!=0);
    }


    //region Manejo Paquetes
    public void menuManejoPaquetes(){
        //FALTA IMPLEMENTAR
    };

    //endregion


    //region Manejo Empleados
    public void menuManejoEmpleados(){
        //FALTA IMPLEMENTAR
    };
    //endregion


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
    public void verUnPaquete(String codigo) {

    }

    @Override
    public void verPaquetePorEstado(EstadosPaquete estadosPaquete) {

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

    static void validarPassword (String contraseña) throws PasswordInvalida {
        if (!contraseña.equals("123Admin")){
            throw new PasswordInvalida("Contraseña incorrecta!");
        }
    }
}
