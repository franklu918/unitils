package org.unitils.inject.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Filip Neven
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoInjectStatic {

    public enum PropertyAccessType {
        FIELD,
        SETTER,
        DEFAULT;
    }

    Class target();

    PropertyAccessType propertyAccessType() default PropertyAccessType.DEFAULT;
    
}