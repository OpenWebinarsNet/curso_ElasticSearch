package camisetastematicas.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class Lanzador extends SpringBootServletInitializer{
    public static void main(String [] args){
        SpringApplication.run(Lanzador.class,args);
    }
}
