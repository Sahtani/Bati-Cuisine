package Service.Implementations;

import Model.Entities.Material;
import Repository.Implementations.MaterialRepository;
import Service.Interfaces.IComponentService;

import java.sql.SQLException;

import static Util.DataValidator.validateMaterial;

public class MaterialService implements IComponentService<Material> {
    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public Material add(Material material) throws SQLException {

        try {
            validateMaterial(material);
            return materialRepository.create(material);
        } catch (IllegalArgumentException e) {
            System.err.println("Validation failed: " + e.getMessage());
            return null;
        }
    }
}
