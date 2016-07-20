import com.sun.javaws.exceptions.InvalidArgumentException;
import model.Client;
import model.Panier;
import model.Produit;
import model.ProduitPanier;
import org.junit.Test;
import services.ClientService;
import services.PanierService;
import services.ProduitService;
import services.date.DateService;
import services.date.FixedDateService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.Assert.*;

public class PanierServiceTest {

    // getPanier
    @Test
    public void testGetPanier_everythingWrong() {
        // Given
        Client client = null;

        // When
        try {
            Panier panier = PanierService.get().getPanier(client);
            fail();
        } catch (InvalidArgumentException e) {
            // Then
            assertTrue(true);
        }
    }

    @Test
    public void testGetPanier_clientWithoutPanier() {
        // Given
        Client client = null;
        try {
            client = ClientService.get().creer("han.solo@gmail.com", "0123456789");
        } catch (Exception e) {
            fail();
        }

        // When
        try {
            Panier panier = PanierService.get().getPanier(client);
            // Then
            assertEquals(client.email, panier.client.email);
        } catch (InvalidArgumentException e) {
            fail();
        }
    }

    @Test
    public void testGetPanier_clientWithPanier() {
        // Given
        Client client = null;
        Panier panier = null;
        try {
            client = ClientService.get().creer("luke.skywalker@gmail.com", "0123456789");
            panier = PanierService.get().getPanier(client);
        } catch (Exception e) {
            fail();
        }

        // When
        try {
            Panier panier2 = PanierService.get().getPanier(client);
            // Then
            assertEquals(panier, panier2);
        } catch (InvalidArgumentException e) {
            fail();
        }
    }
    // ----------------------

    // invalider
    @Test
    public void testInvalider_everythingOk() {
        // Given
        Client client = null;
        Panier panier = null;
        try {
            client = ClientService.get().creer("luke.skywalker@gmail.com", "0123456789");
            panier = PanierService.get().getPanier(client);
        } catch (Exception e) {
            fail();
        }

        // When
        try {
            PanierService.get().invalider(panier);
            Panier newPanier = PanierService.get().getPanier(client);
            // Then
            assertNotEquals(panier, newPanier);
        } catch (InvalidArgumentException e) {
            fail();
        }
    }

    @Test
    public void testInvalider_everythingWrong() {
        // Given
        Panier panier = null;

        // When
        try {
            PanierService.get().invalider(panier);
            fail();
        } catch (InvalidArgumentException e) {
            // Then
            assertTrue(e.getRealMessage().contains("Le panier ne peut être null"));
        }
    }
    // ----------------------

    // ajouterProduit
    @Test
    public void testAjouterProduit_everythingWrong() {
        // Given
        Panier panier = null;
        Produit produit = null;

        // When
        try {
            PanierService.get().ajouterProduit(panier, produit);
            fail();
        } catch (InvalidArgumentException e) {
            // Then
            assertTrue(e.getRealMessage().contains("Le panier ne peut être null"));
            assertTrue(e.getRealMessage().contains("Le produit ne peut être null"));
        }
    }

    @Test
    public void testAjouterProduit_everythingOk() {
        // Given
        Client client;
        Panier panier = null;
        Produit produit = null;
        try {
            client = ClientService.get().creer("luke.skywalker@gmail.com", "0123456789");
            panier = PanierService.get().getPanier(client);
            produit = ProduitService.get().creer("Classeur", null, 5.5f);
        } catch (Exception e) {
            fail();
        }

        // When
        try {
            PanierService.get().ajouterProduit(panier, produit);
            // Then
            assertEquals(1, panier.produits.size());
        } catch (InvalidArgumentException e) {
            fail();
        } finally {
            try {
                PanierService.get().invalider(panier);
            } catch (InvalidArgumentException e) {
                fail();
            }
        }
    }

    @Test
    public void testAjouterProduit_alreadyAdded() {
        // Given
        Client client;
        Panier panier = null;
        Produit produit = null;
        try {
            client = ClientService.get().creer("luke.skywalker@gmail.com", "0123456789");
            panier = PanierService.get().getPanier(client);
            produit = ProduitService.get().creer("Classeur", null, 5.5f);
        } catch (Exception e) {
            fail();
        }

        // When
        try {
            PanierService.get().ajouterProduit(panier, produit);
            PanierService.get().ajouterProduit(panier, produit);
            // Then
            assertEquals(1, panier.produits.size());
        } catch (InvalidArgumentException e) {
            fail();
        } finally {
            try {
                PanierService.get().invalider(panier);
            } catch (InvalidArgumentException e) {
                fail();
            }
        }
    }
    // ----------------------

