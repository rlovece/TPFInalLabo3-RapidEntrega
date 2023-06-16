package org.example.repositorio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.example.Exceptiones.InexistenteException;
import org.example.models.Local;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalRepo implements IRepositorio<Local>{
    private final File archivo = new File("src/main/java/org/axample/Archivos/locales.json");
    private final ObjectMapper mapper = new ObjectMapper();

    private List<Local> locales;

    @Override
    public void cargar() {
        try{
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class,Local.class);
            this.locales = mapper.readValue(archivo,collectionType);
        }catch (IOException e){
            this.locales = new ArrayList<>();
        }
    }

    @Override
    public void guardar() {
        try{
            mapper.writerWithDefaultPrettyPrinter(archivo,this.locales);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Local> listar() {
        cargar();
        return (ArrayList<Local>) this.locales;
    }

    @Override
    public void agregar(Local... objeto) {
        cargar();

        this.locales.addAll(List.of(objeto));
    }

    @Override
    public void eliminar(String id) {
        cargar();
        for(Local local : this.locales){
            if(local.getId().equalsIgnoreCase(id)){
                this.locales.remove(local);

                break;
            }
        }
        guardar();
    }

    @Override
    public void modificar(String id) {

    }

    @Override
    public Local buscar(String id) throws InexistenteException {
        return null;
    }
}
