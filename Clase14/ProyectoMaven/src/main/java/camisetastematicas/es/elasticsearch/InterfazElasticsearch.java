package camisetastematicas.es.elasticsearch;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import camisetastematicas.es.modelo.Camiseta;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class InterfazElasticsearch {

    // Busquedas con parametros
    private static final String TEMPLATE_QUERY_TEMA="GET camisetas/camiseta/_search\n" +
            "{\n" +
            "  \"query\": {\"match_phrase\": {\n" +
            "    \"temas\":\"${temapedido}\"\n" +
            "  }}\n" +
            "}";
    private static final String PARAMETRO_TEMA="temapedido";

    private static final String TEMPLATE_QUERY_TEMA_CON_PRECIO="GET camisetas/camiseta/_search\n" +
            "{\n" +
            "  \"query\": {\n" +
            "    \"bool\": {\"must\":[\n" +
            "        {\"match_phrase\": \n" +
            "          {\"temas\":\"${temapedido}\"}\n" +
            "        },\n" +
            "        {\"range\": \n" +
            "          {\"precio\":{\"lte\":${preciomaximo}}}\n" +
            "        }\n" +
            "      ] }}\n" +
            "}";
    private static final String PARAMETRO_PRECIO="preciomaximo";

    // Cliente de bajo nivel
    @Autowired
    @Qualifier("lowlevelclient")
    public RestClient clienteBajoNivel;

    // Cliente de alto nivel
    @Autowired
    @Qualifier("highlevelclient")
    public RestHighLevelClient clienteAltoNivel;

    // Para busqueda con cliente a alto nivel
    private static final String INDICE="camisetas";

    // Para busqueda con cliente a bajo nivel: verbo, ruta, "hits", "_source“
    private static final String VERBO_CONSULTA="GET";
    private static final String ENDPOINT_CONSULTA="camisetas/camiseta/_search";
    private static final String CAMPO_HITS="hits";
    private static final String CAMPO_SOURCE="_source";


    public List<Camiseta> buscarTodasCamisetas(){
        List<Camiseta> lista=null;
        return lista;
    }

    public List<Camiseta> buscarCamisetasPorTema(String tema){
        List<Camiseta> lista=null;
        return lista;
    }

    public List<Camiseta> buscarCamisetasPorTemaYPrecio(String tema,String precio){
        List<Camiseta> lista=null;
        return lista;
    }

    JsonObject convertirAJson(Camiseta camiseta){
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(camiseta);
        JsonObject object = element.getAsJsonObject();

        return object;
    }
}
