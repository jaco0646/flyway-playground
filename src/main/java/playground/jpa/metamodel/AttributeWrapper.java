package playground.jpa.metamodel;

import jakarta.persistence.metamodel.Attribute;
import lombok.experimental.Delegate;

import java.lang.invoke.MethodType;
import java.util.Collection;

class AttributeWrapper<X, T> implements Attribute<X, T> {
    @Delegate
    private final Attribute<X, T> delegate;
    private final boolean isCollection;
    private final boolean isNumeric;

    AttributeWrapper(Attribute<X, T> attribute) {
        this.delegate = attribute;
        this.isCollection = isCollection(attribute);
        this.isNumeric = isNumeric(attribute);
    }

    private boolean isCollection(Attribute<X, T> attribute) {
        Class<T> maybeCollection = attribute.getJavaType();
        return Collection.class.isAssignableFrom(maybeCollection);
    }

    private boolean isNumeric(Attribute<X, T> attribute) {
        Class<T> maybeNumber = attribute.getJavaType();
        Class<?> boxed = MethodType.methodType(maybeNumber).wrap().returnType();
        return Number.class.isAssignableFrom(boxed);
    }

    boolean implementsCollection() {
        return isCollection;
    }

    boolean implementsNumber() {
        return isNumeric;
    }
}
