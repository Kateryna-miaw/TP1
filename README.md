# README : Cabinet Vétérinaire Distribué (Java RMI)

## 1. Rôle de chaque module et relations entre eux

L'architecture est divisée en trois modules distincts :

* **Module `common`** : Contient les interfaces distantes (`Animal`, `Cabinet`, `DossierDeSuivi`, `Observateur`) et les objets transmis par valeur (`Espece`, `Chien` implémentant `Serializable`). C'est le contrat partagé entre le client et le serveur.

* **Module `server`** : Héberge les implémentations réelles (`AnimalImpl`, `CabinetImpl`, `DossierDeSuiviImpl`) et le script `Server` qui instancie le registre et y publie l'objet central `cabinet`.

* **Module `client`** : Contient la logique applicative côté utilisateur et la communication réseau. Il ne dialogue avec le serveur qu'au travers des interfaces de `common`.

## 2. Organisation du client (Interface, Logique, RMI)

Le client est structuré en trois couches pour séparer les responsabilités :

* **`MainClient.java`** : Point d'entrée qui établit la connexion réseau initiale via `LocateRegistry`.

* **`ConsoleUI.java`** : Gère exclusivement l'affichage et les saisies (`Scanner`). Ce fichier ne contient aucune importation liée à `java.rmi`.

* **`ServiceClient.java`** : Encapsule l'objet distant `Cabinet`. Cette classe gère les exceptions réseau (`RemoteException`) et l'export/unexport de l'objet `ObservateurImpl` utilisé pour recevoir les callbacks du serveur.

## 3. Environnement, Configuration et Hypothèses

* **Environnement de développement** : IntelliJ IDEA (version 2023.3.3) utilisé pour l'écriture du code, et outils en ligne de commande (terminal) pour la compilation et l'exécution.

* **Version du JDK** : Java 17 ou supérieur (le projet créé sous Java 21).
  
* **Dépendances** : Aucune dépendance externe, RMI natif uniquement.

* **Ports et Registre** : Utilisation du port standard **1099**. Le registre RMI est instancié directement de manière embarquée (mode interne) par l'application serveur pour simplifier le déploiement.

## 4. Commandes de compilation et ordre de démarrage

*Depuis la racine du projet (contenant les dossiers `src` et `bin`) :*

**A. Compilation des sources**

```bash
javac -d bin src/hello/common/*.java src/hello/server/*.java src/hello/client/*.java
```

**B. Démarrage du Serveur (Ordre 1)**
Le serveur doit être lancé avec l'argument `--embedded` pour qu'il crée lui-même le registre RMI sur le port 1099.

```bash
java -cp bin hello.server.Server --embedded
```

**C. Démarrage du Client (Ordre 2)**
Dans un nouveau terminal. Il prend en paramètres l'IP et le port (par défaut localhost 1099).

```bash
java -cp bin hello.client.MainClient localhost 1099
```

## 5. Lancement de la CLI, saisies et arrêt propre

Une fois le client lancé, un menu interactif (1-6) s'affiche. Les saisies invalides (comme taper des lettres au lieu d'un nombre pour l'espérance de vie) sont bloquées par une boucle jusqu'à saisie d'un entier valide.

**Attention - Arrêt propre :** Pour fermer le client, il faut obligatoirement utiliser l'option **`0. Quitter`**. Cela ordonne au `ServiceClient` de désabonner l'observateur auprès du serveur et de forcer la libération du thread RMI via `UnicastRemoteObject.unexportObject()`, permettant à la machine virtuelle de s'arrêter proprement sans laisser de processus fantôme.
