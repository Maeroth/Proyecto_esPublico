package es.espublico.ejercicio.persistencia;

import java.util.List;
import es.espublico.ejercicio.modelo.Venta;

/**
 * Interfaz que define los métodos de CSV DAO
 * @author Manuel León
 * @since 04/02/2023
 */
public interface ItfzCsvDAO {
	
	public void altaVentas(List<Venta> listaVentas);

}
