package cloud.webgen.web.crud.core.domain.ports;

import cloud.webgen.web.crud.core.domain.enums.SimpleCRUDMethods;
import io.swagger.v3.oas.models.media.Schema;

public interface DocumentationService {

    void generateDocumentation(SimpleCRUDMethods method, String path, Class<?> clazz);
    void createPathForGetAll(String path);
    void createPathForGetOne(String path);
    void createPathForGetAllPaged(String path);
    void createPathForPost(String path, Class<?> clazz);
    void createPathForPut(String path,Class<?> clazz);
    void createPathForDelete(String path);
}
