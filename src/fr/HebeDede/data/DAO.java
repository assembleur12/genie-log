package fr.HebeDede.data;

import java.sql.Connection;

import fr.HebeDede.exception.UtilisateurInconnuException;

public abstract class DAO<T> {
	protected Connection connect = null;

	public DAO() {
		this.connect = DatabaseConnection.getInstance();
	}

	/**
	 * M�thode de cr�ation
	 * 
	 * @param obj
	 * @return boolean
	 * @throws UtilisateurInconnuException 
	 */
	public abstract void create(T obj) throws UtilisateurInconnuException;

	/**
	 * M�thode pour effacer
	 * 
	 * @param obj
	 * @return boolean
	 */
	public abstract void delete(T obj);

	/**
	 * M�thode de mise � jour
	 * 
	 * @param obj
	 * @return boolean
	 */
	public abstract void update(T obj);

	/**
	 * M�thode de recherche des informations
	 * 
	 * @param id
	 * @return T
	 * @throws UtilisateurInconnuException 
	 */
	public abstract T find(Integer id) throws UtilisateurInconnuException;
	}
