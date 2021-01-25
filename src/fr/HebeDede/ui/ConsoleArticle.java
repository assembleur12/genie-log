package fr.HebeDede.ui;

import java.util.InputMismatchException;
import java.util.List;

import fr.HebeDede.model.Article;
import fr.HebeDede.model.Bandedessinee;
import fr.HebeDede.model.Figurine;
import fr.HebeDede.repositories.ArticleDAO;
import fr.HebeDede.repositories.BandedessineeDAO;
import fr.HebeDede.repositories.FigurineDAO;
import fr.HebeDede.service.ConsoleService;

public class ConsoleArticle {
	
	public static ArticleDAO articleDAO = new ArticleDAO();
	
	static BandedessineeDAO bdDAO = new BandedessineeDAO();
	
	static FigurineDAO figDAO = new FigurineDAO();

	public static void promptArticleList() {
		List<Bandedessinee> bdList = bdDAO.findAllBD();
		List<Figurine> figList = figDAO.findAllFig();
		ConsoleService.affiche("\n******* Liste des Articles *******\n");
		if (bdList.isEmpty()) {
			ConsoleService.affiche("Aucune article dans la base de données !");
		}
		for (Bandedessinee bd : bdList) {
			ConsoleService.affiche("Bande dessinée - Réf. article " + bd.getIdArticle() + " | Titre : " + bd.getLibelle() + " | Collection : " + bd.getCollection()  + " | Prix : " + bd.getPrix() + "€" + " | Disponible : " + bd.afficheDispo());
		}
		for (Figurine fig : figList) {
			ConsoleService.affiche("Figurine - Réf. article " + fig.getIdArticle() + " | Description : " + fig.getDescription() + " | Prix : " + fig.getPrix() + "€" + " | Disponible : " + fig.afficheDispo());
		}
		
		ConsoleService.affiche("\nMenu :");
		Integer choice = 0;
		if (Console.user == null || Console.user.getRole().equals("Client")) {
			ConsoleService.affiche("1. Afficher les détails d'un article");
			ConsoleService.affiche("2. Recherche multi-critères d'articles");
			ConsoleService.affiche("0. Retour au menu principal");
			choice = ConsoleService.choixMenuMinMax(0,2);
		} else {
			ConsoleService.affiche("1. Afficher les détails d'un article");
			ConsoleService.affiche("2. Recherche multi-critères d'articles");
			ConsoleService.affiche("3. Ajouter un article");
			ConsoleService.affiche("0. Retour au menu principal");
			choice = ConsoleService.choixMenuMinMax(0,3);
		}
		
		selectArticleList(choice);
	}

	public static void selectArticleList(int choice) {
		if (Console.user == null || Console.user.getRole().equals("Client")) {
			switch (choice) {
				case 1:
					ouvrirFicheArticle();
					break;
				case 2:
					ConsoleService.affiche("\nLa recherche multi-critère n'est pas disponible pour le moment.");
					choice = ConsoleService.choixMenuMinMax(0,2);
					selectArticleList(choice);
					break;
				case 3:
					ConsoleService.affiche("\nChoix incorrect.");
					choice = ConsoleService.choixMenuMinMax(0,2);
					selectArticleList(choice);
					break;
				case 0:
					Console.promptMenu();
					break;
				default:
					ConsoleService.affiche("\nChoix incorrect.");
					choice = ConsoleService.choixMenuMinMax(0,3);
					selectArticleList(choice);
					break;
			}
		} else {
			switch (choice) {
				case 1:
					ouvrirFicheArticle();
					break;
				case 2:
					ConsoleService.affiche("\nLa recherche multi-critère n'est pas disponible pour le moment.");
					choice = ConsoleService.choixMenuMinMax(0,3);
					selectArticleList(choice);
					break;
				case 3:
					ConsoleArticle.ajouterArticle();
					break;
				case 0:
					Console.promptMenu();
					break;
				default:
					ConsoleService.affiche("\nChoix incorrect.");
					choice = ConsoleService.choixMenuMinMax(0,3);
					selectArticleList(choice);
					break;
			}
		}
	}

