package es.espublico.ejercicio.parte1;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.espublico.ejercicio.modelo.Venta;
import es.espublico.ejercicio.negocio.GestionCSV;

/**
 * Test Unitario que lee el fichero CSV y verifica que hay 900000 resultados, además de que el primer registro sea el que tenga el orderId = 443368995
 * @author Manuel León
 * @since 06/02/2023
 *
 */
@SpringBootTest
public class LecturaCSVTest{
	
	@Autowired
	private GestionCSV gestionCsv = new GestionCSV();
	Logger logger = LoggerFactory.getLogger(LecturaCSVTest.class);

		@Test
		public void testLecturaCSV() throws IOException{

			try {

				List<Venta> listaVenta = gestionCsv.leerFicheroCSV();
				Venta venta = new Venta();
				//
				venta.setOrderId(443368995);
				assertEquals(900000, listaVenta.size()); //Total de registros que deben quedar al final de filtrar los repetidos
				assertEquals(venta, listaVenta.get(0)); //El primero de la lista debe tener el id 443368995 ya que aún no se ha ordenado
			} catch (Exception e) {
				
				logger.error(e.getMessage(), e);

			}
		}

}
