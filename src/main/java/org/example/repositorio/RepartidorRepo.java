package org.example.repositorio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.example.models.Repartidor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RepartidorRepo implements IRepositorio<Repartidor> {
    private final File archivoRepar = new File("");

    private final ObjectMapper mapperRepar = new ObjectMapper();

    private List<Repartidor> listaRepartidores;

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

    @Override
    public ArrayList<Repartidor> listar() {
        cargar();
        return (ArrayList<Repartidor>) this.listaRepartidores;
    }

    @Override
    public void agregar(Repartidor... objeto) {
        cargar();
        this.listaRepartidores.addAll(List.of(objeto));
        guardar();
    }

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

    @Override
    public void modificar(Repartidor nuevo) {

        cargar();
        for(Repartidor r : this.listaRepartidores)
        {
            if(r.getId() == nuevo.getId())
            {
                r.setNombreYApellido(nuevo.getNombreYApellido());
                // ATRIBUTOS
                break;
            }
        }
        guardar();
    }
}
