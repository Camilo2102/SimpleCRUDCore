package cloud.webgen.web.crud.core.domain.ports;

import cloud.webgen.web.commons.exceptions.HttpException;
import cloud.webgen.web.crud.core.domain.model.WebGenAuditModel;

public interface SimpleUpdateService <T extends WebGenAuditModel> {
    /**
     * Actualiza un elemento existente en el servicio.
     *
     * @param element Elemento con las actualizaciones.
     * @return Elemento actualizado.
     * @throws HttpException Excepción lanzada si no se encuentra el elemento para actualizar.
     */
    T update(Object element) throws HttpException;
}
