package es.espublico.ejercicio.negocio;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import es.espublico.ejercicio.modelo.Venta;
import es.espublico.ejercicio.persistencia.ItfzCsvDAO;


/**
 * Clase que gestiona la lectura / escritura del fichero CSV y el guardado a base de datos
 * @author Manuel León
 * @since 04/02/2023
 *
 */
public class GestionCSV implements ItfzGestionCSV{

	private ItfzCsvDAO csvDao;
	
	@Value("${fichero.rutaLecturaCSV}")
	private String rutaLecturaCSV;
	@Value("${fichero.rutaEscrituraCSV}")
	private String rutaEscrituraCSV;
	 
	//MÉTODOS PÚBLICOS

	/**
	 * Escribe los datos de la lista pasada por parámetros en BD. Van a ser muchos así que se incorpora un interceptor (LISTENING) para que mida el rendimiento en tiempo podamos hacer una configuración adecuada para este caso
	 * en el fichero spring-properties.properties en el parámetro datasource.lotes 
	 * @param listaVentas
	 * @throws IOException -> Si hay algún tipo de error con el fichero, salta esta excepción
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void escribirCsvEnBD(List<Venta> listaVentas) {
		csvDao.altaVentas(listaVentas);
	}
	
	/**
	 * Lee los datos del fichero CSV, filtra los repetidos y los devuelve en una lista
	 * Se coloca un interceptor (listening) para medir el tiempo y el rendimiento de la lectura y la ordenación
	 *
	 * @throws IOException
	 */
	public List<Venta> leerFicheroCSV() throws IOException{
		CSVReader reader = null;

		List<Venta> listaVentas = null; //Lista no ordenada de lectura de fichero (es meramente auxiliar)
		List<Venta> listaSalida = new ArrayList<Venta>();  //Lista ordenada con los datos que será devuelta por el método

		try {
			reader = new CSVReader(new FileReader(rutaLecturaCSV));
			
			//Procesamos el fichero CSV y lo guardamos en una lista de objetos Venta: 
			CsvToBean<Venta> csvToBean = new CsvToBeanBuilder(reader)
					.withType(Venta.class)
					//.withSkipLines(100) //hace caso omiso de lo que le ponga aquí, así que lo dejo comentado. La idea era que leyera por lotes para el rendimiento en máquinas más modestas
					.build();
			
			//A continuación volcamos lo que vamos obteniendo a la lista de salida. He tenido que poner un bucle while, porque la primera lectura la hace sobre la cabecera, la segunda es sobre los datos
			while((listaVentas = csvToBean.parse()) != null && listaVentas.size() > 0) {
				listaSalida.addAll(listaVentas.stream().distinct().collect(Collectors.toList())); //Eliminamos los repetidos de la lectura del fichero y los vamos montando en la lista de salida
			}
		}catch(IOException e) {
			throw e; //relanzamos la excepción
		}finally {
			if(reader != null) reader.close(); //cerramos el flujo
		}
		
		return listaSalida;
	}

	/**
	 * Escribe un fichero CSV con los datos de la lista listaVentas, previamente los ordena por el campo order_id
	 * @param listaVentas -> Lista de ventas sin ordenar
	 * @throws IOException -> Si la escritura explota, lo sabremos por esta excepción
	 */
	@Override
	public void escribirFicheroCSV(List<Venta> listaVentas) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException{
		
		//Antes de nada ordenamos la lista de entrada por el campo order_id
		listaVentas = listaVentas.stream()
				.sorted(Comparator.comparingLong(Venta::getOrderId)) //Ordenamos por OrderId
				.collect(Collectors.toList()); //Lo convierte en una lista
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(rutaEscrituraCSV); //Abrimos flujo de escritura hacia el fichero definido en Constantes.RUTA_FICHERO
			
			StatefulBeanToCsv<Venta> conversor = new StatefulBeanToCsvBuilder<Venta>(writer)
					.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER) //Toma en cuenta el caracter de las comillas
					.withSeparator(CSVWriter.DEFAULT_SEPARATOR) //La ',' de toda la vida en los csv
					.withOrderedResults(false) //ya le hemos ordenado la lista, así que no vuelva a hacerlo
					.build();
			
			conversor.write(listaVentas);
		}catch(IOException e){
			throw e; //relanzamos la excepción
		}finally {
			if(writer != null) writer.close(); //cerramos el flujo	
		}
					
	}

	//Getters y Setters
	public ItfzCsvDAO getCsvDao() {
		return csvDao;
	}

	public void setCsvDao(ItfzCsvDAO csvDao) {
		this.csvDao = csvDao;
	}

}
