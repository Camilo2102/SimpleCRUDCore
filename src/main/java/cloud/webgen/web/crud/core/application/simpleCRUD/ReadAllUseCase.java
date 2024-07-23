package cloud.webgen.web.crud.core.application.simpleCRUD;

import cloud.webgen.web.commons.exceptions.HttpException;
import cloud.webgen.web.crud.core.domain.model.WebGenAuditModel;
import cloud.webgen.web.crud.core.domain.ports.SimpleReadAllService;
import cloud.webgen.web.crud.core.domain.ports.WebgenAuditRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ReadAllUseCase<T extends WebGenAuditModel> implements SimpleReadAllService<T> {

    private final WebgenAuditRepository<T> repository;

    public ReadAllUseCase(WebgenAuditRepository<T> repository) {
        this.repository = repository;
    }

    /**
     * Obtiene todos los elementos del servicio.
     *
     * @return Lista de elementos.
     */
    @Override
    public List<T> getAll() {
        return (List<T>) this.repository.findAll();
    }

}
