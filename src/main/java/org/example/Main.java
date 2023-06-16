package org.example;

import org.example.enums.Zonas;
import org.example.recursos.EntradaSalida;

public class Main {
    public static void main(String[] args) {

        int nro = EntradaSalida.entradaInt("HOLA");
        Zonas entrada = EntradaSalida.entradaZona2();
        EntradaSalida.SalidaInformacion(entrada.getDescripcion(), "Usted eligio...");
    }
}