package org.example.repositorio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.example.excepciones.InexistenteException;
import org.example.models.EmpleadoLocal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoLocalRepo implements IRepositorio<EmpleadoLocal> {
    private final File archivo = new File("src\\main\\java\\org\\example\\archivos\\empleadosLocal.json");

    private final ObjectMapper mapper = new ObjectMapper();

    private List<EmpleadoLocal> empleadosLocal;

    /**
     * <h2>Cargar datos del JSON empleadosLocal</h2>
     * Utiliza la libreria Jackson. Lee los datos del archivo empleadosLocal y los guarda en una lista de EmpleadoLocal.
     * En caso de que el archivo este vacio, inicializa la lista.
     *
     * @author Angeles Higa
     */
    @Override
    public void cargar() {
        try{
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class,EmpleadoLocal.class);
            this.empleadosLocal = mapper.readValue(archivo,collectionType);
        }catch(IOException e){
            this.empleadosLocal = new ArrayList<>();
        }
    }

    /**
     * <h2>Guardar datos al JSON empleadosLocal</h2>
     * Utiliza la libreria Jackson. Guarda los datos de la lista de EmpleadoLocal en el archivo empleadosLocal.
     *
     * @author Angeles Higa
     */

    @Override
    public void guardar() {
        try{
           mapper.writerWithDefaultPrettyPrinter().writeValue(archivo,this.empleadosLocal);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    /**
     * <h2>Listar empleadosLocal</h2>
     *
     * @return una lista de EmpleadoLocal del archivo JSON.
     * @author Angeles Higa
     */

    @Override
    public ArrayList<EmpleadoLocal> listar() {
        cargar();
        return (ArrayList<EmpleadoLocal>) this.empleadosLocal;
    }

    /**
     * <h2>Agregar un EmpleadoLocal al JSON empleadosLocal</h2>
     * Agrega un EmpleadoLocal al archivo.
     *
     * @author Angeles Higa
     */
    @Override
    public void agregar(EmpleadoLocal... objeto) {
        cargar();

        this.empleadosLocal.addAll(List.of(objeto));

        guardar();
    }

    /**
     * <h2>Eliminar un EmpleadoLocal del JSON empleadosLocal</h2>
     * Elimina un EmpleadoLocal del archivo.
     *
     * @author Angeles Higa
     */
    @Override
    public void eliminar(int id) {
        cargar();

        for(EmpleadoLocal empleado : this.empleadosLocal){

            if(empleado.getId() == id){
                this.empleadosLocal.remove(empleado);
                break;
            }
        }

        guardar();
    }


    /**
     * <h2>Modificar un EmpleadoLocal del JSON empleadosLocal</h2>
     * Modifica un EmpleadoLocal existente, mediante el uso de getters y setters.
     *
     * @param objeto Recibe el nuevo objeto ya modificado.
     * @author Angeles Higa
     */
    @Override
    public void modificar(EmpleadoLocal objeto) {
        cargar();

        for(EmpleadoLocal empleado : this.empleadosLocal){
            if(empleado.getId() == objeto.getId()){

                empleado.setId(objeto.getId());
                empleado.setNombre(objeto.getNombre());
                empleado.setApellido(objeto.getApellido());
                empleado.setDni(objeto.getDni());
                empleado.setTelefono(objeto.getTelefono());
                empleado.setMail(objeto.getMail());
                empleado.setUsername(objeto.getUsername());
                empleado.setPassword(objeto.getPassword());
                empleado.setLegajo(objeto.getLegajo());
                empleado.setJornada(objeto.getJornada());
                empleado.setEstado(objeto.getEstado());
                empleado.setSupervisor(objeto.getSupervisor());
            }
        }
        guardar();
    }


    /**
     * <h2>Buscar un EmpleadoLocal del JSON empleadosLocal</h2>
     * Busca a un EmpleadoLocal por el DNI.
     *
     * @param dni Recibe el DNI del EmpleadoLocal a buscar.
     * @return EmpleadoLocal buscado
     * @throws InexistenteException en caso de que no exista el DNI ingresado
     */
    @Override
    public EmpleadoLocal buscar(String dni) throws InexistenteException{

        this.empleadosLocal = listar();

        for(EmpleadoLocal empleado: empleadosLocal)
        {
            if(empleado.getDni().equalsIgnoreCase(dni))
            {
                return empleado;
            }
        }

        throw new InexistenteException("DNI inexistente");
    }


    /**
     * <h2>Buscar Ultimo ID de un EmpleadoLocal del JSON empleadosLocal</h2>
     * Busca el id del ultimo EmpleadoLocal registrado.
     *
     * @return ultimoId
     * @throws IndexOutOfBoundsException
     */

    @Override
    public int buscarUltimoID() {

        this.empleadosLocal = listar();

        EmpleadoLocal buscado = new EmpleadoLocal();

        try{
            buscado = this.empleadosLocal.get(this.empleadosLocal.size() - 1);
            return buscado.getId();
        }catch(IndexOutOfBoundsException e){
            return -1;
        }
    }


}
