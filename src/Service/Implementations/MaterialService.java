package Service.Implementations;

import Model.Entities.Material;
import Repository.Implementations.MaterialRepository;
import Service.Interfaces.IComponentService;

import java.sql.SQLException;
import java.util.List;

import static Util.DataValidator.validateMaterial;

public class MaterialService implements IComponentService<Material> {
    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {

        this.materialRepository = materialRepository;
    }

    @Override
    public Material add(Material material, int projectId) throws SQLException {

        try {
            validateMaterial(material);
            return materialRepository.create(material, projectId);
        } catch (IllegalArgumentException e) {
            System.err.println("Validation failed: " + e.getMessage());
            return null;
        }
    }

    public List<Material> getAllMaterials() throws SQLException {
        return materialRepository.getAllMaterials();
    }

    public double calculateTotalCostMT() throws SQLException {
        double totalCost = 0.0;
        List<Material> materials = getAllMaterials();

        totalCost = materials.stream()
                .mapToDouble(material ->
                        (material.getQuantity() * material.getUnitCost() * material.getQualityCoefficient())
                                + material.getTransportCost())
                .sum();

        return totalCost;
    }


}
