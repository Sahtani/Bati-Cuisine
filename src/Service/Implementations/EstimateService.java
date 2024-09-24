package Service.Implementations;

import Model.Entities.Estimate;
import Repository.Implementations.EstimateRepository;
import Service.Interfaces.IEstimateService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EstimateService implements IEstimateService {


    private EstimateRepository estimateRepository;
    private Connection connection;

    public EstimateService(EstimateRepository estimateRepository) {
        this.estimateRepository = estimateRepository;

    }


    @Override
    public Estimate addEstimate(Estimate estimate) throws SQLException {
        estimateRepository.create(estimate);
        return estimate;
    }

    @Override
    public void updateEstimate(int estimateId, Estimate estimate) throws SQLException {

    }

    @Override
    public Optional<Estimate> deleteEstimate(int estimateId) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Estimate> getAllEstimates() throws SQLException {
        return estimateRepository.findAll();
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
            estimateRepository.updateStatus(estimate,newStatus);
        } else {
            System.out.println("Estimate not found.");
        }
    }


//    public void acceptEstimate(int estimateId) throws SQLException {
//
//    }
//
//    public void refuseEstimate(int estimateId) throws SQLException {
//        Estimate estimate = estimateRepository.findById(estimateId);
//        if (estimate != null) {
//            estimate.setAccepted(false); // Refuse the estimate
//            estimateRepository.update(estimate);
//        } else {
//            System.out.println("Estimate not found.");
//        }
//    }


}