	public static void ouvrirFicheArticle() {
		ConsoleService.affiche("Renseigner la référence de l'article à consulter :");
		Integer idArticle = 0;
		do {
			try {
				idArticle = Console.sc.nextInt();
				if (articleDAO.find(idArticle) == null || idArticle == 0) {
					ConsoleService.affiche("La référence article est inexistante, veuillez réessayer.");
				}
			} catch (InputMismatchException e) {
				ConsoleService.affiche("Seuls les nombres sont acceptez, veuillez réessayer.");
				Console.sc.nextLine();
			}
		} while (articleDAO.find(idArticle) == null || idArticle == 0);
			Console.sc.nextLine();
			
		Article article = articleDAO.find(idArticle);
		if (article.getClass().getSimpleName().equals("Figurine")) {
			promptFicheFigurine((Figurine) article);
		}
		else if (article.getClass().getSimpleName().equals("Bandedessinee")) {
			promptFicheBd((Bandedessinee) article);
		}
	}

	public static void promptFicheBd(Bandedessinee bd) {
		ConsoleService.affiche("\n******* Fiche Bande déssinée *******\n\nRéf. article " + bd.getIdArticle());
		ConsoleService.affiche("Titre : " + bd.getLibelle());
		ConsoleService.affiche("Collection : " + bd.getCollection());
		ConsoleService.affiche("Auteur : " + bd.getAuteur());
		ConsoleService.affiche("Editeur : " + bd.getEditeur());
		ConsoleService.affiche("Catégorie : " + bd.getCategorie());
		ConsoleService.affiche("Nbr. pages : " + bd.getNbrPages());
		ConsoleService.affiche("Description : " + bd.getDescription());
		ConsoleService.affiche("\nEtat : " + bd.getEtat());
		ConsoleService.affiche("Prix : " + bd.getPrix() + "€");
		ConsoleService.affiche("Disponible en magasin : " + bd.afficheDispo());
		
		ConsoleService.affiche("\nMenu :");
		Integer choice = 0;
		if (Console.user == null){
			ConsoleService.affiche("0. Retour à la liste des Articles");
			choice = ConsoleService.choixMenuMinMax(0,0);
		}
		else if (Console.user.getRole().equals("Client")) {
			ConsoleService.affiche("1. Réserver la BD");
			ConsoleService.affiche("0. Retour à la liste des Articles");
			choice = ConsoleService.choixMenuMinMax(0,1);
		}
		else if (Console.user.getRole().equals("Employe") || Console.user.getRole().equals("Chef")) {
			ConsoleService.affiche("1. Consulter les réservations de la BD");
			ConsoleService.affiche("2. Modifier les informations de la BD");
			ConsoleService.affiche("3. Supprimer la figurine du catalogue");
			ConsoleService.affiche("0. Retour à la liste des Articles");
			choice = ConsoleService.choixMenuMinMax(0,3);
		}
		
		selectFicheBd(choice, bd);
	}

	public static void promptFicheFigurine(Figurine fig) {
		ConsoleService.affiche("\n******* Fiche Figurine *******\n\nRéf. article " + fig.getIdArticle());
		ConsoleService.affiche("Description : " + fig.getDescription());
		ConsoleService.affiche("Taille figurine : " + fig.getTaille() + "cm");
		ConsoleService.affiche("\nPrix : " + fig.getPrix() + "€");
		ConsoleService.affiche("Disponible en magasin : " + fig.afficheDispo());
		
		ConsoleService.affiche("\nMenu :");
		Integer choice = 0;
		if (Console.user == null){
			ConsoleService.affiche("0. Retour à la liste des Articles");
			choice = ConsoleService.choixMenuMinMax(0,0);
		}
		else if (Console.user.getRole().equals("Client")) {
			ConsoleService.affiche("1. Réserver la figurine");
			ConsoleService.affiche("0. Retour à la liste des Articles");
			choice = ConsoleService.choixMenuMinMax(0,1);
		}
		else if (Console.user.getRole().equals("Employe") || Console.user.getRole().equals("Chef")) {
			ConsoleService.affiche("1. Consulter les réservations de la figurine");
			ConsoleService.affiche("2. Modifier les informations de la figurine");
			ConsoleService.affiche("3. Supprimer la figurine du catalogue");
			ConsoleService.affiche("0. Retour à la liste des Articles");
			choice = ConsoleService.choixMenuMinMax(0,3);
		}
		
		selectFicheFigurine(choice, fig);
	}

