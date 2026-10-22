package hello.server;

import hello.common.Cabinet;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            Cabinet cabinet = new CabinetImpl();
            registry.rebind("CabinetVeterinaire", cabinet);
            System.out.println("Serveur RMI démarré sur le port 1099.");
        } catch (Exception e) {
            System.err.println("Erreur serveur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
