package Service.Interfaces;

import Model.Entities.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IClientService {

    void addClient(Client client) throws SQLException;

    void updateClient(int clientId, Client client) throws SQLException;

    void deleteClient(int clientId) throws SQLException;

    List<Client> getAllClients() throws SQLException;

    Client getClientById(int clientId) throws SQLException;

    Optional<Client> findClientByName(String name);

    double applyDiscount(Client client, double totalCost);


}
