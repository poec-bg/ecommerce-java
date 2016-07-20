import com.sun.javaws.exceptions.InvalidArgumentException;
import model.*;
import org.h2.tools.DeleteDbFiles;
import services.ClientService;
import services.PanierService;
import services.ProduitService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Lanceur {
    public static void main(String[] args) throws IOException {
//        try {
//            Produit p1 = ProduitService.get().creer("Classeur", "Un très joli classeur", 5);
//            System.out.println(p1.nom);
//        } catch (InvalidArgumentException e) {
//            System.out.println(e.getRealMessage());
//        }

//        Produit p2 = new Produit();
//        p2.nom = "Intercalaire";
//        p2.prixUnitaire = 0.5f;
//
//        Produit p3 = new Produit();
//        p3.nom = "Stylo";
//        p3.prixUnitaire = (float) 1.2;
//
//        Client client1 = new Client();
//        client1.nom = "Luke";
//        client1.prenom = "Skywalker";
//        client1.adressePostale = "2 rue de mos esley, Tatoiine";
//
//        Panier panier = new Panier();
//        panier.client = client1;
//        panier.date = new Date();
//        panier.produits = new ArrayList<>();
//        panier.produits.add(new ProduitPanier(p3, 2));
//
//        Commande commande = Commande.creer(panier);
//        commande.affiche();

//        try {
////            DeleteDbFiles.execute("~", "test", true);
//
//            Class.forName("org.h2.Driver");
//            try (Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", ""); Statement stat = conn.createStatement()) {
//
////                stat.execute("create table test(id int primary key, name varchar(255))");
////
////                stat.execute("insert into test values(1, 'Hello')");
//
//                try (ResultSet rs = stat.executeQuery("select * from test")) {
//                    while (rs.next()) {
//                        System.out.println(rs.getString("name"));
//                    }
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try {
//            ClientService.get().enregistrer(ClientService.get().creer("luke.skywalker@gmail.com", "0123456789"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        try {
            Client client = ClientService.get().creer("nicolas.giard@coaxys.com", null);
            ClientService.get().modifier(client, "Giard", "Nicolas", "", "");
            ClientService.get().enregistrer(client);
            Produit produitClasseur = ProduitService.get().creer("Classeur", "Super Classeur", 5.5f);
            ProduitService.get().enregistrer(produitClasseur);
            Produit produitIntercalaire = ProduitService.get().creer("Intercalaire", "Rouge", 2.75f);
            ProduitService.get().enregistrer(produitIntercalaire);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Menu:");
            System.out.println("[0] : exit");
            System.out.println("[1] : créer un Produit");
            System.out.println("[2] : lister les Produits");
            System.out.println("[3] : créer un Client");
            System.out.println("[4] : lister les Clients");
            System.out.println("[5] : Ajouter un produit au panier");
            System.out.println("[6] : Détail d'un client");
            System.out.print("Votre choix : ");

            String command = br.readLine();
            if (command.equals("0")) {
                System.out.println("Au revoir");
                break;
            } else if (command.equals("1")) {
                System.out.println("Veuillez saisir les données du Produit");
                System.out.print("Nom : ");
                String nom = br.readLine();
                System.out.print("Description : ");
                String description = br.readLine();
                System.out.print("Prix Unitaire : ");
                float prixUnitaire = Float.parseFloat(br.readLine());

                try {
                    Produit produit = ProduitService.get().creer(nom, description, prixUnitaire);
                    ProduitService.get().enregistrer(produit);
                    System.out.println("Vous venez de créer le produit : " + produit.nom);
                } catch (InvalidArgumentException e) {
                    e.printStackTrace();
                }
            } else if (command.equals("2")) {
                List<Produit> produits = ProduitService.get().lister();
                for (Produit produit : produits) {
                    System.out.println(String.format("%s / %s / %s / %f", produit.id, produit.nom, produit.description, produit.prixUnitaire));
                }
            } else if (command.equals("3")) {
                System.out.println("Veuillez saisir les données du Client");
                System.out.print("Email : ");
                String email = br.readLine();
                System.out.print("Mot de Passe : ");
                String motDePasse = br.readLine();
                System.out.print("Nom : ");
                String nom = br.readLine();
                System.out.print("Prenom : ");
                String prenom = br.readLine();
                try {
                    Client client = ClientService.get().creer(email, motDePasse);
                    ClientService.get().modifier(client, nom, prenom, null, null);
                    ClientService.get().enregistrer(client);
                    System.out.println("Vous venez de créer le client : " + client.nom + " " + client.prenom);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (command.equals("4")) {
                List<Client> clients = ClientService.get().lister();
                for (Client client : clients) {
                    System.out.println(String.format("%s / %s / %s / %s", client.id, client.email, client.nom, client.prenom));
                }
            } else if (command.equals("5")) {
                System.out.print("Veuillez saisir l'id du client : ");
                String idClient = br.readLine();
                System.out.print("Veuillez saisir l'id du produit : ");
                String idProduit = br.readLine();
                try {
                    Client client = ClientService.get().getClient(idClient);
                    Panier panier = PanierService.get().getPanier(client);
                    Produit produit = ProduitService.get().getProduit(idProduit);
                    PanierService.get().ajouterProduit(panier, produit);
                } catch (InvalidArgumentException e) {
                    e.printStackTrace();
                }
            }else if (command.equals("6")) {
                System.out.print("Veuillez saisir l'id du client : ");
                String idClient = br.readLine();
                try {
                    Client client = ClientService.get().getClient(idClient);
                    Panier panier = PanierService.get().getPanier(client);
                    System.out.println(String.format("%s / %s / %s / %s", client.id, client.email, client.nom, client.prenom));
                    for (ProduitPanier produit : panier.produits) {
                        System.out.println(String.format("%s / %.2f / %d", produit.produit.nom, produit.produit.prixUnitaire, produit.quantite));
                    }
                } catch (InvalidArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