	public static void selectFicheBd(int choice, Bandedessinee bd) {
		if (Console.user == null && choice == 0) {
			promptArticleList();
		}
		else if (Console.user == null && choice != 0){
			ConsoleService.affiche("\nChoix incorrect.");
			choice = ConsoleService.choixMenuMinMax(0,3);
			selectFicheBd(choice, bd);
		}
		else if (Console.user.getRole().equals("Client")) {
			switch (choice) {
				case 1:
					ConsoleOption.promptReservation(bd);
					break;
				case 0:
					promptArticleList();
					break;
				default:
					ConsoleService.affiche("\nChoix incorrect.");
					choice = ConsoleService.choixMenuMinMax(0,3);
					selectFicheBd(choice, bd);
					break;
			}
		}
		else if (Console.user.getRole().equals("Employe") || Console.user.getRole().equals("Chef")) {
			switch (choice) {
				case 1:
					ConsoleOption.promptConsultReservation(bd);
					break;
				case 2:
					ConsoleArticle.modifFiche(bd);
					break;
				case 3:
					String ok = "";
					ok = ConsoleService.renseigneChampDeuxString("Êtes-vous sûr de vouloir supprimer cette BD (oui/non) ? ", "oui", "non");
					if (ok.equals("oui")) { ConsoleArticle.supprimerFiche(bd); }
					if (ok.equals("non")) { promptFicheBd(bd); }
					break;
				case 0:
					promptArticleList();
					break;
				default:
					ConsoleService.affiche("\nChoix incorrect.");
					choice = ConsoleService.choixMenuMinMax(0,3);
					selectFicheBd(choice, bd);
					break;
			}
		}
		else {
			ConsoleService.affiche("\nChoix incorrect.");
			choice = ConsoleService.choixMenuMinMax(0,3);
			selectFicheBd(choice, bd);
		}
	}

	public static void selectFicheFigurine(int choice, Figurine fig) {
		if (Console.user == null && choice == 0) {
			promptArticleList();
		}
		else if (Console.user == null && choice != 0){
			ConsoleService.affiche("\nChoix incorrect.");
			choice = ConsoleService.choixMenuMinMax(0,3);
			selectFicheFigurine(choice, fig);
		}
		else if (Console.user.getRole().equals("Client")) {
			switch (choice) {
				case 1:
					ConsoleOption.promptReservation(fig);
					break;
				case 0:
					promptArticleList();
					break;
				default:
					ConsoleService.affiche("\nChoix incorrect.");
					choice = ConsoleService.choixMenuMinMax(0,3);
					selectFicheFigurine(choice, fig);
					break;
			}
		}
		else if (Console.user.getRole().equals("Employe") || Console.user.getRole().equals("Chef")) {
			switch (choice) {
				case 1:
					ConsoleOption.promptConsultReservation(fig);
					break;
				case 2:
					ConsoleArticle.modifFiche(fig);
					break;
				case 3:
					String ok = "";
					ok = ConsoleService.renseigneChampDeuxString("Êtes-vous sûr de vouloir supprimer cette figurine (oui/non) ? ", "oui", "non");
					if (ok.equals("oui")) { ConsoleArticle.supprimerFiche(fig); }
					if (ok.equals("non")) { promptFicheFigurine(fig); }
					break;
				case 0:
					promptArticleList();
					break;
				default:
					ConsoleService.affiche("\nChoix incorrect.");
					choice = ConsoleService.choixMenuMinMax(0,3);
					selectFicheFigurine(choice, fig);
					break;
			}
		}
		else {
			ConsoleService.affiche("\nChoix incorrect.");
			choice = ConsoleService.choixMenuMinMax(0,3);
			selectFicheFigurine(choice, fig);
		}
	}

