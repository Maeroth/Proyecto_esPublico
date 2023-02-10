package es.espublico.ejercicio.persistencia;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import es.espublico.ejercicio.modelo.Film;
import es.espublico.ejercicio.negocio.ResultadoConsultaActorPeliculasAgrupadas;
import es.espublico.ejercicio.negocio.ResultadoConsultaPeliculas;
import es.espublico.ejercicio.negocio.ResultadoPilotoNaveMasApareceEnPeliculas;
import es.espublico.ejercicio.utilidades.Constantes;

/**
 * Clase DAO que nos sirve para el ejercicio 2. Va a insertar los datos en BD y leer de los mismos.
 * @author Manuel León
 * @since 07/02/2023
  */
public class StarWarsDAO extends GeneralDAO<Object> implements ItfzStarWarsDAO{

	/**
	 * Inserta en BD una lista de Entidades Film. Como ya debería traer las relaciones, es un volcado directo a BD de todas las entidades
	 * NOTA: En este caso vamos a hacer saveOrUpdate que, en el caso de que alguna de las entidades haya cambiado en la API, se actualizará 
	 * si ya existiera previamente.
	 * 
	 * @param List<Film> - Lista de Film a insertar
	 * @throws HibernateException
	 */
	@Override
	public void insertarListaFilms(List<Film> listaFilms) throws HibernateException{
		
		Session sesion = super.getSessionFactory().openSession();
		Transaction transaccion = sesion.getTransaction();
		
		try {
			transaccion.begin(); //Abrimos transacción
			
			for(Film film: listaFilms) {
				sesion.saveOrUpdate(film); //Si la entidad existe ya en BD, la actualiza, si no, la inserta
			}
			sesion.flush();
			
			transaccion.commit(); //Si todo va bien, hacemos commit
		}catch(HibernateException e) {
			transaccion.rollback(); //Hacemos rollback si ha ido mal
			throw new HibernateException(Constantes.MSJ_ERROR_INSERT+" " +e.getCause().getMessage(), e); //relanzamos la excepc
		}finally {
			if(sesion != null) {
				sesion.close(); //cerramos la sesión
			}
		}
		
	}
	
	/**
	 * Este método devuelve de forma agrupada las películas que ha hecho un actor.
	 * 
	 * NOTA: He intentado hacerlo con Criteria y HQL pero la cláusula GROUP_CONCAT no la reconoce Hibernate
	 * @throws HibernateExcpetion
	 */
	@Override
	public List<ResultadoConsultaActorPeliculasAgrupadas> consultaActorPeliculasAgrupadas() throws HibernateException {
		List<ResultadoConsultaActorPeliculasAgrupadas> listaResultados = null;
		
		Session session = null;
		try{
			session = super.getSessionFactory().openSession(); //Abrimos sesión
			
			SQLQuery query = session.createSQLQuery(Constantes.QUERY_SQL_ACTOR_NUMPELICULAS_TITULO); //Vamos a montar una SQLQuery - La Cláusula GROUP_CONCAT no la reconoce HQL
			listaResultados = query.setResultTransformer(Transformers.aliasToBean(ResultadoConsultaActorPeliculasAgrupadas.class)) 
									.list(); //Devolvemos una lista transmutando el resultado a una lista de la clase ResultadoConsultaActorPeliculasAgrupadas
			
		}catch(HibernateException e) {

			throw new HibernateException(Constantes.MSJ_ERROR_LEER_BD+" " +e.getCause().getMessage(), e); //relanzamos la excepc
		
		}finally {
			if(session != null) {
				session.close(); //si existe sesión, la cerramos
			}
		}
		
		return listaResultados;
	}

	/**
	 * Devuelve todas las películas en BD
	 * @throws HibernateException
	 */
	@Override
	public List<ResultadoConsultaPeliculas> devolverTodasLasPeliculas() throws HibernateException {
		List<ResultadoConsultaPeliculas> listaResultados = null;
		Session session = null;
		
		try {
			session = super.getSessionFactory().openSession(); //Abrimos sesión
			Criteria criteria = session.createCriteria(Film.class);
			criteria.setProjection(Projections.projectionList()
					.add(Projections.property("url").as("url")) //Queremos el campo url
					.add(Projections.property("title").as("title")) //Queremos el campo title
					)
					.setResultTransformer(Transformers.aliasToBean(ResultadoConsultaPeliculas.class)); //Lo transformamos a la clase que vamos a devolver
			
			listaResultados = criteria.list();
		}catch(HibernateException e) {
			
			throw new HibernateException(Constantes.MSJ_ERROR_LEER_BD+" " +e.getCause().getMessage(), e); //relanzamos la excepc
			
		}finally {
			if(session != null) {
				session.close(); //si existe sesión la cerramos
			}
		}
		return listaResultados;
	}

