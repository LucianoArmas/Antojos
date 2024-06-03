package antojos.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "antojos.ecommerce")
public class EcommerceApplication {


	public static void main(String[] args) {
			SpringApplication.run(EcommerceApplication.class, args);
	}

}
