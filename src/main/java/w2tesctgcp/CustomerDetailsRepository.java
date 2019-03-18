package w2tesctgcp;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;





@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, String>{
	public CustomerDetails findByregMob(String regMob);
}
