package org.example.repositorio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.example.excepciones.CodigoPaqueteExistente;
import org.example.excepciones.InexistenteException;
import org.example.models.Paquete;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaqueteRepo implements IRepositorio<Paquete>{
    private final File archivoPaquete = new File("src\\main\\java\\org\\example\\archivos\\paquetes.json");

    private final ObjectMapper mapperPaquete = new ObjectMapper();



    private List<Paquete> listadoPaquetes;

    /**
     * <h2>Cargar datos del JSON paquetes</h2>
     * Utiliza la libreria Jackson. Lee los datos del archivo paquetes y los guarda en una lista de Paquete.
     * En caso de que el archivo este vacio, inicializa la lista.
     *
     * @author Dafne Lucero
     */

    @Override
    public void cargar() {

        try
        {

            CollectionType collectionType = mapperPaquete.getTypeFactory().constructCollectionType(List.class, Paquete.class);
            this.listadoPaquetes= mapperPaquete.readValue(archivoPaquete,collectionType);
        }catch(IOException e)
        {
            this.listadoPaquetes = new ArrayList<>();
        }
    }

    /**
     * <h2>Guardar datos al JSON paquetes</h2>
     * Utiliza la libreria Jackson. Guarda los datos de la lista de Paquete en el archivo paquetes.
     *
     * @author Dafne Lucero
     */

    @Override
    public void guardar() {
        try
        {
            mapperPaquete.writerWithDefaultPrettyPrinter().writeValue(archivoPaquete,this.listadoPaquetes);
        }catch(IOException e)
        {
            throw new RuntimeException(e);
        }

    }

    /**
     * <h2>Listar paquetes</h2>
     *
     * @return una lista de Paquete del archivo JSON.
     * @author Dafne Lucero
     */

    @Override
    public ArrayList<Paquete> listar() {
        cargar();
        return (ArrayList<Paquete>) this.listadoPaquetes;
    }

    /**
     * <h2>Agregar un Paquete al JSON paquetes</h2>
     * Agrega un Paquete al archivo correspondiente.
     *
     * @author Dafne Lucero
     */

    @Override
    public void agregar(Paquete... objeto) {
        cargar();
        this.listadoPaquetes.addAll(List.of(objeto));
        guardar();
    }

    /**
     * <h2>Eliminar un Paquete del JSON paquetes</h2>
     * Elimina un Paquete del archivo.
     *
     * @author Dafne Lucero
     */
    @Override
    public void eliminar(int id) {
        cargar();
        for(Paquete s : this.listadoPaquetes)
        {
            if(s.getId() == id)
            {
                this.listadoPaquetes.remove(s);
                break;
            }
        }
        guardar();
    }

    /**
     * <h2>Modificar un Paquete del JSON paquetes</h2>
     * Modifica un Paquete existente, mediante el uso de getters y setters.
     *
     * @param nuevo Recibe el nuevo objeto ya modificado.
     * @author Dafne Lucero
     */
    @Override
    public void modificar(Paquete nuevo) {

        cargar();
        for(Paquete s : this.listadoPaquetes)
        {
            if(s.getCodigoIdentificacion().equals(nuevo.getCodigoIdentificacion()))
            {
                s.setRemitente(nuevo.getRemitente());
                s.setTiposPaquete(nuevo.getTiposPaquete());
                s.setZonaEntrega(nuevo.getZonaEntrega());
                s.setDestinatario(nuevo.getDestinatario());
                s.setDomicilioEntrega(nuevo.getDomicilioEntrega());
                s.setEstado(nuevo.getEstado());
                s.setRepatidorAsignado(nuevo.getRepatidorAsignado());
            }
        }
        guardar();
    }

    /**
     * <h2>Buscar un Paquete del JSON paquetes</h2>
     * Busca a un Paquete por el codigo de identificacion.
     *
     * @param codigo Recibe el codigo de identificacion del Paquete a buscar
     * @return Paquete buscado
     * @throws InexistenteException en caso de que no exista el codigo ingresado
     */

    @Override
    public Paquete buscar(String codigo) throws InexistenteException {
        this.listadoPaquetes= listar();
        for(Paquete s: listadoPaquetes)
        {
            if(s.getCodigoIdentificacion().equals(codigo))
            {
                return s;
            }
        }
        throw new InexistenteException("Codigo inexistente");
    }

    /**
     * <h2>Buscar Ultimo ID de un Paquete del JSON paquetes</h2>
     * Busca el id del ultimo Paquete registrado.
     *
     * @return ultimoId
     * @throws IndexOutOfBoundsException
     */

    @Override
    public int buscarUltimoID() {
        this.listadoPaquetes= listar();
        Paquete buscado = new Paquete();
        try {
            buscado = this.listadoPaquetes.get(this.listadoPaquetes.size() -1 );
            return buscado.getId();
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
    }
}

