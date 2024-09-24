package Service.Interfaces;

import Model.Entities.Estimate;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IEstimateService {

    Estimate addEstimate(Estimate estimate) throws SQLException;

    void updateEstimate(int estimateId, Estimate estimate) throws SQLException;

    Optional<Estimate> deleteEstimate(int estimateId) throws SQLException;

    List<Estimate> getAllEstimates() throws SQLException;

    Optional<Estimate> getEstimateById(int estimateId, int clientId) throws SQLException;

    void update(Estimate estimate, double newTotalCost) throws SQLException;

    void updateStatus(Estimate estimate,boolean newStatus) throws SQLException;




}
