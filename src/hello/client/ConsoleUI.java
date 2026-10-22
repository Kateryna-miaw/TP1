package hello.client;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final ServiceClient serviceClient;
    private final Scanner scanner;

    public ConsoleUI(ServiceClient serviceClient) {
        this.serviceClient = serviceClient;
        this.scanner = new Scanner(System.in);
    }

    public void lancer() {
        boolean continuer = true;
        while (continuer) {
            afficherMenu();
            System.out.print("> ");
            String choix = scanner.nextLine();
            switch (choix) {
                case "1" -> ajouterAnimal();
                case "2" -> serviceClient.listerAnimaux();
                case "3" -> ajouterObservation();
                case "4" -> consulterDossier();
                case "0" -> continuer = false;
                default -> System.out.println("Choix invalide.");
            }
        }
        serviceClient.fermer();
        scanner.close();
    }

    private void afficherMenu() {
        System.out.println("\n=== Cabinet vétérinaire ===");
        System.out.println("1. Ajouter un animal");
        System.out.println("2. Lister les animaux");
        System.out.println("3. Ajouter une observation");
        System.out.println("4. Consulter un dossier de suivi");
        System.out.println("0. Quitter");
    }

    private void ajouterAnimal() {
        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        System.out.print("Espèce: ");
        String espece = scanner.nextLine();
        System.out.print("Durée de vie moyenne: ");
        int dureeVie = Integer.parseInt(scanner.nextLine());
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Propriétaire: ");
        String proprietaire = scanner.nextLine();
        serviceClient.ajouterAnimal(nom, espece, dureeVie, description, proprietaire);
    }

    private void ajouterObservation() {
        System.out.print("Nom de l'animal: ");
        String nom = scanner.nextLine();
        System.out.print("Observation: ");
        String observation = scanner.nextLine();
        serviceClient.ajouterObservation(nom, observation);
    }

    private void consulterDossier() {
        System.out.print("Nom de l'animal: ");
        String nom = scanner.nextLine();
        List<String> entrees = serviceClient.consulterDossier(nom);
        if (entrees.isEmpty()) {
            System.out.println("Dossier vide.");
            return;
        }
        System.out.println("Dossier de suivi:");
        for (String entree : entrees) {
            System.out.println("- " + entree);
        }
    }
}
