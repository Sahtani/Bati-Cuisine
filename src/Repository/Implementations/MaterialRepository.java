package Repository.Implementations;

import Model.Entities.Material;
import Repository.Interfaces.ComponentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Material> getAllMaterials() throws SQLException {
        List<Material> materials = new ArrayList<>();
        String query = "SELECT * FROM material";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Material material = new Material();
                material.setId(resultSet.getInt("id"));
                material.setName(resultSet.getString("name"));
                material.setVatRate(resultSet.getDouble("tvarate"));
                material.setUnitCost(resultSet.getDouble("unitcost"));
                material.setQuantity(resultSet.getDouble("quantity"));
                material.setTransportCost(resultSet.getDouble("transportcost"));
                material.setQualityCoefficient(resultSet.getDouble("qualitycoefficient"));
                materials.add(material);
            }
        }

        return materials;
    }

    public void updateMaterialVAT(int materialId, double newVatRate) throws SQLException {
        String query = "UPDATE materials SET vat_rate = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, newVatRate);
            statement.setInt(2, materialId);
            statement.executeUpdate();
        }
    }


}
