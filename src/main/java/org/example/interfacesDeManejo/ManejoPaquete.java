package org.example.interfacesDeManejo;

import org.example.excepciones.CodigoPaqueteExistente;
import org.example.models.Cliente;
import org.example.models.Paquete;

public interface ManejoPaquete {

    boolean modificarPaquete (int id);
    void registroPaquete();
    void validacionCodigoPaquete () throws CodigoPaqueteExistente;

}
