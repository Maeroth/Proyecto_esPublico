package es.espublico.ejercicio.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import es.espublico.ejercicio.modelo.Venta;
import es.espublico.ejercicio.negocio.GestionCSV;

/**
 * Controlador REST de gestión del proceso CSV
 * @author Manuel León
 * @since 09/02/2023
 *
 */
@RestController
public class GestionCsvController {
	
	@Autowired
	private GestionCSV gestionCsv;
	Logger logger = LoggerFactory.getLogger(GestionCSV.class);

	/**
	 * PROCESADO CSV
	 * 1) Lectura del CSV en la ruta que se haya definido en el fichero de propiedades
	 * 2) Volcado a BD
	 * 3) Escritura del nuevo CSV, ordenando los datos por orderID
	 * @return JSON
	 */
	@RequestMapping(value="/ejercicio1", method=RequestMethod.POST)
	public Object handleAjaxRequest() {
		try {
			List<Venta>listaVentas = gestionCsv.leerFicheroCSV(); //leemos CSV
			gestionCsv.escribirCsvEnBD(listaVentas); //Guardamos en BD
			gestionCsv.escribirFicheroCSV(listaVentas); //escribimos CSV ordenado por orderId
			return new ResponseEntity<>("OK", HttpStatus.OK); //Devolvemos OK
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //devolvemos error
		}
	}
	
	//GETTERS Y SETTERS
	public GestionCSV getGestionCsv() {
		return gestionCsv;
	}

	public void setGestionCsv(GestionCSV gestionCsv) {
		this.gestionCsv = gestionCsv;
	}
}
