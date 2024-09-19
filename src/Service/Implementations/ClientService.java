package Service.Implementations;

import Model.Entities.Client;
import Repository.Implementations.ClientRepository;
import Service.Interfaces.IClientService;
import Util.DataValidator;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClientService implements IClientService {

    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void addClient(Client client) throws SQLException {
        if (DataValidator.isValidName(client.getName()) &&
                DataValidator.isValidAddress(client.getAddress()) &&
                DataValidator.isValidPhoneNumber(client.getPhone())&&
                DataValidator.isValidBoolean(client.isProfessional())){



                clientRepository.create(client);


            } else{
                System.out.println("Client data is invalid. Please check the provided information.");
            }

        }

        @Override
        public void updateClient ( int clientId, Client client) throws SQLException {
            Optional<Client> existingClient = Optional.ofNullable(getClientById(clientId));

            if (existingClient.isPresent()) {
                client.setId(clientId);
                clientRepository.update(client);
            } else {
                System.out.println("Client with ID " + clientId + " not found.");
            }
        }



    public void deleteClient (int clientId) throws SQLException {
            Optional<Client> existingClient = Optional.ofNullable(getClientById(clientId));


            if (existingClient.isPresent()) {
                clientRepository.delete(clientId);
            } else {
                System.out.println("Client with ID " + clientId + " not found.");
            }
        }

        @Override
        public List<Client> getAllClients () throws SQLException {
            return clientRepository.findAll();
        }

        @Override
        public Client getClientById ( int clientId) throws SQLException {
            Client client = clientRepository.findById(clientId);
            return client ;
        }
    }
