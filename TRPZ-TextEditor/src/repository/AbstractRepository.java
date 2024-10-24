package repository;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRepository<T> implements Repository<T> {
    protected List<T> dataStore = new ArrayList<>();

    @Override
    public void create(T entity) {
        dataStore.add(entity);
    }

    @Override
    public T read(int id) {
        return dataStore.get(id); // assuming id matches index for simplicity
    }

    @Override
    public void update(T entity) {
        int index = dataStore.indexOf(entity);
        if (index >= 0) {
            dataStore.set(index, entity);
        }
    }

    @Override
    public void delete(int id) {
        dataStore.remove(id);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(dataStore);
    }
}

