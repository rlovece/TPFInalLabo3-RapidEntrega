// <editor-fold defaultstate="collapsed" desc="Paquetes y Librerias">
package org.example.repositorio;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.Cliente;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
//</editor-fold>

public class RepoClientes implements IRepositorio<Cliente>, Serializable {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    String ruta = "src/main/java/org/example/archivos/clientes.json";
    ObjectMapper mapeo = new ObjectMapper();
    private final File archivo = new File(ruta);
    ArrayList<Cliente> listadoClientes = new ArrayList<>();

//    </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="Alta / Baja / Modificacion / Listado">
    @Override
    public void agregar(Cliente objeto) {
        cargar();

        listadoClientes.add(objeto);
        guardar();
    }

    @Override
    public ArrayList<Cliente> listar() {
        cargar();
        return this.listadoClientes;
    }

    @Override
    public void agregar(Cliente... objeto) {

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
    public void modificar(Cliente objeto) {

    }

    @Override
    public boolean existe(String dato) {
        cargar();
        for (Cliente cliente : listadoClientes) {
            if (cliente.getDni().equals(dato)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Cliente buscar(int dato) {
        cargar();
        Cliente aux = null;
        try {
            for (Cliente user : listadoClientes) {
                if (user.getId()==dato) {
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
    public Cliente modificacion(int id, Cliente nuevo) {
        cargar();
        try {
            for (Cliente user : listadoClientes) {
                if (user.getId() == id) {
                    user.setNombreYApellido(nuevo.getNombreYApellido());
                    user.setDni(nuevo.getDni());
                    user.setTelefono(nuevo.getTelefono());
                    user.setDomicilio(nuevo.getDomicilio());
                    user.setUsername(nuevo.getUsername());
                    user.setPassword(nuevo.getPassword());
                }
            }
        } catch (NullPointerException e) {
            return null;
        }
        guardar();
        return nuevo;
    }
    public int cantidad() {
        cargar();
        return listadoClientes.size();
    }

//    </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Serializacion & Deserealizacion">
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
            listadoClientes = mapeo.readValue(archivo, new TypeReference<ArrayList<Cliente>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //    </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Validacion">
    public boolean validacion_Login(String username, String password) {
        cargar(
        );
        Cliente aux = null;
        try {
            for (Cliente user : listadoClientes) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    return true;
                }
            }
        } catch (Exception e) {

        }
        return false;
    }

    public boolean validacion_Dni(String dni) {
        cargar(
        );
        Cliente aux = null;
        for (Cliente user : listadoClientes) {
            if (!user.getDni().equals(dni)) {
                return true;
            }
        }
        return false;
    }

//    </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="To String">
    @Override
    public String toString() {
        return String.format("%s", listadoClientes).replace("[", "").replace("]", "");
    }
//    </editor-fold>

}
