package org.example.repositorio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.example.excepciones.InexistenteException;
import org.example.models.Paquete;
import org.example.models.Repartidor;
import org.example.models.Supervisor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RepartidorRepo implements IRepositorio<Repartidor> {
    private final File archivoRepar = new File("src\\main\\java\\org\\example\\archivos\\repartidores.json");

    private final ObjectMapper mapperRepar = new ObjectMapper();

    private List<Repartidor> listaRepartidores;

    /**
     * <h2>Cargar datos del JSON repartidores</h2>
     * Utiliza la libreria Jackson. Lee los datos del archivo repartidores y los guarda en una lista de Repartidores.
     * En caso de que el archivo este vacio, inicializa la lista.
     *
     * @author Oriana Dafne Lucero
     */
    @Override
    public void cargar() {

        try
        {
            CollectionType collectionType = mapperRepar.getTypeFactory().constructCollectionType(List.class,Repartidor.class);
            this.listaRepartidores= mapperRepar.readValue(archivoRepar,collectionType);
        }catch(IOException e)
        {
            this.listaRepartidores = new ArrayList<>();
        }
    }

    /**
     * <h2>Guardar datos al JSON reparitodres</h2>
     * Utiliza la libreria Jackson. Guarda los datos de la lista de clase
     * Repartidor en el archivo supervisores.
     *
     * @author Oriana Dafne Lucero
     */
    @Override
    public void guardar() {
        try
        {
            mapperRepar.writerWithDefaultPrettyPrinter().writeValue(archivoRepar,this.listaRepartidores);
        }catch(IOException e)
        {
            throw new RuntimeException(e);
        }

    }

    /**
     * <h2>Listar Repartidores</h2>
     * Carga y retorna una lista de repartidores desde los repartidores cargados en el JSON repartidores
     * @return una lista de Repartidor del archivo JSON.
     * @author Oriana Dafne Lucero
     */
    @Override
    public ArrayList<Repartidor> listar() {
        cargar();
        return (ArrayList<Repartidor>) this.listaRepartidores;
    }

    /**
     * <h2>Agregar un Repartidores al JSON repartidores</h2>
     * Agrega un repartidores al archivo JSON
     * @author Oriana Dafne Lucero
     */
    @Override
    public void agregar(Repartidor... objeto) {
        cargar();
        this.listaRepartidores.addAll(List.of(objeto));
        guardar();
    }

    /**
     * <h2>Eliminar un Repartidor del JSON repartidores</h2>
     * Elimina un Repartidor del archivo.
     *
     * @author Oriana Dafne Lucero
     */
    @Override
    public void eliminar(int id) {
        cargar();
        for(Repartidor r : this.listaRepartidores)
        {
            if(r.getId() == id)
            {
                this.listaRepartidores.remove(r);
                break;
            }
        }
        guardar();
    }

    /**
     * <h2>Modificar un Repartidor del JSON repartidores</h2>
     * Modifica un Repartidor existente, mediante el uso de getters y setters.
     * @param nuevo Recibe el nuevo objeto Repartidor ya modificado.
     * @author Oriana Dafne Lucero
     */
    @Override
    public void modificar(Repartidor nuevo) {

        cargar();
        for(Repartidor s : this.listaRepartidores)
        {
            if(s.getId() == nuevo.getId())
            {

                s.setNombre(nuevo.getNombre());
                s.setApellido(nuevo.getApellido());
                s.setDni(nuevo.getDni());
                s.setTelefono(nuevo.getTelefono());
                s.setMail(nuevo.getMail());
                s.setUsername(nuevo.getUsername());
                s.setPassword(nuevo.getPassword());
                s.setSupervisor(nuevo.getSupervisor());
                s.setZona(nuevo.getZona());
                s.setTiposPaquetes(nuevo.getTiposPaquetes());

                break;
            }
        }
        guardar();
    }

    /**
     * <h2>Modificar un Repartidor del JSON repartidores</h2>
     * Busca a un Repartidor por el DNI.
     * @param dni Recibe el DNI del Repartidor a buscar.
     * @return Repartidor buscado
     * @throws InexistenteException en caso de que no exista el DNI ingresado
     */
    @Override
    public Repartidor buscar(String dni) throws InexistenteException{
        this.listaRepartidores= listar();
        for(Repartidor s: listaRepartidores)
        {
            if(s.getDni().equalsIgnoreCase(dni))
            {
                return s;
            }
        }
        throw new InexistenteException("Repartidor inexistente");
    }

    /**
     * <h2>Modificar un Repartidor del JSON repartidores</h2>
     * Busca el id del ultimo Repartidor registrado.
     * @return ultimoId registrado en el JSON repartidores
     */
    @Override
    public int buscarUltimoID() {
        this.listaRepartidores= listar();
        Repartidor buscado = new Repartidor();
        try {
            buscado = this.listaRepartidores.get(this.listaRepartidores.size() -1 );
            return buscado.getId();
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
    }
}
