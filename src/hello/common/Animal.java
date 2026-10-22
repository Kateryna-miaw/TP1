package hello.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Animal extends Remote {
    String getNom() throws RemoteException;

    Espece getEspece() throws RemoteException;

    String getProprietaire() throws RemoteException;

    DossierDeSuivi getDossier() throws RemoteException;
}
