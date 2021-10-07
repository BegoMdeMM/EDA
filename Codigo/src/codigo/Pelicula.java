package codigo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Pelicula {
	private String nombre;
	private HashMap<String, Actor> listaActoresPelicula = new HashMap<String, Actor>();

	public Pelicula(String pN) {
		this.nombre = pN;
	}

	public String getNombre() {
		return this.nombre;
	}
	
	public HashMap<String, Actor> getListaActoresPelicula() {
		return listaActoresPelicula;
	}
	
	public Iterator<Entry<String, Actor>> getIteradorActoresPelicula() {
		return this.listaActoresPelicula.entrySet().iterator();
	}
	
	public void imprimirActoresPelicula(){
		Iterator<Entry<String, Actor>> itr = this.getIteradorActoresPelicula();
		Actor a;
		while (itr.hasNext()) {
			a = itr.next().getValue();
			System.out.println("Actor: " + a.getNombre());
		}
	}
	
	public void addActorPelicula(Actor pActor){
		this.listaActoresPelicula.put(pActor.getNombre(), pActor);
	}
	
	public void borrarActorPelicula(Actor pActor){
		this.listaActoresPelicula.remove(pActor.getNombre());
	}

}
