package jdbc.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {

    E save(E entity);

    E update(E entity);

    boolean delete(K id);

    Optional<E> findById(K id);

    List<E> findAll();

}
