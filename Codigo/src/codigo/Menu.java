package codigo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Menu {

	// Comprueba que el dato introducido es un numero del 0 al num maximo con 3
	// intentos maximos
	public static int pedirNum(int numMax) {
		int eleccion = -1;
		int contadorErrores = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (eleccion < 0 || eleccion > numMax) {
			try {
				System.out.println("Elige una opcion");
				String linea = br.readLine();
				eleccion = Integer.parseInt(linea);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException ex) {
				System.out.println("Debes introducir un numero del 0 al "
						+ numMax);
			}
			contadorErrores++;
			if (contadorErrores == 3) {
				return -1;
			}
		}
		return eleccion;
	}

	// Comprueba que introduce un String
	public static String pedirString() {
		String nombre = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			nombre = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nombre;
	}

	// # 1
	public static void buscarActor() {
		String nombreActor;
		Actor actor1 = null;
		System.out.println("Introduce el nombre del Actor ");
		nombreActor = pedirString();
		actor1 = CatalogoListaActores.getCatalogoListaActores().obtenerActor(
				nombreActor);
		if (actor1 == null) {
			System.out.println("No existe el actor \n");
		} else {
			System.out.println("El actor " + nombreActor
					+ " tiene las siguientes peliculas:");
			actor1.imprimirPeliculas();
		}
	}

	// #2
	public static void anadirActor() {
		String nombreActor;
		Actor actor1 = null;
		System.out.println("Introduce el Actor a anadir: ");
		nombreActor = pedirString();
		// Si el actor a añadir ya existe no hacemos nada
		if (CatalogoListaActores.getCatalogoListaActores().obtenerActor(
				nombreActor) != null) {
			System.out.println("Ya existe el actor");
		} else {
			actor1 = new Actor(nombreActor);
			boolean resp = false;
			String respuesta = null;
			CatalogoListaActores.getCatalogoListaActores().addActor(actor1);
			System.out.println("Actor agregado");

			// Pedir si quiere añadir peliculas
			while (!resp) {
				System.out.println("Quieres agregarle alguna pelicula? S/N");
				respuesta = pedirString();
				if (respuesta.equalsIgnoreCase("S")) {
					agregarPeliculaAlActor(actor1);
				} else if (respuesta.equalsIgnoreCase("N")) {
					resp = true;
				}
			}
		}
	}

	// # 2.1 Cuando anadimos peliculas al actor creado
	private static void agregarPeliculaAlActor(Actor actor1) {
		String nombrePelicula = "";
		Pelicula pelicula1 = null;
		System.out.println("Introduce el titulo de la pelicula ");
		nombrePelicula = pedirString();
		// Comprobamos si existe la pelicula en el catalogo sino habra
		// que crearla
		pelicula1 = CatalogoListaPeliculas.getCatalogoListaPeliculas()
				.obtenerPelicula(nombrePelicula);
		if (pelicula1 == null) {
			// si no existe es una nueva pelicula
			pelicula1 = new Pelicula(nombrePelicula);
			CatalogoListaPeliculas.getCatalogoListaPeliculas().addPelicula(
					pelicula1);
		}
		actor1.addPeliculaActor(pelicula1);
		pelicula1.addActorPelicula(actor1);
	}

	// # 3
	public static void numeroPeliculasActor() {
		String nombreActor = "";
		int numPeliculas = 0;
		Actor actor1 = null;
		System.out.println("Introduce nombre del actor: ");
		nombreActor = pedirString();
		actor1 = CatalogoListaActores.getCatalogoListaActores().obtenerActor(
				nombreActor);
		if (actor1 != null) {
			numPeliculas = actor1.numeroPeliculasActor();
			System.out.println("El actor ha participado en " + numPeliculas);
		} else {
			System.out.println("El actor introducido no existe");
		}
	}

	// #4
	public static void borrarActor() {
		String nombreActor = "";
		Actor actor1 = null;
		System.out.println("Introduce nombre del actor a borrar: ");
		nombreActor = pedirString();
		actor1 = CatalogoListaActores.getCatalogoListaActores().obtenerActor(
				nombreActor);
		if (actor1 != null) {
			CatalogoListaActores.getCatalogoListaActores().borrarActor(actor1);
			System.out.println("Actor borrado");
		} else {
			System.out.println("No existe el actor");
		}
	}

	// # 5
	public static void guardarArchivo() {
		// CatalogoListaActores.getCatalogoListaActores().guardarArchivo();
	}

	// # 6
	public static void obtenerActoresOrd() {
		CatalogoListaActores.getCatalogoListaActores().imprimirOrdenada();
	}

	// # 7
	public static void estanConectados() {
		// introducimos dos actores por consola y nos aseguramos de que los dos
		// existan y no sean iguales
		Actor a1 = pedirActor();
		Actor a2 = pedirActor();
		boolean esta = false;
		if (a1 != null && a2 != null
				&& !a1.getNombre().equalsIgnoreCase(a2.getNombre())) {
			Queue<Actor> porExaminar = (Queue<Actor>) new LinkedList<Actor>();
			HashMap<String, Relacion> examinados = new HashMap<String, Relacion>();
			try {
				esta = CatalogoListaActores.getCatalogoListaActores()
						.estanConectados(a1, a2, porExaminar, examinados);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(esta);
			CatalogoListaActores.getCatalogoListaActores().limpiarListas(
					porExaminar, examinados);
		}

	}

	// # 8
	public static void estanConectados2() {
		Actor a1 = pedirActor();
		Actor a2 = pedirActor();
		Stack<Relacion> resultado = null;
		if (a1 != null && a2 != null
				&& !a1.getNombre().equalsIgnoreCase(a2.getNombre())) {
			Queue<Actor> porExaminar = (Queue<Actor>) new LinkedList<Actor>();
			HashMap<String, Relacion> examinados = new HashMap<String, Relacion>();
			try {
				resultado = CatalogoListaActores.getCatalogoListaActores()
						.estanConectados2(a1, a2, porExaminar, examinados);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (resultado != null) {
				CatalogoListaActores.getCatalogoListaActores().imprimirPila(
						resultado);
			} else {
				System.out.println(a1.getNombre() + " no esta relacionado con "
						+ a2.getNombre());
			}
			CatalogoListaActores.getCatalogoListaActores().limpiarListas(
					porExaminar, examinados);
		}
		;
	}

	// # 9
	public static void gradoRelaciones() {
		Stopwatch timer = new Stopwatch();
		double resultado = CatalogoListaActores.getCatalogoListaActores()
				.gradoRelaciones();
		System.out.println("El grado de relaciones resultante es " + resultado);
		System.out.println("Tiempo de ejecución: " + timer.elapsedTime());
	}

	// # 10
	public static void centralidad() {
		double resultado;
		Queue<Actor> porExaminar = (Queue<Actor>) new LinkedList<Actor>();
		HashMap<String, Relacion> examinados = new HashMap<String, Relacion>();

		Actor a1 = pedirActor();
		Stopwatch timer = new Stopwatch();
		if (a1 != null) {
			resultado = a1.centralidad(porExaminar, examinados);
			System.out.println("La centralidad del actor : " + a1.getNombre()
					+ " es : " + resultado);
		}else{
			System.out.println("No existe el actor introducido.");
		}
		System.out.println("Tiempo de ejecución: " + timer.elapsedTime());

	}

	private static Actor pedirActor() {
		String nombreActor = "";
		System.out.println("Introduce el nombre del Actor ");
		nombreActor = pedirString();
		if (CatalogoListaActores.getCatalogoListaActores().obtenerActor(
				nombreActor) == null) {
			return null;
		} else {
			return CatalogoListaActores.getCatalogoListaActores().obtenerActor(
					nombreActor);
		}
	}

}
