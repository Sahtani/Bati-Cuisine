package Repository.Implementations;

import Model.Entities.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        statement.setInt(5, client.getId());

    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO clients (name, address, phone, isProfessional) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE clients SET name = ?, address = ?, phone = ?, isProfessional = ? WHERE id = ?";
    }
}
