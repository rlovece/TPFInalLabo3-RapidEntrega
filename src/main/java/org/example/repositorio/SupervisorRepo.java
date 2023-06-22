package org.example.repositorio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.example.excepciones.InexistenteException;
import org.example.models.EmpleadoLocal;
import org.example.models.Paquete;
import org.example.models.Repartidor;
import org.example.models.Supervisor;
import org.example.recursos.EntradaSalida;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SupervisorRepo implements IRepositorio<Supervisor> {

    private final File archivoSuper = new File("src\\main\\java\\org\\example\\archivos\\supervisores.json");

    private final ObjectMapper mapperSuper = new ObjectMapper();

    private List<Supervisor> listaSupervisores;

    /**
     * <h2>Cargar datos del JSON supervisores</h2>
     * Utiliza la libreria Jackson. Lee los datos del archivo supervisores y los guarda en una lista de Supervisores.
     * En caso de que el archivo este vacio, inicializa la lista.
     *
     * @author Oriana Dafne Lucero
     */
    @Override
    public void cargar() {

        try
        {
            CollectionType collectionType = mapperSuper.getTypeFactory().constructCollectionType(List.class,Supervisor.class);
            this.listaSupervisores= mapperSuper.readValue(archivoSuper,collectionType);
        }catch(IOException e)
        {
            this.listaSupervisores = new ArrayList<>();
        }
    }

    /**
     * <h2>Guardar datos al JSON supervisores</h2>
     * Utiliza la libreria Jackson. Guarda los datos de la lista de clase
     * Supervisor en el archivo supervisores.
     *
     * @author Oriana Dafne Lucero
     */
    @Override
    public void guardar() {
        try
        {
            mapperSuper.writerWithDefaultPrettyPrinter().writeValue(archivoSuper,this.listaSupervisores);
        }catch(IOException e)
        {
            throw new RuntimeException(e);
        }

    }

    /**
     * <h2>Listar Supervisores</h2>
     * Carga y retorna una lista de supervisores desde los supervisores cargados en el JSON supervisores
     * @return una lista de Supervisor del archivo JSON.
     * @author Oriana Dafne Lucero
     */
    @Override
    public ArrayList<Supervisor> listar() {
        cargar();
        return (ArrayList<Supervisor>) this.listaSupervisores;
    }

    /**
     * <h2>Agregar un Supervisores al JSON supervisores</h2>
     * Agrega un supervisores al archivo JSON
     * @author Oriana Dafne Lucero
     */
    @Override
    public void agregar(Supervisor... objeto) {
        cargar();
        this.listaSupervisores.addAll(List.of(objeto));
        guardar();
    }

    /**
     * <h2>Eliminar un Supervisor del JSON supervisores</h2>
     * Elimina un Supervisor del archivo.
     *
     * @author Oriana Dafne Lucero
     */
    @Override
    public void eliminar(int id) {
        cargar();
        for(Supervisor s : this.listaSupervisores)
        {
            if(s.getId() == id)
            {
                this.listaSupervisores.remove(s);
                break;
            }
        }
        guardar();
    }

    /**
     * <h2>Modificar un Supervisor del JSON supervisores</h2>
     * Modifica un Supervisor existente, mediante el uso de getters y setters.
     * @param nuevo Recibe el nuevo objeto Supervisor ya modificado.
     * @author Oriana Dafne Lucero
     */
    @Override
    public void modificar(Supervisor nuevo) {

        cargar();
        for (Supervisor s : this.listaSupervisores) {
            if (s.getId() == nuevo.getId()) {
                s.setNombre(nuevo.getNombre());
                s.setApellido(nuevo.getApellido());
                s.setDni(nuevo.getDni());
                s.setTelefono(nuevo.getTelefono());
                s.setMail(nuevo.getMail());
                s.setUsername(nuevo.getUsername());
                s.setPassword(nuevo.getPassword());
                s.setCantEmpleadosACargo(nuevo.getCantEmpleadosACargo());
            }
        }
        guardar();
    }

    /**
     * <h2>Modificar un Supervisor del JSON supervisores</h2>
     * Busca a un Supervisor por el DNI.
     * @param dni Recibe el DNI del Supervisor a buscar.
     * @return Supervisor buscado
     * @throws InexistenteException en caso de que no exista el DNI ingresado
     */
    @Override
    public Supervisor buscar(String dni) throws InexistenteException{
        this.listaSupervisores = listar();
        for(Supervisor s: this.listaSupervisores) {

            if (s.getDni().equalsIgnoreCase(dni)) {
                return s;
            }
        }
        throw new InexistenteException("Supervisor Inexistente");
    }

    /**
     * <h2>Modificar un Supervisor del JSON supervisores</h2>
     * Busca el id del ultimo Supervisor registrado.
     * @return ultimoId registrado en el JSON supervisores
     */
    @Override
    public int buscarUltimoID() {
        this.listaSupervisores= listar();
        Supervisor buscado = new Supervisor();
        try {
            buscado = this.listaSupervisores.get(this.listaSupervisores.size() -1 );
            return buscado.getId();
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
    }
}

