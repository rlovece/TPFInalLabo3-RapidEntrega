package org.example.Models;
import org.example.Enums.TiposPaquete;
import org.example.Enums.Zonas;

import java.util.ArrayList;

public class Repartidor extends Empleado {

    private Supervisor supervisor;
    private String licencia;

    private Zonas zona;
    private TiposPaquete tiposPaquetes;
    private ArrayList<Paquete> paquetesAsignados;

}
