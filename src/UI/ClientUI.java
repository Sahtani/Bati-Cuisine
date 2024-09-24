package UI;

import Model.Entities.Client;
import Service.Implementations.ClientService;
import Service.Interfaces.IClientService;
import Util.DataValidator;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

import static java.lang.System.exit;

public class ClientUI {

    private IClientService clientService;
    private Scanner scanner = new Scanner(System.in);

    public ClientUI(IClientService clientService) {
        this.clientService = clientService;
    }

    public void displayClientMenu() throws SQLException {
        while (true) {
            viewClients();
            System.out.println("\n--- Manage Clients ---");
            System.out.println("1. Add a New Client");
            System.out.println("2. Display All Clients");
            System.out.println("3. Update a Client");
            System.out.println("4. Delete a Client");
            System.out.println("5. Return to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    addClient();
                    break;
                case 2:
                    viewClients();
                    break;
                case 3:
                    updateClient();
                    break;
                case 4:
                    deleteClient();
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");

            }

        }
    }

    public Client addClient() throws SQLException {
        System.out.println("\n=== Add Client ===");
        Client client = getClientInput();
        clientService.addClient(client);
        System.out.println("Client added successfully.");
        return client;
    }

    private void updateClient() throws SQLException {
        System.out.println("\n=== Update Client ===");
        System.out.print("Enter client ID to update: ");
        int clientId = Integer.parseInt(scanner.nextLine());

        Client existingClient = clientService.getClientById(clientId);
        if (existingClient != null) {
            System.out.println("Updating client: " + existingClient.getName());
            Client updatedClient = getClientInput();
            clientService.updateClient(clientId, updatedClient);
            System.out.println("Client updated successfully.");
        } else {
            System.out.println("Client not found.");
        }
    }

    private void deleteClient() throws SQLException {
        System.out.println("\n=== Delete Client ===");
        System.out.print("Enter client ID to delete: ");
        int clientId = Integer.parseInt(scanner.nextLine());
        clientService.deleteClient(clientId);
        System.out.println("Client deleted successfully.");
    }

    private void viewClients() throws SQLException {
        System.out.println("\n=== View Clients ===");
        for (Client client : clientService.getAllClients()) {
            System.out.println(client);
        }
    }

    public Client getClientInput() {
        String name;
        String address;
        String phone;
        boolean isProfessional = false;

        do {
            System.out.print("Enter client name: ");
            name = scanner.nextLine();
        } while (!DataValidator.isValidName(name));

        do {
            System.out.print("Enter client address: ");
            address = scanner.nextLine();
        } while (!DataValidator.isValidAddress(address));

        do {
            System.out.print("Enter client phone: ");
            phone = scanner.nextLine();
        } while (!DataValidator.isValidPhoneNumber(phone));

        do {
            System.out.print("Is the client professional? (true/false): ");
            if (scanner.hasNextBoolean()) {
                isProfessional = scanner.nextBoolean();
                scanner.nextLine();
            } else {
                scanner.next();
                continue;
            }
        } while (!DataValidator.isValidBoolean(isProfessional));

        return new Client(name, address, phone, isProfessional);
    }


}