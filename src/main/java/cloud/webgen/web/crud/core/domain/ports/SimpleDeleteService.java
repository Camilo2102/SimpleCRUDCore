package cloud.webgen.web.crud.core.domain.ports;

import cloud.webgen.web.commons.exceptions.HttpException;
import cloud.webgen.web.crud.core.domain.model.WebGenAuditModel;

public interface SimpleDeleteService <T extends WebGenAuditModel> {
    /**
     * Elimina un elemento del servicio por su identificador único.
     *
     * @param id Identificador único del elemento a ser eliminado.
     * @return Elemento eliminado.
     * @throws HttpException Excepción lanzada si no se encuentra el elemento para eliminar.
     */
    T delete(String id) throws HttpException;
}