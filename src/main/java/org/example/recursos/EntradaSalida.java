package org.example.recursos;
import org.example.enums.EstadosPaquete;
import org.example.enums.TiposPaquete;
import org.example.enums.Zonas;
import org.example.excepciones.CodigoPaqueteExistente;
import org.example.excepciones.DNIIncorrecto;
import org.example.excepciones.EdadInvalida;
import org.example.excepciones.EmailIncorrecto;

import javax.swing.*;
import java.util.EnumSet;
import java.util.UUID;

import static javax.swing.JOptionPane.*;

public class EntradaSalida {

    ///region Entradas
    public static int entradaInt (String msj){

        boolean continuar = false;
        int nro = 0;

        while (!continuar) {
            try {
                String entrada = showInputDialog(msj);
                nro = Integer.parseInt(entrada);
                continuar = true;
            } catch (NumberFormatException e) {
                String error = "Introduzca un nro entero";
                EntradaSalida.SalidaError(error, "Error");
            }
        }
        return  nro;
    }
    public static float entradafloat (String mensaje){
        boolean continuar = false;
        float nro = 0;
        do {
            try {
                String entrada = showInputDialog(mensaje);
                nro= Float.parseFloat(entrada);
                continuar = true;
            }catch (NumberFormatException e){
                String error = "Introduzca un nro";
                EntradaSalida.SalidaError(error, "Error");
            }
        } while (continuar);
        return  nro;
    }
    public static float entradaEdad (String mensaje){
        boolean continuar = false;
        int nro = 0;
        do {
            try {
                String entrada = showInputDialog(mensaje);
                nro= Integer.parseInt(entrada);
                validarEdad(nro);
                continuar = true;
            } catch (NumberFormatException e){
                String error = "Introduzca su edad, cant de años";
                EntradaSalida.SalidaError(error, "Error");
            } catch (EdadInvalida e) {
                String error = "Debe ser mayor de 18 años";
                EntradaSalida.SalidaError(error, "Error");
            }
        } while (continuar);
        return  nro;
    }
    public static TiposPaquete entradaTipoPaquete (){
        boolean continuar = false;
        EnumSet<TiposPaquete> tiposPaquetes = EnumSet.allOf(TiposPaquete.class);
        do {
            try {
                String entrada = showInputDialog("\n Introduzca el tipo de paquete: \n" + tiposPaquetes + "\n\n");
                TiposPaquete tipoEntrada = TiposPaquete.valueOf(entrada);
                return tipoEntrada;
            } catch (IllegalArgumentException e){
                String error = "Introduzca un Tipo de Paquete Valido";
                EntradaSalida.SalidaError(error, "Error");
            }
        } while (!continuar);
        return  null;
    }

    /**
     * <h1> Entrada de una Zona</h1>
     * Metodo para ingresar una instancia de Zonas. El método permite sellecionar la zona deseada eliminado
     * posibles errores y siendo amigable con el usuario.
     * Para esto utiliza una arreglo de Zonas y luego se muestra el atributo descripción utilizando
     * JComboBox y JOptionPane
     *
     * @see Zonas
     * @see JComboBox
     * @see JOptionPane
     * @return Zona seleccionada
     * @author Ruth Lovece
     */
    public static Zonas entradaZona(){
        Zonas[] zonas = Zonas.values();

        JComboBox<Zonas> comboBox = new JComboBox<>(zonas);

        int seleccion = JOptionPane.showOptionDialog(
                null,
                comboBox,
                "Seleccione una zona",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                zonas[0]);

        Zonas zonaEntrada = zonas[0];;
        if (seleccion != JOptionPane.CLOSED_OPTION) {
             zonaEntrada = (Zonas) comboBox.getSelectedItem();
            System.out.println("Opción seleccionada: " + zonaEntrada);
        } else {
            System.out.println("Diálogo cerrado sin selección.");
        }
        return zonaEntrada;
    }

    public static EstadosPaquete entradaEstadosPaquete (){
        boolean continuar = false;
        EnumSet<EstadosPaquete> tiposEstadosPaquetes = EnumSet.allOf(EstadosPaquete.class);
        do {
            try {
                String entrada = showInputDialog("\n Introduzca el tipo de paquete: \n" + tiposEstadosPaquetes + "\n\n");
                EstadosPaquete estadoPaquetes = EstadosPaquete.valueOf(entrada);
                return estadoPaquetes;
            } catch (IllegalArgumentException e){
                String error = "Introduzca un Tipo de Estado de Paquete Valido";
                EntradaSalida.SalidaError(error, "Error");
            }
        } while (!continuar);
        return  null;
    }

