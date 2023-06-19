package org.example.interfacesDeManejo;

import org.example.enums.EstadosEmpleado;
import org.example.enums.EstadosPaquete;
import org.example.excepciones.CodigoPaqueteExistente;
import org.example.models.Cliente;
import org.example.models.Paquete;

public interface ManejoPaquete {

    boolean modificarPaquete (int id);
    void registroPaquete();
    void validacionCodigoPaquete (String codigo) throws CodigoPaqueteExistente;
    String nuevoCogigoPaquete ();
    void verUnPaquete (String codigo);
    void verPaquetePorEstado (EstadosPaquete estadosPaquete);

}
