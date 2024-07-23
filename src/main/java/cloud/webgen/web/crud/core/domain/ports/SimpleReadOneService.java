package cloud.webgen.web.crud.core.domain.ports;

import cloud.webgen.web.commons.exceptions.HttpException;
import cloud.webgen.web.crud.core.domain.model.WebGenAuditModel;

public interface SimpleReadOneService<T extends WebGenAuditModel> {
    /**
     * Obtiene un elemento por su identificador único.
     *
     * @param id Identificador único del elemento.
     * @return Elemento encontrado.
     * @throws HttpException Excepción lanzada si no se encuentra el elemento.
     */
    T getOne(String id) throws HttpException;
}
