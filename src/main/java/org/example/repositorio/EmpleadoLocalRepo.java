package org.example.repositorio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.example.Exceptiones.InexistenteException;
import org.example.models.EmpleadoLocal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoLocalRepo implements IRepositorio<EmpleadoLocal> {
    private final File archivo = new File("src/main/java/org/axample/Archivos/empleadosLocal.json");

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
        return this.empleadosLocal;
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

            //if(){break;}
        }

        guardar();
    }

    @Override
    public void modificar(EmpleadoLocal objeto) {

    }


}
