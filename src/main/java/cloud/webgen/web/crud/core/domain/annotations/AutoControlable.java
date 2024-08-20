package cloud.webgen.web.crud.core.domain.annotations;

import cloud.webgen.web.crud.core.domain.enums.SimpleCRUDMethods;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
@Component
@Schema
public @interface AutoControlable {
    String repositoryName() default "";

    SimpleCRUDMethods[] availableMethods() default {
            SimpleCRUDMethods.CREATE,
            SimpleCRUDMethods.GET_ALL,
            SimpleCRUDMethods.GET_BY_ID,
            SimpleCRUDMethods.GET_ALL_PAGED,
            SimpleCRUDMethods.UPDATE,
            SimpleCRUDMethods.DELETE
    };


}
