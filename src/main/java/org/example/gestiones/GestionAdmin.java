package org.example.gestiones;

import org.example.enums.EstadosPaquete;
import org.example.excepciones.CodigoPaqueteExistente;
import org.example.excepciones.InexistenteException;
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
    ///endregion

    //regionNO Implementadas porque no es tarea de administrador
    @Override
    public void registroPaquete() {}
    @Override
    public String nuevoCogigoPaquete (){return null;}
    //endregion

    //endregion


    //region Manejo Empleados
    public void menuManejoEmpleados(){
        //FALTA IMPLEMENTAR
    };
    //endregion







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
