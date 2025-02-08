package lucas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Clasa care reprezinta UI-ul sistemului de facturare
 * Permite adaugarea, stergerea, cautarea si plata facturiilor printr-o interfata vizuala
 */
public class SistemFacturareUI extends JFrame {

    private SistemFacturare sistemFacturare;
    private DefaultTableModel tableModel;
    private List<Factura> facturiFiltrate;

    /**
     * Constructor care initializeaza interfata grafica
     */
    public SistemFacturareUI() {
        sistemFacturare = new SistemFacturare();

        setTitle("Sistem de Facturare");// titlul ferestrei
        setSize(1000, 600); // Dimensiunea ferestrei
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sa inchida aplicatia la inchiderea ferestrei
        setLocationRelativeTo(null); // pune fereastra in centrul ecranului

        setLayout(new BorderLayout());

        // se configura tabelul pentru a afisa facturile
        String[] coloane = {"Nume Client", "CNP", "Telefon", "Produse", "Suma Totala", "Suma Platita", "Data", "Este Platita"};
        tableModel = new DefaultTableModel(coloane, 0);
        JTable tabel = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabel);
        add(scrollPane, BorderLayout.CENTER);

        // panoul de control
        JPanel controlPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        JPanel rightPanel = new JPanel(new GridLayout(5, 1, 10, 10));

        // crearea campurilor pentru introducerea datelor
        JTextField numeField = new JTextField();
        JTextField cnpField = new JTextField();
        JTextField telefonField = new JTextField();
        JTextField produseField = new JTextField();
        JTextField sumaField = new JTextField();
        JTextField sumaPlatitaField = new JTextField();
        JTextField dataField = new JTextField();

