package cloud.webgen.web.crud.core.application.simpleCRUD;

import cloud.webgen.web.commons.exceptions.HttpException;
import cloud.webgen.web.commons.mapper.ObjectParser;
import cloud.webgen.web.crud.core.domain.model.WebGenAuditModel;
import cloud.webgen.web.crud.core.domain.ports.SimpleCreateService;
import cloud.webgen.web.crud.core.domain.ports.WebgenAuditRepository;
import org.springframework.stereotype.Service;


public class CreateUseCase<T extends WebGenAuditModel> implements SimpleCreateService<T> {

    private final WebgenAuditRepository<T> repository;
    private final Class<T> entityClass;

    public CreateUseCase(WebgenAuditRepository<T> repository, Class<T> entityClass) {
        this.repository = repository;
        this.entityClass = entityClass;
    }

    /**
     * Guarda un nuevo elemento en el servicio.
     *
     * @param element Elemento a ser guardado.
     * @return Elemento guardado.
     */
    @Override
    public T save(Object element) throws HttpException {
        T object = ObjectParser.parseObjectToClass(element, entityClass);

        return this.repository.save(object);
    }
}
