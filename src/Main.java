import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Repository.Implementations.ClientRepository;
import Model.Entities.Client;
import Config.Db;

public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Establish a database connection
            connection = Db.getInstance().getConnection();

            // Create a ClientRepository instance
            ClientRepository clientRepository = new ClientRepository(connection);

            // Create a new client
            Client newClient = new Client("John Doe", "123 Main St", "555-1234", true);

            // Insert the new client into the database
            clientRepository.create(newClient);

            System.out.println("Client inserted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
