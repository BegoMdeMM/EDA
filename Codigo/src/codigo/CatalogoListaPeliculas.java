package codigo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class CatalogoListaPeliculas {

	private HashMap<String, Pelicula> lista = new HashMap<String, Pelicula>();
	private static CatalogoListaPeliculas miListaPeliculas;

	private CatalogoListaPeliculas() {

	}

	public static CatalogoListaPeliculas getCatalogoListaPeliculas() {
		if (miListaPeliculas != null) {
			return miListaPeliculas;
		} else {
			return miListaPeliculas = new CatalogoListaPeliculas();
		}
	}

	public Pelicula obtenerPelicula(String pN) {
		Pelicula p = null;
		if (this.lista.containsKey(pN)) {

			p = this.lista.get(pN);
		}
		return p;
	}

	public void addPelicula(Pelicula p) {
		lista.put(p.getNombre(), p);
	}

	private Iterator<Entry<String, Pelicula>> getIteradorPeliculas() {
		return lista.entrySet().iterator();
	}

	public void imprimirPeliculas() {
		Iterator<Entry<String, Pelicula>> itr = this.getIteradorPeliculas();
		Pelicula p;
		while (itr.hasNext()) {
			p = (Pelicula) itr.next().getValue();
			System.out.println("Pelicula: " + p.getNombre());
		}
	}
}
