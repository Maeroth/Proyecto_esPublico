package es.espublico.ejercicio.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * POJO para almacenar información de Films (API: swapi.py4e.com/api/films/)
 * @author Manuel León
 * @since 06/02/2023
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Film implements Serializable {

	private static final long serialVersionUID = 1L;

    private String title;
    private int episode_id;
    private String opening_crawl;
    private String director;
    private String producer;
    private Date release_date;
    private Set<People> actores = new HashSet<People>();
    private String[] characters; //Sólo valdrá las urls del JSON. No interviene en SQL
    private Set<Starship> naves = new HashSet<Starship>();
    private String[] starships; //Sólo contiene las urls del JSON. No interviene en SQL
    private Date created;
    private Date edited;
    private String url; //PRIMARY KEY
    
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getEpisode_id() {
		return episode_id;
	}
	public void setEpisode_id(int episode_id) {
		this.episode_id = episode_id;
	}
	public String getOpening_crawl() {
		return opening_crawl;
	}
	public void setOpening_crawl(String opening_crawl) {
		this.opening_crawl = opening_crawl;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public Date getRelease_date() {
		return release_date;
	}
	public void setRelease_date(Date release_date) {
		this.release_date = release_date;
	}
	public Set<People> getActores() {
		return actores;
	}
	public void setActores(Set<People> actores) {
		this.actores = actores;
	}
	public String[] getCharacters() {
		return characters;
	}
	public void setCharacters(String[] characters) {
		this.characters = characters;
	}
	public Set<Starship> getNaves() {
		return naves;
	}
	public void setNaves(Set<Starship> naves) {
		this.naves = naves;
	}
	public String[] getStarships() {
		return starships;
	}
	public void setStarships(String[] starships) {
		this.starships = starships;
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
        starship.getPeliculas().add(this);
    }

    public void addPeople(People people){
        this.actores.add(people);
        people.getPeliculas().add(this);
    }
	
	//Métodos sobrecargados para poder distinguir por el campo id_film y poder ordenar listas
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Film film = (Film) o;
		return this.url == film.getUrl();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.url);
	}
	
    

	

}
