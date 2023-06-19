package org.example.interfacesDeManejo;
import org.example.excepciones.InexistenteException;
import org.example.models.Cliente;


public interface ManejoCliente {
    boolean modificarCliente (String dni) throws InexistenteException;
    void registroCliente();
}
