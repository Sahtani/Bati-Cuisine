package UI;

import Config.Db;
import Model.Entities.*;
import Repository.Implementations.ClientRepository;
import Repository.Implementations.LaborRepository;
import Repository.Implementations.MaterialRepository;
import Repository.Implementations.ProjectRepository;
import Service.Implementations.ClientService;
import Service.Implementations.LaborService;
import Service.Implementations.MaterialService;
import Service.Implementations.ProjectService;
import Service.Interfaces.IClientService;
import Service.Interfaces.IProjectService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class ProjectManagementUI {

    private final Scanner scanner = new Scanner(System.in);
    private static Connection connection = Db.getInstance().getConnection();

    private final IClientService clientService;
    private final IProjectService projectService;

    private final LaborService laborService;
    private final MaterialService materialService;


    private final ClientUI clientUI;
    private final MaterialUI materialUI;

    private final LaborUI laborUI;

    public ProjectManagementUI(IClientService clientService, IProjectService projectService, LaborService laborService2, MaterialService materialService2) {
        this.clientService = clientService;
        this.projectService = projectService;
        this.laborService = laborService2;
        this.materialService = materialService2;

        this.clientUI = new ClientUI(clientService);
        this.materialUI = new MaterialUI(materialService, (IProjectService) projectService);
        this.laborUI = new LaborUI(laborService, (IProjectService) projectService);

    }

    public static void main(String[] args) throws SQLException {
        IClientService clientService = new ClientService(new ClientRepository(connection));


        IProjectService projectService = new ProjectService(new ProjectRepository(connection, clientService));
        MaterialService materialService = new MaterialService(new MaterialRepository(connection));
        LaborService laborService = new LaborService(new LaborRepository(connection));

        ProjectManagementUI ui = new ProjectManagementUI(clientService, projectService, laborService, materialService);
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

    private Project createProject() throws SQLException {

        Project addedProject = null;
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
            return null;
        } else {
            System.out.println("Invalid option. Returning to the main menu.");
            return null;
        }

        if (clientName == null || clientName.isEmpty()) {
            System.out.println("No client found. Returning to the main menu.");
            return null;
        }

        System.out.println("--------------------- New Project Creation ----------------------");
        System.out.print("Enter project name: ");
        String projectName = scanner.nextLine();
        Optional<Client> clientOptional = clientService.findClientByName(clientName);

        if (clientOptional.isPresent()) {
            Project project = new Project();
            project.setProjectName(projectName);
            project.setClient(clientOptional.get());
            addedProject = projectService.addProject(project);
            int projectId = addedProject.getId();

            String materialResponse;
            do {
                Material material = materialUI.createMaterial(projectId);
                addedProject.getComponents().add(material);
                System.out.print("Do you want to add another material? (y/n): ");
                materialResponse = scanner.nextLine();
            } while (materialResponse.equalsIgnoreCase("y"));

            System.out.println("-------------------- Labor Addition --------------");
            String laborResponse;
            do {
                Labor labor = laborUI.createLabor(projectId);
                addedProject.getComponents().add(labor);
                System.out.print("Do you want to add another labor? (y/n): ");
                laborResponse = scanner.nextLine();
            } while (laborResponse.equalsIgnoreCase("y"));


        } else {
            System.out.println("Client not found. Returning to the main menu.");
        }
        applyMargprofitProject(addedProject);
        String response;
        assert addedProject != null;
        System.out.print("Do you want to calculate the cost of the project " + addedProject.getProjectName() + "? (y/n): ");
        response = scanner.nextLine();
        if (response.equalsIgnoreCase("y")) {
            displayProjectCost(addedProject);
        } else {
            // Return to the main menu :

            mainMenu();
        }

        return addedProject;
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

    private void applyMargprofitProject(Project addedProject) throws SQLException {


        System.out.print("Do you want to apply a profit margin to the project? (y/n): ");
        String profitMarginResponse = scanner.nextLine();
        double profitMarginPercentage = 0;
        if (profitMarginResponse.equalsIgnoreCase("y")) {
            System.out.print("Enter profit margin percentage (%): ");
            profitMarginPercentage = scanner.nextDouble();
            System.out.println(profitMarginPercentage);
            projectService.updateProfitMargin(addedProject, profitMarginPercentage);
            scanner.nextLine();
        }

        System.out.println("---Calculating the cost---");
        System.out.println("--- Cost Calculation Result ---");

        assert addedProject != null;
        System.out.println("Project Name:" + addedProject.getProjectName());
        System.out.println("Client:" + addedProject.getClient().getName());
        System.out.println("Site Address:" + addedProject.getClient().getAddress());


    }

    private void displayProjectCost(Project addedProject) throws SQLException {

        try {
            System.out.println("---Cost Details---");

            System.out.println("--- Project Cost Details for: " + addedProject.getProjectName() + " ---");

            // Calculate total material cost
            double totalMaterialCost = addedProject.getComponents().stream()
                    .filter(component -> component instanceof Material)
                    .map(component -> (Material) component)
                    .mapToDouble(material -> (material.getQuantity() * material.getUnitCost() * material.getQualityCoefficient())
                            + material.getTransportCost())
                    .sum();

            // get material details :
            System.out.println("1. Materials :");
            double totalMaterialCostWithVAT = addedProject.getComponents().stream()
                    .filter(component -> component instanceof Material)
                    .map(component -> (Material) component)
                    .mapToDouble(material -> {
                        double materialCost = (material.getQuantity() * material.getUnitCost() * material.getQualityCoefficient())
                                + material.getTransportCost();
                        double vatRate = material.getVatRate();
                        double materialCostWithVAT = materialCost + (materialCost * vatRate / 100);
                        System.out.printf("- %s : %.2f € (quantity: %.2f, unit cost: %.2f €/m², quality: %.1f, transport: %.2f €, VAT: %.0f%%)\n",
                                material.getName(), materialCostWithVAT, material.getQuantity(), material.getUnitCost(),
                                material.getQualityCoefficient(), material.getTransportCost(), vatRate);
                        return materialCostWithVAT;
                    }).sum();

            // display total material cost before and with tva :
            System.out.printf("**Total material cost before VAT: %.2f €**\n", totalMaterialCost);
            System.out.printf("**Total material cost with VAT: %.2f €**\n", totalMaterialCostWithVAT);


            // Calculate total labor cost

            double totalLaborCost = addedProject.getComponents().stream()
                    .filter(component -> component instanceof Labor)
                    .map(component -> (Labor) component)
                    .mapToDouble(labor -> (labor.getHourlyRate() * labor.getHoursWorked() * labor.getWorkerProductivity()))
                    .sum();
            // get labors details :

            System.out.println("2. Labor:");
            double totalLaborCostWithVAT = addedProject.getComponents().stream()
                    .filter(component -> component instanceof Labor)
                    .map(component -> (Labor) component)
                    .mapToDouble(labor -> {
                        double laborCost = labor.getHourlyRate() * labor.getHoursWorked() * labor.getWorkerProductivity();
                        double vatRate = labor.getVatRate();
                        double laborCostWithVAT = laborCost + (laborCost * vatRate / 100);
                        System.out.printf("- %s : %.2f € (hourly rate: %.2f €/h, hours worked: %.2f h, productivity: %.1f, VAT: %.0f%%)\n",
                                labor.getName(), laborCostWithVAT, labor.getHourlyRate(), labor.getHoursWorked(),
                                labor.getWorkerProductivity(), vatRate);
                        return laborCostWithVAT;
                    }).sum();

            // display total labor cost before and with tva :
            System.out.printf("**Total labor cost before VAT: %.2f €**\n", totalLaborCost);
            System.out.printf("**Total labor cost with VAT: %.2f €**\n", totalLaborCostWithVAT);


            //display total cost before margin :
            double totalCost = totalMaterialCostWithVAT + totalLaborCostWithVAT;
            System.out.printf("3.Total project cost before margin: %.2f €\n", totalCost);

            // Profit margin display with margin:

            double profitmargin = applyMargin(totalCost, addedProject.getProfitMargin());


            System.out.printf("3.Profit margin (%.0f%%) : %.2f €\n", addedProject.getProfitMargin(), profitmargin);

            // Total final cost display

            double finalTotalCost = applyMargin(totalCost, profitmargin);
            projectService.updateTotalCost(addedProject, finalTotalCost);
            System.out.printf("**Total final project cost : %.2f €**\n", finalTotalCost);

        } catch (Exception e) {
            System.out.println("An error occurred while calculating the costs: " + e.getMessage());
        }
    }

    private void calculateProjectCost( ) {
        displayExistingProjects();

        System.out.print("Enter the ID of the project you want to calculate the cost for: ");

        try {
            int projectId = scanner.nextInt();

            Optional<Project>  project = projectService.getProjectById(projectId);
            System.out.println(project.get());
            if (project.isPresent()) {
                displayProjectCost(project.get());
            } else {
                System.out.println("Project not found with the given ID.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a numeric project ID.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //Calculates the final cost by applying a specified profit margin percentage
    private double applyMargin(double baseCost, double marginPercentage) {
        return baseCost * (marginPercentage / 100);
    }
}
