package UI;

import Model.Entities.Client;
import Service.Implementations.ClientService;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

import static java.lang.System.exit;

public class ClientUI {

    private ClientService clientService;
    private Scanner scanner = new Scanner(System.in);

    public ClientUI(ClientService clientService) {
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

    private void addClient() throws SQLException {
        System.out.println("\n=== Add Client ===");
        Client client = getClientInput();
        clientService.addClient(client);
        System.out.println("Client added successfully.");
    }

    private void updateClient() throws SQLException {
        System.out.println("\n=== Update Client ===");
        System.out.print("Enter client ID to update: ");
        int clientId = Integer.parseInt(scanner.nextLine());

        Optional<Client> clientOptional = clientService.getClientById(clientId);

        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            System.out.println("Updating client: " + client.getName());

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

    private Client getClientInput() {
        System.out.print("Enter client name: ");
        String name = scanner.nextLine();
        System.out.print("Enter client address: ");
        String address = scanner.nextLine();
        System.out.print("Enter client phone: ");
        String phone = scanner.nextLine();
        System.out.print("Is the client professional? (true/false): ");
        boolean isProfessional = scanner.nextBoolean();
        scanner.nextLine();

        return new Client(name, address, phone, isProfessional);
    }

}