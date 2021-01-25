package fr.HebeDede.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Option implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idOption;

	private Timestamp dateDebutOption;

	private Timestamp dateFinOption;

	private Article article;

	private Utilisateur utilisateur;
	
	public Option(Timestamp début, Timestamp fin, Article art, Utilisateur user, Integer id) {
		this.dateDebutOption = début;
		this.dateFinOption = fin;
		this.utilisateur = user;
		this.article = art;
		this.idOption = id;
	}
	
	public Option(Timestamp début, Timestamp fin, Article art, Utilisateur user) {
		this.dateDebutOption = début;
		this.dateFinOption = fin;
		this.utilisateur = user;
		this.article = art;
	}
	
	public Option() {
	}

	public Integer getIdOption() {
		return idOption;
	}

	public void setIdOption(Integer idOption) {
		this.idOption = idOption;
	}

	public Timestamp getDateDebutOption() {
		return this.dateDebutOption;
	}

	public void setDateDebutOption(Timestamp dateDebutOption) {
		this.dateDebutOption = dateDebutOption;
	}

	public Timestamp getDateFinOption() {
		return this.dateFinOption;
	}

	public void setDateFinOption(Timestamp dateFinOption) {
		this.dateFinOption = dateFinOption;
	}

	public Article getArticle() {
		return this.article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}