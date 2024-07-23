package cloud.webgen.web.crud.core.application.simpleCRUD;

import cloud.webgen.web.commons.exceptions.HttpException;
import cloud.webgen.web.crud.core.domain.model.WebGenAuditModel;
import cloud.webgen.web.crud.core.domain.ports.SimpleReadOneService;
import cloud.webgen.web.crud.core.domain.ports.WebgenAuditRepository;
import org.springframework.http.HttpStatus;

public class ReadOneUseCase <T extends WebGenAuditModel> implements SimpleReadOneService<T> {

    private final WebgenAuditRepository<T> repository;

    public ReadOneUseCase(WebgenAuditRepository<T> repository) {
        this.repository = repository;
    }

    /**
     * Obtiene un elemento por su identificador único.
     *
     * @param id Identificador único del elemento.
     * @return Elemento encontrado.
     * @throws HttpException Excepción lanzada si no se encuentra el elemento.
     */
    @Override
    public T getOne(String id) throws HttpException {
        return this.repository.findById(id).orElseThrow(() -> new HttpException("No data found", HttpStatus.NOT_FOUND) );
    }
}
