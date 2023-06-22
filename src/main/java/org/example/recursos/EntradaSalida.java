package org.example.recursos;
import org.example.enums.EstadosEmpleado;
import org.example.enums.EstadosPaquete;
import org.example.enums.TiposPaquete;
import org.example.enums.Zonas;
import org.example.excepciones.*;

import javax.swing.*;
import java.util.EnumSet;
import java.util.UUID;

import static javax.swing.JOptionPane.*;

public class EntradaSalida {

    ///region Entradas
    /**
     * <h2> Entrada de un número entero</h2>
     * Metodo para ingresar un número entero por teclado.
     * La entrada se obtiene con JOptionPane como string y se parsea a Integer dentro de un try-catch que muestra error y retorna al bucle do
     * en caso de capturase la excepción, e ingresa nuevamente al bucle do.
     *
     * @see JOptionPane
     * @return numero entero ingresado
     * @author Ruth Lovece
     */
    public static int entradaInt (String msj){

        boolean continuar = false;
        int nro = 0;

        while (!continuar) {
            try {
                String entrada = showInputDialog(msj);
                nro = Integer.parseInt(entrada);
                continuar = true;
            } catch (NumberFormatException e) {
                String error = "Introduzca un nro entero";
                EntradaSalida.SalidaError(error, "Error");
            }
        }
        return  nro;
    }

    /**
     * <h2> Entrada de un número flotante</h2>
     * Metodo para ingresar un número flotante por teclado.
     * La entrada se obtiene con JOptionPane como string y se parsea a Float dentro de un try-catch que muestra error y retorna al bucle do
     * en caso de capturase la excepción, e ingresa nuevamente al bucle do.
     *
     * @see JOptionPane
     * @return numero entero ingresado
     * @author Ruth Lovece
     */
    public static float entradafloat (String mensaje){
        boolean continuar = false;
        float nro = 0;
        do {
            try {
                String entrada = showInputDialog(mensaje);
                nro= Float.parseFloat(entrada);
                continuar = true;
            }catch (NumberFormatException e){
                String error = "Introduzca un nro";
                EntradaSalida.SalidaError(error, "Error");
            }
        } while (continuar);
        return  nro;
    }

    /**
     * <h2> Entrada de un Tipo de Paquete</h2>
     * Metodo para ingresar una instancia de TiposPaquete. El método permite selecionar la tipo detro de la lista de opciones,
     * eliminado posibles errores y siendo amigable con el usuario.
     * Para esto utiliza una arreglo de TiposPaquetes y luego se muestra el atributo descripción utilizando
     * JComboBox y JOptionPane
     *
     * @see org.example.enums.TiposPaquete
     * @see JComboBox
     * @see JOptionPane
     * @return tipo de paquete seleccionado
     * @author Ruth Lovece
     */
    public static TiposPaquete entradaTipoPaquete (){
        TiposPaquete[] estados = TiposPaquete.values();

        JComboBox<TiposPaquete> comboBox = new JComboBox<>(estados);

        int seleccion = JOptionPane.showOptionDialog(
                null,
                comboBox,
                "Selecione el tipo de paquete",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                estados[0]);

        TiposPaquete entrada = estados[0];;
        if (seleccion != JOptionPane.CLOSED_OPTION) {
            entrada = (TiposPaquete) comboBox.getSelectedItem();
        }
        return entrada;
    }

    /**
     * <h2> Entrada de una Zona</h2>
     * Metodo para ingresar una instancia de Zonas. El método permite sellecionar la zona deseada eliminado
     * posibles errores y siendo amigable con el usuario.
     * Para esto utiliza una arreglo de Zonas y luego se muestra el atributo descripción utilizando
     * JComboBox y JOptionPane
     *
     * @see Zonas
     * @see JComboBox
     * @see JOptionPane
     * @return Zona seleccionada
     * @author Ruth Lovece
     */
    public static Zonas entradaZona(){
        Zonas[] zonas = Zonas.values();

        JComboBox<Zonas> comboBox = new JComboBox<>(zonas);

        int seleccion = JOptionPane.showOptionDialog(
                null,
                comboBox,
                "Seleccione una zona",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                zonas[0]);

        Zonas zonaEntrada = zonas[0];;
        if (seleccion != JOptionPane.CLOSED_OPTION) {
             zonaEntrada = (Zonas) comboBox.getSelectedItem();
        }
        return zonaEntrada;
    }

