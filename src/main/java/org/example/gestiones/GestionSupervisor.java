package org.example.gestiones;

import org.example.interfacesDeManejo.ManejoCliente;
import org.example.interfacesDeManejo.ManejoPaquete;
import org.example.models.Cliente;
import org.example.models.Paquete;
import org.example.models.Supervisor;
import org.example.recursos.EntradaSalida;
import org.example.repositorio.SupervisorRepo;

import java.util.ArrayList;
import java.util.Scanner;

public class GestionSupervisor implements ManejoCliente, ManejoPaquete {



    SupervisorRepo repoSuper = new SupervisorRepo();
    ArrayList<Supervisor> listadoSupervisores;

    /// region Metodos Supervisor


    ///endregion

    /// region Metodos Cliente
    @Override
    public boolean modificarCliente(String dni) {
        return false;
    }

    @Override
    public void registroCliente() {

    }

    /// endregion

    /// region Metodos Paquete

    @Override
    public boolean modificarPaquete(int id) {
        return false;
    }


    @Override
    public void registroPaquete() {

    }

    /// endregion
}
