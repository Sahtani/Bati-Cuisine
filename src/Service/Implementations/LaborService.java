package Service.Implementations;

import Model.Entities.Labor;
import Repository.Implementations.LaborRepository;
import Repository.Implementations.MaterialRepository;
import Service.Interfaces.IComponentService;

import java.sql.SQLException;

import static Util.DataValidator.validateLabor;

public class LaborService implements IComponentService<Labor> {

    private final LaborRepository laborRepository;
    public LaborService(LaborRepository laborRepository) {
        this.laborRepository = laborRepository;
    }

    @Override
    public Labor add(Labor labor) throws SQLException {
        try {
            validateLabor(labor);
            return laborRepository.create(labor);
        } catch (IllegalArgumentException e) {
            System.err.println("Validation failed: " + e.getMessage());
            return null;
        }
    }
}
