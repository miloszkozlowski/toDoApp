package pl.mihome.toDoApp.model;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
@Table(name = "projects")
public class Projekt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String description;
	
	@JsonIgnore
	@OneToMany(mappedBy = "projekt", cascade = CascadeType.ALL)
	Set<ZadanieGrupa> grupyZadan;
	
	@JsonIgnore
	@OneToMany(mappedBy = "projekt", cascade = CascadeType.ALL)
	Set<ProjektKroki> projektKroki; 
	
	public Projekt() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<ZadanieGrupa> getGrupyZadan() {
		return grupyZadan;
	}

	public void setGrupyZadan(Set<ZadanieGrupa> grupyZadan) {
		this.grupyZadan = grupyZadan;
	}

	public Long getId() {
		return id;
	}

	public List<ProjektKroki> getProjektKroki() {
		return projektKroki.stream()
				.sorted(Comparator.comparingInt(ProjektKroki::getDaysToDeadline))
				.collect(Collectors.toList());
	}

	public void setProjektKroki(Set<ProjektKroki> projektKroki) {
		this.projektKroki = projektKroki;
	}
	
	
	
}
