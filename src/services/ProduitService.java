package services;

import com.sun.javaws.exceptions.InvalidArgumentException;
import model.Produit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProduitService {

    private List<Produit> produits;

    /**
     * Singleton
     */
    private static ProduitService instance;

    /**
     * Constructeur privé = personne ne peut faire de new ProduitService()
     */
    private ProduitService() {
        produits = new ArrayList<>();
    }

    /**
     * Seule méthode pour récupérer une instance (toujours la même) de ProduitService
     *
     * @return toujours la même instance de ProduitService
     */
    public static ProduitService get() {
        if (instance == null) {
            instance = new ProduitService();
        }
        return instance;
    }

    public Produit creer(String nom, String description, float prixUnitaire) throws InvalidArgumentException {

        List<String> validationMessages = new ArrayList<>();
        if (nom == null || nom.equals("")) {
            validationMessages.add("Le nom ne peut être null ou vide");
        }
        if (prixUnitaire < 0) {
            validationMessages.add("Le prix unitaire ne peut être inférieur à 0");
        }
        if (validationMessages.size() > 0) {
            throw new InvalidArgumentException((String[]) validationMessages.toArray(new String[0]));
        }

        Produit produit = new Produit();
        produit.id = UUID.randomUUID().toString();
        produit.nom = nom;
        produit.description = description;
        produit.prixUnitaire = prixUnitaire;
        return produit;
    }

    public void modifier(Produit produit, String nom, String description, float prixUnitaire) throws InvalidArgumentException {

        List<String> validationMessages = new ArrayList<>();
        if (produit == null) {
            validationMessages.add("Le produit ne peut être null");
        }
        if (nom == null || "".equals(nom)) {
            validationMessages.add("Le nom ne peut être null ou vide");
        }
        if (prixUnitaire < 0) {
            validationMessages.add("Le prix unitaire ne peut être inférieur à 0");
        }
        if (validationMessages.size() > 0) {
            throw new InvalidArgumentException((String[]) validationMessages.toArray(new String[0]));
        }

        produit.nom = nom;
        produit.description = description;
        produit.prixUnitaire = prixUnitaire;
    }

    public void supprimer(Produit produit) throws InvalidArgumentException {
        if (produit == null) {
            throw new InvalidArgumentException(new String[]{"Le produit ne peut être null"});
        }
        produit.isSupprime = true;
    }

    public void categoriser(Produit produit, String categorie) {
        System.out.println("categoriser");
    }

    public List<Produit> lister() {
        return produits;
    }

    public void enregistrer(Produit produit) {
        this.produits.add(produit);
    }

    public Produit getProduit(String idProduit) throws InvalidArgumentException {

        List<String> validationMessages = new ArrayList<>();
        if (idProduit == null || idProduit.equals("")) {
            validationMessages.add("L'idProduit ne peut être null ou vide");
        }
        if (validationMessages.size() > 0) {
            throw new InvalidArgumentException((String[]) validationMessages.toArray(new String[0]));
        }

        for (Produit produit : produits) {
            if (produit.id.equals(idProduit)) {
                return produit;
            }
        }
        return null;
    }

    public void clear() {
        produits = new ArrayList<>();
    }
}
