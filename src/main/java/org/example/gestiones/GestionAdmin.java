package org.example.gestiones;

import org.example.excepciones.CodigoPaqueteExistente;
import org.example.interfacesDeManejo.ManejoPaquete;
import org.example.repositorio.PaqueteRepo;

public class GestionAdmin implements ManejoPaquete {

    PaqueteRepo paqueteRepo = new PaqueteRepo();

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
