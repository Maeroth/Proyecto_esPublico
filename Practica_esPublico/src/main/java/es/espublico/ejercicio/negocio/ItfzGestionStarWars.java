package es.espublico.ejercicio.negocio;

import java.util.List;

import org.hibernate.HibernateException;

import es.espublico.ejercicio.modelo.Film;

/**
 * Interfaz que define los métodos necesarios para trabajar con la API STAR WARS
 * @author Manuel León
 * @since 04/02/2023
 *
 */
public interface ItfzGestionStarWars {

	public List<Film> cargarAPIStarWarsEnMemoria();
	public void insertarFilmsEnBD(List<Film> listaFilms) throws HibernateException;
	public List<ResultadoConsultaActorPeliculasAgrupadas> consultarActoresYSusPeliculas() throws HibernateException;
	public List<ResultadoConsultaPeliculas> devolverPeliculas() throws HibernateException;
	public ResultadoPilotoNaveMasApareceEnPeliculas devolverActorNaveMasApareceEnPeliculas(List<String> listaUrlPeliculas) throws HibernateException;
}
