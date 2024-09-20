package UI;

import Config.Db;
import Model.Entities.Client;
import Model.Entities.Labor;
import Model.Entities.Material;
import Model.Entities.Project;
import Repository.Implementations.ClientRepository;
import Repository.Implementations.LaborRepository;
import Repository.Implementations.MaterialRepository;
import Repository.Implementations.ProjectRepository;
import Service.Implementations.ClientService;
import Service.Implementations.LaborService;
import Service.Implementations.MaterialService;
import Service.Implementations.ProjectService;
import Service.Interfaces.IClientService;
import Service.Interfaces.IComponentService;
import Service.Interfaces.IProjectService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class ProjectManagementUI {
    private final Scanner scanner = new Scanner(System.in);
    private static Connection connection = Db.getInstance().getConnection();
    private final IClientService clientService;
    private final IProjectService projectService;
    private final IComponentService<Labor> laborService;
    private final IComponentService<Material> materialService;

 //   private final ProjectUI projectUI;
    private final ClientUI clientUI;
    private final MaterialUI materialUI;
    private final LaborUI laborUI;

    public ProjectManagementUI(IClientService clientService, IProjectService projectService, IComponentService<Labor> laborService, IComponentService<Material> materialService) {
        this.clientService = clientService;
        this.projectService = projectService;
        this.materialService = materialService;
        this.laborService = laborService;

       // this.projectUI = new ProjectUI(projectService, clientService);
        this.clientUI = new ClientUI(clientService);
        this.materialUI = new MaterialUI(materialService);
        this.laborUI = new LaborUI(laborService);
    }

    public static void main(String[] args) throws SQLException {
        IClientService clientService = new ClientService(new ClientRepository(connection));
        IProjectService projectService = new ProjectService(new ProjectRepository(connection,clientService));
        IComponentService<Material> materialService = new MaterialService(new MaterialRepository(connection));
        IComponentService<Labor> laborService =new LaborService(new LaborRepository(connection));

        ProjectManagementUI ui = new ProjectManagementUI(clientService, projectService,laborService ,materialService );
        ui.mainMenu();
    }

    public void mainMenu() throws SQLException {
        while (true) {
            System.out.println("=== Main Menu ===");
            System.out.println("1. Create a new project");
            System.out.println("2. Display existing projects");
            System.out.println("3. Calculate project cost");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    createProject();
                    break;
                case 2:
                    displayExistingProjects();
                    break;
                case 3:
                    calculateProjectCost();
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void createProject() throws SQLException {
        System.out.println("--- Client Search ---");
        System.out.println("1. Search for an existing client");
        System.out.println("2. Add a new client");
        System.out.print("Choose an option: ");
        int clientChoice = scanner.nextInt();
        scanner.nextLine();
        String clientName = "";

        if (clientChoice == 1) {
            clientName = searchExistingClient();
        } else if (clientChoice == 2) {
            clientUI.addClient();
            return;
        } else {
            System.out.println("Invalid option. Returning to the main menu.");
            return;
        }

        if (clientName == null || clientName.isEmpty()) {
            System.out.println("No client found. Returning to the main menu.");
            return;
        }

        System.out.println("--------------------- New Project Creation ----------------------");
        System.out.print("Enter project name: ");
        String projectName = scanner.nextLine();

        Optional<Client> clientOptional = clientService.findClientByName(clientName);

        if (clientOptional.isPresent()) {
            Project project = new Project();
            project.setProjectName(projectName);
            project.setClient(clientOptional.get());
            projectService.addProject(project);
            System.out.println("Project added successfully.");
            materialUI.createMaterial();
            scanner.nextLine();
            laborUI.createLabor();
        } else {
            System.out.println("Client not found. Returning to the main menu.");
        }
    }


    private String searchExistingClient() {
        System.out.println("--- Searching for existing client ---");
        System.out.print("Enter client name: ");
        String clientName = scanner.nextLine();
        Optional<Client> clientOptional = clientService.findClientByName(clientName);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            System.out.println("Client found! Name: " + client.getName());
            System.out.print("Do you want to proceed with this client? (y/n): ");
            String response = scanner.nextLine();
            return response.equalsIgnoreCase("y") ? client.getName() : null;
        } else {
            System.out.println("Client not found.");
            return null;
        }
    }

    private void displayExistingProjects() {
        System.out.println("Displaying existing projects...");
    }

    private void calculateProjectCost() {
        System.out.print("Do you want to apply VAT to the project? (y/n): ");
        String vatResponse = scanner.nextLine();
        double vatPercentage = 0;
        if (vatResponse.equalsIgnoreCase("y")) {
            System.out.print("Enter VAT percentage (%): ");
            vatPercentage = scanner.nextDouble();
            scanner.nextLine();
        }

        System.out.print("Do you want to apply a profit margin to the project? (y/n): ");
        String profitMarginResponse = scanner.nextLine();
        double profitMarginPercentage = 0;
        if (profitMarginResponse.equalsIgnoreCase("y")) {
            System.out.print("Enter profit margin percentage (%): ");
            profitMarginPercentage = scanner.nextDouble();
            scanner.nextLine();
        }

        System.out.println("Calculating project cost...");
        System.out.println("--- Cost Calculation Result ---");
        System.out.println("Project cost calculated successfully.");
    }
}
