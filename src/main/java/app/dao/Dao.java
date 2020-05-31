package app.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    List<T> getAll();

    Optional<T> get(long id);

    void save(T t);

    void update(T t, String[] params);

    void delete(T t);
}
