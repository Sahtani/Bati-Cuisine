package Repository.Implementations;

import Model.Interfaces.Identifiable;
import Repository.Interfaces.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T extends Identifiable> implements Repository<T> {

    protected Connection connection;

    public BaseRepository(Connection connection) {
        this.connection = connection;
    }

    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    protected abstract String getTableName();

    protected abstract void setParameters(PreparedStatement statement, T entity) throws SQLException;

    @Override
    public void create(T entity) throws SQLException {
        String query = getInsertQuery();
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(statement, entity);
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating entity failed, no ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating entity failed, no rows affected.");
            }
        }
    }


    @Override
    public T findById(int id) throws SQLException {
        T entity = null;
        String query = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    entity = mapResultSetToEntity(resultSet);
                }
            }
        }
        return entity;
    }

    @Override
    public List<T> findAll() throws SQLException {
        List<T> entities = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                entities.add(mapResultSetToEntity(resultSet));
            }
        }
        return entities;
    }

    @Override
    public void update(T entity) throws SQLException {
        String query = getUpdateQuery();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setParameters(statement, entity);
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    protected abstract String getInsertQuery();

    protected abstract String getUpdateQuery();

}
