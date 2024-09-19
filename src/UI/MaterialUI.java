package UI;

import Model.Entities.Material;
import Service.Implementations.MaterialService;

import java.sql.SQLException;
import java.util.Scanner;

public class MaterialUI {
    private final MaterialService materialService;
    private final Scanner scanner = new Scanner(System.in);

    public MaterialUI(MaterialService materialService) {
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

    private void createMaterial() throws SQLException {
        System.out.print("Enter material name: ");
        String name = scanner.nextLine();

        System.out.print("Enter unit cost: ");
        double unitCost = scanner.nextDouble();

        System.out.print("Enter quantity: ");
        double quantity = scanner.nextDouble();

//        System.out.print("Enter component type: ");
//        scanner.nextLine();
//        String componentType = scanner.nextLine();


        System.out.print("Enter transport cost: ");
        double transportCost = scanner.nextDouble();

        System.out.print("Enter quality coefficient: ");
        double qualityCoefficient = scanner.nextDouble();

        System.out.print("Enter VAT rate: ");
        double vatRate = scanner.nextDouble();

        Material material = new Material(name, unitCost, quantity, transportCost, qualityCoefficient, vatRate);
        Material addedMaterial = materialService.add(material);

        if (addedMaterial != null) {
            System.out.println("Material created with ID: " + material.getId());
        } else {
            System.out.println("Failed to create material.");
        }
    }
}
