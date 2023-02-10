package es.espublico.ejercicio.parte2;


import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import es.espublico.ejercicio.modelo.Film;


import es.espublico.ejercicio.negocio.GestionStarWars;

/**
 * Test Unitario que lee los datos de la API Star Wars https://swapi.py4e.com/api y los carga en memoria
 * @author Manuel León
 * @since 06/02/2023
 *
 */
@SpringBootTest
public class LecturaAPIStarWarsTest{
	
	@Autowired
	private GestionStarWars gestionStarWars = new GestionStarWars();
	Logger logger = LoggerFactory.getLogger(LecturaAPIStarWarsTest.class);

		@Test
		public void testLecturaAPIStarWars(){

			try {
				List<Film> listaPeliculas = gestionStarWars.cargarAPIStarWarsEnMemoria(); //Obtenemos los datos de la API STAR WARS
				
				//Vamos a verificar que la película a new Hope está en memoria
				Film f1 = new Film();
				f1.setUrl("https://swapi.py4e.com/api/films/1/"); //A new Hope
				
				boolean resultado = listaPeliculas.stream().filter(o -> o.getUrl().equals(f1.getUrl())).findFirst().isPresent();
				
				 				
				assertTrue(resultado); //Total de registros que deben quedar al final de filtrar los repetidos
				
			} catch (Exception e) {
				
				logger.error(e.getMessage(), e);

			}
		}
}
