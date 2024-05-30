package de.htw.saplantservice.core.domain;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Testcontainers
public class PlantServiceTest {
    @Autowired
    private IPlantService plantService;
    private IPlantRepository plantRepository;

}
