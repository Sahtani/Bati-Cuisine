package Service.Interfaces;

import Model.Entities.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IClientService {

    void addClient(Client client) throws SQLException;

    void updateClient(int clientId, Client client) throws SQLException;

    void deleteClient(int clientId) throws SQLException;

    List<Client> getAllClients() throws SQLException;

    Optional<Client> getClientById(int clientId) throws SQLException;;


}
