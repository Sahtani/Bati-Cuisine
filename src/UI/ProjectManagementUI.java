package UI;

import Config.Db;
import Model.Entities.*;
import Repository.Implementations.*;
import Service.Implementations.*;
import Service.Interfaces.IClientService;
import Service.Interfaces.IEstimateService;
import Service.Interfaces.IProjectService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ProjectManagementUI {

    private final Scanner scanner = new Scanner(System.in);
    private static Connection connection = Db.getInstance().getConnection();

    private final IClientService clientService;
    private final IProjectService projectService;

    private final LaborService laborService;
    private final MaterialService materialService;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    private final ClientUI clientUI;
    private final MaterialUI materialUI;

    private final LaborUI laborUI;
    private final IEstimateService estimateService;

    public ProjectManagementUI(IClientService clientService, IProjectService projectService, LaborService laborService, MaterialService materialService, IEstimateService estimateService) {
        this.clientService = clientService;
        this.projectService = projectService;
        this.laborService = laborService;
        this.materialService = materialService;
        this.estimateService = estimateService;

        this.clientUI = new ClientUI(clientService);
        this.materialUI = new MaterialUI(materialService, (IProjectService) projectService);
        this.laborUI = new LaborUI(laborService, (IProjectService) projectService);


    }

    public static void main(String[] args) throws SQLException {
        IClientService clientService = new ClientService(new ClientRepository(connection));


        IProjectService projectService = new ProjectService(new ProjectRepository(connection, clientService));
        MaterialService materialService = new MaterialService(new MaterialRepository(connection));
        LaborService laborService = new LaborService(new LaborRepository(connection));
        IEstimateService estimateService = new EstimateService(new EstimateRepository(connection, projectService));

        ProjectManagementUI ui = new ProjectManagementUI(clientService, projectService, laborService, materialService, estimateService);
        ui.mainMenu();
    }

    public void mainMenu() throws SQLException {
        while (true) {
            System.out.println("=== Main Menu ===");
            System.out.println("1. Create a new project");
            System.out.println("2. Display existing projects");
            System.out.println("3. Calculate project cost");
            System.out.println("4. Manage Estimates (Accept/Refuse)");
            System.out.println("5. Exit");
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
                    manageEstimates();
                case 5:
                    manageEstimates();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private String createProject() throws SQLException {
        Project addedProject = null;
        System.out.println("--- Client Search ---");
        System.out.println("1. Search for an existing client");
        System.out.println("2. Add a new client");
        System.out.print("Choose an option: ");
        int clientChoice = scanner.nextInt();
        scanner.nextLine();
        Client client ;
        String clientName = "";

        if (clientChoice == 1) {
            clientName = searchExistingClient();
        } else if (clientChoice == 2) {
            client = clientUI.addClient();
            clientName = client.getName();
        } else {
            System.out.println("Invalid option. Returning to the main menu.");
            return null;
        }

        System.out.print("Do you want to proceed with this client? (y/n): ");
        String responseUser = scanner.nextLine();

        if (!responseUser.equalsIgnoreCase("y")) {
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
        assert addedProject != null;
        System.out.print("Do you want to calculate the cost of the project " + addedProject.getProjectName() + "? (y/n): ");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("y")) {
            displayProjectCost(addedProject);
        } else {
            mainMenu();
        }
        return addedProject != null ? addedProject.getProjectName() : null;
    }



    private String searchExistingClient() {

        System.out.println("--- Searching for existing client ---");
        System.out.print("Enter client name: ");
        String clientName = scanner.nextLine();
        Optional<Client> clientOptional = clientService.findClientByName(clientName);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            System.out.println("Client found! Name: " + client.getName());

        } else {
            System.out.println("Client not found.");
            return null;
        }
        return clientName;
    }

    private void displayExistingProjects() throws SQLException {
        List<Project> projects = projectService.getAllProject();
        if (projects.isEmpty()) {
            System.out.println("No existing projects found.");
        } else {
            System.out.printf("%-10s %-30s %-15s %-20s%n", "Project ID", "Project Name", "Total Cost", "Client");
            System.out.println("--------------------------------------------------------------------------------");
            for (Project project : projects) {
                System.out.printf("%-10d %-30s %-15.2f %-20s%n",
                        project.getId(),
                        project.getProjectName(),
                        project.getTotalCost(),
                        project.getClient().getName());
            }
        }
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
            double profitmargin = 0;
            if(addedProject.getProfitMargin()!=0){
                 profitmargin = applyMargin(totalCost, addedProject.getProfitMargin());

            }else{
                addedProject.getProfitMargin();

            }




            System.out.printf("3.Profit margin (%.0f%%) : %.2f €\n", addedProject.getProfitMargin(), profitmargin);

            // Total final cost display

            double finalTotalCost = profitmargin+totalCost;
            double totalPrice = 0;


            if (addedProject.getClient().isProfessional()) {
                 totalPrice = clientService.applyDiscount(addedProject.getClient(), finalTotalCost);
                System.out.println(finalTotalCost);

            }
            projectService.updateTotalCost(addedProject, totalPrice);
            System.out.printf("**Total final project cost : %.2f €**\n", totalPrice);

            System.out.println("--- Saving the estimate ---");
            scanner.nextLine();
            saveEstimate(addedProject);

            System.out.println("--- End of the project ---\n");

        } catch (Exception e) {
            System.out.println("An error occurred while calculating the costs: " + e.getMessage());
        }
    }

    private void calculateProjectCost() throws SQLException {

        displayExistingProjects();

        System.out.print("Enter the ID of the project you want to calculate the cost for: ");

        try {
            int projectId = scanner.nextInt();

            Optional<Project> project = projectService.getProjectById(projectId);
            //    System.out.println(project.get());
            if (project.isPresent()) {
                if (project.get().getTotalCost() == 0) {
                    displayProjectCost(project.get());
                } else
                    System.out.println("The project has a defined total cost.");

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

    public void saveEstimate(Project project) {
        try {

            String datePattern = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
            Pattern pattern = Pattern.compile(datePattern);
            LocalDate issuanceDate = null;
            LocalDate validityDate = null;

            String issuanceDateStr;
            do {
                System.out.print("Enter the estimate issuance date (format: dd/MM/yyyy): ");
                issuanceDateStr = scanner.nextLine().trim();
                if (!pattern.matcher(issuanceDateStr).matches()) {
                    System.out.println("Invalid date format. Please use dd/MM/yyyy.");
                }
            } while (!pattern.matcher(issuanceDateStr).matches());
            issuanceDate = LocalDate.parse(issuanceDateStr, formatter);

            String validityDateStr;
            do {
                System.out.print("Enter the estimate validity date (format: dd/MM/yyyy): ");
                validityDateStr = scanner.nextLine().trim();
                if (!pattern.matcher(validityDateStr).matches()) {
                    System.out.println("Invalid date format. Please use dd/MM/yyyy.");
                }
            } while (!pattern.matcher(validityDateStr).matches());
            validityDate = LocalDate.parse(validityDateStr, formatter);


            Estimate estimate = new Estimate();

            estimate.setProject(project);
            estimate.setEstimatedAmount(project.getTotalCost());
            estimate.setIssueDate(issuanceDate);
            estimate.setValidityDate(validityDate);


            System.out.print("Do you want to save the estimate? (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equalsIgnoreCase("y")) {

                estimateService.addEstimate(estimate);
                System.out.println("Estimate saved successfully!");
            } else {
                System.out.println("Saving canceled.");
            }
        } catch (Exception e) {
            System.out.println("Error saving the quote: " + e.getMessage());
        }
    }

    //Manage Estimates (Accept/Refuse)

    private void manageEstimates() {
        try {
            System.out.println("--- Manage Estimates ---");
            System.out.print("Enter your client  ID to manage estimate: ");
            int clientId = scanner.nextInt();

            System.out.print("Enter the estimate ID to manage: ");
            int estimateId = scanner.nextInt();
            Optional<Client> client = Optional.ofNullable(clientService.getClientById(clientId));
            Optional<Estimate> estimate = estimateService.getEstimateById(estimateId, clientId);
            scanner.nextLine();

            System.out.print("Do you want to accept the estimate? (y/n): ");
            String input = scanner.nextLine();
            if (estimate.isPresent()) {
                if (input.equalsIgnoreCase("y")) {
                    estimateService.updateStatus(estimate.get(), true);
                    System.out.println("Estimate accepted.");
                } else {
                    estimateService.updateStatus(estimate.get(), false);
                    System.out.println("Estimate refused.");
                }
            } else {
                System.out.println("Estimate not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
