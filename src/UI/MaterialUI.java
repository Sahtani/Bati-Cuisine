package UI;

import Model.Entities.Material;
import Service.Implementations.MaterialService;
import Service.Interfaces.IComponentService;

import java.sql.SQLException;
import java.util.Scanner;

public class MaterialUI {

    private final Scanner scanner = new Scanner(System.in);
    private final IComponentService<Material> materialService;

    public MaterialUI(IComponentService<Material> materialService) {
        this.materialService = materialService;
    }

    public void showMenu() throws SQLException {
        while (true) {
            System.out.println("Material Management");
            System.out.println("1. Create Material");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createMaterial();
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void createMaterial() throws SQLException {
        while (true) {
            System.out.println("---------- Adding the materials ------------");
            System.out.print("Enter material name: ");
            String name = scanner.nextLine();
            System.out.print("Enter the unit cost (€/m²): ");
            double unitCost = scanner.nextDouble();
            System.out.print("Enter the quantity (in m²): ");
            double quantity = scanner.nextDouble();
            System.out.print("Enter VAT rate: ");
            double vatRate = scanner.nextDouble();
            System.out.print("Enter the transportation cost (€): ");
            double transportCost = scanner.nextDouble();
            System.out.print("Enter the material quality coefficient (1.0 = standard, > 1.0 = high quality): ");
            double qualityCoefficient = scanner.nextDouble();
            scanner.nextLine();

            Material material = new Material(name, unitCost, quantity, transportCost, qualityCoefficient, vatRate);
            Material addedMaterial = materialService.add(material);

            if (addedMaterial != null) {
                System.out.println("Material created with ID: " + material.getId());
            } else {
                System.out.println("Failed to create material.");
            }

            System.out.print("Do you want to add another material? (y/n): ");
            String response = scanner.nextLine();

            if (!response.equalsIgnoreCase("y")) {
                break;
            }
        }
    }

}

