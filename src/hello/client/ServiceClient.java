package hello.client;

import hello.common.Animal;
import hello.common.Cabinet;
import hello.common.Espece;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.List;

public class ServiceClient {
    private final Cabinet cabinet;
    private final ObservateurImpl observateur;

    public ServiceClient(Cabinet cabinet) throws RemoteException {
        this.cabinet = cabinet;
        this.observateur = new ObservateurImpl();
        this.cabinet.abonner(observateur);
    }

    public void ajouterAnimal(String nom, String especeNom, int dureeVieMoyenne, String description, String proprietaire) {
        try {
            cabinet.ajouterAnimal(nom, new Espece(especeNom, dureeVieMoyenne, description), proprietaire);
            System.out.println("Animal ajouté avec succès.");
        } catch (RemoteException e) {
            System.err.println("Échec de l'ajout: " + e.getMessage());
        }
    }

    public void listerAnimaux() {
        try {
            List<Animal> animaux = cabinet.listerAnimaux();
            if (animaux.isEmpty()) {
                System.out.println("Aucun animal enregistré.");
                return;
            }
            for (Animal animal : animaux) {
                System.out.println("- " + animal.getNom() + " | " + animal.getEspece() + " | propriétaire: " + animal.getProprietaire());
            }
        } catch (RemoteException e) {
            System.err.println("Échec de la récupération: " + e.getMessage());
        }
    }

    public void ajouterObservation(String nomAnimal, String observation) {
        try {
            cabinet.ajouterObservation(nomAnimal, observation);
            System.out.println("Observation enregistrée.");
        } catch (RemoteException e) {
            System.err.println("Échec de l'ajout d'observation: " + e.getMessage());
        }
    }

    public List<String> consulterDossier(String nomAnimal) {
        try {
            Animal animal = cabinet.rechercherAnimal(nomAnimal);
            if (animal == null) {
                System.out.println("Animal introuvable.");
                return Collections.emptyList();
            }
            return animal.getDossier().getEntrees();
        } catch (RemoteException e) {
            System.err.println("Échec de consultation du dossier: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public void fermer() {
        try {
            cabinet.desabonner(observateur);
            UnicastRemoteObject.unexportObject(observateur, true);
        } catch (RemoteException e) {
            System.err.println("Erreur de désabonnement: " + e.getMessage());
        }
    }
}
