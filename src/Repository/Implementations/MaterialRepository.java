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
    public Material create(Material material,int projectId) {
        String sqlInsert = "INSERT INTO material (name, unitcost, quantity,tvarate, transportcost, qualitycoefficient,typecomponent,project_id) VALUES (?, ?, ?, ?, ?, ?,?,?)";
        String sqlSelect = "SELECT id FROM material WHERE name = ? AND unitcost = ?";

        try (PreparedStatement insertStmt = connection.prepareStatement(sqlInsert)) {
            insertStmt.setString(1, material.getName());
            insertStmt.setDouble(2, material.getUnitCost());
            insertStmt.setDouble(3, material.getQuantity());
            insertStmt.setDouble(4, material.getVatRate());
            insertStmt.setDouble(5, material.getTransportCost());
            insertStmt.setDouble(6, material.getQualityCoefficient());
            insertStmt.setString(7, material.getComponentType());
            insertStmt.setInt(8,projectId);
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
