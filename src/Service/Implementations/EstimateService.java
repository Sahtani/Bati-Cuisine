package Service.Implementations;

import Model.Entities.Estimate;
import Repository.Implementations.EstimateRepository;
import Service.Interfaces.IEstimateService;

import java.sql.SQLException;
import java.util.Optional;

public class EstimateService implements IEstimateService {


    private final EstimateRepository estimateRepository;


    public EstimateService(EstimateRepository estimateRepository) {
        this.estimateRepository = estimateRepository;

    }


    @Override
    public void addEstimate(Estimate estimate) throws SQLException {
        estimateRepository.create(estimate);
    }


    @Override
    public Optional<Estimate> getEstimateById(int estimateId, int clientId) throws SQLException {
        Optional<Estimate> estimate = Optional.ofNullable(estimateRepository.findById(estimateId));

        if (estimate.isPresent() && estimate.get().getProject().getClient().getId() == clientId) {
            return estimate;
        }

        return estimate;
    }

    @Override
    public void update(Estimate estimate, double newTotalCost) throws SQLException {

    }

    @Override
    public void updateStatus(Estimate estimate, boolean newStatus) throws SQLException {
        estimate = estimateRepository.findById(estimate.getId());
        if (estimate != null) {
            estimate.setAccepted(newStatus);
            estimateRepository.updateStatus(estimate, newStatus);
        } else {
            System.out.println("Estimate not found.");
        }
    }


}
