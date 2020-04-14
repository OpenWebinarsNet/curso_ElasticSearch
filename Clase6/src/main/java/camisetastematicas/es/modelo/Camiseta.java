package camisetastematicas.es.modelo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.annotation.Id;

@Document(indexName = "galeriacamisetas", type = "camiseta")
public class Camiseta {

    private String nombre;
    private int precio;
    @Id
    private String link;
    private List<String> temas=new ArrayList<String>();

    // Necesario constructor vacio para jackson (libreria usa repositorio de Spring Data)
    public Camiseta(){}

    public Camiseta(String nombre, int precio, String link, List<String> temas){
        this.nombre=nombre;
        this.precio=precio;
        this.link=link;
        this.temas=temas;
    }


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

    public String toString() {
		return "CamiLista ("+getNombre()+","+getPrecio()+","+getLink()+"), temas: "+getTemas();
	}
}
