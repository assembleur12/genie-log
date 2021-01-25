package fr.HebeDede.data;

import java.sql.Connection;

import fr.HebeDede.exception.UtilisateurInconnuException;

public abstract class DAO<T> {
	protected Connection connect = null;

	public DAO() {
		this.connect = DatabaseConnection.getInstance();
	}

	/**
	 * Méthode de création
	 * 
	 * @param obj
	 * @return boolean
	 * @throws UtilisateurInconnuException 
	 */
	public abstract void create(T obj) throws UtilisateurInconnuException;

	/**
	 * Méthode pour effacer
	 * 
	 * @param obj
	 * @return boolean
	 */
	public abstract void delete(T obj);

	/**
	 * Méthode de mise à jour
	 * 
	 * @param obj
	 * @return boolean
	 */
	public abstract void update(T obj);

	/**
	 * Méthode de recherche des informations
	 * 
	 * @param id
	 * @return T
	 * @throws UtilisateurInconnuException 
	 */
	public abstract T find(Integer id) throws UtilisateurInconnuException;
	}
