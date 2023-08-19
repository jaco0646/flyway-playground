package playground.jpa.metamodel;

import jakarta.persistence.metamodel.SingularAttribute;
import lombok.Getter;
import lombok.experimental.Delegate;

import java.lang.invoke.MethodType;

public class SingularAttributeWrapper<X, T> implements SingularAttribute<X, T> {
    @Delegate
    private final SingularAttribute<X, T> delegate;

    @Getter
    private final boolean isNumeric;

    SingularAttributeWrapper(SingularAttribute<X, T> singularAttribute) {
        this.delegate = singularAttribute;
        this.isNumeric = isNumeric(singularAttribute.getJavaType());
    }

    private boolean isNumeric(Class<T> maybeNumber) {
        Class<?> wrapper = MethodType.methodType(maybeNumber).wrap().returnType();
        return Number.class.isAssignableFrom(wrapper);
    }
}
