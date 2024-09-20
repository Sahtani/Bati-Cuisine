package UI;

import Model.Entities.Labor;
import Service.Implementations.LaborService;
import Service.Interfaces.IClientService;
import Service.Interfaces.IComponentService;

import java.sql.SQLException;
import java.util.Scanner;

public class LaborUI {

    private final Scanner scanner = new Scanner(System.in);
    private final IComponentService<Labor> laborService;

    public LaborUI(IComponentService<Labor> laborService) {
        this.laborService = laborService;
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
                    createLabor();
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void createLabor() throws SQLException {
        while (true) {
            System.out.println("---------- - Adding labour ------------");

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

            Labor labor = new Labor(name, hourlyRate, hoursWorked, workerProductivity, vatRate);
            Labor addedLabor = laborService.add(labor);

            if (addedLabor != null) {
                System.out.println("Labor created with ID: " + labor.getId());
            } else {
                System.out.println("Failed to create labor.");
            }
            System.out.print("Do you want to add another type of labor? (y/n): ");
            String response = scanner.nextLine();
            if (!response.equalsIgnoreCase("y")) break;
        }
    }
}
