package hello.server;

import hello.common.Animal;
import hello.common.Cabinet;
import hello.common.Espece;
import hello.common.Observateur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CabinetImpl extends UnicastRemoteObject implements Cabinet {
    private static final int[] SEUILS_PATIENTS = {5, 10, 20};

    private final List<Animal> animaux;
    private final List<Observateur> observateurs;
    private final Set<Integer> seuilsDejaNotifies;

    public CabinetImpl() throws RemoteException {
        super();
        this.animaux = new ArrayList<>();
        this.observateurs = new ArrayList<>();
        this.seuilsDejaNotifies = new HashSet<>();
    }

    @Override
    public synchronized void ajouterAnimal(String nom, Espece espece, String proprietaire) throws RemoteException {
        if (rechercherAnimalInterne(nom) != null) {
            throw new RemoteException("Un animal avec ce nom existe déjà : " + nom);
        }
        animaux.add(new AnimalImpl(nom, espece, proprietaire));
        verifierEtNotifierSeuils();
    }

    @Override
    public synchronized Animal rechercherAnimal(String nom) {
        return rechercherAnimalInterne(nom);
    }

    @Override
    public synchronized List<Animal> listerAnimaux() {
        return new ArrayList<>(animaux);
    }

    @Override
    public synchronized void supprimerAnimal(String nom) throws RemoteException {
        Animal animal = rechercherAnimalInterne(nom);
        if (animal == null) {
            throw new RemoteException("Animal introuvable : " + nom);
        }
        animaux.remove(animal);
    }

    @Override
    public synchronized void ajouterObservation(String nomAnimal, String observation) throws RemoteException {
        Animal animal = rechercherAnimalInterne(nomAnimal);
        if (animal == null) {
            throw new RemoteException("Animal introuvable : " + nomAnimal);
        }
        animal.getDossier().ajouterEntree(observation);
    }

    @Override
    public synchronized int nombrePatients() {
        return animaux.size();
    }

    @Override
    public synchronized void abonner(Observateur observateur) {
        observateurs.add(observateur);
    }

    @Override
    public synchronized void desabonner(Observateur observateur) {
        observateurs.remove(observateur);
    }

    private Animal rechercherAnimalInterne(String nom) {
        for (Animal animal : animaux) {
            try {
                if (animal.getNom().equalsIgnoreCase(nom)) {
                    return animal;
                }
            } catch (RemoteException ignored) {
                // Les implémentations sont locales au serveur, cet appel ne devrait pas échouer.
            }
        }
        return null;
    }

    private void verifierEtNotifierSeuils() {
        int nbPatients = animaux.size();
        for (int seuil : SEUILS_PATIENTS) {
            if (nbPatients >= seuil && seuilsDejaNotifies.add(seuil)) {
                notifierObservateurs("Alerte: le cabinet a atteint " + nbPatients + " patients (seuil " + seuil + ").");
            }
        }
    }

    private void notifierObservateurs(String message) {
        Iterator<Observateur> iterator = observateurs.iterator();
        while (iterator.hasNext()) {
            Observateur observateur = iterator.next();
            try {
                observateur.notifierAlerte(message);
            } catch (RemoteException e) {
                iterator.remove();
            }
        }
    }
}
