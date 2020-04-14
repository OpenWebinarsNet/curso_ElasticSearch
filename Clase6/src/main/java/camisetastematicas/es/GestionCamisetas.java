package camisetastematicas.es;


import camisetastematicas.es.modelo.Camiseta;
import camisetastematicas.es.vista.MenusUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import camisetastematicas.es.servicio.InterfazElasticsearch;

import java.io.*;
import java.util.List;

public class GestionCamisetas {

    final int OPCION_NUEVA_CAMISETA = 1;
    final int OPCION_VER_TODAS = 2;
    final int OPCION_BUSCAR_POR_NOMBRE = 3;
    final int OPCION_BUSCAR_POR_PRECIO = 4;
    final int OPCION_MODIFICAR_POR_NOMBRE = 5;
    final int OPCION_BORRAR_POR_NOMBRE = 6;
    final int OPCION_SALIR = 7;

    final String MENSAJE_BUSCAR_POR_NOMBRE = "Nombre a buscar: ";
    final String MENSAJE_BUSCAR_PRECIO = "Precio: ";


    static final String TEXTO_CONTINUAR = "\n\n\t ==== Presione enter para continuar =====";

    @Autowired
    InterfazElasticsearch servicio;


    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        GestionCamisetas app = context.getBean(GestionCamisetas.class);
        app.MetodoMain();

        ((org.springframework.context.ConfigurableApplicationContext) context).close();
    }

    public void MetodoMain() {

        MenusUsuario menusUsuario = new MenusUsuario();

        Camiseta camiseta, camisetaAntesLink,camisetaAntesNombre;
        int opcion;
        String nombreBusqueda;
        int precio;
        List<Camiseta> listaCamisetas;

        while (true) {
            vaciarPantallaWindows();
            opcion = menusUsuario.menuPrincipal();

            switch (opcion) {
                case OPCION_NUEVA_CAMISETA:
                    camiseta = menusUsuario.introducirValoresCamisetaConsola();

                    camisetaAntesLink = servicio.buscarPorLink(camiseta.getLink());
                    camisetaAntesNombre = servicio.buscarPorNombre(camiseta.getNombre());

                    if ((null != camisetaAntesLink) || (null != camisetaAntesNombre)) {
                        System.out.println("Otra camiseta tenia el mismo id (link) o el mismo nombre, camiseta no guardada !\n\n");

                        menusUsuario.obtenerPalabraDeConsola(TEXTO_CONTINUAR);
                    } else {
                        servicio.guardarCamiseta(camiseta);
                    }

                    break;
                case OPCION_VER_TODAS:
                    servicio.buscarTodasCamisetas();
                    menusUsuario.obtenerPalabraDeConsola(TEXTO_CONTINUAR);
                    break;
                case OPCION_BUSCAR_POR_NOMBRE:
                    nombreBusqueda = menusUsuario.obtenerPalabraDeConsola(MENSAJE_BUSCAR_POR_NOMBRE);
                    camiseta = servicio.buscarPorNombre(nombreBusqueda);

                    if (null == camiseta) {
                        System.out.println("Camiseta no encontrada !\n\n");
                    } else {
                        System.out.println("Camiseta obtenida: " + camiseta);
                    }

                    menusUsuario.obtenerPalabraDeConsola(TEXTO_CONTINUAR);
                    break;
                case OPCION_BUSCAR_POR_PRECIO:
                    precio = menusUsuario.obtenerEnteroDeConsola(MENSAJE_BUSCAR_PRECIO);
                    listaCamisetas = servicio.buscarPorPrecio(precio);
                    if (listaCamisetas.size() == 0) {
                        System.out.println("Ninguna camiseta con el precio\n\n");
                    } else {
                        System.out.println("Lista de camisetas con el precio: " + listaCamisetas);
                    }
                    menusUsuario.obtenerPalabraDeConsola(TEXTO_CONTINUAR);
                    break;
                case OPCION_BORRAR_POR_NOMBRE:
                    nombreBusqueda = menusUsuario.obtenerPalabraDeConsola(MENSAJE_BUSCAR_POR_NOMBRE);
                    camiseta = servicio.buscarPorNombre(nombreBusqueda);
                    if (null == camiseta) {
                        System.out.println(" !! Nombre no encontrado !!\n\n");
                        menusUsuario.obtenerPalabraDeConsola(TEXTO_CONTINUAR);
                    } else {
                        servicio.borrarCamiseta(camiseta);
                    }
                    break;
                case OPCION_MODIFICAR_POR_NOMBRE:
                    nombreBusqueda = menusUsuario.obtenerPalabraDeConsola(MENSAJE_BUSCAR_POR_NOMBRE);
                    camisetaAntesLink = servicio.buscarPorNombre(nombreBusqueda);
                    camiseta = menusUsuario.introducirCamisetaConValorPrevio(camisetaAntesLink);
                    servicio.guardarCamiseta(camiseta);

                    menusUsuario.obtenerPalabraDeConsola(TEXTO_CONTINUAR);
                    break;
                case OPCION_SALIR:
                    break;
                default:
                    System.out.println("Opcion invalida !!\n\n");
                    menusUsuario.obtenerPalabraDeConsola(TEXTO_CONTINUAR);
                    break;
            }
            if (opcion == OPCION_SALIR) {
                break;
            }
        }
    }


    static void vaciarPantallaWindows() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
