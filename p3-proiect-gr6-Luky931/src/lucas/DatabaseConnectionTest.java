package lucas;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        String username = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/Facturi";
        if (username == null || password == null) {
            System.err.println("Error: Username or password are not correct");
            return;
        }

        try {
        	Connection connection = DriverManager.getConnection(url, username, password);
        	
        	Statement statement=connection.createStatement();
        	
        	ResultSet resultsSet=statement.executeQuery("select * from facturi");
        	
        	while (resultsSet.next()){
        		System.out.println(resultsSet.getInt(1)+" "+resultsSet.getString(2)+resultsSet.getInt(3));
        	}
        	
            System.out.println("Connected to MySql database successfully!");
            
            connection.close();
        } 
        catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
}
