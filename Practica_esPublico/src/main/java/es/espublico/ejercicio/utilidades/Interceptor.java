package es.espublico.ejercicio.utilidades;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Clase que intercepta una llamada a un método y actúa como listener
 * En esta práctica se va a utilizar para el medir el tiempo de ejecución de algunas tareas
 * @author Manuel León
 * @since 05/02/2023
 *
 */
public class Interceptor {
	private static final Logger logger = LoggerFactory.getLogger(Interceptor.class);
	 
	 /**
	  * Mide el tiempo de guardado en BD (para listas muy grandes de datos)
	  * @param delegar
	  * @return delegar
	  * @throws Throwable
	  */
	 public Object medirTiempoGuardadoBD(ProceedingJoinPoint delegar) throws Throwable{
		 	logger.info("## INTERCEPTOR (Inicio): Iniciamos lectura del tiempo de inserción en Base de datos por bloques. ");
		 	// se ejecuta antes del metodo
	        long tiempoInicio = System.currentTimeMillis();

	        // pasamos el control al metodo ejecutado y en vuelo
	        // si es de tipo void retorna null.
	        Object obj = delegar.proceed();

	        // se ejecuta despues del metodo
	        long tiempoFin = System.currentTimeMillis();
	        logger.info("## INTERCEPTOR (Fin): La duracion de escritura en BD ha sido:  " +
	                              (tiempoFin - tiempoInicio) + "mseg.");
	        
	        return obj;
	    }
	 
	 /**
	  * Mide el tiempo de lectura de fichero CSV
	  * @param delegar
	  * @return delegar
	  * @throws Throwable
	  */
	 public Object medirTiempoLecturaFicheroCSV(ProceedingJoinPoint delegar) throws Throwable{
		 	logger.info("## INTERCEPTOR (Inicio): Iniciamos lectura fichero CSV. ");
		 	// se ejecuta antes del metodo
	        long tiempoInicio = System.currentTimeMillis();

	        // pasamos el control al metodo ejecutado y en vuelo
	        // si es de tipo void retorna null.
	        Object obj = delegar.proceed();

	        // se ejecuta despues del metodo
	        long tiempoFin = System.currentTimeMillis();
	        logger.info("## INTERCEPTOR (Fin): La duracion de lectura del CSV ha sido:  " +
	                              (tiempoFin - tiempoInicio) + "mseg.");
	        
	        return obj;
	    }
	 
	 /**
	  * Mide el tiempo de escritura a un fichero CSV
	  * @param delegar
	  * @return delegar
	  * @throws Throwable
	  */
	 public Object medirTiempoEscrituraFicheroCSV(ProceedingJoinPoint delegar) throws Throwable{
		 	logger.info("## INTERCEPTOR (Inicio): Iniciamos escritura fichero CSV. ");
		 	// se ejecuta antes del metodo
	        long tiempoInicio = System.currentTimeMillis();

	        // pasamos el control al metodo ejecutado y en vuelo
	        // si es de tipo void retorna null.
	        Object obj = delegar.proceed();

	        // se ejecuta despues del metodo
	        long tiempoFin = System.currentTimeMillis();
	        logger.info("## INTERCEPTOR (Fin): La duracion de escritura del CSV ha sido:  " +
	                              (tiempoFin - tiempoInicio) + "mseg.");
	        
	        return obj;
	    }
}
