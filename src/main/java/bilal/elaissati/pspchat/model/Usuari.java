package bilal.elaissati.pspchat.model;

public class Usuari {
    private String nom;
    private String contrasenya;

    public Usuari(String nom, String contrasenya) {
        this.nom = nom;
        this.contrasenya = contrasenya;
    }

    public String getNom() {
        return nom;
    }

    public String getContrasenya() {
        return contrasenya;
    }
}
