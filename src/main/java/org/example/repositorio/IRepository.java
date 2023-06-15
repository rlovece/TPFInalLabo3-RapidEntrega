package org.example.Repositorio;

import org.example.Exceptiones.InexistenteException;

import java.util.ArrayList;

public interface IRepositorio <T> {

    void cargar();
    void guardar();
    ArrayList<T> listar();
    void agregar(T... objeto);
    void eliminar(String id);
    void modificar(String id);
    T buscar (String id) throws InexistenteException;
}
