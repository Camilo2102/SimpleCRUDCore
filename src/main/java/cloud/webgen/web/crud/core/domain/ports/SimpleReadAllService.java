package cloud.webgen.web.crud.core.domain.ports;

import cloud.webgen.web.commons.exceptions.HttpException;
import cloud.webgen.web.crud.core.domain.model.WebGenAuditModel;

import java.util.List;

public interface SimpleReadAllService<T extends WebGenAuditModel> {
    /**
     * Obtiene todos los elementos del servicio.
     *
     * @return Lista de elementos.
     */
    List<T> getAll();
}