    public static EstadosPaquete entradaEstadosPaquete (){
        EstadosPaquete[] estadosPaquetes = EstadosPaquete.values();

        JComboBox<EstadosPaquete> comboBox = new JComboBox<>(estadosPaquetes);

        int seleccion = JOptionPane.showOptionDialog(
                null,
                comboBox,
                "Seleccione un estado de paquete",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                estadosPaquetes[0]);

        EstadosPaquete estado = estadosPaquetes[0];;
        if (seleccion != JOptionPane.CLOSED_OPTION) {
            estado = (EstadosPaquete) comboBox.getSelectedItem();
        }
        return estado;
    }

    /**
     * <h1> Entrada de un String</h1>
     * Metodo para ingresar  un String utilizando JOptionPane
     *
     * @see JOptionPane
     * @return String ingresado por teclado
     * @author Ruth Lovece
     */
    public static String entradaString (String msj){
        return showInputDialog(msj);
    }

    /**
     * <h1> Entrada de un email</h1>
     * Metodo para solicitar el ingreso de correo electronico por teclado. Se utiliza JOptionPane para la
     * solicitud.
     * <p>
     * Se invoca método {@link org.example.recursos.EntradaSalida#validarEmail(String)} dentro de un try-catch
     * para asegurase que el correo ingresado contenga @ y .com.
     * En caso de ingresar al catch, se muetra el error al usuario y se ingresa nuevamente al bucle do.
     *
     * @see JOptionPane
     * @return email ingresado por teclado
     * @author Ruth Lovece
     */
    public static String entradaMail (){
        boolean continuar = false;
        do {
            try {
                String entrada = showInputDialog("\n Ingrese correo electrónico \n\n");
                validarEmail(entrada);
                return entrada;
            } catch (EmailIncorrecto e){
                String error = "Correo inválido, debe contener @ y .com \n\n";
                EntradaSalida.SalidaError(error, "Error");
            }
        } while (!continuar);
        return  null;
    }

    /**
     * <h1> Entrada de un DNI</h1>
     * Metodo para solicitar el ingreso de DNI por teclado. Se utiliza JOptionPane para la
     * solicitud.
     * <p>
     * Se invoca mètodo {@link org.example.recursos.EntradaSalida#validarDNI(String)} dentro de un try-catch
     * para asegurase que el DNI contiene 8 dìgitos numericos
     * En caso de ingresar al catch, se muetra el error al usuario y se ingresa nuevamente al bucle do.
     *
     * @see JOptionPane
     * @return DNI ingresado por teclado
     * @author Ruth Lovece
     */
    public static String entradaDNI (){
        boolean continuar = false;
        do {
            try {
                String entrada = showInputDialog("\n Ingrese DNI \n\n");
                validarDNI(entrada);
                return entrada;
            } catch (DNIIncorrecto e){
                String error = "El DNI debe contener 8 digitos numericos. \nSi Su DNI tiene 7 digitos, agregue 0 al comienzo. \n\n";
                EntradaSalida.SalidaError(error, "Error");
            }
        } while (!continuar);
        return  null;
    }

    /**
     * <h2> Entrada de un Teléfono</h2>
     * Metodo para solicitar el ingreso de telèfono por teclado. Se utiliza JOptionPane para la
     * solicitud.
     * <p>
     * Se invoca mètodo {@link org.example.recursos.EntradaSalida#validarTelefono(String)} dentro de un try-catch
     * para asegurase que el Teléfono contiene 10 dìgitos numericos y que no comienza con 0 ni 15
     * En caso de ingresar al catch, se muetra el error al usuario y se ingresa nuevamente al bucle do.
     *
     * @see JOptionPane
     * @return teléfono ingresado por teclado
     * @author Ruth Lovece
     */
    public static String entradaTelefono (){
        boolean continuar = false;
        do {
            try {
                String entrada = showInputDialog("\n Ingrese nro de teléfono \n" +
                        "Debe contener 10 digitos, no incluir 0 ni 15 \n\n");
                validarTelefono(entrada);
                continuar = true;
                return entrada;
            } catch (TelefonoInvalido e){
                //String error = "El Telefono debe contener 10 digitos numericos. \nCódigo de área sin 0 y número de celular sin 15 \n\n";
                EntradaSalida.SalidaError(e.getMessage(), "Error");
            }
        } while (!continuar);
        return  null;
    }


