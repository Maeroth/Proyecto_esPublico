package es.espublico.ejercicio.negocio;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.springframework.web.client.RestTemplate;
import es.espublico.ejercicio.modelo.Film;
import es.espublico.ejercicio.modelo.People;
import es.espublico.ejercicio.modelo.Starship;
import es.espublico.ejercicio.persistencia.ItfzStarWarsDAO;
import es.espublico.ejercicio.utilidades.Constantes;

/**
 * Clase de gestión de los datos obtenidos de https://swapi.py4e.com/api/
 * @author Manuel León
 * @since 07/02/2023
 *
 */
public class GestionStarWars implements ItfzGestionStarWars{
	RestTemplate restTemplate;
	private ItfzStarWarsDAO starWarsDao;
	
	private List<Film> listaFilms = new ArrayList<Film>();
	private List<People> listaPeople = new ArrayList<People>();
	private List<Starship> listaStarship = new ArrayList<Starship>();
	
	
	/**
	 * Método que lee de la api "https://swapi.py4e.com/api/" los datos a cargar en la aplicación
	 * Una vez leídos los datos de la API, efectúa las relaciones entidad-entidad en memoria
	 * @return List<Film>
	 */
	@Override
	public List<Film> cargarAPIStarWarsEnMemoria() {
		//Limpiamos la memoria de las listas (por si se hubiera llamado previamente a la carga de datos)
		listaFilms.clear();
		listaPeople.clear();
		listaStarship.clear();
		
		//Lee de la API y carga en memoria las entidades
		leerFilms();
		leerPeople();
		leerStarships();
		//Efectúa las relaciones de las entidades en memoria
		relacionarPeopleConStarship();
		relacionarStarhipConPeople(); //Esto es redundante ya que lo hace relacionarPeopleConStarships, pero lo hacemos por mantener el orden (por si hubiera una relación definida en starships que no estuviera en people) 
		relacionarFilmsConStarshipYPeople();
		//Devuelve la lista de films que ya tiene todo relacionado en memoria y listo para ser volcado a BD
		return listaFilms;
		
	}

	/**
	 * Inserta en BD la entidad Film y todas sus dependencias
	 * @throws HibernateException
	 */
	@Override
	public void insertarFilmsEnBD(List<Film> listaFilms) throws HibernateException{
		starWarsDao.insertarListaFilms(listaFilms);
		
	}
	
	/**
	 * Consulta las películas agrupadas por actores y los devuelve en una lista
	 * @return List<ResultadoConsultaActorPeliculasAgrupadas>
	 * @throws HibernateException
	 */
	@Override
	public List<ResultadoConsultaActorPeliculasAgrupadas> consultarActoresYSusPeliculas() throws HibernateException{
		return starWarsDao.consultaActorPeliculasAgrupadas();
	}
	
	/**
	 * Devuelve todas las películas registradas
	 * @return List<ResultadoConsultaPeliculas>
	 * @throws HibernateException
	 */
	@Override
	public List<ResultadoConsultaPeliculas> devolverPeliculas() throws HibernateException {
		return starWarsDao.devolverTodasLasPeliculas();
	}
	
	/**
	 * Devuelve el actor que pilota la nave que más aparece en un conjunto de películas
	 * @param listaUrlPeliculas - Lista de url de cada película
	 * @return List<String>
	 * @throws HibernateException
	 */
	@Override
	public ResultadoPilotoNaveMasApareceEnPeliculas devolverActorNaveMasApareceEnPeliculas(List<String> listaUrlPeliculas)
			throws HibernateException {
		
		return starWarsDao.devolverActorNaveMasApareceEnPeliculas(listaUrlPeliculas);

	}
	
	//MÉTODOS PRIVADOS:
	
	/**
	 * Lee los datos de la entidad FILM de la API y los almacena en memoria
	 */
	private void leerFilms(){
		EntityManagerFilms managerFilms = new EntityManagerFilms();
		//Nos traemos los datos de todas las films
		managerFilms = restTemplate.getForObject(Constantes.API_STAR_WARS_FILM, EntityManagerFilms.class);
		
		while(managerFilms.getNext() != null) {//Mientras que siga habiendo páginas que tramitar, seguimos leyendo
			//Empujamos las films actuales al contenedor general del ManagerFilm
			listaFilms.addAll(managerFilms.getResults());
			//Leemos la siguiente página
			managerFilms = restTemplate.getForObject(managerFilms.getNext(), EntityManagerFilms.class);
		}
		//Finalmente, añadimos las últimas leídas
		listaFilms.addAll(managerFilms.getResults());
	}

