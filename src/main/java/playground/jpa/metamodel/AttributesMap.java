package playground.jpa.metamodel;

import lombok.Getter;

import java.util.Map;
import java.util.NoSuchElementException;

public class AttributesMap {
    private final Map<String, AttributeWrapper<?, ?>> map;

    @Getter
    private final Class<?> entityClass;

    AttributesMap(Map<String, AttributeWrapper<?, ?>> map) {
        this.map = map;
        this.entityClass = map.values().iterator().next().getDeclaringType().getJavaType();
    }

    public boolean isCollection(String name) {
        if (map.containsKey(name)) {
            return map.get(name).implementsCollection();
        }
        throw noSuchElementException(name);
    }

    public boolean isNumeric(String name) {
        if (map.containsKey(name)) {
            return map.get(name).implementsNumber();
        }
        throw noSuchElementException(name);
    }

    private NoSuchElementException noSuchElementException(String name) {
        return new NoSuchElementException("No such column '" + name + "' found in " + entityClass);
    }
}
