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
        String name = scanner.nextLine();

        System.out.print("Enter hourly rate (€/h): ");
        double hourlyRate = scanner.nextDouble();

        System.out.print("Enter the number of hours worked:  ");
        double hoursWorked = scanner.nextDouble();

        System.out.print("Enter the productivity factor (1.0 = standard, > 1.0 = high productivity): ");
        double workerProductivity = scanner.nextDouble();

        System.out.print("Enter VAT rate: ");
        double vatRate = scanner.nextDouble();
        Optional<Project> project = projectService.getProjectById(projectId);
        Labor labor = new Labor(name,vatRate,project.get(),hourlyRate,hoursWorked,workerProductivity);



        Labor addedLabor = laborService.add(labor,projectId);

        if (addedLabor != null) {
            System.out.println("Labor created with ID: " + labor.getId());
        } else {
            System.out.println("Failed to create labor.");
        }
        return labor;
    }
}

