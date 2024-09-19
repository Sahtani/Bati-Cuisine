package Repository.Interfaces;
@FunctionalInterface
public interface ComponentRepository<T> {
    T create(T entity);
}