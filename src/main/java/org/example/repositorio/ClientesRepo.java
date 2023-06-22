// <editor-fold defaultstate="collapsed" desc="Paquetes y Librerias">
package org.example.repositorio;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.example.excepciones.InexistenteException;
import org.example.models.Cliente;
import org.example.models.Paquete;
import org.example.models.Supervisor;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//</editor-fold>
/**
 * @author Cavallo, Pablo David
 */
public class ClientesRepo implements IRepositorio<Cliente> {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private final String ruta = "src\\main\\java\\org\\example\\archivos\\clientes.json";
    private final ObjectMapper mapeo = new ObjectMapper();
    private final File archivo = new File(ruta);
    private ArrayList<Cliente> listadoClientes;

//    </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Alta / Baja / Modificacion / Listado">
    /**
     * <h2>Agregar</h2>
     * <p>El método agregar carga una lista del archivo json,
     * agrega los clientes proporcionados como parámetros a
     * esa estructura de datos y luego vuelve a guarda los cambios
     * realizados.
     * @param objeto (Dato tipo Cliente)
     * @author Cavallo, Pablo David
     */
    @Override
    public void agregar(Cliente... objeto) {
        cargar();
        listadoClientes.addAll(List.of(objeto));
        guardar();
    }
    /**
     * <h2>Listar</h2>
     * El método listar carga una lista del archivo json,
     * y devuelve la lista completa de clientes como resultado.
     * @return Un Arraylist
     * @author Cavallo, Pablo David
     */
    @Override
    public ArrayList<Cliente> listar() {
        cargar();
        return this.listadoClientes;
    }
    /**
     * <h2>Eliminar</h2>
     * <p>El método eliminar carga una lista del archivo json,
     * busca el primer cliente con un ID que coincida
     * con el valor proporcionado, lo elimina de la
     * lista y guarda los cambios.
     * @param dato (Dato tipo int)
     * @author Cavallo, Pablo David
     */
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
    /**
     * <h2>Modificar</h2>
     * <p>El método modificar busca un cliente existente cargado
     * de una lista del archivo json y reemplaza sus atributos
     * con los valores del cliente nuevo pasado como argumento.
     * Luego, guarda los cambios realizados .
     * @param nuevo (Dato tipo Cliente)
     * @author Cavallo, Pablo David
     */
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
                    user.setEstadoCliente(nuevo.isEstadoCliente());
                }
            }
        } catch (NullPointerException e) {
        }
        guardar();
    }
    /**
     * <h2>Buscar</h2>
     * <p>El método buscar busca un cliente en una lista
     * de clientes utilizando su DNI. Si encuentra el cliente,
     * lo devuelve; de lo contrario, lanza una excepción para
     * indicar que el cliente buscado no existe.
     * @param dni (Dato tipo String)
     * @throws InexistenteException extends Exception
     * @return Retorna un dato tipo Cliente
     * @author Cavallo, Pablo David
     */
    @Override
    public Cliente buscar(String dni) throws InexistenteException {
        this.listadoClientes= listar();
        for(Cliente s: listadoClientes)
        {
            if(s.getDni().equals(dni))
            {
                return s;
            }
        }
        throw new InexistenteException("Cliente Inexistente");
    }
    /**
     * <h2>Busca el Ultimo ID</h2>
     * <p>El método busca el último ID de cliente en la lista de clientes.
     * Si la lista está vacía o si hay algún error al acceder al último
     * elemento, devuelve -1. Si se encuentra un ID válido, lo devuelve.
     * @return Retorna un int
     * @author Cavallo, Pablo David
     */
    @Override
    public int buscarUltimoID() {
        this.listadoClientes= listar();
        Cliente buscado = new Cliente();
        try {
            buscado = this.listadoClientes.get(this.listadoClientes.size() -1 );
            return buscado.getId();
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
    }
    /**
     * <h2>Cantidad</h2>
     * <p>El método cantidad carga los datos  del archivo json,
     * accede a la lista listadoClientes y retorna la cantidad
     * de elementos en esa lista utilizando size().
     * Esto permite obtener la cantidad de clientes almacenados en la lista.
     * @return Retorna un int
     * @author Cavallo, Pablo David
     */
    public int cantidad() {
        cargar();
        return listadoClientes.size();
    }
//    </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Serializacion">
    /**
     * <h2>Guardar</h2>
     * El método Guardar utiliza una biblioteca o clase de mapeo
     * de objetos a JSON para escribir una lista de clientes en un
     * archivo en formato JSON legible. Si se produce algún error
     * durante el proceso de escritura, se lanza una excepción de
     * tipo RuntimeException.
     * @throws IOException extends Exception
     * @author Cavallo, Pablo David
     */
    @Override
    public void guardar() {
        try {
            mapeo.writerWithDefaultPrettyPrinter().writeValue(archivo, this.listadoClientes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * <h2>Cargar</h2>
     * <p>El método cargar utiliza ObjectMapper de Jackson
     * para leer y mapear un archivo JSON a una lista de
     * objetos Cliente. Si la lectura o el mapeo fallan,
     * se crea una lista vacía como valor predeterminado.
     * @throws IOException extends Exception
     * @author Cavallo, Pablo David
     */
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
