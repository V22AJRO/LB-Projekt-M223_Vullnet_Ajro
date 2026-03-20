package ch.wiss.countryexplorer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.wiss.countryexplorer.model.ERole;
import ch.wiss.countryexplorer.model.Role;
import ch.wiss.countryexplorer.repository.RoleRepository;

@SpringBootApplication
public class CountryExplorerApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(CountryExplorerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(ERole.ROLE_USER));
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
        }
    }
}