package cloud.webgen.web.crud.core.infrastructure.inbound.utils;


import cloud.webgen.web.commons.exceptions.HttpException;
import cloud.webgen.web.commons.utils.BeanLocator;
import cloud.webgen.web.crud.core.domain.ports.WebgenAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class BeanProjectFinder {

    private final BeanLocator beanLocator;

    @Autowired
    public BeanProjectFinder(BeanLocator beanLocator) {
        this.beanLocator = beanLocator;
    }

    public <T> T findUseCaseBeanByName(String serviceName, Class<T> tClass) throws HttpException {
        try {
            return this.beanLocator.getBeanByString(serviceName, tClass);
        } catch (Exception e) {
            throw new HttpException("Not found", HttpStatus.NOT_FOUND);
        }
    }

    public WebgenAuditRepository<?> findRepository(String repositoryName) throws Exception {
        try {
            return this.beanLocator.getBeanByString(repositoryName, WebgenAuditRepository.class);
        } catch (Exception e) {
            throw new Exception("Not found");
        }
    }
}