	/**
	 * Lee los datos de la entidad PEOPLE de la API y los almacena en memoria
	 */
	private void leerPeople(){
		EntityManagerPeople managerPeople = new EntityManagerPeople();
		//Nos traemos los datos de todas las people
		managerPeople = restTemplate.getForObject(Constantes.API_STAR_WARS_PEOPLE, EntityManagerPeople.class);
		
		while(managerPeople.getNext() != null) {//Mientras que siga habiendo páginas que tramitar, seguimos leyendo
			//Empujamos las films actuales al contenedor general del ManagerPeople
			listaPeople.addAll(managerPeople.getResults());
			//Leemos la siguiente página
			managerPeople = restTemplate.getForObject(managerPeople.getNext(), EntityManagerPeople.class);
		}
		//Finalmente, añadimos las últimas leídas
		listaPeople.addAll(managerPeople.getResults());
		
	}
	
	/**
	 * Lee los datos de la entidad STARSHIPS de la API y los almacena en memoria
	 */
	private void leerStarships(){
		EntityManagerStarships managerStarhip = new EntityManagerStarships();
		//Nos traemos los datos de todas las starships
		managerStarhip = restTemplate.getForObject(Constantes.API_STAR_WARS_STARSHIP, EntityManagerStarships.class);
		
		while(managerStarhip.getNext() != null) {//Mientras que siga habiendo páginas que tramitar, seguimos leyendo
			//Empujamos las films actuales al contenedor general del ManagerStarship
			listaStarship.addAll(managerStarhip.getResults());
			//Leemos la siguiente página
			managerStarhip = restTemplate.getForObject(managerStarhip.getNext(), EntityManagerStarships.class);
		}
		//Finalmente, añadimos las últimas leídas
		listaStarship.addAll(managerStarhip.getResults());
		
	}
	
	
	/**
	 * Relaciona la entidad Film, la entidad Starship y la entidad People con sus métodos de sincronización
	 */
	private void relacionarFilmsConStarshipYPeople() {
		for(Film film : listaFilms) {
			for(String urlPersona : film.getCharacters()) {
				film.addPeople(buscarPersona(urlPersona));
			}
			for(String urlStarship : film.getStarships()) {
				film.addStarship(buscarNave(urlStarship));
			}
		}
	}
	
	/**
	 * Relaciona la entidad People y Starship con sus métodos de sincronización
	 */
	private void relacionarPeopleConStarship() {
		for(People person : listaPeople) {
			for(String urlStarship : person.getStarships()) {
				person.addStarship(buscarNave(urlStarship));
			}
		}
	}
	
	/**
	 * Relaciona la entidad Starhip y People con sus métodos de sincronización
	 */
	private void relacionarStarhipConPeople() {
		for(Starship nave : listaStarship) {
			for(String urlPilot : nave.getPilots()) {
				nave.addPeople(buscarPersona(urlPilot));
			}
		}
	}
	
	/**
	 * Dado un código url, busca en memoria la entidad persona con ese url
	 * @param String url - Url de la persona
	 * @return People
	 */
	private People buscarPersona(String url) {
		People person = null;
		for(People personAux : listaPeople){
			if(personAux.getUrl().equals(url)) {
				person = personAux;
			}
		}
		return person;
	}
	
	/**
	 * Dado un código url, busca en memoria la entidad starship con ese url
	 * @param String url - Url de la nave
	 * @return Starship
	 */
	private Starship buscarNave(String url) {
		Starship starship = null;
		for(Starship starshipAux : listaStarship){
			if(starshipAux.getUrl().equals(url)) {
				starship = starshipAux;
			}
		}
		return starship;
	}

	//GETTERS Y SETTERS
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public ItfzStarWarsDAO getStarWarsDao() {
		return starWarsDao;
	}

	public void setStarWarsDao(ItfzStarWarsDAO starWarsDao) {
		this.starWarsDao = starWarsDao;
	}

	

}