	public static void ajouterArticle() {
		String objet = ConsoleService.renseigneChampDeuxString("\nAjouter une BD ou une figurine ? (Saisir \"bd\" ou \"figurine\") : ", "bd", "figurine");
		
		if (objet.equals("bd")) {
			ConsoleService.affiche("\nRenseigner les informations de la BD :\nTitre : ");
			Bandedessinee newBd = new Bandedessinee();
			newBd.setLibelle(Console.sc.nextLine());
			ConsoleService.affiche("Collection : ");
			newBd.setCollection(Console.sc.nextLine());
			ConsoleService.affiche("Auteur : ");
			newBd.setAuteur(Console.sc.nextLine());
			ConsoleService.affiche("Editeur : ");
			newBd.setEditeur(Console.sc.nextLine());
			ConsoleService.affiche("Catégorie : ");
			newBd.setCategorie(Console.sc.nextLine());
			Integer nbPages = ConsoleService.renseigneChampMaxMinInt("Nbr. pages : ", 0, null);
			newBd.setNbrPages(nbPages);
			ConsoleService.affiche("Description : ");
			newBd.setDescription(Console.sc.nextLine());
			String etat = ConsoleService.renseigneChampDeuxString("Etat de la BD (Saisir \"Neuf\" ou \"Occasion\") : ", "Neuf", "Occasion");
			newBd.setEtat(etat);
			Float prix = ConsoleService.renseigneChampMaxMinFloat("Prix : ", 0f, null);
			newBd.setPrix(prix);
			String dispo = ConsoleService.renseigneChampDeuxString("Disponibilité en magasin (oui/non) :", "oui", "non");
			if (dispo.equals("oui")) { newBd.setDispo(true); }
			if (dispo.equals("non")) { newBd.setDispo(false); }
			
			bdDAO.create(newBd);
			
			ConsoleService.affiche("La BD a été créée avec succès, redirection vers la fiche de la BD");
			ConsoleService.sleep(3000);
			promptFicheBd(bdDAO.findByIdArticle(articleDAO.findLastEntryId()));
		}
		
		else if (objet.equals("figurine")) {
			ConsoleService.affiche("\nRenseigner les informations de la BD :\nDescription : ");
			Figurine newFig = new Figurine();
			newFig.setDescription(Console.sc.nextLine());
			Integer taille = ConsoleService.renseigneChampMaxMinInt("Taille en cm (nombre entier) : ", 0, null);
			newFig.setTaille(taille);
			Float prix = ConsoleService.renseigneChampMaxMinFloat("Prix : ", 0f, null);
			newFig.setPrix(prix);
			String dispo = ConsoleService.renseigneChampDeuxString("Disponibilité en magasin (oui/non) :", "oui", "non");
			if (dispo.equals("oui")) { newFig.setDispo(true); }
			if (dispo.equals("non")) { newFig.setDispo(false); }
			
			figDAO.create(newFig);
			
			ConsoleService.affiche("La figurine a été créée avec succès, redirection vers la fiche de la figurine");
			ConsoleService.sleep(3000);
			promptFicheFigurine(figDAO.findByIdArticle(articleDAO.findLastEntryId()));
		}
		
	}

