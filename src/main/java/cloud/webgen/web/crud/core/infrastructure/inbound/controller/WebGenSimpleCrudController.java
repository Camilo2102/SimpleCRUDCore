package cloud.webgen.web.crud.core.infrastructure.inbound.controller;

import cloud.webgen.web.commons.exceptions.HttpException;
import cloud.webgen.web.crud.core.domain.enums.SimpleCRUDUseCaseNames;
import cloud.webgen.web.crud.core.domain.ports.*;
import cloud.webgen.web.crud.core.infrastructure.inbound.utils.BeanProjectFinder;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Hidden
@RequestMapping("/api/v2")
public class WebGenSimpleCrudController {
    private final BeanProjectFinder beanServiceFinder;

    /**
     * Constructor del controlador ApiController.
     *
     * @param beanLocator El localizador de beans utilizado para encontrar servicios CRUD.
     */
    @Autowired
    public WebGenSimpleCrudController(BeanProjectFinder beanLocator) {
        this.beanServiceFinder = beanLocator;
    }

    /**
     * Maneja las solicitudes GET para obtener todos los datos de un tipo específico.
     *
     * @param variable La variable de la ruta que identifica el tipo de datos.
     * @return Una respuesta HTTP con la lista de todos los datos y el estado correspondiente.
     * @throws HttpException Si ocurre un error al procesar la solicitud.
     */
    @RequestMapping(value = "/{variable}", method = RequestMethod.GET)
    public ResponseEntity<?> getMethod(@PathVariable("variable") String variable) throws HttpException {
        SimpleReadAllService<?> readService = this.beanServiceFinder.findUseCaseBeanByName(variable + SimpleCRUDUseCaseNames.GET_ALL.getUseCase(), SimpleReadAllService.class);
        List<?> allData = readService.getAll();
        return new ResponseEntity<>(allData, HttpStatus.OK);
    }

    /**
     * Maneja las solicitudes GET para obtener un dato específico por su ID.
     *
     * @param variable La variable de la ruta que identifica el tipo de datos.
     * @param id El ID del dato a recuperar.
     * @return Una respuesta HTTP con el dato recuperado y el estado correspondiente.
     * @throws HttpException Si ocurre un error al procesar la solicitud.
     */
    @RequestMapping(value = "/{variable}/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMethod(@PathVariable("variable") String variable, @PathVariable("id") String id) throws HttpException {
        SimpleReadOneService<?> readService = this.beanServiceFinder.findUseCaseBeanByName(variable + SimpleCRUDUseCaseNames.GET_BY_ID.getUseCase(), SimpleReadOneService.class);
        Object foundData = readService.getOne(id);
        return new ResponseEntity<>(foundData, HttpStatus.OK);
    }

    /**
     * Maneja las solicitudes GET para obtener un dato específico por su ID.
     *
     * @param variable La variable de la ruta que identifica el tipo de datos.
     * @param page El numero de pagina.
     * @param size El tamaño de pagina.
     * @return Una respuesta HTTP con el dato recuperado y el estado correspondiente.
     * @throws HttpException Si ocurre un error al procesar la solicitud.
     */
    @RequestMapping(value = "/paged/{variable}", method = RequestMethod.GET)
    public ResponseEntity<?> getMethod(@PathVariable("variable") String variable, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) throws HttpException {
        SimpleReadPagedService<?> readService = this.beanServiceFinder.findUseCaseBeanByName(variable + SimpleCRUDUseCaseNames.GET_ALL_PAGED.getUseCase(), SimpleReadPagedService.class);
        List<?> foundData = readService.getAllPaged(PageRequest.of(page, size));
        return new ResponseEntity<>(foundData, HttpStatus.OK);
    }

    /**
     * Maneja las solicitudes POST para crear nuevos datos.
     *
     * @param variable La variable de la ruta que identifica el tipo de datos.
     * @param body El cuerpo de la solicitud que contiene los datos a guardar.
     * @return Una respuesta HTTP con el objeto guardado y el estado correspondiente.
     * @throws HttpException Si ocurre un error al procesar la solicitud.
     */
    @RequestMapping(value = "/{variable}", method = RequestMethod.POST)
    public ResponseEntity<?> postMethod(@PathVariable("variable") String variable, @RequestBody Object body) throws HttpException {
        SimpleCreateService<?> crudService = this.beanServiceFinder.findUseCaseBeanByName(variable + SimpleCRUDUseCaseNames.CREATE.getUseCase(), SimpleCreateService.class);
        Object savedData = crudService.save(body);
        return new ResponseEntity<>(savedData, HttpStatus.CREATED);
    }

    /**
     * Maneja las solicitudes PUT para actualizar datos existentes.
     *
     * @param variable La variable de la ruta que identifica el tipo de datos.
     * @param body El cuerpo de la solicitud que contiene los datos actualizados.
     * @return Una respuesta HTTP con el objeto actualizado y el estado correspondiente.
     * @throws HttpException Si ocurre un error al procesar la solicitud.
     */
    @RequestMapping(value = "/{variable}", method = RequestMethod.PUT)
    public ResponseEntity<?> putMethod(@PathVariable("variable") String variable, @RequestBody Object body) throws HttpException {
        SimpleUpdateService<?> updateService = this.beanServiceFinder.findUseCaseBeanByName(variable + SimpleCRUDUseCaseNames.UPDATE.getUseCase(), SimpleUpdateService.class);
        Object updatedData = updateService.update(body);
        return new ResponseEntity<>(updatedData, HttpStatus.OK);
    }

    /**
     * Maneja las solicitudes DELETE para eliminar datos existentes.
     *
     * @param variable La variable de la ruta que identifica el tipo de datos.
     * @param id El ID del dato a eliminar.
     * @return Una respuesta HTTP con el objeto eliminado y el estado correspondiente.
     * @throws HttpException Si ocurre un error al procesar la solicitud.
     */
    @RequestMapping(value = "/{variable}/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMethod(@PathVariable("variable") String variable, @PathVariable("id") String id) throws HttpException {
        SimpleDeleteService<?> deleteService = this.beanServiceFinder.findUseCaseBeanByName(variable + SimpleCRUDUseCaseNames.DELETE.getUseCase(), SimpleDeleteService.class);
        Object deletedData = deleteService.delete(id);
        return new ResponseEntity<>(deletedData, HttpStatus.OK);
    }
}
