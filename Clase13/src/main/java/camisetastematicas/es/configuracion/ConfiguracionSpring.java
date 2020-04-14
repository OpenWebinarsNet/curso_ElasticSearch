package camisetastematicas.es.configuracion;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ConfiguracionSpring extends WebMvcConfigurerAdapter {

    private String SERVIDOR_ELASTIC="localhost";
    private int PUERTO_ELASTIC=9200;
    private static final String ESQUEMA_HTTP="http";

    @Bean(name = "lowlevelclient")
    public RestClient client() {
        RestClient restClient = RestClient.builder(
                new HttpHost(SERVIDOR_ELASTIC,PUERTO_ELASTIC, ESQUEMA_HTTP)).build();
        return restClient;
    }
    @Bean(name = "highlevelclient")
    public RestHighLevelClient highLevelClient(){
        return new RestHighLevelClient(RestClient.builder(new HttpHost(SERVIDOR_ELASTIC, PUERTO_ELASTIC, ESQUEMA_HTTP)));
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/webjars/**","/img/**","/static/**","/js/**")
                .addResourceLocations(
                        "classpath:/META-INF/resources/webjars/",
                        "classpath:/static/img/","classpath:/static/css/",
                        "classpath:/static/js/");
    }



}
