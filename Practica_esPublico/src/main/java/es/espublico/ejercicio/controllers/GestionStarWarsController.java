package es.espublico.ejercicio.controllers;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import es.espublico.ejercicio.modelo.Film;
import es.espublico.ejercicio.negocio.GestionCSV;
import es.espublico.ejercicio.negocio.GestionStarWars;
import es.espublico.ejercicio.negocio.ResultadoConsultaActorPeliculasAgrupadas;
import es.espublico.ejercicio.negocio.ResultadoConsultaPeliculas;
import es.espublico.ejercicio.negocio.ResultadoPilotoNaveMasApareceEnPeliculas;

/**
 * Controlador REST de gestión del proceso de lectura api STAR WARS
 * @author Manuel León
 * @since 09/02/2023
 *
 */
@RestController
public class GestionStarWarsController {
	
	@Autowired
	private GestionStarWars gestionStarWars;
	Logger logger = LoggerFactory.getLogger(GestionCSV.class);
	/**
	 * PROCESADO API STAR WARS
	 * 1) Carga de datos de la API en memoria
	 * 2) Volcado a BD
	 * 3) Obtenemos la lista de los actores y sus películas y la devolvemos
	 * @return JSON - List<ResultadoConsultaActorPeliculasAgrupadas>
	 */
	@RequestMapping(value="/ejercicio2_devolverActores", method=RequestMethod.POST)
	public Object handleAjaxRequestActors() {
		List<ResultadoConsultaActorPeliculasAgrupadas> listaActores = null;
		try {
			List<Film> listaPeliculas = gestionStarWars.cargarAPIStarWarsEnMemoria(); //Obtenemos los datos de la API STAR WARS
			gestionStarWars.insertarFilmsEnBD(listaPeliculas); //Volcamos en BD los datos
			listaActores = gestionStarWars.consultarActoresYSusPeliculas(); //Obtenemos la lista con los actores y sus películas
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //devolvemos el error

		}
		
		return listaActores;
	}


	/**
	 * CONSULTA DE LAS PELÍCULAS QUE HAY EN BD
	 * Obtenemos el url y el título de las películas que hay en BD
	 * @return JSON - List<ResultadoConsultaPeliculas>
	 */
	@RequestMapping(value="/ejercicio2_devolverPeliculas", method=RequestMethod.POST)
	public Object handleAjaxRequestFilm() {
		List<ResultadoConsultaPeliculas> listaPeliculas = null;
		try {
			listaPeliculas = gestionStarWars.devolverPeliculas(); //Nos traemos la lista con los resultados de las películas
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //devolvemos el error
		}
		
		return listaPeliculas;
	}
	
	/**
	 * CONSULTA DEL ACTOR QUE HA PILOTADO LA NAVE QUE MÁS APARECE EN UN CONJUNTO DE PELÍCULAS
	 * Obtenemos el nombre del actor, el nombre de la nave y el número de veces que aparece esa nave sobre un conjunto de películas seleccionadas
	 * @param listaUrlPeliculas - Lista con las url de las películas seleccionadas
	 * @return JSON - List<handleAjaxRequestPilotoNavesGrupoPeliculas>
	 */
	@RequestMapping(value="/ejercicio2_devolverPilotoNavesGrupoPeliculas", method=RequestMethod.POST)
	public Object handleAjaxRequestPilotoNavesGrupoPeliculas(@RequestBody List<String> listaUrlPeliculas) {
		ResultadoPilotoNaveMasApareceEnPeliculas resultado = null;
		try {
			resultado = gestionStarWars.devolverActorNaveMasApareceEnPeliculas(listaUrlPeliculas); //Nos traemos los resultados de la consulta
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //lanzamos el error
		}
		
		return resultado;
	}
	
	//GETTERS Y SETTERS
	public GestionStarWars getGestionStarWars() {
		return gestionStarWars;
	}

	public void setGestionStarWars(GestionStarWars gestionStarWars) {
		this.gestionStarWars = gestionStarWars;
	}

}