	/**
	 * Devuelve una única tupla. Ésta será el personaje que ha pilotado la nave que más veces ha aparecido en un conjunto de películas.
	 * La consulta SQL sería así: 
	 * 
	 * SELECT p.name as pName, st.name as stName, COUNT(f.ID)as total FROM people p JOIN people_starships ps ON (p.ID = ps.ID_PEOPLE)
					   JOIN starships st ON (ps.ID_STARSHIP = st.ID)
                       JOIN films_starships fs ON (fs.ID_STARSHIP = st.ID)
                       JOIN films f ON (fs.ID_FILM = f.ID) 
					   WHERE f.ID IN("https://swapi.py4e.com/api/films/1/",
									  "https://swapi.py4e.com/api/films/2/",
									  "https://swapi.py4e.com/api/films/3/",
									  "https://swapi.py4e.com/api/films/4/",
									  "https://swapi.py4e.com/api/films/5/",
									  "https://swapi.py4e.com/api/films/6/",
									  "https://swapi.py4e.com/api/films/7/")
                       GROUP BY pName, stName ORDER BY total DESC, pName ASC LIMIT 1;
	 *
	 * @param List<String> - Lista de url de cada película
	 * @throws HibernateExpception
	 */
	@Override
	public ResultadoPilotoNaveMasApareceEnPeliculas devolverActorNaveMasApareceEnPeliculas(List<String> listaUrlPeliculas) throws HibernateException {
		ResultadoPilotoNaveMasApareceEnPeliculas resultado = null;
		Session session = null;

		try {
			if(listaUrlPeliculas != null && listaUrlPeliculas.size() > 0) { //si la lista viene vacía, devolvemos nulo
				session = super.getSessionFactory().openSession(); //Abrimos sesión
	
				Criteria criteria = session.createCriteria(Film.class, "film"); //Trabajamos sobre Film
				criteria.createAlias("film.naves", "naves"); //relación con starship
				criteria.createAlias("naves.pilotos", "actores"); //relación con people a través de starship (con esto cerramos la relación FILM->STARSHIP->PEOPLE)
				
				ProjectionList projectionList = Projections.projectionList(); //Creamos una nueva proyección 
				projectionList.add(Projections.groupProperty("actores.name").as("personName")); //Agrupamos por el campo name de people
				projectionList.add(Projections.groupProperty("naves.name").as("starshipName")); //Agrupamos por el campo name de starship
				projectionList.add(Projections.count("film.url").as("totalApariciones")); //nueva columna: COUNT(film.url) as totalApariciones, agrupamos por las dos proyecciones anteriores
				criteria.setProjection(projectionList); //Añadimos la proyección a criteria
				
				
				criteria.add(Restrictions.in("film.url", listaUrlPeliculas)); //cláusula WHERE url IN(...)
				
				
				criteria.addOrder(Order.desc("totalApariciones")); //ordenamos por "totalApariciones" de forma descendente
				criteria.addOrder(Order.asc("personName")); //ordenamos por "personName" de forma ascendente
				criteria.setMaxResults(1); //limitamos el resultado a 1
				
				criteria.setResultTransformer(Transformers.aliasToBean(ResultadoPilotoNaveMasApareceEnPeliculas.class)); //Lo transformamos a la clase que vamos a devolver
	
				resultado = (ResultadoPilotoNaveMasApareceEnPeliculas) criteria.uniqueResult();	 //único resultado	
			}
		}catch(HibernateException e) {
			
			throw new HibernateException(Constantes.MSJ_ERROR_LEER_BD+" " +e.getCause().getMessage(), e); //relanzamos la excepción
		}finally {
			if(session != null) {
				session.close(); //cerramos el flujo
			}
		}
		return resultado;
	}
}
