package lucas;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clasa care reprezinta o factura, informatiile despre client, produse, suma, cat sa platit, si data facturarii
 * @author lucas
 * 
 */
public class Factura {
    private String numeClient;
    private String cnp;
    private String telefon;
    private String produse;
    private double sumaTotala;
    private double sumaPlatita;
    private String data;
    private boolean estePlatita;  // Câmpul pentru statusul plății

    /**
     * Constructor parametrizabil cu toti parametrii
     * @param numeClient: Numele Clientului
     * @param cnp: CNP-ul Clientului
     * @param telefon: Telefonul Clientului
     * @param produse: Produsele cumparate de Client
     * @param sumaTotala: Suma totala a facturii
     * @param sumaPlatita: Suma platita a facturii
     * @param data: Data facturarii
     * @param estePlatita: Indica daca factura a fost platita
     */
    public Factura(String numeClient, String cnp, String telefon, String produse, double sumaTotala, double sumaPlatita, String data, boolean estePlatita) {
        this.numeClient = numeClient;
        this.cnp = cnp;
        this.telefon = telefon;
        this.produse = produse;
        this.sumaTotala = sumaTotala;
        this.sumaPlatita = sumaPlatita;
        this.data = data;
        this.estePlatita = estePlatita;  // Inițializare status plată
    }

    // Gettere
    public String getNumeClient() {
        return numeClient;
    }

    public String getCnp() {
        return cnp;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getProduse() {
        return produse;
    }

    public double getSumaTotala() {
        return sumaTotala;
    }

    public double getSumaPlatita() {
        return sumaPlatita;
    }
    
    public String getData() {
        return data;
    }
    
    public boolean isEstePlatita() {
        return estePlatita;  // Returnează statusul plății
    }
    
    public void setEstePlatita(boolean estePlatita) {
        this.estePlatita = estePlatita;  // Setează statusul plății
    }

    /**
     * Functie de platit
     * Adauga o suma la suma deja platita
     * @param suma: Cat vrei sa platesti
     */
    public void plateste(double suma) {
        this.sumaPlatita += suma;
    }

    /**
     * Verific daca factura a fost complet platita
     * Compara Suma totala cu suma platita 
     * @return true daca suma platita este mai mare sau egala cu suma totala
     */
    public boolean estePlatita() {
        return sumaPlatita >= sumaTotala;  // Verifică dacă factura este complet plătită
    }

    /**
     * Formeaza factura ca sir separat de virgule
     */
    public String toString() {
        return numeClient + "," + cnp + "," + telefon + "," + produse + "," + sumaTotala + "," + sumaPlatita + "," + data + "," + estePlatita;
    }

    /**
     * Da convert la un sir de caractere intr-o factura
     * Asta ii pentru siruri deja salvate
     * @param line: Este sirul de caractere care reprezinta o factura
     * @return o factura creata din string
     */
    public static Factura fromString(String line) {
        String[] parts = line.split(",");
        return new Factura(
            parts[0], parts[1], parts[2], parts[3],
            Double.parseDouble(parts[4]), Double.parseDouble(parts[5]), parts[6], Boolean.parseBoolean(parts[7])
        );
    }
}
