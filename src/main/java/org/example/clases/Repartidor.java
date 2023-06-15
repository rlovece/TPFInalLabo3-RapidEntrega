package org.example.clases;
import org.example.enums.TiposPaquete;
import org.example.enums.Zonas;

import java.util.ArrayList;

public class Repartidor extends Empleado {

    private Supervisor supervisor;
    private String licencia;

    private Zonas zona;
    private TiposPaquete tiposPaquetes;
    private ArrayList<Paquete> paquetesAsignados;

}
