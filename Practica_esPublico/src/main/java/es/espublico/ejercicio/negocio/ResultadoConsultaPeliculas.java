package es.espublico.ejercicio.negocio;

import java.io.Serializable;

/**
 * Clase específica para la consulta del número de películas
 * @Author Manuel León
 * since 07/02/2023
 */
public class ResultadoConsultaPeliculas implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String url;
	private String title;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	
}
