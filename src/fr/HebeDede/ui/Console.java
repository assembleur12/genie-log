package fr.HebeDede.ui;

import java.sql.SQLException;
import java.util.Scanner;

import fr.HebeDede.data.DatabaseConnection;
import fr.HebeDede.model.Utilisateur;
import fr.HebeDede.repositories.UtilisateurDAO;
import fr.HebeDede.service.AuthentificationService;
import fr.HebeDede.service.ConsoleService;

public class Console {
	
	public static Utilisateur user;
	public static Scanner sc = new Scanner(System.in);
	public static Long dureeResa = (long) (4*60*60*1000);
	
	public static void promptLogin() {
		AuthentificationService authentificationService = new AuthentificationService();
		
		String username = "";
		String password = "";
		
		Boolean userConnected = false;
		while(userConnected != true) {
			ConsoleService.affiche("Login :");
			while (username.equals("")) {
				username = sc.nextLine();
				if (username.equals("")) {
					ConsoleService.affiche("Veuillez renseigner un nom d'utilisateur.");
				}
			}
			ConsoleService.affiche("Password :");
			password = sc.nextLine();
			
			userConnected = authentificationService.login(username, password);
			if (userConnected != true) {
				ConsoleService.affiche("Mot de passe incorrect ! Réessayez.");
				username = "";
				password = "";
			}
		}
		UtilisateurDAO userDAO = new UtilisateurDAO();
		user = userDAO.findByUsername(username);
		
		ConsoleService.affiche("Vous êtes connectés !");
		
		promptMenu();
	}

	public static void promptMenu() {
		ConsoleService.affiche("\n******* Site Web MarouaUma *******\n\n Menu principal :");
		
		ConsoleService.affiche("1. Recherche/Liste des articles\n");
		Integer choice = 0;
		if (user == null) {
			ConsoleService.affiche("2. Login");
			ConsoleService.affiche("3. Créer un compte");
			ConsoleService.affiche("\n0. Quitter l'application");
			choice = ConsoleService.choixMenuMinMax(0,3);
		}
		else {
			switch (user.getRole()){
			case "Employe" :
				ConsoleService.affiche("2. Liste des options");
				ConsoleService.affiche("3. Mon compte");
				ConsoleService.affiche("\n0. Quitter l'application");
				choice = ConsoleService.choixMenuMinMax(0,3);
				break;
			case "Chef" :
				ConsoleService.affiche("2. Liste des options");
				ConsoleService.affiche("3. Mon compte");
				ConsoleService.affiche("4. Gérer comptes utilisateurs");
				ConsoleService.affiche("\n0. Quitter l'application");
				choice = ConsoleService.choixMenuMinMax(0,4);
				break;
			case "Client" :
				ConsoleService.affiche("2. Mes options");
				ConsoleService.affiche("3. Mon compte");
				ConsoleService.affiche("\n0. Quitter l'application");
				choice = ConsoleService.choixMenuMinMax(0,3);
				break;
			}
		}
		
		selectMenu(choice);
	}
		
	public static void selectMenu(int choice) {
	    if (user == null) {
	    	switch (choice) {
		    	case 1:
		    		ConsoleArticle.promptArticleList();
		    		break;
		    	case 2:
					promptLogin();
					break;
		    	case 3:
		    		ConsoleUtilisateur.promptCreerUtilisateur();
		    		break;
		      	case 0:
	    			closeApp();
	    			break;
	    		default:
	    			ConsoleService.affiche("\n Choix incorrect.");
	    			promptMenu();
					break;
	    	}
	    } else {
			switch (choice) {
		    	case 1:
		    		ConsoleArticle.promptArticleList();
		    		break;
		    	case 2:
		    		switch (user.getRole()) {
		    			case "Employe":
		    				ConsoleOption.promptOptionsListAll();
		    				break;
		    			case "Chef":
		    				ConsoleOption.promptOptionsListAll();
		    				break;
		    			case "Client":
		    				ConsoleOption.promptOptionsListClient(user);
		    				break;
		    		}
		    		break;
		    	case 3:
		    		ConsoleUtilisateur.promptConsulterUtilisateur(user);
		    		break;
	    		case 4:
	    			switch (user.getRole()) {
		    			case "Chef":
		    				ConsoleUtilisateur.promptListUtilisateurs();
		    				break;
	    			}
	    			break;
	    		case 0:
	    			closeApp();
	    			break;
	    		default:
	    			ConsoleService.affiche("\n Choix incorrect.");
	    			promptMenu();
					break;
			}
	    }
	}

	public static void closeApp() {
		try {
			DatabaseConnection.getInstance().close();
			sc.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}