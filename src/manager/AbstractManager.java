package manager;

import entity.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractManager<T extends Entity> {

    protected Map<String, T> map;

    protected AbstractManager() {
        map = new HashMap<>();
    }

    public T findById(String id) {
        return map.get(id);
    }

    public List<T> listAll() {
        return new ArrayList<>(map.values());
    }
    public Map<String, T> getMap() {
        return map;
    }
    public void loadFromObject(Object obj) {
        if (obj instanceof Map) {
            map = (Map<String, T>) obj;
        }
    }
    public abstract boolean add(T obj);
}
