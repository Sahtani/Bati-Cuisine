package Service.Implementations;

import Model.Entities.Estimate;
import Repository.Implementations.EstimateRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class EstimateService {


    private EstimateRepository estimateRepository;

    public EstimateService(Connection connection) {
        this.estimateRepository = new EstimateRepository(connection);
    }

    public void saveEstimate(Estimate estimate) throws SQLException {
        estimateRepository.create(estimate);
    }
}
