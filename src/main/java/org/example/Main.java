package org.example;

import org.example.Enums.TiposPaquete;
import org.example.Enums.Zonas;
import org.example.Recursos.EntradaSalida;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        Zonas entrada = EntradaSalida.entradaZona();
        JOptionPane.showMessageDialog(null, entrada, "", JOptionPane.WARNING_MESSAGE);
    }
}