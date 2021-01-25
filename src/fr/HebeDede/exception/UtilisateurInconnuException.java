package fr.HebeDede.exception;

public class UtilisateurInconnuException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public UtilisateurInconnuException() {
		System.out.println("L'utilisateur n'existe pas !");
	}
}