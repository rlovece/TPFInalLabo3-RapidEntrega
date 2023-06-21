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

    @Override
    public void cargar() {
        try{
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class,EmpleadoLocal.class);
            this.empleadosLocal = mapper.readValue(archivo,collectionType);
        }catch(IOException e){
            this.empleadosLocal = new ArrayList<>();
        }
    }

    @Override
    public void guardar() {
        try{
           mapper.writerWithDefaultPrettyPrinter().writeValue(archivo,this.empleadosLocal);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public ArrayList<EmpleadoLocal> listar() {
        cargar();
        return (ArrayList<EmpleadoLocal>) this.empleadosLocal;
    }

    @Override
    public void agregar(EmpleadoLocal... objeto) {
        cargar();

        this.empleadosLocal.addAll(List.of(objeto));

        guardar();
    }

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
