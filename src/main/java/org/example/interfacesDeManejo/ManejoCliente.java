package org.example.interfacesDeManejo;
import org.example.models.Cliente;


public interface ManejoCliente {

    boolean modificarCliente (String dni);

    Cliente buscarCliente (String dni);

     void registroCliente();
}
