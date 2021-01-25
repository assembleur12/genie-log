package fr.HebeDede.service;

import fr.HebeDede.model.Utilisateur;
import fr.HebeDede.repositories.UtilisateurDAO;
import fr.HebeDede.ui.Console;
import fr.HebeDede.ui.ConsoleUtilisateur;

public class AuthentificationService {
	
	public Boolean login(String username, String password) {
		Utilisateur utilisateur;
		Boolean connected = false;
		utilisateur = ConsoleUtilisateur.userDAO.findByUsername(username);
		if (utilisateur == null) {
			Console.promptLogin();
		}
		connected = utilisateur.getPassword().equals(password);
		return connected;
	}
	
}