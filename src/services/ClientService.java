package services;

import com.sun.javaws.exceptions.InvalidArgumentException;
import exceptions.MetierException;
import model.Client;
import validators.EmailValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientService {

    private List<Client> clients;

    /**
     * Singleton
     */
    private static ClientService instance;

    /**
     * Constructeur privé = personne ne peut faire de new ClientService()
     */
    private ClientService() {
        clients = new ArrayList<>();
    }

    /**
     * Seule méthode pour récupérer une instance (toujours la même) de ClientService
     *
     * @return toujours la même instance de ClientService
     */
    public static ClientService get() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }

    public Client creer(String email, String motDePasse) throws InvalidArgumentException, MetierException {

        List<String> validationMessages = new ArrayList<>();
        if (email == null || email.equals("")) {
            validationMessages.add("Le email ne peut être null ou vide");
        } else {
            if (!EmailValidator.validate(email)) {
                validationMessages.add("Le format de l'email est invalide");
            }
        }
        if (motDePasse == null || motDePasse.equals("")) {
            validationMessages.add("Le motDePasse ne peut être null ou vide");
        }
        if (validationMessages.size() > 0) {
            throw new InvalidArgumentException((String[]) validationMessages.toArray(new String[0]));
        }

        for (Client client : clients) {
            if(client.email.equals(email)) {
                throw new MetierException("Cet email est déjà utilisé");
            }
        }

        Client client = new Client();
        client.id = UUID.randomUUID().toString();
        client.email = email;
        client.motDePasse = motDePasse;
        return client;
    }

    public void modifier(Client client, String nom, String prenom, String adressePostale, String telephone) throws InvalidArgumentException {

        List<String> validationMessages = new ArrayList<>();
        if (client == null) {
            validationMessages.add("Le client ne peut être null");
        }
        if (validationMessages.size() > 0) {
            throw new InvalidArgumentException((String[]) validationMessages.toArray(new String[0]));
        }

        if (nom != null && !nom.equals("")) {
            client.nom = nom;
        }
        if (prenom != null && !prenom.equals("")) {
            client.prenom = prenom;
        }
        if (adressePostale != null && !adressePostale.equals("")) {
            client.adressePostale = adressePostale;
        }
        if (telephone != null && !telephone.equals("")) {
            client.telephone = telephone;
        }

    }

    public void supprimer(Client client) throws InvalidArgumentException {
        if (client == null) {
            throw new InvalidArgumentException(new String[]{"Le client ne peut être null"});
        }
        client.isSupprime = true;
    }

    public Client fusionner(Client client1, Client client2) throws InvalidArgumentException {

        List<String> validationMessages = new ArrayList<>();
        if (client1 == null) {
            validationMessages.add("Le client1 ne peut être null");
        }
        if (client2 == null) {
            validationMessages.add("Le client2 ne peut être null");
        }
        if (validationMessages.size() > 0) {
            throw new InvalidArgumentException((String[]) validationMessages.toArray(new String[0]));
        }

        if ((client1.nom == null || client1.nom.equals("")) && (client2.nom != null && !client2.nom.equals(""))) {
            client1.nom = client2.nom;
        }
        if ((client1.prenom == null || client1.prenom.equals("")) && (client2.prenom != null && !client2.prenom.equals(""))) {
            client1.prenom = client2.prenom;
        }
        if ((client1.adressePostale == null || client1.adressePostale.equals("")) && (client2.adressePostale != null && !client2.adressePostale.equals(""))) {
            client1.adressePostale = client2.adressePostale;
        }
        if ((client1.telephone == null || client1.telephone.equals("")) && (client2.telephone != null && !client2.telephone.equals(""))) {
            client1.telephone = client2.telephone;
        }
        return client1;
    }

    public boolean authenticate(String email, String motDePasse) throws InvalidArgumentException {

        List<String> validationMessages = new ArrayList<>();
        if (email == null || email.equals("")) {
            validationMessages.add("Le email ne peut être null ou vide");
        }
        if (motDePasse == null || motDePasse.equals("")) {
            validationMessages.add("Le motDePasse ne peut être null ou vide");
        }
        if (validationMessages.size() > 0) {
            throw new InvalidArgumentException((String[]) validationMessages.toArray(new String[0]));
        }

        for (Client client : clients) {
            if(client.email.equals(email) && client.motDePasse.equals(motDePasse)) {
                return true;
            }
        }

        return false;
    }

    public List<Client> lister() {
        return clients;
    }

    public void enregistrer(Client client) {
        this.clients.add(client);
    }

    public Client getClient(String idClient) throws InvalidArgumentException {

        List<String> validationMessages = new ArrayList<>();
        if (idClient == null || idClient.equals("")) {
            validationMessages.add("L'idClient ne peut être null ou vide");
        }
        if (validationMessages.size() > 0) {
            throw new InvalidArgumentException((String[]) validationMessages.toArray(new String[0]));
        }

        for (Client client : clients) {
            if(client.id.equals(idClient)) {
                return client;
            }
        }
        return null;
    }

    public void clear() {
        clients = new ArrayList<>();
    }
}
