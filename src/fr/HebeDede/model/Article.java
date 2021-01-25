package fr.HebeDede.model;

import java.io.Serializable;


public class Article implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idArticle;

	private Boolean enRayon;

	private Float prix;
	
	public Article(Boolean rayon, Float prix, Integer id) {
		this.enRayon = rayon;
		this.prix = prix;
		this.idArticle = id;
	}
	
	public Article(Boolean rayon, Float prix) {
		this.enRayon = rayon;
		this.prix = prix;
	}
	
	public Article() {
	}

	public Integer getIdArticle() {
		return this.idArticle;
	}

	public void setIdArticle(Integer idArticle) {
		this.idArticle = idArticle;
	}

	public String afficheDispo() {
		if (Boolean.TRUE.equals(this.getdispo())) {
			return "Oui";
		}
		else {
			return "Non";
		}
	}

	public Boolean getdispo() {
		return enRayon;
	}

	public void setDispo(Boolean enRayon) {
		this.enRayon = enRayon;
	}

	public Float getPrix() {
		return this.prix;
	}

	public void setPrix(Float prix) {
		this.prix = prix;
	}
	
}