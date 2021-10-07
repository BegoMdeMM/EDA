package codigo;

public class GestionMenu {
	public static void mostrarMenu() {

		int eleccion = 0;

		System.out.println();
		System.out.println();
		System.out.println("<==%---------PRINCIPAL---------%==>");
		System.out.println("<        1 Buscar actor           >");
		System.out.println("<        2 Anadir actor           >");
		System.out.println("<        3 Numero peliculas actor >");
		System.out.println("<        4 Borrar actor           >");
		System.out.println("<        5 Guardar en fichero     >");
		System.out.println("<        6 Obtener actores ord    >");
		System.out.println("<        7 EstanConectados        >");
		System.out.println("<        8 EstanConectados2       >");
		System.out.println("<        9 GradoRelaciones        >");
		System.out.println("<        10 Centralidad           >");
		System.out.println("<        11 Pruebas               >");
		System.out.println("<        0 Salir                  >");
		System.out.println("<==%---------------------------%==>\n");

		eleccion = Menu.pedirNum(11);

		if (eleccion != -1) {

			switch (eleccion) {
			case 1:
				Menu.buscarActor();
				break;

			case 2:
				Menu.anadirActor();
				break;

			case 3:
				Menu.numeroPeliculasActor();
				break;

			case 4:
				Menu.borrarActor();
				break;

			case 5:
				Menu.guardarArchivo();
				break;

			case 6:
				Menu.obtenerActoresOrd();
				break;

			case 7:
				Menu.estanConectados();
				break;
				
			case 8:
				Menu.estanConectados2();
				break;
				
			case 9:
				Menu.gradoRelaciones();
				break;
				
			case 10:
				Menu.centralidad();
				break;
			case 11:
				pruebas();
				break;
			case 0:
				// Exit
				System.out.println("Fin de la Aplicacion");
				System.exit(0);
				break;
			}
			mostrarMenu();

		} else {
			System.out.println("Programa Finalizado");
		}

	}

	private static void pruebas() {
		System.out.println();
		System.out.println("<==%----------PRUEBAS----------%==>");
		System.out.println("<        1 Listar Actores         >");
		System.out.println("<        2 Listar Peliculas       >");
		System.out.println("<        3 Buscar Pelicula        >");
		System.out.println("<        4 Cargar Colegas         >");
		System.out.println("<        5 Atras                  >");
		System.out.println("<==%---------------------------%==>");

		int resp = -1;

		resp = Menu.pedirNum(5);

		if (resp != -1) {
			switch (resp) {
			case 1:
				Prueba.listarActores();
				break;
			case 2:
				Prueba.listarPeliculas();
				break;
			case 3:
				Prueba.buscarPelicula();
				break;
			case 4:
				Prueba.cargarcolegas();
				break;
			case 5:
				mostrarMenu();
				break;
			default:
				break;
			}
		}

	}

}
