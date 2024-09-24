package Service.Implementations;

import Model.Entities.Labor;
import Repository.Implementations.LaborRepository;
import Service.Interfaces.IComponentService;

import java.sql.SQLException;
import java.util.List;

import static Util.DataValidator.validateLabor;

public class LaborService implements IComponentService<Labor> {

    private final LaborRepository laborRepository;

    public LaborService(LaborRepository laborRepository) {
        this.laborRepository = laborRepository;
    }

    @Override
    public Labor add(Labor labor, int projectId) throws SQLException {
        try {
            validateLabor(labor);
            return laborRepository.create(labor, projectId);
        } catch (IllegalArgumentException e) {
            System.err.println("Validation failed: " + e.getMessage());
            return null;
        }
    }

    public List<Labor> getAllLabors() throws SQLException {
        return laborRepository.getAllLabor();
    }

    public double calculateTotalCostLB() throws SQLException {
        double totalCost = 0.0;
        List<Labor> labors = laborRepository.getAllLabor();

        totalCost = labors.stream()
                .mapToDouble(material ->
                        (material.getHourlyRate() * material.getHoursWorked() * material.getWorkerProductivity())
                )
                .sum();

        return totalCost;
    }

}
