package org.example.gestiones;

import org.example.excepciones.CodigoPaqueteExistente;
import org.example.interfacesDeManejo.ManejoPaquete;
import org.example.models.Paquete;
import org.example.recursos.EntradaSalida;
import org.example.repositorio.PaqueteRepo;

import javax.swing.*;
import java.time.LocalDateTime;

public class GestionAdmin implements ManejoPaquete {

    PaqueteRepo paqueteRepo = new PaqueteRepo();

    @Override
    private boolean modificarPaquete(int id) {
        return false;
    }

    @Override
    public void registroPaquete() {
        Paquete nuevo = new Paquete();
        nuevo.setId((paqueteRepo.buscarUltimoID())+1);

        nuevo.setFechaIngreso(LocalDateTime.now());
        //nuevo.setRemitente(EntradaSalida.entradaString("    NUEVO PAQUETE \nIngrese el remitente"));
        EntradaSalida.SalidaInformacion("Seleccione el tipo de paquete", "TIPO DE PAQUETE");
        nuevo.setTiposPaquete(EntradaSalida.entradaTipoPaquete());
        EntradaSalida.SalidaInformacion("Seleccione la Zona", "Zona");
        nuevo.setZonaEntrega(EntradaSalida.entradaZona());
        nuevo.setDestinatario(JOptionPane.showInputDialog("Ingrese el destinatario"));
        nuevo.setDomicilioEntrega(JOptionPane.showInputDialog("Ingrese el domicilio de entrega"));
        // nuevo.setEstado(EntradaSalida);  CONTINUAR CON VALIDACION DE ENTRADA

        paqueteRepo.agregar(nuevo);

    }

    public String nuevoCogigoPaquete (){
        boolean continuar = false;
        do {
            try {
                String codigo = EntradaSalida.CodigoPaquete();
                validacionCodigoPaquete(codigo);
                return codigo;
            } catch (CodigoPaqueteExistente e){

            }
        } while (!continuar);
        return null;
    }

    @Override
    public void validacionCodigoPaquete(String codigo) throws CodigoPaqueteExistente {
        for (Paquete paquete: paqueteRepo.listar()) {
            if (paquete.getCodigoIdentificacion().equals(codigo)){
                throw new CodigoPaqueteExistente("Codigo Paquete Existente");
            }
            break;
        }
    }
}
