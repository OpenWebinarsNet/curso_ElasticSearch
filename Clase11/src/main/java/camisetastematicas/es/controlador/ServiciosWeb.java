package camisetastematicas.es.controlador;

import camisetastematicas.es.modelo.Camiseta;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiciosWeb {

    // Rutas de recursos
    private static final String SERVICIO_INDICE = "/camisetastematicas";
    private static final String SERVICIO_GALERIA = "/galeria";
    private static final String SERVICIO_BUSCAR_POR_TEMA="/buscarportema";
    private static final String SERVICIO_BUSCAR_POR_TEMA_Y_PRECIO="/buscarportemayprecio";

    // Plantillas
    private static final String PLANTILLA_GALERIA = "plantillaGaleria";
    private static final String PLANTILLA_INDICE = "plantillaIndice";

    @RequestMapping(value = SERVICIO_INDICE, method = RequestMethod.GET)
    public String indice(Model model) {
        return PLANTILLA_INDICE;
    }

    @RequestMapping(value = SERVICIO_GALERIA, method = RequestMethod.POST)
    public String galeria(Model model) {
        List<Camiseta> lista=datosPrueba();
        return PLANTILLA_GALERIA;
    }

    @RequestMapping(value = SERVICIO_BUSCAR_POR_TEMA, method = RequestMethod.POST)
    public String buscarPorTema(Model model,@RequestParam String temabuscado) {
        List<Camiseta> lista=datosPrueba();
        return PLANTILLA_GALERIA;
    }

    @RequestMapping(value = SERVICIO_BUSCAR_POR_TEMA_Y_PRECIO, method = RequestMethod.POST)
    public String buscarPorTemaYPrecio(Model model,@RequestParam String temabuscado,@RequestParam String preciomaximo) {

        List<Camiseta> lista=datosPrueba();
        return PLANTILLA_GALERIA;
    }


    List<Camiseta> datosPrueba(){
        Camiseta camiseta1=new Camiseta("Usa made in china",15, "/img/Economia.png", Arrays.asList("Economía"));
        Camiseta camiseta2=new Camiseta("Movilidad sostenible",20, "/img/MedioambientePolitica.png", Arrays.asList("Medio ambiente","Política"));
        List<Camiseta> listCamisetas=new ArrayList<Camiseta>();
        listCamisetas.add(camiseta1);
        listCamisetas.add(camiseta2);

        return datosPrueba();
    }
}
