package fr.HebeDede.ui;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.HebeDede.model.Article;
import fr.HebeDede.model.Bandedessinee;
import fr.HebeDede.model.Figurine;
import fr.HebeDede.model.Option;
import fr.HebeDede.model.Utilisateur;
import fr.HebeDede.repositories.BandedessineeDAO;
import fr.HebeDede.repositories.FigurineDAO;
import fr.HebeDede.repositories.OptionDAO;
import fr.HebeDede.service.ConsoleService;

public class ConsoleOption {
	
	static OptionDAO optionDAO = new OptionDAO();
	
	public static void promptOptionsListClient(Utilisateur user) {
		ConsoleService.affiche ("\n******* Mes réservations *******");
		List<Option> optionList = optionDAO.findByUtilisateur(user);
		List<Integer> optionId = new ArrayList<Integer>();
		List<String> menuList = new ArrayList<String>();
		if (optionList.isEmpty()) {
			ConsoleService.affiche("\nVous n'avez pas fait de réservation d'article pour le moment !");
		}
		for (Option option : optionList) {
			BandedessineeDAO bdDAO = new BandedessineeDAO();
			FigurineDAO figDAO = new FigurineDAO();
			Bandedessinee bd = bdDAO.findByIdArticle(option.getArticle().getIdArticle());
			Figurine fig = figDAO.findByIdArticle(option.getArticle().getIdArticle());
			if (bd != null) {
				ConsoleService.affiche("\nRéservation n°" + option.getIdOption() + " - Article n°: " + option.getArticle().getIdArticle() + 
						"\nLibellé : " + bd.getLibelle() + " - Collection : " + bd.getCollection() + 
						"\nArticle disponible jusqu'au " + option.getDateFinOption());
			}
			else if (fig != null) {
				ConsoleService.affiche("\nRéservation n°" + option.getIdOption() + " - Article n°: " + option.getArticle().getIdArticle() + 
						"\nLibellé : " + fig.getDescription() + 
						"\nArticle disponible jusqu'au " + option.getDateFinOption());
			}
			menuList.add(option.getIdOption() + ". Annuler la réservation n°" + option.getIdOption());
			optionId.add(option.getIdOption());
		}
		ConsoleService.affiche("\nMenu :");
		for (String string : menuList) {
			ConsoleService.affiche(string);
		}
		ConsoleService.affiche("\n0. Retour au menu principal");
		
		Integer choice = ConsoleService.choixMenuIntList(optionId);
		
	    if (choice != 0) {
	    	Option option = optionDAO.find(choice);
	    	promptAnnulerOption(option, user);
	    }
	    else if (choice == 0) {
	    	Console.promptMenu();
	    }
		
	}

	public static void promptOptionsListAll() {
		ConsoleService.affiche("\n******* Liste des Réservation *******");
		List<Option> optionList = optionDAO.findAll();
		List<Integer> optionId = new ArrayList<Integer>();
		if (optionList.isEmpty()) {
			ConsoleService.affiche("\nAucune réservation en cours !");
		}
		for (Option option : optionList) {
			BandedessineeDAO bdDAO = new BandedessineeDAO();
			FigurineDAO figDAO = new FigurineDAO();
			Bandedessinee bd = bdDAO.findByIdArticle(option.getArticle().getIdArticle());
			Figurine fig = figDAO.findByIdArticle(option.getArticle().getIdArticle());
			Date dateActuelle = new Date();
			Timestamp tsDateActuelle = new Timestamp(dateActuelle.getTime());
			if (option.getDateFinOption().after(tsDateActuelle)) {
				if (bd != null) {
					ConsoleService.affiche("\nRéservation n°" + option.getIdOption() + " - Article n°: "
							+ option.getArticle().getIdArticle() + "\nLibellé : " + bd.getLibelle() + " - Collection : "
							+ bd.getCollection() + "\nArticle disponible jusqu'au " + option.getDateFinOption());
				} else if (fig != null) {
					ConsoleService.affiche("\nRéservation n°" + option.getIdOption() + " - Article n°: "
							+ option.getArticle().getIdArticle() + "\nLibellé : " + fig.getDescription()
							+ "\nArticle disponible jusqu'au " + option.getDateFinOption());
				} 
			}
			optionId.add(option.getIdOption());
		}
		ConsoleService.affiche("\nMenu :");
		ConsoleService.affiche("1. Annuler une réservation");
		ConsoleService.affiche("\n0. Retour au menu principal");
		
		Integer choice = ConsoleService.choixMenuMinMax(0,optionId.get(optionId.size() -1).intValue());
	
	    if (choice == 1) {
	    	ConsoleService.affiche("\nRenseigner le numero de l'option à annuler : ");
	    	Integer idOption = ConsoleService.choixMenuIntList(optionId);
	    	Option option = optionDAO.find(idOption);
	    	Utilisateur optionUser = option.getUtilisateur();
	    	promptAnnulerOption(option, optionUser);
	    }
	    else if (choice == 0) {
	    	Console.promptMenu();
	    }
	}

