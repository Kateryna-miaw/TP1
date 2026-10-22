package hello.client;

import hello.common.Observateur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObservateurImpl extends UnicastRemoteObject implements Observateur {
    public ObservateurImpl() throws RemoteException {
        super();
    }

    @Override
    public void notifierAlerte(String message) {
        System.out.println("\n[ALERTE SERVEUR] " + message);
        System.out.print("> ");
    }
}
