package cloud.webgen.web.crud.core.application.simpleCRUD;

import cloud.webgen.web.commons.exceptions.HttpException;
import cloud.webgen.web.commons.mapper.ObjectParser;
import cloud.webgen.web.crud.core.domain.model.WebGenAuditModel;
import cloud.webgen.web.crud.core.domain.ports.SimpleUpdateService;
import cloud.webgen.web.crud.core.domain.ports.WebgenAuditRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

public class UpdateUseCase <T extends WebGenAuditModel> implements SimpleUpdateService<T> {
    private final WebgenAuditRepository<T> repository;
    private final Class<T> entityClass;

    public UpdateUseCase(WebgenAuditRepository<T> repository, Class<T> entityClass) {
        this.repository = repository;
        this.entityClass = entityClass;
    }

    /**
     * Actualiza un elemento existente en el servicio.
     *
     * @param element Elemento con las actualizaciones.
     * @return Elemento actualizado.
     * @throws HttpException Excepción lanzada si no se encuentra el elemento para actualizar.
     */
    @Override
    public T update(Object element) throws HttpException {
        T object = ObjectParser.parseObjectToClass(element, entityClass);

        return repository.findById(object.getId())
                .map(existingElement -> repository.save(object))
                .orElseThrow(() -> new HttpException("Elemento no encontrado para actualizar", HttpStatus.NOT_FOUND));
    }
}
