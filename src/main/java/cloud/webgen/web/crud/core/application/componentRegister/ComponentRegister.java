package cloud.webgen.web.crud.core.application.componentRegister;

import cloud.webgen.web.commons.utils.BeanLocator;
import cloud.webgen.web.commons.utils.StringUtils;
import cloud.webgen.web.crud.core.domain.annotations.AutoControlable;
import cloud.webgen.web.crud.core.domain.enums.SimpleCRUDMethods;
import cloud.webgen.web.crud.core.domain.ports.DocumentationService;
import cloud.webgen.web.crud.core.domain.ports.WebgenAuditRepository;
import cloud.webgen.web.crud.core.infrastructure.inbound.utils.BeanProjectFinder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class ComponentRegister {

    private final BeanLocator beanLocator;
    private final BeanProjectFinder beanProjectFinder;
    private final ApplicationContext applicationContext;
    private final DocumentationService documentationService;

    @PostConstruct
    public void init() throws Exception {
        log.info(applicationContext.getBeansOfType(WebgenAuditRepository.class).toString());

        DefaultListableBeanFactory beanFactory = this.beanLocator.getListableBean();
        Map<String, Object> models = this.beanLocator.getBeansWithAnnotation(AutoControlable.class);

        for (Map.Entry<String, Object> entry : models.entrySet()) {
            String key = entry.getKey().split("Model")[0];
            Object bean = entry.getValue();
            Class<?> beanClass = bean.getClass();

            AutoControlable autoControlableAnnotation = beanClass.getAnnotation(AutoControlable.class);
            String repositoryName = autoControlableAnnotation.repositoryName();

            String actualRepositoryName = !Objects.equals(repositoryName, "")
                    ? repositoryName
                    : StringUtils.firstLowerLetter(key) + "Repository";

            WebgenAuditRepository<?> repository = this.beanProjectFinder.findRepository(actualRepositoryName);

            SimpleCRUDMethods[] crudMethods = autoControlableAnnotation.availableMethods();

            for (SimpleCRUDMethods crudMethod : crudMethods) {
                BeanDefinition crudUseCase;
                if(crudMethod.isRequiredConversion()){
                    crudUseCase = BeanDefinitionBuilder
                            .genericBeanDefinition(crudMethod.getClazz())
                            .addConstructorArgValue(repository)
                            .addConstructorArgValue(beanClass)
                            .getBeanDefinition();
                } else {
                    crudUseCase = BeanDefinitionBuilder
                            .genericBeanDefinition(crudMethod.getClazz())
                            .addConstructorArgValue(repository)
                            .getBeanDefinition();
                }

                this.documentationService.generateDocumentation(crudMethod, key, beanClass);

                beanFactory.registerBeanDefinition(key + crudMethod.getName() + "Service", crudUseCase);
                log.info("Service created '{}' for repository '{}'",key + crudMethod.getName() + "Service", actualRepositoryName);
            }



        }
    }
}
