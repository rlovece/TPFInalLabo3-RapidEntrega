package org.example.recursos;
import org.example.enums.EstadosPaquete;
import org.example.enums.TiposPaquete;
import org.example.enums.Zonas;
import org.example.excepciones.CodigoPaqueteExistente;
import org.example.excepciones.EdadInvalida;

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
    public static Zonas entradaZona (){
        boolean continuar = false;
        EnumSet<Zonas> tiposPaquetes = EnumSet.allOf(Zonas.class);
        do {
            try {
                String entrada = showInputDialog("\n Introduzca la zona válida: \n" + tiposPaquetes + "\n\n");
                Zonas zonaEntrada = Zonas.valueOf(entrada);
                return zonaEntrada;
            } catch (IllegalArgumentException e){
                String error = "Introduzca una Zona";
                EntradaSalida.SalidaError(error, "Error");
            }
        } while (!continuar);
        return  null;
    }
    public static Zonas entradaZona2 (){
        boolean continuar = false;
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
    public static String entradaString (String msj){
        return showInputDialog(msj);
    }
    public static String entradaMail (String msj){
        return showInputDialog(msj);
    }
    public static String entradaDNI (String msj){
        return showInputDialog(msj);
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


    ///endregion

}