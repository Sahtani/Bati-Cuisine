

import Config.Db;
import Repository.Implementations.ClientRepository;
import Repository.Implementations.ProjectRepository;
import Service.Implementations.ClientService;
import Service.Implementations.ProjectService;
import UI.ClientUI;
import UI.ProjectUI;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        displayMainMenu();
    }
    public static void displayMainMenu() throws SQLException {
        Connection connection = Db.getInstance().getConnection();
        ClientRepository clientRepository = new ClientRepository(connection);
        ClientService clientService = new ClientService(clientRepository);
        ProjectRepository projectRepository = new ProjectRepository(connection,clientService);
        ProjectService projectService = new ProjectService(projectRepository);
        ProjectUI projectUI = new ProjectUI(projectService,clientService);

        ClientUI clientUI = new ClientUI(clientService);


        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Manage Clients");
            System.out.println("2. Manage Projects");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    clientUI.displayClientMenu();
                    break;
                case 2:
                     projectUI.displayProjectMenu();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }}
}