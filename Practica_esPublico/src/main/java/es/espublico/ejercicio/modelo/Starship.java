package es.espublico.ejercicio.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;


/**
 * POJO Starships. Se recibe por REST y se guarda en BD
 * @author Manuel León
 * @since 06/02/2023
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Starship implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String url; //PK
	private String name;
	private String model;
	private String manufacturer;
	private String cost_in_credits;
	private String length;
	private String max_atmosphering_speed;
	private String crew;
	private String passengers;
	private String cargo_capacity;
	private String consumables;
	private String hyperdrive_rating;
	private String MGLT;
	private String starship_class;
	private String[] pilots; //Sólo se usa para la url del JSON, no en BD
	private Set<People> pilotos = new HashSet<People>();
	private String[] films; //Sólo se usa para la url del JSON, no en BD
	private Set<Film> peliculas = new HashSet<Film>();
	private Date created;
	private Date edited;
	
	
	//GETTERS Y SETTERS
	
	//Métodos de sincronización para relaciones many to many (usado en los inserts y updates)
    public void addPeople(People people){
        this.pilotos.add(people);
        people.getNaves().add(this);
    }

    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getCost_in_credits() {
		return cost_in_credits;
	}

	public void setCost_in_credits(String cost_in_credits) {
		this.cost_in_credits = cost_in_credits;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getMax_atmosphering_speed() {
		return max_atmosphering_speed;
	}

	public void setMax_atmosphering_speed(String max_atmosphering_speed) {
		this.max_atmosphering_speed = max_atmosphering_speed;
	}

	public String getCrew() {
		return crew;
	}

	public void setCrew(String crew) {
		this.crew = crew;
	}

	public String getPassengers() {
		return passengers;
	}

	public void setPassengers(String passengers) {
		this.passengers = passengers;
	}

	public String getCargo_capacity() {
		return cargo_capacity;
	}

	public void setCargo_capacity(String cargo_capacity) {
		this.cargo_capacity = cargo_capacity;
	}

	public String getConsumables() {
		return consumables;
	}

	public void setConsumables(String consumables) {
		this.consumables = consumables;
	}

	public String getHyperdrive_rating() {
		return hyperdrive_rating;
	}

	public void setHyperdrive_rating(String hyperdrive_rating) {
		this.hyperdrive_rating = hyperdrive_rating;
	}

	public String getMGLT() {
		return MGLT;
	}

	//ESTE SET lo mapeamos para que recoja el campo en mayúsculas
	@JsonSetter("MGLT")
	public void setMGLT(String MGLT) {
		this.MGLT = MGLT;
	}

	public String getStarship_class() {
		return starship_class;
	}

	public void setStarship_class(String starship_class) {
		this.starship_class = starship_class;
	}

	public String[] getPilots() {
		return pilots;
	}

	public void setPilots(String[] pilots) {
		this.pilots = pilots;
	}

	public Set<People> getPilotos() {
		return pilotos;
	}

	public void setPilotos(Set<People> pilotos) {
		this.pilotos = pilotos;
	}

	public String[] getFilms() {
		return films;
	}

	public void setFilms(String[] films) {
		this.films = films;
	}

	public Set<Film> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(Set<Film> peliculas) {
		this.peliculas = peliculas;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getEdited() {
		return edited;
	}

	public void setEdited(Date edited) {
		this.edited = edited;
	}

	public void addFilm(Film film){
        this.peliculas.add(film);
        film.getNaves().add(this);
    }
	
	//Métodos sobrecargados para poder distinguir por el campo id_starship y poder ordenar listas
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Starship nave = (Starship) o;
		return this.url == nave.getUrl();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.url);
	}


}