        // buton pentru adaugarea facturii
        JButton adaugaButton = new JButton("Adauga Factura");
        adaugaButton.addActionListener(e -> {
            String nume = numeField.getText();
            String cnp = cnpField.getText();
            String telefon = telefonField.getText();
            String produse = produseField.getText();

            double sumaTotala;
            double sumaPlatita;

            // Se valideaza sumele
            try {
                sumaTotala = Double.parseDouble(sumaField.getText());
                sumaPlatita = Double.parseDouble(sumaPlatitaField.getText());

                if (sumaTotala <= 0 || sumaPlatita < 0 || sumaPlatita > sumaTotala) {
                    throw new NumberFormatException("Sume invalide.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Sume invalide!", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // se verifica daca data e valida
            String data = dataField.getText();
            if (!isValidDate(data)) {
                JOptionPane.showMessageDialog(this, "Data invalida! Format corect: dd/MM/yyyy", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Factura factura = new Factura(nume, cnp, telefon, produse, sumaTotala, sumaPlatita, data, false);
            sistemFacturare.adaugaFactura(factura);
            actualizeazaTabelul();
        });

        // buton pentru stergere facturi
        JButton stergeButton = new JButton("Sterge Factura");
        stergeButton.addActionListener(e -> {
            int selectedRow = tabel.getSelectedRow();
            if (selectedRow >= 0) {
                Factura factura = sistemFacturare.getFacturi().get(selectedRow);
                sistemFacturare.stergeFactura(factura);
                actualizeazaTabelul();
            } else {
                JOptionPane.showMessageDialog(this, "Selectati o factura de sters!", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });

        // buton pentru cautare facturi
        JButton cautaButton = new JButton("Cauta Factura");
        cautaButton.addActionListener(e -> {
            String criteriu = JOptionPane.showInputDialog("Introduceti numele clientului sau CNP-ul:");
            if (criteriu != null && !criteriu.trim().isEmpty()) {
                facturiFiltrate = sistemFacturare.cautaFactura(criteriu);
                actualizeazaTabelul(facturiFiltrate);
                
                // buton de inapoi la facturi daca se apasa cauta factura
                JButton backButton = new JButton("Inapoi la toate facturile");
                backButton.addActionListener(backEvent -> {
                    facturiFiltrate = null;
                    actualizeazaTabelul();
                    controlPanel.remove(backButton);
                    controlPanel.revalidate();
                    controlPanel.repaint();
                });
                controlPanel.add(backButton, BorderLayout.NORTH);
                controlPanel.revalidate();
                controlPanel.repaint();
            }
        });

        // butonul pentru plata facturii
        JButton platesteButton = new JButton("Plateste Suma");
        platesteButton.addActionListener(e -> {
            int selectedRow = tabel.getSelectedRow();
            if (selectedRow >= 0) {
                Factura factura = sistemFacturare.getFacturi().get(selectedRow);
                String sumaString = JOptionPane.showInputDialog("Introduceti suma de platit:");

                // verifica daca suma este valida sau nu iar daca nu arata un mesaj de eroare
                try {
                    double suma = Double.parseDouble(sumaString);
                    if (suma <= 0 || factura.getSumaPlatita() + suma > factura.getSumaTotala()) {
                        throw new NumberFormatException("Suma invalida.");
                    }

                    sistemFacturare.platesteFactura(factura, suma);
                    actualizeazaTabelul();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Suma invalida!", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selectati o factura pentru a plati!", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Adaugarea campurilor de input in partea stanga
        leftPanel.add(new JLabel("Nume Client:"));
        leftPanel.add(numeField);
        leftPanel.add(new JLabel("CNP:"));
        leftPanel.add(cnpField);
        leftPanel.add(new JLabel("Telefon:"));
        leftPanel.add(telefonField);
        leftPanel.add(new JLabel("Produse:"));
        leftPanel.add(produseField);
        leftPanel.add(new JLabel("Suma Totala:"));
        leftPanel.add(sumaField);
        leftPanel.add(new JLabel("Suma Platita:"));
        leftPanel.add(sumaPlatitaField);
        leftPanel.add(new JLabel("Data (dd/MM/yyyy):"));
        leftPanel.add(dataField);

        // Adaugarea butoanelor in partea dreapta
        rightPanel.add(adaugaButton);
        rightPanel.add(stergeButton);
        rightPanel.add(cautaButton);
        rightPanel.add(platesteButton);

        controlPanel.add(leftPanel, BorderLayout.WEST);// aici am pus campurile de input: Nume, Cnp, Telefon, Suma totala, Suma platita
        controlPanel.add(rightPanel, BorderLayout.EAST);// aici am pus butoanele: Adaugare, Stergere, Cautare, Plateste 

        add(controlPanel, BorderLayout.SOUTH);
        actualizeazaTabelul();// actualizeaza tabelul pentru a arata facturile curente
    }

    /**
     * Verificare de data conform formatului dd/MM/yyyy
     * @param date: Data care trebuie verificata
     * @return: true daca ii valida, daca nu false
     */
    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Actualizeaza tabelul cu lista de facturi
     */
    private void actualizeazaTabelul() {
        tableModel.setRowCount(0);
        for (Factura f : sistemFacturare.getFacturi()) {
            tableModel.addRow(new Object[]{
                f.getNumeClient(),
                f.getCnp(),
                f.getTelefon(),
                f.getProduse(),
                f.getSumaTotala(),
                f.getSumaPlatita(),
                f.getData(),
                f.isEstePlatita() ? "Da" : "Nu"
            });
        }
    }

    /**
     * Actualizeaza tabelul cu o lista SPECIFICA de facturi
     * @param facturiFiltrate: lista de facturi care vor fi afisate 
     */
    private void actualizeazaTabelul(List<Factura> facturiFiltrate) {
        // setRowCount(0) goleste datele existente
        tableModel.setRowCount(0);
        
        // Adauga fiecare factura din lista
        for (Factura f : facturiFiltrate) {
            tableModel.addRow(new Object[]{
                f.getNumeClient(),
                f.getCnp(),
                f.getTelefon(),
                f.getProduse(),
                f.getSumaTotala(),
                f.getSumaPlatita(),
                f.getData(),
                f.isEstePlatita() ? "Da" : "Nu"
            });
        }
    }

    /**
     * Programul principal de pornire a aplicatiei
     * @param args
     */
    public static void main(String[] args) {
        // Crearea si afisarea UI-ului
        SwingUtilities.invokeLater(() -> {
            new SistemFacturareUI().setVisible(true);
        });
    }
}