    /**
     * <h2> Entrada de generación password</h2>
     * Metodo para solicitar el ingreso y generación de password por teclado. Se utiliza JOptionPane para la
     * solicitud.
     * <p>
     * Se invoca mètodo {@link org.example.recursos.EntradaSalida#validarPassword(String)} dentro de un try-catch
     * para asegurase que el Password contiene al menos 6 dígitos alfanuméricos
     * En caso de ingresar al catch, se muetra el error al usuario y se ingresa nuevamente al bucle do.
     *
     * @see JOptionPane
     * @return password ingresada por teclado
     * @author Ruth Lovece
     */
    public static String entradaGeneracionPassword (){
        boolean continuar = false;
        do {
            try {
                String entrada = showInputDialog("\n Ingrese una contraseña \n" +
                        "Debe contener al menos 6 dígitos alfanuméricos \n\n");
                validarPassword(entrada);
                return entrada;
            } catch (PasswordInvalida e){
                String error = "La contraseña ingresada no cumple los requisitos\nDebe contener al menos 6 dígitos y una letra \n\n";
                EntradaSalida.SalidaError(error, "Error");
            }
        } while (!continuar);
        return  null;
    }

    /**
     * <h2> Entrada de un Estado Empleado</h2>
     * Metodo para ingresar una instancia de EstadosEmpleados. El método permite selecionar la estado detro de la lista de opciones,
     * eliminado posibles errores y siendo amigable con el usuario.
     * Para esto utiliza una arreglo de EstadosEmpleado y luego se muestra el atributo descripción utilizando
     * JComboBox y JOptionPane
     *
     * @see org.example.enums.EstadosEmpleado
     * @see JComboBox
     * @see JOptionPane
     * @return estado del empleado seleccionada
     * @author Ruth Lovece
     */
    public static EstadosEmpleado entradaEstadoEmpleado(){
        EstadosEmpleado[] estados = EstadosEmpleado.values();

        JComboBox<EstadosEmpleado> comboBox = new JComboBox<>(estados);

        int seleccion = JOptionPane.showOptionDialog(
                null,
                comboBox,
                "Selecione el estado",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                estados[0]);

        EstadosEmpleado entrada = estados[0];
        if (seleccion != JOptionPane.CLOSED_OPTION) {
            entrada = (EstadosEmpleado) comboBox.getSelectedItem();
        }
        System.out.println(entrada);
        return entrada;
    }

    /**
     * <h2> Entrada username</h2>
     * Metodo para solicitar el ingreso username por teclado. Se utiliza JOptionPane para la
     * solicitud.
     * <p>
     * Se invoca mètodo {@link org.example.recursos.EntradaSalida#validarUsername(String)} dentro de un try-catch
     * para asegurase que el username contiene al menos 5 dígitos alfanuméricos
     * En caso de ingresar al catch, se muetra el error al usuario y se ingresa nuevamente al bucle do.
     *
     * @see JOptionPane
     * @return username ingresada por teclado
     * @author Ruth Lovece
     */
    public static String entradaUsermane (){
        boolean continuar = false;
        do {
            try {
                String entrada = showInputDialog("\n Ingrese nombre de usuario \n" +
                        "Debe contener al menos 5 dígitos alfanuméricos \n\n");
                validarUsername(entrada);
                return entrada;
            } catch (UsernameInvalido e){
                String error = "El usuario ingresado no cumple con los requisitos\nDebe contener al menos 5 dígitos y una letra \n\n";
                EntradaSalida.SalidaError(error, "Error");
            }
        } while (!continuar);
        return  null;
    }

    ///endRegion

    ///region Salidas
    /**
     * <h2> Mensaje con Error</h2>
     * Metodo mostrar errores al usuario utilizando JOptionPane. Se solititan como paràmetros el mensaje que
     * se quiere mostrar y el tìtulo de la ventana.
     *
     * @see JOptionPane
     * @param msj
     * @param titulo
     * @author Ruth Lovece
     */
    public static void SalidaError (String msj, String titulo){
        JOptionPane.showMessageDialog(null, msj, titulo, ERROR_MESSAGE);
    }

    /**
     * <h2> Mensaje con Información</h2>
     * Metodo para mostrar información al usuario utilizando JOptionPane. Se solititan como paràmetros el mensaje que
     * se quiere mostrar y el tìtulo de la ventana.
     *
     * @see JOptionPane
     * @param msj
     * @param titulo
     * @author Ruth Lovece
     */
    public static void SalidaInformacion (String msj, String titulo){
        JOptionPane.showMessageDialog(null, msj, titulo, INFORMATION_MESSAGE);
    }

