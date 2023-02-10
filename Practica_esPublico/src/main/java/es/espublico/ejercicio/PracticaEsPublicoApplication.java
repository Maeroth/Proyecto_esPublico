package es.espublico.ejercicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath:applicationContext.xml") //Cargamos el fichero de configuración de spring
@SpringBootApplication
public class PracticaEsPublicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticaEsPublicoApplication.class, args);
	}

}
