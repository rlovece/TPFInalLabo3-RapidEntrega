package org.example.interfacesDeManejo;

import org.example.models.Cliente;
import org.example.models.Paquete;

public interface ManejoPaquete {

    Paquete modificarPaquete (int id);

    Paquete buscarPaquete (int id);

    void registroPaquete();
}
