package org.example.repositorio;

import org.example.Exceptiones.InexistenteException;

import java.util.ArrayList;

public interface IRepositorio <T> {

    void cargar();
    void guardar();
    ArrayList<T> listar();
    void agregar(T... objeto);
    void eliminar(int id);
    void modificar(T objeto);

}
