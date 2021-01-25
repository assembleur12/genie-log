package fr.HebeDede.model;

import java.io.Serializable;

public class Figurine extends Article implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idFigurine;

	private String description;

	private Integer taille;
	
	public Figurine (Boolean enRayon, Float prix, Integer idArticle, String desc, Integer taille, Integer id) {
		super(enRayon, prix, idArticle);
		this.description = desc;
		this.taille = taille;
		this.idFigurine = id;
	}
	
	public Figurine (Boolean enRayon, Float prix, String desc, Integer taille) {
		super(enRayon, prix);
		this.description = desc;
		this.taille = taille;
	}
	
	public Figurine(){
	}

	public Integer getIdFigurine() {
		return this.idFigurine;
	}

	public void setIdFigurine(Integer idFigurine) {
		this.idFigurine = idFigurine;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTaille() {
		return this.taille;
	}

	public void setTaille(Integer taille) {
		this.taille = taille;
	}

}