    /**
     * <h2> Mensaje con Advertencia</h2>
     * Metodo para mostrar advertencias al usuario utilizando JOptionPane. Se solititan como paràmetros el mensaje que
     * se quiere mostrar y el tìtulo de la ventana.
     *
     * @see JOptionPane
     * @param msj
     * @param titulo
     * @author Ruth Lovece
     */
    public static void SalidaAdvertencia (String msj, String titulo) {
        JOptionPane.showMessageDialog(null, msj, titulo, WARNING_MESSAGE);
    }
    ///endregion

    ///region Entradas Aleatoria
    /**
     * <h2> Codigo aleatorio</h2>
     * Metodo para generar un código de 10 dìgitos alfanumèrico aleatorio.
     *
     * @see UUID
     * @return codigo alfanumérico de 10 dìgitos
     * @author Ruth Lovece
     */
    public static String CodigoPaquete (){
        String codigo = UUID.randomUUID().toString().substring(0,10).toUpperCase(); //genera un codigo random
        return codigo;
    }

    ///endregion

    ///region Validaciones

    /**
     * <h2> Validación ingreso Email</h2>
     * Método que valida que la dirección de correo electrónico ingresada contenga @ y .com
     * En caso de no contenear cualquiera estos se lanza un excepción.
     *
     * @param email
     * @exception EmailIncorrecto
     * @author Ruth Lovece
     *
     * */
    public static void validarEmail (String email) throws EmailIncorrecto {
        if (!email.contains("@") || !email.contains(".com")){
            throw new EmailIncorrecto("Formato de email incorrecto");
        }
    }

    /**
     * <h2> Validación ingreso DNI</h2>
     * Método que valida que el dni contenga 8 dígitos numéricos.
     * En caso contrario se lanzará la excepción
     *
     * @param dni
     * @exception DNIIncorrecto
     * @author Ruth Lovece
     *
     * */
    public static void validarDNI (String dni) throws DNIIncorrecto {
        try {
            Integer.parseInt(dni);
        } catch (NumberFormatException e) {
            throw new DNIIncorrecto("DNI con digitos no numéricos");
        }
        if (dni.length()!=8) {
            throw new DNIIncorrecto("DNI no contiene 8 digitos");
        }

    }

    /**
     * <h2> Validación ingreso Teléfono</h2>
     * Método que valida que el teléfono contenga 10 dígitos numéricos, y no comienza con 0 ni 15.
     * En caso contrario se lanzará la excepción
     *
     * @param telefono
     * @exception TelefonoInvalido
     * @author Ruth Lovece
     *
     * */
    public static void validarTelefono (String telefono) throws TelefonoInvalido {
        try {
            Long.parseLong(telefono);
        } catch (NumberFormatException e) {
            throw new TelefonoInvalido("Teléfono con digitos no numéricos");
        }
        if (telefono.length()!=10 ||
                telefono.charAt(0)=='0' ||
                telefono.charAt(0)=='1' && telefono.charAt(1)=='5') {
            throw new TelefonoInvalido("Telefono con digitos incorrectos");
        }
    }

    /**
     * <h2> Validación ingreso para generar Password</h2>
     * Método que valida que la contraseña que se desea generar e ingresada por teclado contenga al menos 6 dígitos,
     * y al menos una letra.
     * En caso contrario se lanzará la excepción
     *
     * @param password
     * @exception PasswordInvalida
     * @author Ruth Lovece
     *
     * */
    public static void validarPassword (String password) throws PasswordInvalida {
        try {
            Integer.parseInt(password);
            throw new PasswordInvalida("Password sin letras");
        } catch (NumberFormatException e) {
            if (password.length()<6) {
                throw new PasswordInvalida("Password con menos de 6 caracteres");
            }
        }
    }

    /**
     * <h2> Validación username</h2>
     * Método que valida que el username contiene al menos 5 dígitos alfanumericos.
     * En caso contrario se lanzará la excepción
     *
     * @param username
     * @exception DNIIncorrecto
     * @author Ruth Lovece
     *
     * */
    public static void validarUsername (String username) throws UsernameInvalido {
        try {
            Integer.parseInt(username);
            throw new UsernameInvalido("Username sin letras");
        } catch (NumberFormatException e) {
            if (username.length()<5) {
                throw new UsernameInvalido("El usuario debe contener minimo 5 caracteres");
            }
        }

    }
    ///endregion

}