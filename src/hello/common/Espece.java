package hello.common;

import java.io.Serial;
import java.io.Serializable;

public class Espece implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String nom;
    private final int dureeVieMoyenne;
    private final String description;

    public Espece(String nom, int dureeVieMoyenne, String description) {
        this.nom = nom;
        this.dureeVieMoyenne = dureeVieMoyenne;
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public int getDureeVieMoyenne() {
        return dureeVieMoyenne;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return nom + " (" + dureeVieMoyenne + " ans)";
    }
}
