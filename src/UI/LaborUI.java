package UI;

import Model.Entities.Labor;
import Service.Implementations.LaborService;

import java.sql.SQLException;
import java.util.Scanner;

public class LaborUI {
    private final LaborService laborService;
    private final Scanner scanner = new Scanner(System.in);

    public LaborUI(LaborService laborService) {
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

    private void createLabor() throws SQLException {
        System.out.print("Enter labor name: ");
        String name = scanner.nextLine();

        System.out.print("Enter hourly rate: ");
        double hourlyRate = scanner.nextDouble();

        System.out.print("Enter hours worked: ");
        double hoursWorked = scanner.nextDouble();

        System.out.print("Enter worker productivity (0.0 - 1.0): ");
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
    }
}
