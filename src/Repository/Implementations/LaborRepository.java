package Repository.Implementations;

import Model.Entities.Labor;
import Repository.Interfaces.ComponentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LaborRepository implements ComponentRepository<Labor> {
    private final Connection connection;

    public LaborRepository(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Labor create(Labor labor, int projectId) {
        String sql = "INSERT INTO labor (name, tvarate, hourlyrate, hoursworked, workerproductivity,typecomponent,project_id) VALUES (?, ?, ?, ?, ?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, labor.getName());
            stmt.setDouble(2, labor.getVatRate());
            stmt.setDouble(3, labor.getHourlyRate());
            stmt.setDouble(4, labor.getHoursWorked());
            stmt.setDouble(5, labor.getWorkerProductivity());
            stmt.setString(6, labor.getComponentType());
            stmt.setInt(7, labor.getProject().getId());

            stmt.executeUpdate();

            int affectedRows = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return labor;
    }

    public List<Labor> getAllLabor() throws SQLException {
        List<Labor> labors = new ArrayList<>();
        String query = "SELECT * FROM labor";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Labor labor = new Labor();
                labor.setId(resultSet.getInt("id"));
                labor.setName(resultSet.getString("name"));
                labor.setHourlyRate(resultSet.getDouble("hourlyrate"));
                labor.setHoursWorked(resultSet.getDouble("hoursworked"));
                labor.setWorkerProductivity(resultSet.getDouble("workerproductivity"));
                labors.add(labor);
            }
        }

        return labors;
    }

    public void updateLaborVAT(int laborId, double newVatRate) throws SQLException {
        String query = "UPDATE labor SET vat_rate = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, newVatRate);
            statement.setInt(2, laborId);
            statement.executeUpdate();
        }
    }

    public List<Labor> getLaborsByProjectId(int projectId) throws SQLException {
        List<Labor> labors = new ArrayList<>();
        String query = "SELECT * FROM labor WHERE project_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, projectId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Labor labor = new Labor();
                labor.setId(resultSet.getInt("id"));
                labor.setVatRate(resultSet.getDouble("tvarate"));

                labor.setHourlyRate(resultSet.getDouble("hourlyrate"));
                labor.setHoursWorked(resultSet.getDouble("hoursworked"));
                labor.setWorkerProductivity(resultSet.getDouble("workerproductivity"));
                labors.add(labor);
            }
        }
        return labors;
    }

}
