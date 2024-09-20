package UI;

import Model.Entities.Material;
import Model.Entities.Project;
import Service.Implementations.ClientService;
import Service.Implementations.MaterialService;
import Service.Implementations.ProjectService;
import Service.Interfaces.IComponentService;
import Service.Interfaces.IProjectService;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class MaterialUI {

    private final Scanner scanner = new Scanner(System.in);
    private final IComponentService<Material> materialService;
    private final IProjectService projectService;


    public MaterialUI(IComponentService<Material> materialService,IProjectService projectService) {
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

    public void createMaterial(int projectId) throws SQLException {

        System.out.println("---------- Material Addition ------------");
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
        Optional<Project> project = projectService.getProjectById(projectId);
        System.out.println(project.get().getId());
      Material material = new Material(name,vatRate,project.get(), unitCost, quantity, transportCost, qualityCoefficient);
        Material addedMaterial = materialService.add(material,project.get().getId());

        if (addedMaterial != null) {
            System.out.println("Material created with ID: " + material.getId());
        } else {
            System.out.println("Failed to create material.");
        }

    }
}




