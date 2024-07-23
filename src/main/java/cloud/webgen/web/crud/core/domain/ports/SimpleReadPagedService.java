package cloud.webgen.web.crud.core.domain.ports;

import cloud.webgen.web.crud.core.domain.model.WebGenAuditModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SimpleReadPagedService<T extends WebGenAuditModel>{
    /**
     * Obtiene los datos paginados
     * @param pageable paginador de los datos
     * @return Lista de elementos paginados
     */
    List<T> getAllPaged(Pageable pageable);
}
