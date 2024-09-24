package UI;

import Model.Entities.Labor;
import Model.Entities.Project;
import Service.Interfaces.IComponentService;
import Service.Interfaces.IProjectService;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class LaborUI {

    private final Scanner scanner = new Scanner(System.in);
    private final IComponentService<Labor> laborService;
    private final IProjectService projectService;

    public LaborUI(IComponentService<Labor> laborService, IProjectService projectService) {
        this.laborService = laborService;
        this.projectService = projectService;
    }

    public void showMenu() throws SQLException {
        while (true) {
            System.out.println("Labor Management");
            System.out.println("1. Create Labor");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    int projectId = 0;
                    createLabor(projectId);
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public Labor createLabor(int projectId) throws SQLException {
        System.out.print("Enter labor type (e.g., General worker, Specialist): ");
        String name;
        do {
            System.out.print("Enter labor type (e.g., General worker, Specialist): ");
            name = scanner.nextLine();
            if (name == null || name.isEmpty() || (!name.equals("General worker") && !name.equals("Specialist"))) {
                System.out.println("Labor name must be 'General worker' or 'Specialist' and cannot be null or empty.");
            }
        } while (name == null || name.isEmpty() || (!name.equals("General worker") && !name.equals("Specialist")));


        double hourlyRate;
        do {
            System.out.print("Enter hourly rate (€/h): ");
            hourlyRate = scanner.nextDouble();
            if (hourlyRate < 0) {
                System.out.println("Hourly rate cannot be negative.");
            }
        } while (hourlyRate < 0);

        double hoursWorked;
        do {
            System.out.print("Enter the number of hours worked: ");
            hoursWorked = scanner.nextDouble();
            if (hoursWorked < 0) {
                System.out.println("Hours worked cannot be negative.");
            }
        } while (hoursWorked < 0);

        double workerProductivity;
        do {
            System.out.print("Enter the productivity factor (1.0 = standard, > 1.1 = high productivity): ");
            workerProductivity = scanner.nextDouble();
            if (workerProductivity < 0 || workerProductivity > 1) {
                System.out.println("Worker productivity must be between 0 and 1.");
            }
        } while (workerProductivity < 0 || workerProductivity > 1);

        double vatRate;
        do {
            System.out.print("Enter VAT rate: ");
            vatRate = scanner.nextDouble();
            if (vatRate < 0) {
                System.out.println("VAT rate cannot be negative.");
            }
        } while (vatRate < 0);

        scanner.nextLine();

        Optional<Project> project = projectService.getProjectById(projectId);

        Labor labor = new Labor(name, vatRate, project.get(), hourlyRate, hoursWorked, workerProductivity);
        Labor addedLabor = laborService.add(labor, projectId);

        if (addedLabor != null) {
            System.out.println("Labor created with ID: " + labor.getId());
        } else {
            System.out.println("Failed to create labor.");
        }

        return labor;
    }

}

