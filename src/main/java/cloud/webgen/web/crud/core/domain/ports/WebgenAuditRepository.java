package cloud.webgen.web.crud.core.domain.ports;

import cloud.webgen.web.crud.core.domain.model.WebGenAuditModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface WebgenAuditRepository<T extends WebGenAuditModel> extends CrudRepository<T, String> {
    List<T> findAll(Pageable pageable);

}