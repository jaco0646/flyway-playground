package playground.jpa.metamodel;

import lombok.Getter;

import java.util.Map;
import java.util.NoSuchElementException;

public class SingularAttributesMap {
    private final Map<String, SingularAttributeWrapper<?, ?>> map;

    @Getter
    private final Class<?> entityClass;

    SingularAttributesMap(Map<String, SingularAttributeWrapper<?, ?>> map) {
        this.map = map;
        this.entityClass = map.values().iterator().next().getDeclaringType().getJavaType();
    }

    public boolean isNumeric(String name) {
        if (map.containsKey(name)) {
            return map.get(name).isNumeric();
        }
        throw new NoSuchElementException("No such column '" + name + "' found in " + entityClass);
    }
}