    /**
     * <h1> Entrada de un String</h1>
     * Metodo para ingresar  un String utilizando JOptionPane
     *
     * @see JOptionPane
     * @return String ingresado por teclado
     * @author Ruth Lovece
     */
    public static String entradaString (String msj){
        return showInputDialog(msj);
    }

    /**
     * <h1> Entrada de un email</h1>
     * Metodo para solicitar el ingreso de correo electronico por teclado. Se utiliza JOptionPane para la
     * solicitud.
     * <p>
     * Se invoca método {@link org.example.recursos.EntradaSalida#validarEmail(String)} dentro de un try-catch
     * para asegurase que el correo ingresado contenga @ y .com.
     * En caso de ingresar al catch, se muetra el error al usuario y se ingresa nuevamente al bucle do.
     *
     * @see JOptionPane
     * @return email ingresado por teclado
     * @author Ruth Lovece
     */
    public static String entradaMail (){
        boolean continuar = false;
        do {
            try {
                String entrada = showInputDialog("\n Ingrese correo electrónico \n\n");
                validarEmail(entrada);
                return entrada;
            } catch (EmailIncorrecto e){
                String error = "Correo inválido, debe contener @ y .com \n\n";
                EntradaSalida.SalidaError(error, "Error");
            }
        } while (!continuar);
        return  null;
    }

    /**
     * <h1> Entrada de un DNI</h1>
     * Metodo para solicitar el ingreso de DNI por teclado. Se utiliza JOptionPane para la
     * solicitud.
     * <p>
     * Se invoca mètodo {@link org.example.recursos.EntradaSalida#validarDNI(String)} dentro de un try-catch
     * para asegurase que el DNI contiene 8 dìgitos numericos
     * En caso de ingresar al catch, se muetra el error al usuario y se ingresa nuevamente al bucle do.
     *
     * @see JOptionPane
     * @return DNI ingresado por teclado
     * @author Ruth Lovece
     */
    public static String entradaDNI (){
        boolean continuar = false;
        do {
            try {
                String entrada = showInputDialog("\n Ingrese DNI \n\n");
                validarDNI(entrada);
                return entrada;
            } catch (DNIIncorrecto e){
                String error = "El DNI debe contener 8 digitos numericos. \nSi Su DNI tiene 7 digitos, agregue 0 al comienzo. \n\n";
                EntradaSalida.SalidaError(error, "Error");
            }
        } while (!continuar);
        return  null;
    }

    public static String entradaUsermane (String msj){return showInputDialog(msj);}

    ///endRegion

    ///region Salidas
    public static void SalidaError (String msj, String titulo){
        JOptionPane.showMessageDialog(null, msj, titulo, ERROR_MESSAGE);
    }

    public static void SalidaInformacion (String msj, String titulo){
        JOptionPane.showMessageDialog(null, msj, titulo, INFORMATION_MESSAGE);
    }

    public static void SalidaAdvertencia (String msj, String titulo) {
        JOptionPane.showMessageDialog(null, msj, titulo, WARNING_MESSAGE);
    }
    ///endregion

    ///region Entradas Aleatoria
    public static String CodigoPaquete (){
        String codigo = UUID.randomUUID().toString().substring(0,10).toUpperCase(); //genera un codigo random
        return codigo;
    }

    ///endregion

    ///region Validaciones
    public static void validarEdad (int edad) throws EdadInvalida {
        if (edad < 18 || edad > 120){
            throw new EdadInvalida("Edad invalida");
        }
    }

    /**
     * <h1> Validación ingreso Email</h1>
     * Método que valida que la dirección de correo electrónico ingresada contenga @ y .com
     * En caso de no contenear cualquiera estos se lanza un excepción.
     *
     * @param email
     * @exception EmailIncorrecto
     * @author Ruth Lovece
     *
     * */
    public static void validarEmail (String email) throws EmailIncorrecto {
        if (!email.contains("@") || !email.contains(".com")){
            throw new EmailIncorrecto("Formato de email incorrecto");
        }
    }

    /**
     * <h1> Validación ingreso DNI</h1>
     * Método que valida que el dni contenga 8 dígitos numéricos.
     * En caso contrario se lanzará la excepción
     *
     * @param dni
     * @exception DNIIncorrecto
     * @author Ruth Lovece
     *
     * */
    public static void validarDNI (String dni) throws DNIIncorrecto {
        try {
            Integer.parseInt(dni);
        } catch (NumberFormatException e) {
            throw new DNIIncorrecto("DNI con digitos no numéricos");
        }
        if (dni.length()!=8) {
            throw new DNIIncorrecto("DNI no contiene 8 digitos");
        }

    }

    ///endregion

}