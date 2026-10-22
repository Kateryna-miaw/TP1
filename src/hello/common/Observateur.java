package hello.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observateur extends Remote {
    void notifierAlerte(String message) throws RemoteException;
}
