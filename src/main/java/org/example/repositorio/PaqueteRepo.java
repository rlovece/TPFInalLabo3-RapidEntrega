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

    @Override
    public ArrayList<Paquete> listar() {
        cargar();
        return (ArrayList<Paquete>) this.listadoPaquetes;
    }

    @Override
    public void agregar(Paquete... objeto) {
        cargar();
        this.listadoPaquetes.addAll(List.of(objeto));
        guardar();
    }

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

    @Override
    public void modificar(Paquete nuevo) {

        cargar();
        for(Paquete s : this.listadoPaquetes)
        {
            if(s.getId() == nuevo.getId())
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

