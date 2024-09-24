package Repository.Interfaces;

import java.util.List;

@FunctionalInterface
public interface ComponentRepository<T> {
    T create(T entity, int projectId);

}