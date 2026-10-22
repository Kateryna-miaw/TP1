package hello.server;

import hello.common.Animal;
import hello.common.DossierDeSuivi;
import hello.common.Espece;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AnimalImpl extends UnicastRemoteObject implements Animal {
    private final String nom;
    private final Espece espece;
    private final String proprietaire;
    private final DossierDeSuivi dossier;

    public AnimalImpl(String nom, Espece espece, String proprietaire) throws RemoteException {
        super();
        this.nom = nom;
        this.espece = espece;
        this.proprietaire = proprietaire;
        this.dossier = new DossierDeSuiviImpl();
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public Espece getEspece() {
        return espece;
    }

    @Override
    public String getProprietaire() {
        return proprietaire;
    }

    @Override
    public DossierDeSuivi getDossier() {
        return dossier;
    }
}