    // retirerProduit
    @Test
    public void testRetirerProduit_everythingWrong() {
        // Given
        Panier panier = null;
        Produit produit = null;

        // When
        try {
            PanierService.get().retirerProduit(panier, produit);
            fail();
        } catch (InvalidArgumentException e) {
            // Then
            assertTrue(e.getRealMessage().contains("Le panier ne peut être null"));
            assertTrue(e.getRealMessage().contains("Le produit ne peut être null"));
        }
    }

    @Test
    public void testRetirerProduit_productNotFoundInPanier() {
        // Given
        Client client;
        Panier panier = null;
        Produit produit = null;
        try {
            client = ClientService.get().creer("luke.skywalker@gmail.com", "0123456789");
            panier = PanierService.get().getPanier(client);
            produit = ProduitService.get().creer("Classeur", null, 5.5f);
        } catch (Exception e) {
            fail();
        }

        // When
        try {
            PanierService.get().retirerProduit(panier, produit);
            // Then
            assertEquals(0, panier.produits.size());
        } catch (InvalidArgumentException e) {
            fail();
        } finally {
            try {
                PanierService.get().invalider(panier);
            } catch (InvalidArgumentException e) {
                fail();
            }
        }
    }

    @Test
    public void testRetirerProduit_everythingOk() {
        // Given
        Client client;
        Panier panier = null;
        Produit produit = null;
        try {
            client = ClientService.get().creer("luke.skywalker@gmail.com", "0123456789");
            panier = PanierService.get().getPanier(client);
            produit = ProduitService.get().creer("Classeur", null, 5.5f);
            PanierService.get().ajouterProduit(panier, produit);
            PanierService.get().ajouterProduit(panier, ProduitService.get().creer("Intercalaire", null, 2.75f));
        } catch (Exception e) {
            fail();
        }

        // When
        try {
            PanierService.get().retirerProduit(panier, produit);
            // Then
            assertEquals(1, panier.produits.size());
        } catch (InvalidArgumentException e) {
            fail();
        } finally {
            try {
                PanierService.get().invalider(panier);
            } catch (InvalidArgumentException e) {
                fail();
            }
        }
    }
    // ----------------------

    // modifierQuantite
    @Test
    public void testModifierQuantite_everythingWrong() {
        // Given
        Panier panier = null;
        Produit produit = null;
        int quantite = -1;

        // When
        try {
            PanierService.get().modifierQuantite(panier, produit, quantite);
            fail();
        } catch (InvalidArgumentException e) {
            // Then
            assertTrue(e.getRealMessage().contains("Le panier ne peut être null"));
            assertTrue(e.getRealMessage().contains("Le produit ne peut être null"));
            assertTrue(e.getRealMessage().contains("La quantité ne peut être inférieur à 0"));
        }
    }

    @Test
    public void testModifierQuantite_quantityThree() {
        // Given
        Client client;
        Panier panier = null;
        Produit produit = null;
        int quantite = 3;
        try {
            client = ClientService.get().creer("luke.skywalker@gmail.com", "0123456789");
            panier = PanierService.get().getPanier(client);
            produit = ProduitService.get().creer("Classeur", null, 5.5f);
            PanierService.get().ajouterProduit(panier, produit);
        } catch (Exception e) {
            fail();
        }

        // When
        try {
            PanierService.get().modifierQuantite(panier, produit, quantite);

            // Then
            for (ProduitPanier produitPanier : panier.produits) {
                if (produitPanier.produit.id.equals(produit.id)) {
                    assertEquals(quantite, produitPanier.quantite);
                    break;
                }
            }
        } catch (InvalidArgumentException e) {
            fail();
        } finally {
            try {
                PanierService.get().invalider(panier);
            } catch (InvalidArgumentException e) {
                fail();
            }
        }
    }

    @Test
    public void testModifierQuantite_quantityZero() {
        // Given
        Client client;
        Panier panier = null;
        Produit produit = null;
        int quantite = 0;
        try {
            client = ClientService.get().creer("luke.skywalker@gmail.com", "0123456789");
            panier = PanierService.get().getPanier(client);
            produit = ProduitService.get().creer("Classeur", null, 5.5f);
            PanierService.get().ajouterProduit(panier, produit);
        } catch (Exception e) {
            fail();
        }

        // When
        try {
            PanierService.get().modifierQuantite(panier, produit, quantite);

            // Then
            for (ProduitPanier produitPanier : panier.produits) {
                if (produitPanier.produit.id.equals(produit.id)) {
                    fail();
                }
            }
        } catch (InvalidArgumentException e) {
            fail();
        } finally {
            try {
                PanierService.get().invalider(panier);
            } catch (InvalidArgumentException e) {
                fail();
            }
        }
    }
    // ----------------------
}
