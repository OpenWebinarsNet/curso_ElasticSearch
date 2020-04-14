package camisetastematicas.es.servicio;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import camisetastematicas.es.modelo.Camiseta;

public class InterfazElasticsearch {
	
	@Autowired
	private RepositorioCamisetas repositorio;

	public void guardarCamiseta(Camiseta camiseta){
		repositorio.save(camiseta);
	}

	public void buscarTodasCamisetas() {
		long nTotal= repositorio.count();
		if (nTotal==0){
			System.out.println("No hay ninguna camiseta introducida");
		}else {
			repositorio.findAll().forEach(System.out::println);
		}
	}



	public void borrarCamiseta(Camiseta camiseta) {
		repositorio.delete(camiseta);
	}
	
	public Camiseta buscarPorNombre(String nombreCamiseta) {
		List<Camiseta> lista = repositorio.findByNombre(nombreCamiseta);
		if (lista.size()>0){
			return lista.get(0);
		}else {
			return null;
		}
	}
	
	public List<Camiseta> buscarPorPrecio(int precioCamiseta) {
		List<Camiseta> lista = repositorio.findByPrecio(precioCamiseta);
		return lista;
	}

	public Camiseta buscarPorLink(String link) {
		Optional<Camiseta> opCamiseta=repositorio.findById(link);


		if (opCamiseta.isPresent()){
			return opCamiseta.get();
		}else{
			return null;
		}

	}
}
