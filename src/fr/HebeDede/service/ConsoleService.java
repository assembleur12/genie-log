package fr.HebeDede.service;

import java.util.InputMismatchException;
import java.util.List;

import fr.HebeDede.ui.Console;

public class ConsoleService {

	public static void affiche(String message) {
		System.out.println(message);
	}

	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static Integer choixMenuMinMax(Integer minChoix, Integer maxChoix) {
		Boolean choixCorrect = false;
		Integer choice = 0;
	    while (choixCorrect == false) {
			affiche("\nVeuillez renseigner votre choix");
			try {
				choice = Console.sc.nextInt();
				if (choice < minChoix || choice > maxChoix || choice == null) {
					affiche("\nChoix incorrect.");
				} else {
					choixCorrect = true;
				}
			} catch (InputMismatchException e) {
				affiche("\nChoix incorrect.");
				Console.sc.nextLine();
			}
		} 
	    Console.sc.nextLine();
		return choice;
	}

	public static Integer choixMenuIntList(List<Integer> list) {
		Boolean choixCorrect = false;
		Integer choice = 0;
		while (choixCorrect == false) {
			affiche("\nVeuillez renseigner votre choix");
			try {
				choice = Console.sc.nextInt();
				if (!list.contains(choice) && choice != 0) {
					affiche("\nChoix incorrect, le numéro renseigné ne fait pas partie de la liste.");
				} else {
					choixCorrect = true;
				}
			} catch (InputMismatchException e) {
				affiche("Renseignez un numéro dans la liste.");
				Console.sc.nextLine();
			}
		} 
		Console.sc.nextLine();
		return choice;
	}

	public static Integer renseigneChampMaxMinInt(String message, Integer min, Integer max) {
		affiche(message);
		Integer num = 0;
		if (max == null) {
			do {
				try {
					num = Console.sc.nextInt();
					if (num < min || num == 0) {
						affiche("Renseignez un nombre supérieur à " + min + ".");
					}
				} catch (InputMismatchException e) { 
					affiche("Renseignez un nombre supérieur à " + min + ".");
					Console.sc.nextLine();
				}
			} while (num < min || num == 0);
		} else {
			do {
				try {
					num = Console.sc.nextInt();
					if (num < min || num > max || num == 0) {
						affiche("Renseignez un nombre compris entre " + min + " et " + max + ".");
					}
				} catch (InputMismatchException e) { 
					affiche("Renseignez un nombre compris entre " + min + " et " + max + ".");
					Console.sc.nextLine();
				}
			} while (num < min || num > max || num == 0);
			
		}
		Console.sc.nextLine();
		return num;
	}

	public static Float renseigneChampMaxMinFloat(String message, Float min, Float max) {
		affiche(message);
		Float num = 0f;
		if (max == null) {
			do {
				try {
					num = Console.sc.nextFloat();
					if (num < min || num == 0) {
						affiche("Renseignez un nombre supérieur à " + min + ".");
					}
				} catch (InputMismatchException e) {
					affiche("Renseignez un nombre supérieur à " + min + ".");
					Console.sc.nextLine();
				}
			} while (num < min || num == 0);
		} else {
			do {
				try {
					num = Console.sc.nextFloat();
					if (num < min || num > max || num == 0) {
						affiche("Renseignez un nombre compris entre " + min + " et " + max + ".");
					}
				} catch (InputMismatchException e) {
					affiche("Renseignez un nombre compris entre " + min + " et " + max + ".");
					Console.sc.nextLine();
				}
			} while (num < min || num > max || num == 0);
		}
		Console.sc.nextLine();
		return num;
	}

	public static String renseigneChampDeuxString(String message, String choix1, String choix2) {
		affiche(message);
		String string = "";
		do {
			string = Console.sc.nextLine();
			if (!string.equals(choix1) && !string.equals(choix2)) {
				affiche("Merci de saisir " + choix1 + " ou " + choix2 + ".");
			}
		} while (!string.equals(choix1) && !string.equals(choix2));
		return string;
	}

}
