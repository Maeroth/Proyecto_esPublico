package es.espublico.ejercicio.persistencia;

import java.util.List;
import org.hibernate.HibernateException;
import es.espublico.ejercicio.modelo.Film;
import es.espublico.ejercicio.negocio.ResultadoConsultaActorPeliculasAgrupadas;
import es.espublico.ejercicio.negocio.ResultadoConsultaPeliculas;
import es.espublico.ejercicio.negocio.ResultadoPilotoNaveMasApareceEnPeliculas;

public interface ItfzStarWarsDAO {

	public void insertarListaFilms(List<Film> listaFilms) throws HibernateException;
	
	public List<ResultadoConsultaActorPeliculasAgrupadas> consultaActorPeliculasAgrupadas() throws HibernateException;
	
	public List<ResultadoConsultaPeliculas> devolverTodasLasPeliculas() throws HibernateException;
	
	public ResultadoPilotoNaveMasApareceEnPeliculas devolverActorNaveMasApareceEnPeliculas(List<String> listaUrlPeliculas) throws HibernateException;
}
