package camisetastematicas.es.elasticsearch;

import com.google.gson.*;
import camisetastematicas.es.modelo.Camiseta;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class InterfazElasticsearch {

    // Busquedas con parametros
    private static final String TEMPLATE_QUERY_TEMA="{" +
            "  \"query\": {\"match_phrase\": {\n" +
            "    \"temas\":\"${temapedido}\"\n" +
            "  }}\n" +
            "}";
    private static final String PARAMETRO_TEMA="temapedido";

    private static final String TEMPLATE_QUERY_TEMA_CON_PRECIO="{\n" +
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

    // Para busqueda e inserción con cliente a alto nivel
    private static final String INDICE="camisetas";
    private static final String TIPO="camiseta";

    // Para busqueda con cliente a bajo nivel: verbo, ruta, "hits", "_source“
    private static final String VERBO_CONSULTA="GET";
    private static final String ENDPOINT_CONSULTA="camisetas/camiseta/_search";
    private static final String CAMPO_HITS="hits";
    private static final String CAMPO_SOURCE="_source";


    public List<Camiseta> buscarTodasCamisetas(){
        List<Camiseta> lista=new ArrayList<Camiseta>();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        SearchRequest searchRequest = new SearchRequest(INDICE).source(searchSourceBuilder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = clienteAltoNivel.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SearchHits searchHits = searchResponse.getHits();
        Map hitMap;
        Camiseta camiseta;
        JsonElement jsonElement;
        Gson gson=new Gson();
        for (SearchHit hit : searchHits) {
            hitMap = hit.getSourceAsMap();
            jsonElement = gson.toJsonTree(hitMap);
            camiseta = gson.fromJson(jsonElement, Camiseta.class);
            lista.add(camiseta);
        }

        return lista;
    }

    public void insertarDocumento(Camiseta camiseta) {
        JsonObject jsonObject=convertirAJson(camiseta);

        // Expresamos como map
        Map<String,Object> map = new HashMap<String,Object>();
        Gson gson=new Gson();
        map = (Map<String,Object>) gson.fromJson(jsonObject, map.getClass());

        // Request con source el map, JSON
        IndexRequest indexRequest=new IndexRequest(INDICE,TIPO).source(map,XContentType.JSON);
        try {
            clienteAltoNivel.index(indexRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Camiseta> buscarCamisetasPorTema(String tema){
        List<Camiseta> lista=new ArrayList<Camiseta>();

        // reemplazar parametros en plantilla
        Map<String, String> parametrosPlantilla = new HashMap<String, String>();
        parametrosPlantilla.put(PARAMETRO_TEMA, tema);
        StrSubstitutor sub = new StrSubstitutor(parametrosPlantilla);
        String query = sub.replace(TEMPLATE_QUERY_TEMA);

        // Http entity
        Map<String, String> params = Collections.emptyMap();
        HttpEntity entity = new NStringEntity(query, ContentType.APPLICATION_JSON);

        // respuesta como string
        String cuerpoRespuesta=null;
        Response respuesta = null;
        try {
            respuesta = clienteBajoNivel.performRequest(VERBO_CONSULTA, ENDPOINT_CONSULTA, params, entity);
            cuerpoRespuesta = EntityUtils.toString(respuesta.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Respuesta como JsonObject
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(cuerpoRespuesta).getAsJsonObject();

        // hits
        JsonObject hitsPadre= jsonObject.get(CAMPO_HITS).getAsJsonObject();
        JsonArray hits= hitsPadre.get(CAMPO_HITS).getAsJsonArray();

        // tomar _source de cada elemento
        JsonObject source;
        Camiseta camiseta;
        Gson gson=new Gson();
        for (JsonElement hit : hits) {
            source = hit.getAsJsonObject().get(CAMPO_SOURCE).getAsJsonObject();
            camiseta = gson.fromJson(source.toString(),Camiseta.class);
            lista.add(camiseta);
        }

        return lista;
    }

    public List<Camiseta> buscarCamisetasPorTemaYPrecio(String tema,String precio){
        List<Camiseta> lista=new ArrayList<Camiseta>();

        // reemplazar parametros en plantilla
        Map<String, String> parametrosPlantilla = new HashMap<String, String>();
        parametrosPlantilla.put(PARAMETRO_TEMA, tema);
        parametrosPlantilla.put(PARAMETRO_PRECIO, precio);
        StrSubstitutor sub = new StrSubstitutor(parametrosPlantilla);
        String query = sub.replace(TEMPLATE_QUERY_TEMA_CON_PRECIO);

        // Http entity
        Map<String, String> params = Collections.emptyMap();
        HttpEntity entity = new NStringEntity(query, ContentType.APPLICATION_JSON);

        // respuesta como string
        String cuerpoRespuesta=null;
        Response respuesta = null;
        try {
            respuesta = clienteBajoNivel.performRequest(VERBO_CONSULTA, ENDPOINT_CONSULTA, params, entity);
            cuerpoRespuesta = EntityUtils.toString(respuesta.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Respuesta como JsonObject
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(cuerpoRespuesta).getAsJsonObject();

        // hits
        JsonObject hitsPadre= jsonObject.get(CAMPO_HITS).getAsJsonObject();
        JsonArray hits= hitsPadre.get(CAMPO_HITS).getAsJsonArray();

        // tomar _source de cada elemento
        JsonObject source;
        Camiseta camiseta;
        Gson gson=new Gson();
        for (JsonElement hit : hits) {
            source = hit.getAsJsonObject().get(CAMPO_SOURCE).getAsJsonObject();
            camiseta = gson.fromJson(source.toString(),Camiseta.class);
            lista.add(camiseta);
        }


        return lista;
    }

    JsonObject convertirAJson(Camiseta camiseta){
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(camiseta);
        JsonObject object = element.getAsJsonObject();

        return object;
    }
}
