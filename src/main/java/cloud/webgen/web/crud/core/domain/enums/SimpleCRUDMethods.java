package cloud.webgen.web.crud.core.domain.enums;

import cloud.webgen.web.crud.core.application.simpleCRUD.*;
import lombok.Getter;

@Getter
public enum SimpleCRUDMethods {
    CREATE("Create", CreateUseCase.class, true),
    GET_ALL("GetAll", ReadAllUseCase.class, false),
    GET_BY_ID("GetById", ReadOneUseCase.class, false),
    GET_ALL_PAGED("GetAllPaged", ReadAllPagedUseCase.class, false),
    UPDATE("Update", UpdateUseCase.class, true),
    DELETE("Delete", DeleteUseCase.class, false);

    private final String name;
    private final Class<?> clazz;
    private final boolean requiredConversion;

    SimpleCRUDMethods(String name, Class<?> clazz, boolean requiredConversion) {
        this.name = name;
        this.clazz = clazz;
        this.requiredConversion = requiredConversion;
    }

}
