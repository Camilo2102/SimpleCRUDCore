package cloud.webgen.web.crud.core.infrastructure.inbound.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("cloud.webgen.web.commons")
@ComponentScan("cloud.webgen.web.crud.core.infrastructure.outbound.database.adapter")
public class SimpleCrudConfig {
}
