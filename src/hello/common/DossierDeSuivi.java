package hello.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DossierDeSuivi extends Remote {
    void ajouterEntree(String entree) throws RemoteException;

    List<String> getEntrees() throws RemoteException;
}
