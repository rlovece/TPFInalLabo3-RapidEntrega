package org.example.repositorio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.example.models.Supervisor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SupervisorRepo implements IRepositorio<Supervisor> {

    private final File archivoSuper = new File("");

    private final ObjectMapper mapperSuper = new ObjectMapper();

    private List<Supervisor> listaSupervisores;

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

    @Override
    public ArrayList<Supervisor> listar() {
        cargar();
        return (ArrayList<Supervisor>) this.listaSupervisores;
    }

    @Override
    public void agregar(Supervisor... objeto) {
        cargar();
        this.listaSupervisores.addAll(List.of(objeto));
        guardar();
    }

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

    @Override
    public void modificar(Supervisor nuevo) {

        cargar();
        for(Supervisor s : this.listaSupervisores)
        {
            if(s.getId() == nuevo.getId())
            {
                // ATRIBUTOS
            }
        }
        guardar();
    }
}

