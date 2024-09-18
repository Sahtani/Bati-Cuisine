
package UI;

import Model.Entities.Client;
import Model.Entities.Project;
import Model.Enums.ProjectStatus;
import Service.Implementations.ClientService;
import Service.Implementations.ProjectService;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

import static java.lang.System.exit;

public class ProjectUI {

    private final ProjectService projectService;
    private final ClientService clientService;
    private final Scanner scanner = new Scanner(System.in);

    public ProjectUI(ProjectService projectService, ClientService clientService) {
        this.projectService = projectService;
        this.clientService = clientService;
    }

    public void displayClientMenu() throws SQLException {
        while (true) {
            viewProjects();
            System.out.println("\n--- Manage Projects ---");
            System.out.println("1. Add a New Project");
            System.out.println("2. Display All Projects");
            System.out.println("3. Update a Project");
            System.out.println("4. Delete a Project");
            System.out.println("5. Return to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    addProject();
                    break;
                case 2:
                    viewProjects();
                    break;
                case 3:
                    updateProject();
                    break;
                case 4:
                    deleteProject();
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");

            }

        }
    }

    private void addProject() throws SQLException {
        System.out.println("\n=== Add Project ===");
        Project project  = getProjectInput();
        projectService.addProject(project);
        System.out.println("Project added successfully.");
    }

    private void updateProject() throws SQLException {
        System.out.println("\n=== Update Project ===");
        System.out.print("Enter project ID to update: ");
        int projectId = Integer.parseInt(scanner.nextLine());

        Optional<Project> projectOptional = projectService.getProjectById(projectId);

        if (projectOptional.isPresent()) {
            Optional<Client> client = projectOptional.get().getClient();
            System.out.println("Updating client: " + client.get().getName());

            Project updatedClient = getProjectInput();
            projectService.updateProject(projectId, updatedClient);
            System.out.println("Project updated successfully.");
        } else {
            System.out.println("Project not found.");
        }
    }


    private void deleteProject() throws SQLException {
        System.out.println("\n=== Delete Client ===");
        System.out.print("Enter client ID to delete: ");
        int projectId = Integer.parseInt(scanner.nextLine());
        clientService.deleteClient(projectId);
        System.out.println("Project deleted successfully.");
    }

    private void viewProjects() throws SQLException {
        System.out.println("\n=== View Clients ===");
        for (Project project : projectService.getAllProject()) {
            System.out.println(project);
        }
    }

    private Project getProjectInput() throws SQLException {
        System.out.print("Enter project name: ");
        String projectName = scanner.nextLine();

        System.out.print("Enter profit margin: ");
        double profitMargin = scanner.nextDouble();

        System.out.print("Enter total cost: ");
        double totalCost = scanner.nextDouble();

        System.out.print("Enter project status (e.g., IN_PROGRESS, COMPLETED, CANCELLED): ");
        String statusString = scanner.next().toUpperCase();
        ProjectStatus projectStatus = ProjectStatus.valueOf(statusString);

        scanner.nextLine();

        System.out.print("Enter client ID: ");
        int clientId = scanner.nextInt();
        scanner.nextLine();

        Optional<Client> client = clientService.getClientById(clientId);

        return new Project(projectName, profitMargin, totalCost, projectStatus, client);
    }


}


