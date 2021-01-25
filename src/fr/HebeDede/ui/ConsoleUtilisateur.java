package fr.HebeDede.ui;

import java.util.ArrayList;
import java.util.List;

import fr.HebeDede.model.Utilisateur;
import fr.HebeDede.repositories.UtilisateurDAO;
import fr.HebeDede.service.ConsoleService;

public class ConsoleUtilisateur {
	
	public static UtilisateurDAO userDAO = new UtilisateurDAO();

	public static void promptConsulterUtilisateur(Utilisateur user) {
		ConsoleService.affiche("\n******* Mon compte utilisateur *******\n");
		ConsoleService.affiche("Utilisateur n°" + user.getIdUtilisateur() + 
				" - Username : " + user.getUsername() + 
				" - " + user.getRole());
		
		ConsoleService.affiche("\nMenu :");
		ConsoleService.affiche("1. Modifier compte");
		ConsoleService.affiche("2. Supprimer compte");
		
		ConsoleService.affiche("0. Retour au menu principal");
		
		Integer choice = ConsoleService.choixMenuMinMax(0,2);
		
		switch (choice) {
	    	case 1:
	    		promptModifierUtilisateur(Console.user);
	    	case 2:
	    		promptSupprimerUtilisateur(Console.user);
	    	case 0:
	    		Console.promptMenu();
		}
	}

	public static void promptListUtilisateurs() {
		ConsoleService.affiche("\n******* Liste des utilisateurs *******\n");
		List<Utilisateur> userList = userDAO.findAll();
		List<Integer> idList = new ArrayList<Integer>();
		for (Utilisateur utilisateur : userList) {
			ConsoleService.affiche("Utilisateur n°" + utilisateur.getIdUtilisateur() + " - Username : " + utilisateur.getUsername() + " - " + utilisateur.getRole());
			idList.add(utilisateur.getIdUtilisateur());
		}
		
		ConsoleService.affiche("\nMenu :");
		ConsoleService.affiche("1. Ajouter un compte");
		ConsoleService.affiche("2. Modifier un compte");
		ConsoleService.affiche("3. Supprimer un compte");
		
		ConsoleService.affiche("0. Retour au menu principal");
		
		Integer choice = ConsoleService.choixMenuMinMax(0,3);
	    
		Integer numUser = 0;
		Utilisateur utilisateur = new Utilisateur();
	    switch (choice) {
	    	case 1:
	    		promptCreerUtilisateur();
	    	case 2:
	    		ConsoleService.affiche("Renseigner le numéro de l'utilisateur à modifier : ");
	    		numUser = ConsoleService.choixMenuIntList(idList);
					utilisateur = userDAO.find(numUser);
					promptModifierUtilisateur(utilisateur);
		case 3:
	    		ConsoleService.affiche("Renseigner le numéro de l'utilisateur à supprimer : ");
	    		numUser = ConsoleService.choixMenuIntList(idList);
					utilisateur = userDAO.find(numUser);
					promptSupprimerUtilisateur(utilisateur);
		case 0:
	    		Console.promptMenu();
	    }
	    
	}

	public static void promptCreerUtilisateur() {
		ConsoleService.affiche("\n******* Création compte *******\n");
		ConsoleService.affiche("Renseigner un nom d'utilisateur :");
		String username = Console.sc.nextLine();
		String password = "";
		String password2 = "";
		do {
			ConsoleService.affiche("Renseigner un mot de passe :");
			password = Console.sc.nextLine();
			ConsoleService.affiche("Confirmer le mot de passe :");
			password2 = Console.sc.nextLine();
			if (!password.equals(password2)) {
				ConsoleService.affiche("Les mots de passe ne correspondent pas !");
			}
		} while (!password.equals(password2));
		String role = "";
		if (Console.user == null) {
			role = "Client";
		}
		else if (Console.user.getRole().equals("Chef")) {
			role = ConsoleService.renseigneChampDeuxString("Renseigner le type d'utilisateur à créer (Employe/Client): ", "Employe", "Client");
		}
		
		Utilisateur utilisateur = new Utilisateur(username, password, role);
		userDAO.create(utilisateur);
		ConsoleService.affiche("Compte utilisateur créé !");
		if (Console.user == null) {
			ConsoleService.affiche("Vous pouvez maintenant vous connecter à votre compte depuis le menu principal.");
			ConsoleService.sleep(3000);
		}
		else {
			ConsoleService.affiche("Retour au menu principal...");
			ConsoleService.sleep(3000);
		}
		Console.promptMenu();
	}

	public static void promptModifierUtilisateur(Utilisateur utilisateur) {
		String choix = ConsoleService.renseigneChampDeuxString("\nQuel champ voulez-vous modifier ? (username/password) ", "username", "password");
		
		if (choix.equals("username")) {
			ConsoleService.affiche("Renseigner un nouveau nom d'utilisateur :");
			String newUsername = Console.sc.nextLine();
			utilisateur.setUsername(newUsername);
		} else if (choix.equals("password")) {
			String password = "";
			String password2 = "";
			do {
				ConsoleService.affiche("Renseigner un nouveau mot de passe :");
				password = Console.sc.nextLine();
				ConsoleService.affiche("Confirmer le mot de passe :");
				password2 = Console.sc.nextLine();
				if (!password.equals(password2)) {
					ConsoleService.affiche("Les mots de passe ne correspondent pas !");
				}
			} while (!password.equals(password2));
			utilisateur.setPassword(password);
		}
	
		ConsoleService.affiche("Modification de l'utilisateur...");
		userDAO.update(utilisateur);
		ConsoleService.affiche("Compte utilisateur modifié !");
		ConsoleService.affiche("Retour au menu principal...");
		ConsoleService.sleep(3000);
		Console.promptMenu();
		
	}

	public static void promptSupprimerUtilisateur(Utilisateur utilisateur) {
		String choix = ConsoleService.renseigneChampDeuxString("\nÊtes-vous sûr de vouloir supprimer cet utilisateur ?\nSa suppression entrainera également l'effacement des réservations correspondantes également.\n(oui/non) :", "oui", "non");
		
		if (choix.equals("oui")) {
			ConsoleService.affiche("Suppression de l'utilisateur...");
			userDAO.delete(utilisateur);
			if (utilisateur == Console.user) {
				Console.user = null;
			}
			ConsoleService.affiche("Compte utilisateur supprimé !");
		}
		
		ConsoleService.affiche("Retour au menu principal...");
		ConsoleService.sleep(3000);
		Console.promptMenu();
	}

}
