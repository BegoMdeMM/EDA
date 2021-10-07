package codigo;

public class Relacion {
	private Actor padre;
	private Pelicula pelicularelacion;
	private Actor actual;

	public Relacion(Actor padre, Pelicula pelicularelacion, Actor actual) {
		this.padre = padre;
		this.pelicularelacion = pelicularelacion;
		this.actual = actual;
	}

	public Actor getPadre() {
		return padre;
	}

	public void setPadre(Actor padre) {
		this.padre = padre;
	}

	public Pelicula getPelicularelacion() {
		return pelicularelacion;
	}

	public void setPelicularelacion(Pelicula pelicularelacion) {
		this.pelicularelacion = pelicularelacion;
	}

	public Actor getActual() {
		return actual;
	}

	public void setActual(Actor actual) {
		this.actual = actual;
	}

}
