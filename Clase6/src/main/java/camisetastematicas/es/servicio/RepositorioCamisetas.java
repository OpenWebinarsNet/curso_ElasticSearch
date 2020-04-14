package camisetastematicas.es.servicio;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import camisetastematicas.es.modelo.Camiseta;

public interface RepositorioCamisetas extends ElasticsearchRepository<Camiseta,String> {
    List<Camiseta> findByNombre(String nombre);
    List<Camiseta> findByPrecio(int precio);
}
