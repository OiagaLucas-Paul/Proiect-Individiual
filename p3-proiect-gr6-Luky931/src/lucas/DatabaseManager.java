/*package lucas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private Connection connection;

    // Constructor: Initializes the database connection
    public DatabaseManager() {
        try {
            String url = "jdbc:oracle:thin:@localhost:1521:xe"; // Replace with your DB details
            String username = "your_username";
            String password = "your_password";

            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the Oracle database successfully.");
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Add invoice to the database
    public void addFactura(Factura factura) {
        String sql = "INSERT INTO FACTURI (nume_client, cnp, telefon, produse, suma_totala, suma_platita, data_facturarii) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, factura.getNumeClient());
            stmt.setString(2, factura.getCnp());
            stmt.setString(3, factura.getTelefon());
            stmt.setString(4, factura.getProduse());
            stmt.setDouble(5, factura.getSumaTotala());
            stmt.setDouble(6, factura.getSumaPlatita());
            stmt.setString(7, factura.getData());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding factura: " + e.getMessage());
        }
    }

    // Retrieve all invoices from the database
    public List<Factura> getFacturi() {
        List<Factura> facturi = new ArrayList<>();
        String sql = "SELECT * FROM FACTURI";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Factura factura = new Factura(
                    rs.getString("nume_client"),
                    rs.getString("cnp"),
                    rs.getString("telefon"),
                    rs.getString("produse"),
                    rs.getDouble("suma_totala"),
                    rs.getDouble("suma_platita"),
                    rs.getString("data_facturarii")
                );
                facturi.add(factura);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving facturi: " + e.getMessage());
        }
        return facturi;
    }

    // Delete invoice from the database by CNP
    public void deleteFactura(String cnp) {
        String sql = "DELETE FROM FACTURI WHERE cnp = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cnp);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting factura: " + e.getMessage());
        }
    }

    // Update payment information for an invoice
    public void updateFactura(Factura factura) {
        String sql = "UPDATE FACTURI SET suma_platita = ? WHERE cnp = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, factura.getSumaPlatita());
            stmt.setString(2, factura.getCnp());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating factura: " + e.getMessage());
        }
    }

    // Close connection (if necessary)
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
*/