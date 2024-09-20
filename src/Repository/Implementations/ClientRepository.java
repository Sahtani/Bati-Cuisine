package Repository.Implementations;

import Model.Entities.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ClientRepository extends BaseRepository<Client> {

    public ClientRepository(Connection connection) {
        super(connection);
    }

    @Override
    protected Client mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Client client = new Client();
        client.setId(resultSet.getInt("id"));
        client.setName(resultSet.getString("name"));
        client.setAddress(resultSet.getString("address"));
        client.setPhone(resultSet.getString("phone"));
        client.setProfessional(resultSet.getBoolean("isProfessional"));
        return client;
    }

    @Override
    protected String getTableName() {
        return "clients";
    }

    @Override
    protected void setParameters(PreparedStatement statement, Client client) throws SQLException {
        statement.setString(1, client.getName());
        statement.setString(2, client.getAddress());
        statement.setString(3, client.getPhone());
        statement.setBoolean(4, client.isProfessional());

    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO clients (name, address, phone, isProfessional) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE clients SET name = ?, address = ?, phone = ?, isProfessional = ? WHERE id = ?";
    }

    public Optional<Client> findClientByName(String name) {
        String query = "SELECT * FROM clients WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Client client = new Client(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getBoolean("isprofessional")

                );
                return Optional.of(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
