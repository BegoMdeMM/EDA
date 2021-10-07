package codigo;

public class Prueba {

	// # 1
	public static void listarActores() {
		CatalogoListaActores.getCatalogoListaActores().imprimirActores();
	}

	// # 2
	public static void listarPeliculas() {
		CatalogoListaPeliculas.getCatalogoListaPeliculas().imprimirPeliculas();
	}

	// # 3
	public static void buscarPelicula() {
		System.out.println("Introduce el titulo de la pelicula ");
		String nombrePelicula = Menu.pedirString();
		Pelicula peli1 = CatalogoListaPeliculas.getCatalogoListaPeliculas()
				.obtenerPelicula(nombrePelicula);
		if (peli1 == null) {
			System.out.println("No existe la pelicula \n");
			GestionMenu.mostrarMenu();
		} else {
			System.out.print("La Pelicula " + nombrePelicula
					+ " tiene los siguientes actores: \n");
			peli1.imprimirActoresPelicula();
		}
	}

	// # 4
	public static void cargarcolegas() {
		String nombreActor;
		Actor actor1 = null;
		System.out.println("Introduce el nombre del Actor ");
		nombreActor = Menu.pedirString();
		actor1 = CatalogoListaActores.getCatalogoListaActores().obtenerActor(
				nombreActor);
		if (actor1 == null) {
			System.out.println("No existe el actor \n");
		} else {
			System.out.println("El actor " + nombreActor
					+ " tiene los siguientes colegas:");
			actor1.cargarColegas();
			actor1.imprimirColegas();
		}
	}
}