	public static void promptAnnulerOption(Option option, Utilisateur user) {
		List<Option> optionList = optionDAO.findByUtilisateur(user);
		for (Option optionItem : optionList) {
			if (optionItem.getIdOption() == option.getIdOption()) {
				String choix = ConsoleService.renseigneChampDeuxString("\nVous êtes sur le point d'annuler l'option n°"+ option.getIdOption() + ".\nÊtes-vous sûr ? (oui/non) ", "oui", "non");
				
				if(choix.equals("oui")) {
					option.getArticle().setDispo(true);
					optionDAO.delete(option);
					ConsoleService.affiche("Réservation annulée !");
				}
				break;
			}
			else {
				ConsoleService.affiche("Cette option n'appartient pas à l'utilisateur " + user.getUsername() + ", annulation impossible.");
				break;
			}
		}
		
		ConsoleService.affiche("Retour au menu principal...");
		ConsoleService.sleep(3000);
		Console.promptMenu();
		
	}

	public static void promptConsultReservation(Article obj) {
		ConsoleService.affiche("\nRéservations liées à l'Article réf. " + obj.getIdArticle());
		List<Option> optionList = optionDAO.findByArticle(obj);
		for (Option option : optionList) {
			System.out.println("* Réservation n°"+ option.getIdOption() + " de " + option.getUtilisateur().getUsername() + " - Date de réservation : " + option.getDateDebutOption() + " - Date limite : " + option.getDateFinOption());
		}
		
		ConsoleService.affiche("\nAppuyer sur entrée pour revenir à la fiche Article");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ConsoleArticle.findFicheArticle(obj);
	}

	public static void promptReservation(Article obj) {
		if (obj.getdispo().equals(false)) { 
			ConsoleService.affiche("\nL'article n'est pas disponible à la réservation. Redirection vers la fiche article...");
			ConsoleService.sleep(3000);
			ConsoleArticle.findFicheArticle(obj);
		}
		else {
			String confirm = "";
			confirm = ConsoleService.renseigneChampDeuxString("\nVous êtes sur le point de réserver cet article, confirmez-vous l'opération ? (oui/non)", "oui", "non");
			if (confirm.equals("oui")) {
				Date dateActuelle = new Date();
				Timestamp ts = new Timestamp(dateActuelle.getTime());
				Timestamp tsFin = new Timestamp(ts.getTime() + Console.dureeResa);
				Option resa = new Option(ts, tsFin, obj, Console.user);
				optionDAO.create(resa);
				obj.setDispo(false);
				ConsoleArticle.articleDAO.update(obj);
				ConsoleService.affiche("Votre article est réservé ! Vous pouvez le retirer en magasin dans un délai de 4 heures.");
				ConsoleService.affiche("Retour sur la fiche Article...");
				ConsoleService.sleep(3000);
				ConsoleArticle.findFicheArticle(obj);
			}
			if (confirm.equals("non")) {
				ConsoleService.affiche("Redirection vers la fiche Article...");
				ConsoleService.sleep(3000);
				ConsoleArticle.findFicheArticle(obj);
			}
		}
	}

}
