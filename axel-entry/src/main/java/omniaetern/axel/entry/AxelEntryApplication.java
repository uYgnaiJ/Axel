package omniaetern.axel.entry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("omniaetern.axel.model")
@EnableJpaRepositories("omniaetern.axel.admin.repository")
@SpringBootApplication(scanBasePackages = "omniaetern.axel")
public class AxelEntryApplication {

    public static void main(String[] args) {
        SpringApplication.run(AxelEntryApplication.class, args);
    }

}