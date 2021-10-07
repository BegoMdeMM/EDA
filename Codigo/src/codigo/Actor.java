package codigo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class Actor implements Comparable<Actor> {

	private final String nombre;
	private HashMap<String, Pelicula> listaPeliculasPorActor = new HashMap<String, Pelicula>();
	private HashMap<String, Relacion> colegas = new HashMap<String, Relacion>();

	public Actor(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return this.nombre;
	}

	public HashMap<String, Pelicula> getListaPeliculasPorActor() {
		return this.listaPeliculasPorActor;
	}

	public void addPeliculaActor(Pelicula p) {
		this.listaPeliculasPorActor.put(p.getNombre(), p);
	}

	public Iterator<Entry<String, Pelicula>> getIteradorPeliculas() {
		return this.listaPeliculasPorActor.entrySet().iterator();
	}

	public Iterator<Entry<String, Relacion>> getIteradorColegas() {
		return this.colegas.entrySet().iterator();
	}

	public void imprimirPeliculas() {
		Iterator<Entry<String, Pelicula>> itr = this.getIteradorPeliculas();
		Pelicula p;
		while (itr.hasNext()) {
			p = itr.next().getValue();
			System.out.println("Pelicula: " + p.getNombre());
		}
	}

	public Pelicula aParticipadoEnPelicula(String pPelicula) {
		return this.listaPeliculasPorActor.get(pPelicula);
	}

	public int numeroPeliculasActor() {
		return this.listaPeliculasPorActor.size();
	}

	public void borrarPelicula(Pelicula nombrePelicula) {
		this.listaPeliculasPorActor.remove(nombrePelicula);
	}

	/*
	 * public Pelicula obtenerPeliculasDeActor(int z) { Node<Pelicula> peli =
	 * listaPeliculasPorActor.last; for (int i = 0; i < z; i++) { peli =
	 * peli.next; } return peli.data; }
	 */

	public int compareTo(Actor pActor) {
		return this.getNombre().compareTo(pActor.getNombre());
	}

	public void cargarColegas() {
		this.colegas.clear();
		// recorremos listapeliculas y cargamos cada relacion
		Iterator<Entry<String, Pelicula>> itrpeliculas = this
				.getIteradorPeliculas();
		while (itrpeliculas.hasNext()) {
			// Obtenemos cada pelicula
			Pelicula p = itrpeliculas.next().getValue();
			// Por cada pelicula obtenemos los actores y los cargamos en la
			// hashmap
			Iterator<Entry<String, Actor>> itractores = p
					.getIteradorActoresPelicula();
			while (itractores.hasNext()) {
				Actor actual = itractores.next().getValue();
				if (actual.aParticipadoEnPelicula(p.getNombre()) != null
						&& !colegas.containsKey(actual.getNombre())
						&& !actual.getNombre().equals(this.nombre)) {
					// Si el actor ha participado en la pelicula, no existe en
					// la lista de colegas y no es el mismo se añade
					Relacion r = new Relacion(this, p, actual);
					colegas.put(actual.getNombre(), r);
				}
			}
		}
	}

	public HashMap<String, Relacion> getColegas() {
		return colegas;
	}

	public void imprimirColegas() {
		Iterator<Entry<String, Relacion>> itr = this.getIteradorColegas();
		while (itr.hasNext()) {
			System.out.println(itr.next().getValue().getActual().getNombre());
		}
		System.out.println("El numero de Colegas del Actor " + this.nombre
				+ " es: " + this.colegas.size());
	}

	public double centralidad(Queue<Actor> porExaminar,
			HashMap<String, Relacion> examinados) {
		double centralidad = 0.0;

		ArrayList<Actor> listaArray = CatalogoListaActores
				.getCatalogoListaActores().cargarArrayList();

		for (int i = 0; i < 50; i++) {
			Random rand = new Random();
			int randomNum1 = rand.nextInt((listaArray.size() - 1) + 1) + 0;
			int randomNum2 = rand.nextInt((listaArray.size() - 1) + 1) + 0;

			Actor a1 = listaArray.get(randomNum1);
			Actor a2 = listaArray.get(randomNum2);

			try {
				Stack<Relacion> resultado = CatalogoListaActores
						.getCatalogoListaActores().estanConectados2(a1, a2,
								porExaminar, examinados);
				CatalogoListaActores.getCatalogoListaActores().limpiarListas(porExaminar, examinados);
				if (resultado != null) {
					if (comprobarSiEstaActor(resultado)) {
						centralidad++;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return centralidad;
	}

	private boolean comprobarSiEstaActor(Stack<Relacion> relacion) {
		Relacion r = null;
		while (!relacion.isEmpty()) {
			r = relacion.pop();
			if (r.getActual().getNombre().equalsIgnoreCase(this.nombre)) {
				return true;
			}
		}
		return false;
	}

	public void limpiarColegas() {
		this.colegas.clear();
	}
}
