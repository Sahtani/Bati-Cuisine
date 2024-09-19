package Service.Interfaces;

import Model.Entities.Material;

import java.sql.SQLException;

public interface IComponentService<T> {
    T add(T entity) throws SQLException;

}