	public static void modifFiche(Article obj) {
		String typeObj = obj.getClass().getSimpleName();
		if (typeObj.equals("Bandedessinee")) {
			ConsoleService.affiche("\nRenseigner les informations de la BD (Vous pouvez laisser les champs non modifiés vides) :\nTitre : ");
			Bandedessinee newBd = (Bandedessinee) obj;
			String libelle = Console.sc.nextLine();
			if (!libelle.isEmpty()) { newBd.setLibelle(libelle); }
			ConsoleService.affiche("Collection : ");
			String collection = Console.sc.nextLine();
			if (!collection.isEmpty()) { newBd.setCollection(collection); }
			ConsoleService.affiche("Auteur : ");
			String auteur = Console.sc.nextLine();
			if (!collection.isEmpty()) { newBd.setAuteur(auteur); }
			ConsoleService.affiche("Editeur : ");
			String editeur = Console.sc.nextLine();
			if (!editeur.isEmpty()) { newBd.setEditeur(editeur); }
			ConsoleService.affiche("Catégorie : ");
			String categorie = Console.sc.nextLine();
			if (!categorie.isEmpty()) { newBd.setCategorie(categorie); }
			Integer nbPages = ConsoleService.renseigneChampMaxMinInt("Nbr. pages : ", 0, null);
			if (nbPages > 0) { newBd.setNbrPages(nbPages); }
			ConsoleService.affiche("Description : ");
			String desc = Console.sc.nextLine();
			if (!desc.isEmpty()) { newBd.setDescription(desc); }
			String etat = ConsoleService.renseigneChampDeuxString("Etat (Neuf/Occasion) :", "Neuf", "Occasion");
			if (!etat.equals("")) { newBd.setEtat(etat); }
			Float prix = ConsoleService.renseigneChampMaxMinFloat("Prix : ", 0f, null);
			if (prix > 0) { newBd.setPrix(prix); }
			String dispo = ConsoleService.renseigneChampDeuxString("Disponibilité en magasin (oui/non) :", "oui", "non");
			if (!dispo.equals("")) { 
				if (dispo.equals("oui")) { newBd.setDispo(true); }
				if (dispo.equals("non")) { newBd.setDispo(false); } 
			}
			bdDAO.update(newBd);
			
			ConsoleService.affiche("La BD a été mise à jour avec succès, redirection vers la fiche article");
			ConsoleService.sleep(3000);
			promptFicheBd(newBd);
			
		} else if (typeObj.equals("Figurine")) {
			ConsoleService.affiche("\nRenseigner les informations de la figurine (Vous pouvez laisser les champs non modifiés vides) :\nDescription : ");
			Figurine newFig = (Figurine) obj;
			String desc = Console.sc.nextLine();
			if (!desc.isEmpty()) { newFig.setDescription(desc); }
			Integer taille = ConsoleService.renseigneChampMaxMinInt("Taille en cm (nombre entier) : ", 0, null);
			if (taille > 0) { newFig.setTaille(taille); }
			Float prix = ConsoleService.renseigneChampMaxMinFloat("Prix : ", 0f, null);
			if (prix > 0) { newFig.setPrix(prix); }
			String dispo = ConsoleService.renseigneChampDeuxString("Disponibilité en magasin (oui/non) :", "oui", "non");
			if (!dispo.equals("")) { 
				if (dispo.equals("oui")) { newFig.setDispo(true); }
				if (dispo.equals("non")) { newFig.setDispo(false); } 
			}
			figDAO.update(newFig);
			
			ConsoleService.affiche("La BD a été mise à jour avec succès, redirection vers la fiche article");
			ConsoleService.sleep(3000);
			promptFicheFigurine(newFig);
		}
		
	}

	public static void supprimerFiche(Article obj) {
		String typeObj = obj.getClass().getSimpleName();
		if (typeObj.equals("Bandedessinee")) {
			Bandedessinee bd = (Bandedessinee) obj;
			articleDAO.delete(bd);
			ConsoleService.affiche("Elément supprimé du catalogue, retour à la liste des Articles");
			ConsoleService.sleep(3000);
			promptArticleList();
		} else if (typeObj.equals("Figurine")) {
			Figurine fig = (Figurine) obj;
			articleDAO.delete(fig);
			ConsoleService.affiche("Elément supprimé du catalogue, retour à la liste des Articles");
			ConsoleService.sleep(3000);
			promptArticleList();
		}
		else {
			ConsoleService.affiche("Erreur à la suppression de l'élément. Retour à la liste des Articles");
			ConsoleService.sleep(3000);
			promptArticleList();
		}
	}

	public static void findFicheArticle(Article obj) {
		String typeObj = obj.getClass().getSimpleName();
		if (typeObj.equals("Bandedessinee")) { promptFicheBd((Bandedessinee) obj); }
		else if (typeObj.equals("Figurine")) { promptFicheFigurine((Figurine) obj); }
	}

	public static void promptRechercheMultiCriteres() {
		// TODO Auto-generated method stub
	}
}