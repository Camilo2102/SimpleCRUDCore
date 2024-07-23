package cloud.webgen.web.crud.core.domain.enums;

import lombok.Getter;

@Getter
public enum SimpleCRUDUseCaseNames {

    CREATE("CreateService"),
    GET_ALL("GetAllService"),
    GET_BY_ID("GetByIdService"),
    GET_ALL_PAGED("GetAllPagedService"),
    UPDATE("UpdateService"),
    DELETE("DeleteService");

    private final String useCase;

    SimpleCRUDUseCaseNames(String useCase) {
        this.useCase = useCase;
    }
}
