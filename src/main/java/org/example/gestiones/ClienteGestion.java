package org.example.gestiones;

import org.example.enums.EstadosEmpleado;
import org.example.excepciones.Excepciones;
import org.example.interfacesDeManejo.ManejoCliente;
import org.example.models.Cliente;
import org.example.repositorio.ClientesRepo;

import java.util.ArrayList;
import java.util.Scanner;

public class ClienteGestion {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    ClientesRepo repoClientes = new ClientesRepo();
    Cliente cliente = new Cliente();
    Scanner scan = new Scanner(System.in);
    ArrayList<Cliente> listaClientes = new ArrayList<>();
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Metodos">
    public void registrarCliente() {

        Cliente nuevo = new Cliente();
        nuevo.setId(repoClientes.buscarUltimoID());
        System.out.println("Ingresar Nombre : ");
        nuevo.setNombre(scan.nextLine());
        System.out.println("Ingresar Apellido : ");
        nuevo.setApellido(scan.nextLine());
        String dni;
        do {
            System.out.println("Ingresar Dni : ");
            dni = scan.nextLine();
            try {
                if (existe(dni)) {
                    throw new Excepciones("El dni esta repetido");
                }
            } catch (Excepciones e) {
                System.out.println(e.getMessage());
            }
        } while (existe(dni));
        nuevo.setDni(dni);
        String telefono;
        do {
            System.out.println("Ingresar un nuevo Telefono : ");
            telefono = scan.nextLine();
            try {
                if (existe(telefono)) {
                    throw new Excepciones("El telefono esta repetido");
                }
            } catch (Excepciones e) {
                System.out.println(e.getMessage());
            }
        } while (existe(telefono));
        nuevo.setTelefono(telefono);
        String username;
        do {
            System.out.println("Ingresar un nuevo Username : ");
            username = scan.nextLine();
            try {
                if (existe(username)) {
                    throw new Excepciones("El username esta repetido");
                }
            } catch (Excepciones e) {
                System.out.println(e.getMessage());
            }
        } while (existe(username));
        nuevo.setUsername(username);
        System.out.println("Ingresar un nuevo Password : ");
        nuevo.setPassword(scan.nextLine());
        System.out.println("Ingresar un nuevo Domicilio : ");
        nuevo.setDomicilio(scan.nextLine());
        nuevo.setId(nuevo.getId());
        repoClientes.agregar(nuevo);

    }
    public void modificarCliente() {
        Cliente nuevo = new Cliente();
        nuevo.setId(nuevo.getId());
        System.out.println("Ingrese el Id del cliente a modificar");
        int dato = scan.nextInt();
        System.out.println("Ingresar nuevo Nombre : ");
        nuevo.setNombre(scan.nextLine());
        System.out.println("Ingresar nuevo Apellido : ");
        nuevo.setApellido(scan.nextLine());
        String dni;
        do {
            System.out.println("Ingresar Dni : ");
            dni = scan.nextLine();
            try {
                if (existe(dni)) {
                    throw new Excepciones("El dni esta repetido");
                }
            } catch (Excepciones e) {
                System.out.println(e.getMessage());
            }
        } while (existe(dni));
        nuevo.setDni(dni);
        String telefono;
        do {
            System.out.println("Ingresar Telefono : ");
            telefono = scan.nextLine();
            try {
                if (existe(telefono)) {
                    throw new Excepciones("El telefono esta repetido");
                }
            } catch (Excepciones e) {
                System.out.println(e.getMessage());
            }
        } while (existe(telefono));
        nuevo.setTelefono(telefono);
        String username;
        do {
            System.out.println("Ingresar Username : ");
            username = scan.nextLine();
            try {
                if (existe(username)) {
                    throw new Excepciones("El username esta repetido");
                }
            } catch (Excepciones e) {
                System.out.println(e.getMessage());
            }
        } while (existe(username));
        nuevo.setUsername(username);
        System.out.println("Ingresar Password : ");
        nuevo.setPassword(scan.nextLine());
        System.out.println("Ingresar Domicilio : ");
        nuevo.setDomicilio(scan.nextLine());
        nuevo.setId(nuevo.getId());
        repoClientes.modificar(nuevo);
    }
    public void eliminarCliente() {
        System.out.println("Ingrese el dni del cliente a eliminar");
        String dni = scan.nextLine();
        Cliente buscado = repoClientes.buscar(dni);
        buscado.setEstado(EstadosEmpleado.BAJA);
    }
    public void descansoCliente() {
        System.out.println("Ingrese el dni del cliente para darle vacaciones");
        String dni = scan.nextLine();
        Cliente buscado = repoClientes.buscar(dni);
        buscado.setEstado(EstadosEmpleado.VACACIONES);
    }
    public void mostrarClientes() {
        listaClientes = repoClientes.listar();
        System.out.println(listaClientes);
    }
    public boolean existe(String dato) {

        repoClientes.cargar();
        for (Cliente cliente : listaClientes) {
            if (cliente.getDni().equals(dato)){
                return true;
            }
        }
        return false;
    }

    //</editor-fold>
}
