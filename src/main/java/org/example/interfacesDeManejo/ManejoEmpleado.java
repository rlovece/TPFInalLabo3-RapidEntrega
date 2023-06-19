package org.example.interfacesDeManejo;

import org.example.models.Empleado;
import org.example.models.Supervisor;

public interface ManejoEmpleado {
    void registroEmpleado();
    Empleado modificarEmpleado(String dni);

}
