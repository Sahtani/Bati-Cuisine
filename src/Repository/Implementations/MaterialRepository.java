package Repository.Implementations;

import Model.Entities.Material;
import Repository.Interfaces.ComponentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaterialRepository implements ComponentRepository<Material> {
    Connection connection;

    public MaterialRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Material create(Material material) {
        String sqlInsert = "INSERT INTO material (name, unit_cost, quantity, component_type, vat_rate, transport_cost, quality_coefficient) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlSelect = "SELECT id FROM material WHERE name = ? AND unit_cost = ?";

        try (PreparedStatement insertStmt = connection.prepareStatement(sqlInsert)) {
            insertStmt.setString(1, material.getName());
            insertStmt.setDouble(2, material.getUnitCost());
            insertStmt.setDouble(3, material.getQuantity());
            insertStmt.setString(4, material.getComponentType());
            insertStmt.setDouble(5, material.getVatRate());
            insertStmt.setDouble(6, material.getTransportCost());
            insertStmt.setDouble(7, material.getQualityCoefficient());

            int affectedRows = insertStmt.executeUpdate();
            if (affectedRows > 0) {
                try (PreparedStatement selectStmt = connection.prepareStatement(sqlSelect)) {
                    selectStmt.setString(1, material.getName());
                    selectStmt.setDouble(2, material.getUnitCost());

                    try (ResultSet rs = selectStmt.executeQuery()) {
                        if (rs.next()) {
                            int id = rs.getInt(1);
                            material.setId(id);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return material;
    }
}
