package codigo;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class CatalogoListaActores {

	private HashMap<String, Actor> lista = new HashMap<String, Actor>();
	private static CatalogoListaActores miListaActores;
	private Stopwatch stp1;

	private CatalogoListaActores() {

	}

	public static CatalogoListaActores getCatalogoListaActores() {
		if (miListaActores != null) {
			return miListaActores;
		} else {
			return miListaActores = new CatalogoListaActores();
		}
	}

	public Actor obtenerActor(String pNombre) {
		Actor a = null;
		if (this.lista.containsKey(pNombre)) {
			a = this.lista.get(pNombre);
		}
		return a;
	}

	public void borrarActor(Actor a) {
		Iterator<Entry<String, Pelicula>> itrPeliculasActor = a
				.getIteradorPeliculas();
		while (itrPeliculasActor.hasNext()) {
			itrPeliculasActor.next().getValue().borrarActorPelicula(a);
		}
		lista.remove(a.getNombre());
	}

	public void addActor(Actor a) {
		lista.put(a.getNombre(), a);
	}

	public static void cargarDatos() {
		Stopwatch timer = new Stopwatch();
		String[] datosLinea;
		String nombreActor;
		String linea = null;
		Actor actor = null;
		Pelicula pelicula;
		try {
			String fichero = "docs/listaactores40000.txt";
			// String fichero = "docs/lista1M.txt";
			Scanner entrada = new Scanner(new FileReader(fichero));
			while (entrada.hasNext()) {
				linea = entrada.nextLine();
				// si hay lineas en blanco pasa de ellas
				if (linea.trim().length() == 0)
					continue;
				// Cada palabra de "linea" se mete en una posicion del
				// Array de strings "datosLinea"
				datosLinea = linea.split("\\s\\\\\\s");
				// Añadimos el primer elemento que es la pelicula
				pelicula = new Pelicula(datosLinea[0]);

				for (int i = 1; i < datosLinea.length; i++) {
					// Ahora añadimos actores
					nombreActor = datosLinea[i];
					actor = CatalogoListaActores.getCatalogoListaActores()
							.obtenerActor(nombreActor);
					if (actor == null) {
						// si no existe lo creamos y añadimos a la mae
						actor = new Actor(nombreActor);
						CatalogoListaActores.getCatalogoListaActores()
								.addActor(actor);
					}
					actor.addPeliculaActor(pelicula);
					// añadimos cada actor a la pelicula
					pelicula.addActorPelicula(actor);
				}

				// Anadimos la peli al catalogo
				CatalogoListaPeliculas.getCatalogoListaPeliculas().addPelicula(
						pelicula);

			}
			entrada.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(timer.elapsedTime());
	}

	public Iterator<Entry<String, Actor>> getIteradorActores() {
		return lista.entrySet().iterator();
	}

	public void imprimirActores() {
		Iterator<Entry<String, Actor>> itr = this.getIteradorActores();
		Actor a = new Actor("");
		while (itr.hasNext()) {
			a = itr.next().getValue();
			System.out.println("Actor: " + a.getNombre());
		}
	}

	public void imprimirOrdenada() {
		stp1 = new Stopwatch();
		this.ordenarActores(this.cargarArrayList());
	}

	// convierto el hasMap en un ArrayList<Actor>
	public ArrayList<Actor> cargarArrayList() {
		ArrayList<Actor> listaActores = new ArrayList<Actor>();
		Iterator<Entry<String, Actor>> itr = this.getIteradorActores();
		Actor nuevo = null;
		while (itr.hasNext()) {
			nuevo = itr.next().getValue();
			listaActores.add(nuevo);
		}
		return listaActores;
	}

	private void ordenarActores(ArrayList<Actor> pListaActores) {
		// utilizo el metodo quickSort reescrito para ordenar la lista
		// y la imprimo por pantalla;
		CatalogoListaActores.quicksort(pListaActores, 0,
				pListaActores.size() - 1);
		System.out.println(stp1.elapsedTime());
		System.out.println("¿Mostrar la lista ordenada? ¿S/N?");
		String resp = Menu.pedirString();
		if (resp.equalsIgnoreCase("s"))
			listarArrayOrdenada(pListaActores);
	}

	private void listarArrayOrdenada(ArrayList<Actor> pListaActores) {
		Iterator<Actor> itr = pListaActores.iterator();
		while (itr.hasNext()) {
			System.out.println(itr.next().getNombre());
		}
	}

	private static void quicksort(ArrayList<Actor> A, int izq, int der) {
		Actor pivote = A.get(izq); // tomamos primer elemento como pivote
		int i = izq; // i realiza la búsqueda de izquierda a derecha
		int j = der; // j realiza la búsqueda de derecha a izquierda
		Actor aux;
		while (i < j) { // mientras no se crucen las búsquedas
			while (A.get(i).compareTo(pivote) <= 0 && i < j)
				i++; // busca elemento mayor que pivote
			while (A.get(j).compareTo(pivote) > 0)
				j--; // busca elemento menor que pivote
			if (i < j) { // si no se han cruzado
				aux = A.get(i); // los intercambia
				A.set(i, A.get(j));
				A.set(j, aux);
			}
		}
		A.set(izq, A.get(j)); // se coloca el pivote en su lugar de forma que
								// tendremos
		A.set(j, pivote); // los menores a su izquierda y los mayores a su
							// derecha
		if (izq < j - 1)
			quicksort(A, izq, j - 1); // ordenamos subarray izquierdo
		if (j + 1 < der)
			quicksort(A, j + 1, der); // ordenamos subarray derecho
	}

	public boolean estanConectados(Actor a1, Actor a2,
			Queue<Actor> porExaminar, HashMap<String, Relacion> examinados)
			throws InterruptedException {
		// pre: los dos actores exiten en el catalogo de los actores y el actor
		// 1 diferente al actor 2

		a1.cargarColegas();// cargamos los colegas del actor
		Relacion rPrimera = new Relacion(null, null, a1);
		porExaminar.add(rPrimera.getActual());
		examinados.put(rPrimera.getActual().getNombre(), rPrimera);

		while (!porExaminar.isEmpty()) {
			Actor a = porExaminar.poll();
			if (a2.getNombre().equalsIgnoreCase(a.getNombre())) {
				return true;
			} else {
				this.actualizarListas(a, porExaminar, examinados);
			}
		}
		return false;
	}

	private void actualizarListas(Actor a1, Queue<Actor> porExaminar,
			HashMap<String, Relacion> examinados) {
		a1.cargarColegas();
		Iterator<Entry<String, Relacion>> itr2 = a1.getIteradorColegas();
		while (itr2.hasNext()) {
			Relacion aux = itr2.next().getValue();
			if (!examinados.containsKey(aux.getActual().getNombre())) {
				porExaminar.add(aux.getActual());
				examinados.put(aux.getActual().getNombre(), aux);
			}
		}
		a1.limpiarColegas();
	}

	public Stack<Relacion> estanConectados2(Actor a1, Actor a2,
			Queue<Actor> porExaminar, HashMap<String, Relacion> examinados)
			throws InterruptedException {
		if (estanConectados(a1, a2, porExaminar, examinados)) {
			Stack<Relacion> resultado = new Stack<Relacion>();
			Relacion relacionActual = examinados.get(a2.getNombre());
			while (!relacionActual.getActual().getNombre()
					.equalsIgnoreCase(a1.getNombre())) {
				resultado.add(relacionActual);
				relacionActual = examinados.get(relacionActual.getPadre()
						.getNombre());
			}
			limpiarListas(porExaminar, examinados);
			return resultado;
		}
		limpiarListas(porExaminar, examinados);
		return null;
	}

	public void imprimirPila(Stack<Relacion> resultado) {
		while (!resultado.isEmpty()) {
			Relacion rActual = resultado.pop();
			System.out.println("El actor " + rActual.getActual().getNombre()
					+ " esta relacionado con " + rActual.getPadre().getNombre()
					+ " con la pelicula "
					+ rActual.getPelicularelacion().getNombre());
		}

	}

	public void limpiarListas(Queue<Actor> porExaminar,
			HashMap<String, Relacion> examinados) {
		porExaminar.clear();
		examinados.clear();
	}

	public double gradoRelaciones() {
		Queue<Actor> porExaminar = (Queue<Actor>) new LinkedList<Actor>();
		HashMap<String, Relacion> examinados = new HashMap<String, Relacion>();
		Actor a1 = null;
		Actor a2 = null;
		int numeroPruebas = 50;
		double media = 0.0;
		Stack<Relacion> resultado = new Stack<Relacion>();

		ArrayList<Actor> listaArray = CatalogoListaActores
				.getCatalogoListaActores().cargarArrayList();

		for (int i = 0; i < numeroPruebas - 1; i++) {
			Random rand = new Random();
			int randomNum1 = rand.nextInt((listaArray.size() - 1) + 1) + 0;
			int randomNum2 = rand.nextInt((listaArray.size() - 1) + 1) + 0;
			a1 = listaArray.get(randomNum1);
			a2 = listaArray.get(randomNum2);
			try {
				resultado = estanConectados2(a1, a2, porExaminar, examinados);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			limpiarListas(porExaminar, examinados);

			if (resultado != null) {
				media += resultado.size();
			}
		}	
		return media / numeroPruebas;
	}
}
