package es.espublico.ejercicio.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * POJO People. Se recibe por REST y se guarda en BD
 * @author Manuel León
 * @since 06/02/2023
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class People implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String height;
	private String mass;
	private String hair_color;
	private String skin_color;
	private String eye_color;
	private String birth_year;
	private String gender;
	private String[] films; //Sólo sirve para contener la Url del JSON, no interveine en BD
	private Set<Film> peliculas = new HashSet<Film>();
	private String[] starships; //Sólo sirve para contener la Url del JSON, no interveine en BD
	private Set<Starship> naves = new HashSet<Starship>();
	private Date created;
	private Date edited;
	private String url; //PRIMARY KEY
	
	//GETTERS Y SETTERS

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getMass() {
		return mass;
	}

	public void setMass(String mass) {
		this.mass = mass;
	}

	public String getHair_color() {
		return hair_color;
	}

	public void setHair_color(String hair_color) {
		this.hair_color = hair_color;
	}

	public String getSkin_color() {
		return skin_color;
	}

	public void setSkin_color(String skin_color) {
		this.skin_color = skin_color;
	}

	public String getEye_color() {
		return eye_color;
	}

	public void setEye_color(String eye_color) {
		this.eye_color = eye_color;
	}

	public String getBirth_year() {
		return birth_year;
	}

	public void setBirth_year(String birth_year) {
		this.birth_year = birth_year;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String[] getStarships() {
		return starships;
	}

	public void setStarships(String[] starships) {
		this.starships = starships;
	}

	public Set<Starship> getNaves() {
		return naves;
	}

	public void setNaves(Set<Starship> naves) {
		this.naves = naves;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	//Métodos de sincronización para relaciones many to many (usado en los inserts y updates)
    public void addStarship(Starship starship){
        this.naves.add(starship);
        starship.getPilotos().add(this);
    }

    public void addFilm(Film film){
        this.peliculas.add(film);
        film.getActores().add(this);
    }
	
	//Métodos sobrecargados para poder distinguir por el campo id_people y poder ordenar listas
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		People persona = (People) o;
		return (this.url == persona.getUrl());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.url);
	}

}
