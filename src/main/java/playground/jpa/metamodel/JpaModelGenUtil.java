package playground.jpa.metamodel;

import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import java.lang.reflect.Field;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.*;


public class JpaModelGenUtil {
    private JpaModelGenUtil() {}

    public static AttributesMap mapAttributes(Class<?> jpaGenerated) {
        requireNonNull(jpaGenerated.getDeclaredAnnotation(StaticMetamodel.class),
                "Missing @StaticMetamodel annotation in " + jpaGenerated);
        return stream(jpaGenerated.getDeclaredFields())
                .filter(field -> Attribute.class.isAssignableFrom(field.getType()))
                .collect(collectingAndThen(
                        toMap(Field::getName, JpaModelGenUtil::getAttribute),
                        AttributesMap::new
                ));
    }

    private static AttributeWrapper<?, ?> getAttribute(Field field) {
        try {
            Attribute<?, ?> a = requireNonNull(
                    (Attribute<?, ?>) field.get(null),
                    "Unexpected null value of Attribute '" + field.getName()
                            + "' in " + field.getDeclaringClass()
            );
            return new AttributeWrapper<>(a);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
