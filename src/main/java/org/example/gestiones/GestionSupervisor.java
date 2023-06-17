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

    public Supervisor altaSupervisor ()
    {
        Supervisor nuevo= new Supervisor();

        nuevo.setNombreYApellido(EntradaSalida.entradaString("     NUEVO SUPERVISOR  \nIngrese el nombre y apellido:"));
        nuevo.setDni(EntradaSalida.entradaString("Ingrese el DNI"));
        nuevo.setTelefono(EntradaSalida.entradaString("Ingrese el numero de telefono"));
        nuevo.setLegajo(EntradaSalida.entradaInt("Ingrese el numero de legajo"));
        dni, telefono, username, password

        return nuevo;
    }

    ///endregion

    /// region Metodos Cliente
    @Override
    public boolean modificarCliente(String dni) {
        return false;
    }

    @Override
    public Cliente buscarCliente(String dni) {
        return null;
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
    public Paquete buscarPaquete(int id) {
        return null;
    }

    @Override
    public void registroPaquete() {

    }

    /// endregion
}
