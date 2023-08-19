package playground.jpa.metamodel;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import java.lang.reflect.Field;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.*;


public class JpaModelGenUtil {
    private JpaModelGenUtil() {}

    public static SingularAttributesMap mapSingularAttributes(Class<?> jpaGenerated) {
        requireNonNull(jpaGenerated.getDeclaredAnnotation(StaticMetamodel.class),
                "Missing @StaticMetamodel annotation in " + jpaGenerated);
        return stream(jpaGenerated.getDeclaredFields())
                .filter(field -> field.getType().equals(SingularAttribute.class))
                .collect(collectingAndThen(
                        toMap(Field::getName, JpaModelGenUtil::getSingularAttribute),
                        SingularAttributesMap::new
                ));
    }

    private static SingularAttributeWrapper<?, ?> getSingularAttribute(Field field) {
        try {
            SingularAttribute<?, ?> sa = requireNonNull(
                    (SingularAttribute<?, ?>) field.get(null),
                    "Unexpected null value of SingularAttribute '" + field.getName()
                            + "' in " + field.getDeclaringClass()
            );
            return new SingularAttributeWrapper<>(sa);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
