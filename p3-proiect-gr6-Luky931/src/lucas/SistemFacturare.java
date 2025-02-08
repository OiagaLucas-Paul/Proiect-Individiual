package lucas;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SistemFacturare {

    private Connection connection;

    public SistemFacturare() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Facturi", "root", "");
            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Factura> getFacturi() {
        List<Factura> facturi = new ArrayList<>();
        String query = "SELECT * FROM facturi";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String numeClient = resultSet.getString("numeClient");
                String cnp = resultSet.getString("cnp");
                String telefon = resultSet.getString("telefon");
                String produse = resultSet.getString("produse");
                double sumaTotala = resultSet.getDouble("sumaTotala");
                double sumaPlatita = resultSet.getDouble("sumaPlatita");
                String data = resultSet.getString("data");
                boolean estePlatita = resultSet.getBoolean("estePlatita");

                Factura factura = new Factura(numeClient, cnp, telefon, produse, sumaTotala, sumaPlatita, data, estePlatita);
                facturi.add(factura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return facturi;
    }

    public void adaugaFactura(Factura factura) {
        String query = "INSERT INTO facturi (numeClient, cnp, telefon, produse, sumaTotala, sumaPlatita, data, estePlatita) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            String dateString = factura.getData();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate = sdf.parse(dateString);
            String formattedDate = formatDate(utilDate);
            
            statement.setString(1, factura.getNumeClient());
            statement.setString(2, factura.getCnp());
            statement.setString(3, factura.getTelefon());
            statement.setString(4, factura.getProduse());
            statement.setDouble(5, factura.getSumaTotala());
            statement.setDouble(6, factura.getSumaPlatita());
            statement.setString(7, formattedDate);
            statement.setBoolean(8, factura.isEstePlatita());
            
            statement.executeUpdate();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void stergeFactura(Factura factura) {
        String query = "DELETE FROM facturi WHERE numeClient = ? AND cnp = ? AND data = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, factura.getNumeClient());
            statement.setString(2, factura.getCnp());
            statement.setString(3, factura.getData());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void platesteFactura(Factura factura, double suma) {
        double sumaTotalaPlatita = factura.getSumaPlatita() + suma;
        boolean estePlatita = sumaTotalaPlatita >= factura.getSumaTotala();

        String query = "UPDATE facturi SET sumaPlatita = ?, estePlatita = ? WHERE numeClient = ? AND cnp = ? AND data = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, sumaTotalaPlatita);
            statement.setBoolean(2, estePlatita);
            statement.setString(3, factura.getNumeClient());
            statement.setString(4, factura.getCnp());
            statement.setString(5, factura.getData());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Factura> cautaFactura(String criteriu) {
        List<Factura> facturiFiltrate = new ArrayList<>();
        String query = "SELECT * FROM facturi WHERE numeClient LIKE ? OR cnp LIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + criteriu + "%");
            statement.setString(2, "%" + criteriu + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String numeClient = resultSet.getString("numeClient");
                    String cnp = resultSet.getString("cnp");
                    String telefon = resultSet.getString("telefon");
                    String produse = resultSet.getString("produse");
                    double sumaTotala = resultSet.getDouble("sumaTotala");
                    double sumaPlatita = resultSet.getDouble("sumaPlatita");
                    String data = resultSet.getString("data");
                    boolean estePlatita = resultSet.getBoolean("estePlatita");

                    Factura factura = new Factura(numeClient, cnp, telefon, produse, sumaTotala, sumaPlatita, data, estePlatita);
                    facturiFiltrate.add(factura);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return facturiFiltrate;
    }


    public String formatDate(java.util.Date utilDate) {
        if (utilDate == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(utilDate);
    }
}
