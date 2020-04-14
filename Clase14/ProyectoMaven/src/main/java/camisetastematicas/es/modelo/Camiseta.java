package camisetastematicas.es.modelo;

import java.util.List;

public class Camiseta {
    //private String id;
    private String nombre;
    private int precio;
    private String link;
    private List<String> temas;

    public Camiseta( String nombre, int precio, String link, List<String> temas) {
       // this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.link = link;
        this.temas = temas;
    }

   /* public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<String> getTemas() {
        return temas;
    }

    public void setTemas(List<String> temas) {
        this.temas = temas;
    }
}
