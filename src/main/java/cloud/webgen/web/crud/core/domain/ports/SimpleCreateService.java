package cloud.webgen.web.crud.core.domain.ports;

import cloud.webgen.web.commons.exceptions.HttpException;
import cloud.webgen.web.crud.core.domain.model.WebGenAuditModel;

public interface SimpleCreateService<T extends WebGenAuditModel> {

    /**
     * Guarda un nuevo elemento en el servicio.
     *
     * @param element Elemento a ser guardado.
     * @return Elemento guardado.
     */
    T save(Object element) throws HttpException;
}
