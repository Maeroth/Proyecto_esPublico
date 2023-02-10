package es.espublico.ejercicio.negocio;

import java.io.IOException;
import java.util.List;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import es.espublico.ejercicio.modelo.Venta;

/**
 * Interfaz que define los métodos necesarios para trabajar con el fichero CSV
 * @author Manuel León
 * @since 04/02/2023
 *
 */
public interface ItfzGestionCSV {
	
	public void escribirCsvEnBD(List<Venta> listaVentas);
	public List<Venta> leerFicheroCSV() throws IOException ;
	public void escribirFicheroCSV(List<Venta> listaVentas) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;
}
