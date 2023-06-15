package org.example.Models;
import org.example.enums.Zonas;

import java.util.ArrayList;

public class Supervisor extends Empleado {

    private Zonas zona;
    private int cantEmpleadosACargo;

    private ArrayList<Empleado> empleadosACargo;
}
