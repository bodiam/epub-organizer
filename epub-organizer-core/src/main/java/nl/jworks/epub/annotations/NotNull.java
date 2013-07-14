package nl.jworks.epub.annotations;

import java.lang.annotation.*;

/**
 * Annotation to prevent coding errors.
 *
 * See 'Constant expressions & exceptions' in IntelliJ inspections to enable.
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {

}
