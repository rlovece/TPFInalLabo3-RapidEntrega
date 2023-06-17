package org.example.gestiones;

import org.example.excepciones.Excepciones;
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
    public void agregarCliente() {

        Cliente nuevo = new Cliente();
        System.out.println("Ingresar Nombre y Apellido : ");
        nuevo.setNombreYApellido(scan.nextLine());
        String dni;
        do {
            System.out.println("Ingresar Dni : ");
            dni = scan.nextLine();
            try {
                if (repoClientes.existe(dni)) {
                    throw new Excepciones("El dni esta repetido");
                }
            } catch (Excepciones e) {
                System.out.println(e.getMessage());
            }
        } while (repoClientes.existe(dni));
        nuevo.setDni(dni);
        String telefono;
        do {
            System.out.println("Ingresar un nuevo Telefono : ");
            telefono = scan.nextLine();
            try {
                if (repoClientes.existe(telefono)) {
                    throw new Excepciones("El telefono esta repetido");
                }
            } catch (Excepciones e) {
                System.out.println(e.getMessage());
            }
        } while (repoClientes.existe(telefono));
        nuevo.setTelefono(telefono);
        String username;
        do {
            System.out.println("Ingresar un nuevo Username : ");
            username = scan.nextLine();
            try {
                if (repoClientes.existe(username)) {
                    throw new Excepciones("El username esta repetido");
                }
            } catch (Excepciones e) {
                System.out.println(e.getMessage());
            }
        } while (repoClientes.existe(username));
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
        nuevo.setId(repoClientes.cantidad()+1);
        System.out.println("Ingrese el Id del cliente a modificar");
        int dato = scan.nextInt();
        System.out.println("Ingresar Nombre y Apellido : ");
        nuevo.setNombreYApellido(scan.nextLine());
        String dni;
        do {
            System.out.println("Ingresar Dni : ");
            dni = scan.nextLine();
            try {
                if (repoClientes.existe(dni)) {
                    throw new Excepciones("El dni esta repetido");
                }
            } catch (Excepciones e) {
                System.out.println(e.getMessage());
            }
        } while (repoClientes.existe(dni));
        nuevo.setDni(dni);
        String telefono;
        do {
            System.out.println("Ingresar Telefono : ");
            telefono = scan.nextLine();
            try {
                if (repoClientes.existe(telefono)) {
                    throw new Excepciones("El telefono esta repetido");
                }
            } catch (Excepciones e) {
                System.out.println(e.getMessage());
            }
        } while (repoClientes.existe(telefono));
        nuevo.setTelefono(telefono);
        String username;
        do {
            System.out.println("Ingresar Username : ");
            username = scan.nextLine();
            try {
                if (repoClientes.existe(username)) {
                    throw new Excepciones("El username esta repetido");
                }
            } catch (Excepciones e) {
                System.out.println(e.getMessage());
            }
        } while (repoClientes.existe(username));
        nuevo.setUsername(username);
        System.out.println("Ingresar Password : ");
        nuevo.setPassword(scan.nextLine());
        System.out.println("Ingresar Domicilio : ");
        nuevo.setDomicilio(scan.nextLine());
        nuevo.setId(nuevo.getId());
        repoClientes.modificacion(dato, nuevo);
    }
    public void eliminarCliente() {
        System.out.println("Ingrese el Id del cliente a eliminar");
        int dato = scan.nextInt();
        repoClientes.eliminar(dato);
    }
    public void mostrarClientes() {
        listaClientes = repoClientes.listar();
        System.out.println(listaClientes);
    }

    public void menuCliente() {
        int opcion = 0;
        do {
            Scanner scan = new Scanner(System.in);
            System.out.println("\tMenu Clientes");
            System.out.println("\t1 - Login");
            System.out.println("\t2 - Alta");
            System.out.println("\t0 - Salir\n");
            System.out.println("Ingrese una opción : ");
            opcion = scan.nextInt();
            switch (opcion) {
                case 1:
                    break;
                case 2:
                    break;
                case 0:
                    System.out.println("Gracias por la visitan\n\nHasta pronto\n");
                    break;
                default:
                    System.out.println("Opción invalida");
                    break;
            }
        } while (opcion != 0);




    }

    //</editor-fold>
}
