package fr.HebeDede.model;

import java.io.Serializable;

public class Bandedessinee extends Article implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idBandeDessinee;

	private String auteur;

	private String categorie;

	private String collection;

	private String description;

	private String editeur;

	private String etat;

	private String libelle;

	private Integer nbrPages;
	
	public Bandedessinee (Boolean enRayon, Float prix, Integer idArticle, String auteur, String cat, String collection, String desc, String editeur, String etat, String libelle, Integer nbPages, Integer id) {
		super(enRayon, prix, idArticle);
		this.auteur = auteur;
		this.categorie = cat;
		this.collection = collection;
		this.description = desc;
		this.editeur = editeur;
		this.etat = etat;
		this.libelle = libelle;
		this.nbrPages = nbPages;
		this.idBandeDessinee = id;
	}
	
	public Bandedessinee (Boolean enRayon, Float prix, String auteur, String cat, String collection, String desc, String editeur, String etat, String libelle, Integer nbPages) {
		super(enRayon, prix);
		this.auteur = auteur;
		this.categorie = cat;
		this.collection = collection;
		this.description = desc;
		this.editeur = editeur;
		this.etat = etat;
		this.libelle = libelle;
		this.nbrPages = nbPages;
	}
	
	public Bandedessinee() {
		super();
	}

	public Integer getIdBandeDessinee() {
		return this.idBandeDessinee;
	}

	public void setIdBandeDessinee(Integer idBandeDessinee) {
		this.idBandeDessinee = idBandeDessinee;
	}

	public String getAuteur() {
		return this.auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public String getCategorie() {
		return this.categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getCollection() {
		return this.collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEditeur() {
		return this.editeur;
	}

	public void setEditeur(String editeur) {
		this.editeur = editeur;
	}

	public String getEtat() {
		return this.etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Integer getNbrPages() {
		return this.nbrPages;
	}

	public void setNbrPages(Integer nbrPages) {
		this.nbrPages = nbrPages;
	}

}