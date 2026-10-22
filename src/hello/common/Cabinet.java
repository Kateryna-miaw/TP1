package hello.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Cabinet extends Remote {
    void ajouterAnimal(String nom, Espece espece, String proprietaire) throws RemoteException;

    Animal rechercherAnimal(String nom) throws RemoteException;

    List<Animal> listerAnimaux() throws RemoteException;

    void supprimerAnimal(String nom) throws RemoteException;

    void ajouterObservation(String nomAnimal, String observation) throws RemoteException;

    int nombrePatients() throws RemoteException;

    void abonner(Observateur observateur) throws RemoteException;

    void desabonner(Observateur observateur) throws RemoteException;
}
