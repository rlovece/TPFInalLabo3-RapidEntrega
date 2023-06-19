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
import java.util.ArrayList;

public class GestionAdmin implements ManejoPaquete {

    ///region Atributos (los repositorios)
    PaqueteRepo paqueteRepo = new PaqueteRepo();
    SupervisorRepo supervisorRepo = new SupervisorRepo();
    RepartidorRepo repartidorRepo = new RepartidorRepo();
    EmpleadoLocalRepo empleadoLocalRepo = new EmpleadoLocalRepo();
    ClientesRepo clientesRepo = new ClientesRepo();
    //endregion ()

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
    ///endregion

    //region Manejo Paquetes
    /**
     * <h2>Menu Gestión de Paquetes</h2>
     *Muentra dentro de un ciclo do-while las opciones que tiene el administrador sobre los paquetes,
     *lee el ingreso por teclado con JOptionPane,
     * e ingresa al switch correspondiente donde se invocan otros metodos para continuar con la gestión.
     *El ciclo se repite hasta que el usuario admin ingrese la opción 0 para volver al
     * {@link GestionAdmin#menuPrincipal()}.
     *
     * @see EntradaSalida
     * @author Ruth Lovece
     */
    public void menuManejoPaquetes(){
        int opcion = 0;
        do {
            opcion = EntradaSalida.entradaInt("      ELIJA UNA OPCION  \n" +
                    "\n 1 - Ver Paquetes por estado" +
                    "\n 2 - Ver un Paquete" +
                    "\n 3 - Modificar un Paquete" +
                    "\n 4 - Registrar un Paquete" +
                    "\n 0 - Volver\n\n");

            switch (opcion){
                case 1:
                    EstadosPaquete estado = EntradaSalida.entradaEstadosPaquete();
                    verPaquetePorEstado(estado);
                    break;

                case 2:

                    break;

                default:
                    break;
            }
        } while (opcion!=0);
    }

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
