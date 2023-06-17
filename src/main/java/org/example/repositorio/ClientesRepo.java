// <editor-fold defaultstate="collapsed" desc="Paquetes y Librerias">
package org.example.repositorio;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.example.models.Cliente;
import org.example.models.Supervisor;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//</editor-fold>

public class ClientesRepo implements IRepositorio<Cliente> {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    String ruta = "src/main/java/org/example/archivos/clientes.json";
    ObjectMapper mapeo = new ObjectMapper();
    private final File archivo = new File(ruta);
    ArrayList<Cliente> listadoClientes = new ArrayList<>();

//    </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Alta / Baja / Modificacion / Listado">
    @Override
    public void agregar(Cliente... objeto) {
        cargar();
        listadoClientes.addAll(List.of(objeto));
        guardar();
    }

    @Override
    public ArrayList<Cliente> listar() {
        cargar();
        return this.listadoClientes;
    }

    @Override
    public void eliminar(int dato) {
        cargar();
        Cliente aux = null;
        try {
            for (Cliente cliente : listadoClientes) {
                if (cliente.getId() == dato) {
                    aux = cliente;
                    break;
                }
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        listadoClientes.remove(aux);
        guardar();
    }

    @Override
    public void modificar(Cliente nuevo) {
        cargar();
        try {
            for (Cliente user : listadoClientes) {
                if (user.getId() == nuevo.getId()) {
                    user.setNombre(nuevo.getNombre());
                    user.setApellido(nuevo.getApellido());
                    user.setDni(nuevo.getDni());
                    user.setTelefono(nuevo.getTelefono());
                    user.setDomicilio(nuevo.getDomicilio());
                    user.setMail(nuevo.getMail());
                    user.setUsername(nuevo.getUsername());
                    user.setPassword(nuevo.getPassword());
                }
            }
        } catch (NullPointerException e) {

        }
       guardar();
    }

    @Override
    public Cliente buscar(String dni) {
        cargar();
        Cliente aux = null;
        try {
            for (Cliente user : listadoClientes) {
                if (user.getDni().equals(dni)) {
                    aux = user;
                    break;
                }
            }
        } catch (NullPointerException e) {
            return null;
        }
        return aux;
    }

    @Override
    public int buscarUltimoID() {
        this.listadoClientes = listar();
        Cliente buscado = this.listadoClientes.get(this.listadoClientes.size() - 1);
        return buscado.getId();
    }

    public int cantidad() {
        cargar();
        return listadoClientes.size();
    }
//    </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Serializacion">
    @Override
    public void guardar() {
        try {
            mapeo.writerWithDefaultPrettyPrinter().writeValue(archivo, this.listadoClientes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cargar() {
        try {
            CollectionType collectionType = mapeo.getTypeFactory().constructCollectionType(List.class, Cliente.class);
            this.listadoClientes = mapeo.readValue(archivo, collectionType);
        } catch (IOException e) {
            this.listadoClientes = new ArrayList<>();
        }
    }
    //    </editor-fold>

}
