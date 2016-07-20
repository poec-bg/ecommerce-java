package services;

import com.sun.javaws.exceptions.InvalidArgumentException;
import model.Client;
import model.Panier;
import model.Produit;
import model.ProduitPanier;
import services.date.DateService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PanierService {

    private List<Panier> paniers;

    /**
     * Singleton
     */
    private static PanierService instance;

    /**
     * Constructeur privé = personne ne peut faire de new PanierService()
     */
    private PanierService() {
        paniers = new ArrayList<>();
    }

    /**
     * Seule méthode pour récupérer une instance (toujours la même) de PanierService
     *
     * @return toujours la même instance de PanierService
     */
    public static PanierService get() {
        if (instance == null) {
            instance = new PanierService();
        }
        return instance;
    }

    private Panier creer(Client client) {
        Panier panier = new Panier();
        panier.client = client;
        panier.date = DateService.get().now();
        return panier;
    }

    /**
     * Si le panier n'existe pas alors on en crée un pour ce client
     *
     * @param client
     * @return
     */
    public Panier getPanier(Client client) throws InvalidArgumentException {
        List<String> validationMessages = new ArrayList<>();
        if (client == null) {
            validationMessages.add("Le client ne peut être null");
        }
        if (validationMessages.size() > 0) {
            throw new InvalidArgumentException((String[]) validationMessages.toArray(new String[0]));
        }

        for (Panier panier : paniers) {
            if (panier.client.email.equals(client.email)) {
                return panier;
            }
        }

        Panier panier = creer(client);
        paniers.add(panier);
        return panier;
    }

    public void invalider(Panier panier) throws InvalidArgumentException {
        List<String> validationMessages = new ArrayList<>();
        if (panier == null) {
            validationMessages.add("Le panier ne peut être null");
        }
        if (validationMessages.size() > 0) {
            throw new InvalidArgumentException((String[]) validationMessages.toArray(new String[0]));
        }
        paniers.remove(panier);
    }

    public void ajouterProduit(Panier panier, Produit produit) throws InvalidArgumentException {
        List<String> validationMessages = new ArrayList<>();
        if (panier == null) {
            validationMessages.add("Le panier ne peut être null");
        }
        if (produit == null) {
            validationMessages.add("Le produit ne peut être null");
        }
        if (validationMessages.size() > 0) {
            throw new InvalidArgumentException((String[]) validationMessages.toArray(new String[0]));
        }

        boolean founded = false;
        for (ProduitPanier produitPanier : panier.produits) {
            if (produitPanier.produit.equals(produit)) {
                modifierQuantite(panier, produit, produitPanier.quantite + 1);
                founded = true;
            }
        }

        if (!founded) {
            panier.produits.add(new ProduitPanier(produit, 1));
        }
    }

    public void retirerProduit(Panier panier, Produit produit) throws InvalidArgumentException {
        List<String> validationMessages = new ArrayList<>();
        if (panier == null) {
            validationMessages.add("Le panier ne peut être null");
        }
        if (produit == null) {
            validationMessages.add("Le produit ne peut être null");
        }
        if (validationMessages.size() > 0) {
            throw new InvalidArgumentException((String[]) validationMessages.toArray(new String[0]));
        }

        List<ProduitPanier> tempProduitPaniers = new ArrayList<>();
        for (ProduitPanier produitPanier : panier.produits) {
            if (!produitPanier.produit.id.equals(produit.id)) {
                tempProduitPaniers.add(produitPanier);
            }
        }
        panier.produits = tempProduitPaniers;
    }

    public void modifierQuantite(Panier panier, Produit produit, int quantite) throws InvalidArgumentException {
        List<String> validationMessages = new ArrayList<>();
        if (panier == null) {
            validationMessages.add("Le panier ne peut être null");
        }
        if (produit == null) {
            validationMessages.add("Le produit ne peut être null");
        }
        if (quantite < 0) {
            validationMessages.add("La quantité ne peut être inférieur à 0");
        }
        if (validationMessages.size() > 0) {
            throw new InvalidArgumentException((String[]) validationMessages.toArray(new String[0]));
        }


        if(quantite == 0) {
            retirerProduit(panier, produit);
        } else {
            for (ProduitPanier produitPanier : panier.produits) {
                if (produitPanier.produit.id.equals(produit.id)) {
                    produitPanier.quantite = quantite;
                }
            }
        }
    }
}
