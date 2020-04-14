package camisetastematicas.es.vista;

import camisetastematicas.es.modelo.Camiseta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MenusUsuario {
    private static final String MENSAJE_NOMBRE = "Nombre de la camiseta: ";
    private static final String MENSAJE_PRECIO = "Precio: ";
    private static final String MENSAJE_LINK = "Link: ";
    private static final String MENSAJE_TEMAS = "Temas: ";
    private static final String MENSAJE_MAS_TEMAS = "¿Desea introducir mas temas (s/n)? ";
    private static final String MENSAJE_SI_CONTINUAR = "s";

    private static final String MENSAJE_OPCION_MENU_PRINCIPAL="Opcion: ";
    public int menuPrincipal(){
        System.out.println("\tEscoja la opción deseada: \n");
        System.out.println("1-Introducir una camiseta");
        System.out.println("2-Visualizar galeria de camisetas");
        System.out.println("3-Buscar por nombre");
        System.out.println("4-Buscar por precio");
        System.out.println("5-Modificar por nombre");
        System.out.println("6-Borrar por nombre");
        System.out.println("7-Salir");
        System.out.println("\n");
        return obtenerEnteroDeConsola(MENSAJE_OPCION_MENU_PRINCIPAL);
    }
    public Camiseta introducirValoresCamisetaConsola() {
        String nombre = obtenerPalabraDeConsola(MENSAJE_NOMBRE);
        int precio = obtenerEnteroDeConsola(MENSAJE_PRECIO);
        String link = obtenerPalabraDeConsola(MENSAJE_LINK);
        String respuesta;
        String tema;
        List<String> temas = new ArrayList<String>();
        while (true) {
            tema = obtenerPalabraDeConsola(MENSAJE_TEMAS);
            temas.add(tema);
            respuesta = obtenerPalabraDeConsola(MENSAJE_MAS_TEMAS);
            if (!respuesta.toLowerCase().startsWith(MENSAJE_SI_CONTINUAR)) {
                break;
            }
        }

        return new Camiseta(nombre,precio,link,temas);
    }


    public Camiseta introducirCamisetaConValorPrevio(Camiseta camiseta) {
        System.out.println("Valor anterior para el nombre: "+camiseta.getNombre());
        String nombre = obtenerPalabraDeConsola(MENSAJE_NOMBRE);
        System.out.println("Valor anterior para el precio: "+camiseta.getPrecio());
        int precio = obtenerEnteroDeConsola(MENSAJE_PRECIO);
        System.out.println("Valor anterior para el link: "+camiseta.getLink());
        String link = obtenerPalabraDeConsola(MENSAJE_LINK);
        System.out.println("Valor anterior para los temas: "+camiseta.getTemas());
        String respuesta;
        String tema;
        List<String> temas = new ArrayList<String>();
        while (true) {
            tema = obtenerPalabraDeConsola(MENSAJE_TEMAS);
            temas.add(tema);
            respuesta = obtenerPalabraDeConsola(MENSAJE_MAS_TEMAS);
            if (!respuesta.toLowerCase().startsWith(MENSAJE_SI_CONTINUAR)) {
                break;
            }
        }

        return new Camiseta(nombre,precio,link,temas);
    }

    public String obtenerPalabraDeConsola(String mensajeEntrada) {

        System.out.print(mensajeEntrada);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String l = null;
        try {
            l = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return l;
    }

    public int obtenerEnteroDeConsola(String mensajeEntrada) {
        int i = 0;
        System.out.print(mensajeEntrada);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            i = Integer.parseInt(br.readLine());
        } catch (NumberFormatException nfe) {
            System.err.println("Invalid Format!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return i;
    }
}
