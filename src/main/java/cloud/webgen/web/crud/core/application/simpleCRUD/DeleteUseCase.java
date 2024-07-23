package cloud.webgen.web.crud.core.application.simpleCRUD;

import cloud.webgen.web.commons.exceptions.HttpException;
import cloud.webgen.web.crud.core.domain.model.WebGenAuditModel;
import cloud.webgen.web.crud.core.domain.ports.SimpleDeleteService;
import cloud.webgen.web.crud.core.domain.ports.WebgenAuditRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

public class DeleteUseCase<T extends WebGenAuditModel> implements SimpleDeleteService<T> {

    private final WebgenAuditRepository<T> repository;

    public DeleteUseCase(WebgenAuditRepository<T> repository) {
        this.repository = repository;
    }

    /**
     * Elimina un elemento del servicio por su identificador único.
     *
     * @param id Identificador único del elemento a ser eliminado.
     * @return Elemento eliminado.
     * @throws HttpException Excepción lanzada si no se encuentra el elemento para eliminar.
     */
    @Override
    public T delete(String id) throws HttpException {
        T element = repository.findById(id)
                .orElseThrow(() -> new HttpException("Elemento no encontrado para eliminar", HttpStatus.NOT_FOUND));

        repository.deleteById(id);

        return element;
    }
}
