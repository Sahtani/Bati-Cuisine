package Repository.Implementations;

import Model.Entities.Labor;
import Model.Entities.Material;
import Repository.Interfaces.ComponentRepository;

import java.sql.*;

public class LaborRepository implements ComponentRepository<Labor> {
    private final Connection connection;

    public LaborRepository(Connection connection) {
        this.connection = connection;
    }



    @Override
    public Labor create(Labor labor) {
            String sql = "INSERT INTO labor (name, tvarate, hourlyrate, hoursworked, workerproductivity,typecomponent) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, labor.getName());
                stmt.setDouble(2, labor.getVatRate());
                stmt.setDouble(3, labor.getHourlyRate());
                stmt.setDouble(4, labor.getHoursWorked());
                stmt.setDouble(5, labor.getWorkerProductivity());
                stmt.setString(5, labor.getComponentType());
                stmt.executeUpdate();

                int affectedRows = stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return labor;
    }
}
