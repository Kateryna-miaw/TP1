package hello.client;

import hello.common.Cabinet;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Cabinet cabinet = (Cabinet) registry.lookup("CabinetVeterinaire");
            ServiceClient serviceClient = new ServiceClient(cabinet);
            ConsoleUI ui = new ConsoleUI(serviceClient);
            ui.lancer();
        } catch (Exception e) {
            System.err.println("Erreur client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
