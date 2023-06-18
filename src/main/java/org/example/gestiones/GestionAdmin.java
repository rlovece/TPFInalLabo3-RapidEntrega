package org.example.gestiones;

import org.example.excepciones.CodigoPaqueteExistente;
import org.example.interfacesDeManejo.ManejoPaquete;

public class GestionAdmin implements ManejoPaquete {
    @Override
    public boolean modificarPaquete(int id) {
        return false;
    }

    @Override
    public void registroPaquete() {

    }

    @Override
    public void validacionCodigoPaquete() throws CodigoPaqueteExistente {

    }
}
