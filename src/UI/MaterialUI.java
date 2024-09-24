package UI;

import Model.Entities.Material;
import Model.Entities.Project;
import Service.Interfaces.IComponentService;
import Service.Interfaces.IProjectService;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

import static Util.DataValidator.validateMaterial;

public class MaterialUI {

    private final Scanner scanner = new Scanner(System.in);
    private final IComponentService<Material> materialService;
    private final IProjectService projectService;


    public MaterialUI(IComponentService<Material> materialService, IProjectService projectService) {
        this.materialService = materialService;
        this.projectService = projectService;
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

                    //  createMaterial();
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public Material createMaterial(int projectId) throws SQLException {
        System.out.println("---------- Material Addition ------------");

        String name;
        do {
            System.out.print("Enter material name: ");
            name = scanner.nextLine();
            if (name == null || name.isEmpty()) {
                System.out.println("Material name cannot be null or empty.");
            }
        } while (name == null || name.isEmpty());

        double unitCost;
        do {
            System.out.print("Enter the unit cost (€/m²): ");
            unitCost = scanner.nextDouble();
            if (unitCost < 0) {
                System.out.println("Material unit cost cannot be negative.");
            }
        } while (unitCost < 0);

        double quantity;
        do {
            System.out.print("Enter the quantity (in m²): ");
            quantity = scanner.nextDouble();
            if (quantity < 0) {
                System.out.println("Material quantity cannot be negative.");
            }
        } while (quantity < 0 );

        double vatRate;
        do {
            System.out.print("Enter VAT rate: ");
            vatRate = scanner.nextDouble();
            if (vatRate < 0) {
                System.out.println("VAT rate cannot be negative.");
            }
        } while (vatRate < 0);

        double transportCost;
        do {
            System.out.print("Enter the transportation cost (€): ");
            transportCost = scanner.nextDouble();
            if (transportCost < 0) {
                System.out.println("Transport cost cannot be negative.");
            }
        } while (transportCost < 0);

        double qualityCoefficient;
        do {
            System.out.print("Enter the material quality coefficient (1.0 = standard, > 1.1 = high quality): ");
            qualityCoefficient = scanner.nextDouble();
            if (qualityCoefficient < 0 || qualityCoefficient > 1) {
                System.out.println("Quality coefficient must be between 0 and 1.");
            }
        } while (qualityCoefficient < 0 || qualityCoefficient > 1);

        scanner.nextLine();

        Optional<Project> project = projectService.getProjectById(projectId);

        Material material = new Material(name, vatRate, project.get(), unitCost, quantity, transportCost, qualityCoefficient);
        Material addedMaterial = materialService.add(material, project.get().getId());

        if (addedMaterial != null) {
            System.out.println("Material created with ID: " + material.getId());
        } else {
            System.out.println("Failed to create material.");
        }

        return material;
    }


}




