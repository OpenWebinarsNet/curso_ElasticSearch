package camisetastematicas.es.controlador;

import camisetastematicas.es.elasticsearch.InterfazElasticsearch;
import camisetastematicas.es.modelo.Camiseta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ServiciosWeb {

    @Autowired
    InterfazElasticsearch interfazElasticsearch;

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
        List<Camiseta> lista=interfazElasticsearch.buscarTodasCamisetas();
        boolean hayresultados;
        if (lista.size()>0){
            hayresultados=true;
        }else{
            hayresultados=false;
        }
        model.addAttribute("hayresultados",hayresultados);
        model.addAttribute("lista",lista);
        return PLANTILLA_GALERIA;
    }

    @RequestMapping(value = SERVICIO_BUSCAR_POR_TEMA, method = RequestMethod.POST)
    public String buscarPorTema(Model model, @RequestParam String temabuscado) {
        List<Camiseta> lista=interfazElasticsearch.buscarCamisetasPorTema(temabuscado);
        boolean hayresultados;
        if (lista.size()>0){
            hayresultados=true;
        }else{
            hayresultados=false;
        }
        model.addAttribute("hayresultados",hayresultados);
        model.addAttribute("lista",lista);
        return PLANTILLA_GALERIA;
    }

    @RequestMapping(value = SERVICIO_BUSCAR_POR_TEMA_Y_PRECIO, method = RequestMethod.POST)
    public String buscarPorTemaYPrecio(Model model, @RequestParam String temabuscado, @RequestParam String preciomaximo) {
        List<Camiseta> lista=interfazElasticsearch.buscarCamisetasPorTemaYPrecio(temabuscado,preciomaximo);
        boolean hayresultados;
        if (lista.size()>0){
            hayresultados=true;
        }else{
            hayresultados=false;
        }
        model.addAttribute("hayresultados",hayresultados);
        model.addAttribute("lista",lista);
        return PLANTILLA_GALERIA;
    }

}
