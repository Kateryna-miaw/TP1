package hello.server;

import hello.common.DossierDeSuivi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class DossierDeSuiviImpl extends UnicastRemoteObject implements DossierDeSuivi {
    private final List<String> entrees;

    public DossierDeSuiviImpl() throws RemoteException {
        super();
        this.entrees = new ArrayList<>();
    }

    @Override
    public synchronized void ajouterEntree(String entree) {
        entrees.add(entree);
    }

    @Override
    public synchronized List<String> getEntrees() {
        return new ArrayList<>(entrees);
    }
}
