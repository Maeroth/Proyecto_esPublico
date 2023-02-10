package es.espublico.ejercicio.utilidades;

/**
 * Clase de constantes.
 * 
 * @author Manuel León
 * @since 04/02/2023
 */
public class Constantes {
	//CONSTANTES PARA PROCESOS
	public static final int BUFFER_LINEAS = 100;
	// Constantes para las consultas BBDD
	public static final String QUERY_SELECT_ALL_GENERIC = "SELECT T FROM T";
	public static final String QUERY_SQL_ACTOR_NUMPELICULAS_TITULO = "select t1.name as name, COUNT(t1.title) as numTitles, GROUP_Concat(t1.title) as titles "+
																		"from (select pAux.name, f.title from people pAux join films_people fp on (pAux.Id = fp.ID_PEOPLE) join "+
																		"films f on (f.id = fp.id_film)) t1 GROUP BY t1.name order by t1.name ASC";
	
	//Mensajes de la aplicación:
	public static final String MSJ_ERROR_LEER_BD = "Error al leer los registros de la base de datos.";
	public static final String MSJ_ERROR_INSERT = "Error al insertar un registro en base de datos.";

	//ENDPOINTS
	public static final String API_STAR_WARS_FILM ="https://swapi.py4e.com/api/films?format=json";
	public static final String API_STAR_WARS_PEOPLE = "https://swapi.py4e.com/api/people?format=json";
	public static final String API_STAR_WARS_STARSHIP = "https://swapi.py4e.com/api/starships?format=json";
}
