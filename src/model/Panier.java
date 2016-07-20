package model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Panier {
    public int id;
    public Client client;
    public Instant date;
    public List<ProduitPanier> produits;

    public Panier() {
        produits = new ArrayList<>();
    }
}
