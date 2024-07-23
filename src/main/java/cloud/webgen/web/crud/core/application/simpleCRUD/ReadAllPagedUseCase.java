package cloud.webgen.web.crud.core.application.simpleCRUD;

import cloud.webgen.web.crud.core.domain.model.WebGenAuditModel;
import cloud.webgen.web.crud.core.domain.ports.SimpleReadPagedService;
import cloud.webgen.web.crud.core.domain.ports.WebgenAuditRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ReadAllPagedUseCase<T extends WebGenAuditModel> implements SimpleReadPagedService<T> {

    private final WebgenAuditRepository<T> repository;

    public ReadAllPagedUseCase(WebgenAuditRepository<T> repository) {
        this.repository = repository;
    }
    /**
     * Obtiene los datos paginados
     *
     * @param pageable paginador de los datos
     * @return Lista de elementos paginados
     */
    @Override
    public List<T> getAllPaged(Pageable pageable) {
        return this.repository.findAll(pageable);
    }
}
